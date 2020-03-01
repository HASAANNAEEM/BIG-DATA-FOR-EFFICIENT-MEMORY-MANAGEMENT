


 


  


 import scala.util.Random
import scala.collection.mutable.HashMap 
import scala.io.Source
import collection.mutable

object main6 {
  
 def main(args: Array[String]): Unit={
      var value123=List(List[String]())
       var use=List(List[String]())
      var bd1=List[String]()
      var bd2=List[String]()
      var bd3=List[String]()
      var bd4=List[String]()
      var g1=List[String]()
         var ab=List[String]()
    var ac:String=""
       var c1:String=""
          val hashmapfinal = HashMap[String, String]()
          var one2:Float=0

     for (line <- Source.fromFile("D:/scala-SDK-4.7.0-vfinal-2.12-win32.win32.x86_64/uml.txt").getLines)
      {
        bd1=List(line)
        value123=bd1 :: value123
     

     }
       val random = new Random
  var b=List(List[String]()) 
 for(i <- 0 to 20)
            {
   
  
ab=Random.shuffle(value123).head 

b=ab::b

    
            }
  for(i <- 0 to 20)
            {   
    val a=b(i).mkString(",")

        val c=a.split(",")
        val b1=c.toList 
        bd2=b1(0)::bd2
        bd3=b1(1)::bd3
        bd4=b1(2)::bd4
  
            }
  
      println(b.length)
        
      // println(value123)
       val d= bd2.distinct
    // println(d)
        println(d)
       val e1= bd3.distinct
    // println(e1)
       val f1= bd4.distinct
     println(f1)
      
       g1=(d:::f1).distinct
       println(g1)
       var value1234=List[Float]()
       var value12345=List[Float]()
  //    println(bd1)
       var one1:Float= - 1.2f
       var  two1:Float=1.2f
       val hashmap = HashMap[String, String]()
       val length:Int=g1.length
  // println(length)
          for(i <- 0 to length)
            {
               val r=scala.util.Random
               var abcd:Float=r.nextFloat() * (two1 - one1)+one1;
               value1234=abcd::value1234
            }
                value1234= value1234.dropRight(1)
                println(value1234.length)
                val length1:Int=value1234.length
  
                 for(j <- 0 to length1-1) 
                  {
                   var d=g1(j)
                   var d1=value1234(j).toString()
                   hashmap+=(d ->d1)
                  }
                  println(hashmap)

                 val hashmap1 = HashMap[String, String]()
                  val hashmapoutput = HashMap[String, String]()
                 val length2:Int=e1.length
  // println(length2)
                    for(i <- 0 to length2)
                    {
                      val r=scala.util.Random
                      var abcd:Float=r.nextFloat() * (two1 - one1)+one1;
//println(abcd)
                      value12345=abcd::value12345

                    }
                        value12345= value12345.dropRight(1)
                        val length4:Int=value12345.length
                       //   println(length4)
                           for(j <- 0 to length4-1)
                           { 
                             var d=e1(j)
                             var d1=value12345(j).toString()
                             hashmap1+=(d ->d1)
                           }
                          println(hashmap1)
            
                          for(k <- 0 to f1.length-1)
                           { 
                           hashmap.foreach{
                   case (key, value) =>// println (key + " -> " + value)          
                  c1=key
                   
                  if(c1==f1(k)){
                     one2= (hashmap(key)).toFloat 
                      var d1=one2.toString()
                      hashmapfinal+=(c1 ->d1)
                     
                  }
                  }
                
                           } 
                            println(hashmapfinal)
 
      var S  =b
      var mape=hashmap
      var mapl=hashmap1
      var bd=List[String]()
      var value12=List(List[Float]())
       var valueempty=List(List[Float]())
      var valuefinal=List(List[Float]())
      var value1=List(List[Float]())
      
            var c:String=""
            var f:String=""
            var g:String=""
            var wr=List(List[String]())
              var wrfinal=List(List[String]())
               var wrempty=List(List[String]())
            var one:Float=0
            var two:Float=0
            var three:Float=0
       
          
            def remove(num: List[String], list: List[String]) = list diff List(num)
        
              hashmapfinal.foreach  
               {   
                 case (key, value) =>// println (key + " -> " + value)          
                  c=key
                  one= (mape(key)).toFloat 
                mapl.foreach  
                           {
                    case (key, value) =>// println (key + " -> " + value)          
                               g=key
                    
                    mape.foreach  
                     {   
                      case (key, value) =>// println (key + " -> " + value) 
                      f=key
                      two=(mape(key)).toFloat  
                     
                        if(c!=f){
                         
                               
                               bd =List(f,g,c)
                               wr = bd  ::  wr
                          
                        
                                S.foreach{
                                   case (a)=>
                                        if(S.contains(a)){
                                        var v=wr.indexOf(a)
                                        val trunced = wr.take(v) ++ wr.drop(v + 1)
                                        wr.drop(v)      
                                        wr=trunced
                    
                                           }
                                        
                               }
                               
                        
                     
                              
                                 if(g== wr(0)(1)){
                                      three=(mapl(g)).toFloat
                                      var abcd:Float=two + three  -one
                                      value12=List(abcd.toFloat)::value12 
                                             }
                    
                        }          
//                                 else  if(key== wr(0)(1)){
//                            three=(mapl(key)).toFloat
//                                      var abcd:Float=two + three  -one
//                                      value12=List(abcd.toFloat)::value12      
//                                           }
                        }
                               
                                wr= wr.dropRight(1)
                                  value12= value12.dropRight(1)
                               
                               val a=value12.flatten.min
                           
                               
                                 val result123 =value12.flatten.indexOf(a)
                           
                             val cd=wr(result123)
                             val cf=cd
                             wrfinal=cf::wrfinal 
                             wr=wrempty
                            valuefinal=List(a)::valuefinal
                           
                  value12=valueempty
                    
                           }
               
                  
                                               }
                            valuefinal= valuefinal.dropRight(1)
                        
                       wrfinal=wrfinal.dropRight(1)
                         
                                   val result2 = valuefinal.flatten.min
                                   val result12 =valuefinal.flatten.indexOf(result2)
                             
                                    println(valuefinal)
                                     println(wrfinal)
                                    println(wrfinal.length)
                                    
                                      println(valuefinal.length)
                                 println(result2)
                                 println(result12)
                                 println(wrfinal(result12))
//    val wr1= wr.flatten
//    val value1231= value123.flatten
//                                  for(j <- 0 to value123.length-2)
//                           { 
//                                    
//                                    hashmapoutput+=(wr1(j) ->value1231(j))
//                                    
//                           }
//                                   println(hashmapoutput)
                           }
   }  

  

