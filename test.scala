package examples
import java.io.PrintWriter
import scala.io.Source
import org.apache.spark.{Accumulator, SparkConf, SparkContext}
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._
import org.apache.log4j._
import scala.collection.mutable.{ArrayBuffer, Map}
import org.apache.spark.sql.SQLContext
 import scala.util.Random
import scala.collection.mutable.HashMap 
object test {
  

    var g1=List[String]()  
  var vecLen, method :Int = 0
  var rate, margin :Double = 0.0
  var relation2id, entity2id = Map[String,String]()
  var id2entity, id2relation = Map[String,String]()
  var left_entity, right_entity = Map[String,Map[String,String]]()
  var fb_h, fb_r, fb_l = ArrayBuffer[String]()
  var ok = Map[Pair[String,String],Map[String,String]]()
  var relation_vec = Array[Array[String]]()
  var entity_vec = Array[Array[String]]()
  var version :String = ""
  var entity_num, relation_num, train_num :Int = 0
  val RAND_MAX = 0x7fffffff
  val pi = 3.1415926535897932384626433832795
  var random = new util.Random

  var nepoch,nbatches,batchsize :Int =1
  var res = 0.0
  var samples = ArrayBuffer[(String,String,String)]()

  var L1_flag = true

  def prepare(EntityAndId: List[(String,String)],
              RelationAndId: List[(String,String)],
              Traindata: List[(String,String,String)]): Unit = {
println(0)
    EntityAndId.foreach{
      tuple =>
        entity2id(tuple._1) = tuple._2
        id2entity(tuple._2) = tuple._1
    }
println(0)
    RelationAndId.foreach{
      tuple =>
        relation2id(tuple._1) = tuple._2
        id2relation(tuple._2) = tuple._1
    }
println(0)
    entity_num = EntityAndId.length
    relation_num = RelationAndId.length

    println("entity_num:"+entity_num) 
    println("relation_num:"+relation_num)
println(0)
//Traindata.foreach{
//      re =>
//        println(entity2id(re._1))
//        println(relation2id(re._2))
//        println(entity2id(re._3))
//        
//        }

    Traindata.foreach{
     
  re =>
//      println(re)
        if(!left_entity.contains(relation2id(re._2)))
          left_entity(relation2id(re._2)) = Map[String,String]()
//          
//      println(left_entity.contains(relation2id(re._2)))
//          println(left_entity(relation2id(re._2)))    
        if(!left_entity(relation2id(re._2)).contains(entity2id(re._1)))
          left_entity(relation2id(re._2))(entity2id(re._1)) = "0"
        left_entity(relation2id(re._2))(entity2id(re._1)) += 1
//println(!left_entity(relation2id(re._2)).contains(entity2id(re._1)))
        if(!right_entity.contains(relation2id(re._2)))
          right_entity(relation2id(re._2)) = Map[String,String]()
        if(!right_entity(relation2id(re._2)).contains(entity2id(re._3)))
          right_entity(relation2id(re._2))(entity2id(re._3)) = "0"
        right_entity(relation2id(re._2))(entity2id(re._3)) += 1
     
        fb_h += re._1
                 
        fb_l += re._2
        fb_r += re._3
        samples += ((fb_h.last,fb_l.last,fb_r.last))
//      

        if(!ok.contains((entity2id(re._1),relation2id(re._2))))
          ok((entity2id(re._1),relation2id(re._2))) = Map[String,String]()
        ok((entity2id(re._1),relation2id(re._2)))(entity2id(re._3)) = "1"

    }
 println(samples)
 println(samples.length)
 
    train_num = fb_l.length
    println("traindata num:"+train_num)

  }

  def train(vecLen:Int,rate:Double,margin:Double,method:Int,sc:SparkContext): Unit = {
    this.vecLen = vecLen
    this.rate = rate
    this.margin = margin
    this.method = method
println(vecLen)
println(entity_num)
println(relation_num)
    entity_vec = Array.ofDim[String](entity_num,vecLen)
    relation_vec = Array.ofDim[String](relation_num,vecLen)
    var ac=entity_vec.toList

    entity_vec.foreach{
      r =>

        for(i <- r.indices)
          r(i) =( randn(0, 1.0/vecLen, -6/math.sqrt(vecLen), 6/math.sqrt(vecLen))).toString()
        //  
 
    }
  var a:Int=0
  
    relation_vec.foreach{
      r =>
        for(i <- r.indices)
          r(i) = (randn(0, 1.0/vecLen, -6/math.sqrt(vecLen), 6/math.sqrt(vecLen))).toString()
//
 
          norm(r)

    }
println(relation_vec.toList.length)  
   sgd(sc)
 
  }
  

  def vec_len(a: Array[String]): Double = {
    var c=List[Double]()
  a.foreach(b=> c=b.toDouble::c
      )
    var res = 0.0
    var f=Array[Double]()
   
    c.foreach(x=> res  = res+ x*x
        )
    res = math.sqrt(res)
    res
  }
  def norm(a: Array[String]): Unit = {
  var c=List[Double]()
  a.foreach(b=> c=b.toDouble::c
      )
  c.toArray
  
    val x = vec_len(a)
   
    if(x > 1)
      for(i <- a.indices)
        a(i) = (a(i).toDouble/x).toString()
   
  }
//
  def rand(min: Double, max: Double): Double = {
    min + (max-min)*util.Random.nextInt()/(RAND_MAX+1.0)
 
  }

  def normal(x: Double, miu: Double, sigma:Double ): Double = {
    1.0/math.sqrt(2*pi)/sigma*math.exp(-1*(x-miu)*(x-miu)/(2*sigma*sigma))
  }
//
  def randn(miu: Double, sigma: Double, min: Double, max: Double): Double = {
    var x,y,dScope :Double = 0.0
    do{
      x = rand(min,max)
      y = normal(x,miu,sigma)
      dScope = rand(0.0,normal(miu,miu,sigma))
    }while(dScope > y)
    x
  }

  def randMax(x:Int): Int = {
    var res = (random.nextInt()*random.nextInt())%x
    while(res<0)
      res += x
    res
  }
//
  def gradient(h1:String,l1:String,r1:String,h2:String,l2:String,r2:String,
               entityMap:Map[String,Array[String]],
               relationMap:Map[String,Array[String]]): Unit = {
    for(i <- 0 until vecLen){
      
    
    var delta =  2*((entityMap(l1)(i)).toInt-(relationMap(r1)(i)).toInt-(entityMap(h1)(i)).toInt)
//    
      if(L1_flag)
        if(delta > 0)
          delta = 1
        else
          delta = -1
      relationMap(r1)(i) += rate*delta
      entityMap(h1)(i) += rate*delta
      entityMap(l1)(i) += rate*delta
////
      delta = 2*((entityMap(l2)(i)).toInt-(relationMap(r2)(i)).toInt-(entityMap(h2)(i)).toInt)
      if(L1_flag)
        if(delta > 0)
          delta = 1
        else
          delta = -1
      relationMap(r2)(i) += rate*delta
      entityMap(h2)(i) += rate*delta
      entityMap(l2)(i) += rate*delta
    }

  }

  def train_kb(h1:String,l1:String,r1:String,h2:String,l2:String,r2:String,
               entityMap:Map[String,Array[String]],
               relationMap:Map[String,Array[String]],
               ac_res:Accumulator[Double]): Unit = {
    val sum1 = calc_distance(h1,l1,r1,entityMap,relationMap)
    val sum2 = calc_distance(h2,l2,r2,entityMap,relationMap)
    if(sum1 + margin > sum2){
      ac_res += margin.toInt + sum1.toInt - sum2.toInt
      gradient(h1,l1,r1,h2,l2,r2,entityMap,relationMap)
    }
  }

  def calc_distance(h:String, l:String, r:String,
                    entityMap:Map[String,Array[String]],
                    relationMap:Map[String,Array[String]]): String = {
    var sum = "0.0"
//    if(L1_flag)
//      for(i <- 0 until vecLen)
//        sum = math.abs(entityMap(l)(i)-relationMap(r)(i)-entityMap(h)(i))
//    else
//      for(i <- 0 until vecLen)
//        sum = math.pow(entityMap(l)(i)-relationMap(r)(i)-entityMap(h)(i),2)
    sum
 // }

  }
  def sgd(sc:SparkContext): Unit = {

    val samplesRDD = sc.parallelize(samples).persist()      

    val bcOK = sc.broadcast(ok)
    for(epoch <- 0 until nepoch){
      res = 0
      val ac_res = sc.accumulator[Double](0.0)
      var start = System.currentTimeMillis()
      for(batch <- 0 until nbatches){
        val bcRelation_vec = sc.broadcast(relation_vec)
      //  println(bcRelation_vec)
        val bcEntity_vec = sc.broadcast(entity_vec)
// println(bcEntity_vec)
        var sampledRDD = samplesRDD
          .sample(withReplacement = true, batchsize/train_num.toDouble, System.currentTimeMillis())
        println("num partition:"+sampledRDD.getNumPartitions)

        val vecRDD = sampledRDD.mapPartitions{



 iter => 
            var EntityVecMap = Map[String,Array[String]]()
            var RelationVecMap = Map[String,Array[String]]()
            var VecMap = ArrayBuffer[(Map[String,Array[String]],Map[String,Array[String]])]()
//}
//            //

//            println(bcEntity_vec)       
            iter.foreach{
 
              p=>
                if(!EntityVecMap.contains(p._1))
                 
                  EntityVecMap(p._1) = bcEntity_vec.value((p._1).toInt)
                if(!EntityVecMap.contains(p._3))
                  EntityVecMap(p._3) = bcEntity_vec.value((p._3).toInt)
                if(!RelationVecMap.contains(p._2))
                  RelationVecMap(p._2) = bcRelation_vec.value((p._2).toInt)
            
                var j = randMax(entity_num)

                if(random.nextInt()%1000 < 500){
                  while(if(bcOK.value.contains((p._1,p._3)))bcOK.value((p._1,p._3)).contains((j).toString())else false)
                    j = randMax(entity_num)
                  if(!EntityVecMap.contains((j).toString()))
                    EntityVecMap((j).toString()) = bcEntity_vec.value(j)
                  train_kb(p._1,p._2,p._3,p._1,(j).toString(),p._3,EntityVecMap,RelationVecMap,ac_res)
                }
                else{
                  while(if(bcOK.value.contains(((j).toString(),p._3)))bcOK.value(((j).toString(),p._3)).contains(p._2)else false)
                    j = randMax(entity_num)
                  if(!EntityVecMap.contains((j).toString()))
                    EntityVecMap((j).toString()) = bcEntity_vec.value(j)
                  train_kb(p._1,p._2,p._3,j.toString(),p._2,p._3,EntityVecMap,RelationVecMap,ac_res)
                }
                println(0)
                    println(EntityVecMap(p._1))
                
                    norm(RelationVecMap(p._3))
                norm(EntityVecMap(p._1))
                norm(EntityVecMap(p._2))
                norm(EntityVecMap(j.toString()))
            
        
            
        }      
             VecMap += ((EntityVecMap,RelationVecMap))
            VecMap.iterator
            
        }
 
     //   val vecMap = vecRDD.collect()
        //vecMap.foreach{
//          p=>
//            p._1.foreach{
//              kv=>
//                entity_vec(kv._1) = kv._2
//            }
//            p._2.foreach{
//              kv=>
//                relation_vec((kv._1).toInt) = kv._2
//            }
//        }
      }
////
      var end = System.currentTimeMillis()
      println("epoch"+epoch+"  res:"+ac_res.value)
      println("epoch"+epoch+" cost time:"+(end-start)/1000.0)

      val out = new PrintWriter("relation2vec."+version)
      val out2 = new PrintWriter("entity2vec."+version)
      
      relation_vec.foreach{
        a =>
      
          a.foreach{
            x=>
      //println(x.toString())
              out.print(x)
              out.print("\t")
          }
          out.println()
      }
      entity_vec.foreach{
        a =>
          a.foreach{
            x=>
              out2.print(x)
              out2.print("\t")
             
          }
          out2.println()
      }
      out.close()
      out2.close()
    }
  }


  def main(args: Array[String]): Unit = {

    var start = System.currentTimeMillis()
       var value123=List(List[String]())
       var use=List(List[String]())
      var bd1=List[String]()
      var bd2=List[String]()
      var bd3=List[String]()
      var bd4=List[String]()
      
         var ab=List[String]()
    var ac:String=""
       var c1:String=""
println("0")
 Logger.getLogger("org").setLevel(Level.ERROR)
       
   println("0")
	val sc=new SparkContext("local[*]","test")
   

    println("0")
//    val f1 = sc.textFile("/Users/zhangdenghui/TransE/FB15k/entity2id.txt")
//    val f2 = sc.textFile("/Users/zhangdenghui/TransE/FB15k/relation2id.txt")
    val f1 = sc.textFile("C:/Users/HP/Desktop/entity2id.txt")
    
    val f2 = sc.textFile("C:/Users/HP/Desktop/relation2id.txt")
     var Train= List[(String,String,String)]()  
    for (line <- Source.fromFile("D:/scala-SDK-4.7.0-vfinal-2.12-win32.win32.x86_64/uml.txt").getLines)
      {
        bd1=List(line)
        value123=bd1 :: value123
         val a=bd1.mkString(",")

        val c=a.split(",")
        val b1=c.toList 
        bd2=b1(0)::bd2
        bd3=b1(1)::bd3
        bd4=b1(2)::bd4
        Train=(b1(0),b1(1),b1(2))::Train
      }

           val d= bd2.distinct
    val e1= bd3.distinct
    val f12= bd4.distinct
    // combine head and Tail
    g1=(d:::f12).distinct
       println(g1)
      println(g1.length)
                      
        var l=List[Float]()
       var e=List[Float]()
     var one1:Float=  1f
       var  two1:Float=1.2f
// give the random value to link  
       val random = new Random
  
 for(i <- 0 to e1.length-1)
            {
   
 val r=scala.util.Random
    var abcd:Float=r.nextFloat() * (two1 - one1)+one1;
    
  // l<-l/||l|| for  each l belong to L
 val result = abcd/(abcd).abs
        
l=result::l  

            }
//      println(l.length)
    
  // give the random value to head and tail     
    
      for(i <- 0 to g1.length-1)
            {
        
        
          val r=scala.util.Random
               var abcd:Float=r.nextFloat() * (two1 - one1)+one1;
      
            e=abcd::e
            }
      var EntityAndId= List[(String,String)]()
      
      var RelationAndId= List[(String,String)]()
 
        
      for(i <- 0 to g1.length-1)
            {
  EntityAndId=(g1(i),e(i).toString())::EntityAndId
            }
    
    
    //  println(EntityAndId)
    println(g1.length)
      println(e1.length)
    for(i <- 0 to e1.length-1)
            {
      RelationAndId=(e1(i),l(i).toString())::RelationAndId
            }
println(RelationAndId)
//    val f3 = sc.textFile("/Users/zhangdenghui/TransE/FB15k/train.txt")

    val f3 = sc.textFile("C:/Users/HP/Desktop/train.txt")
    val Traindata = f3.map{
      line =>
      val re = line.split("\t")
      (re(0),re(1),re(2)) }.collect()
//
//
  //  println(Train)
   prepare(EntityAndId.toList,RelationAndId.toList,Train.toList)
    var end = System.currentTimeMillis()
    println("prepare time:"+(end-start)/1000.0)

    var vecLen :Int = 100
    var method :Int = 0
    var rate :Double = 0.001
    var margin : Int = 1
    random.setSeed(System.currentTimeMillis())

    if (method==1)
      version = "bern";
    else
      version = "unif";

    nepoch = 10
    nbatches = 2
    batchsize = train_num / nbatches
  
    start = System.currentTimeMillis()
 train(vecLen,rate,margin,method,sc)
    end = System.currentTimeMillis()
    println("train time:"+(end-start)/1000.0)

  }
}