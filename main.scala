import scala.collection.mutable.HashMap 

object main {
  def main(args: Array[String]): Unit={
    
      var S  =  List(List("BILL","PRESIDENTOF","AL"),  List("AL","PARTY","DEN"),  List("JOHN","PARTY","DEN"),  List("JOHN","PRESIDENTOF","LYNDON1"),  List("LYNDON1","PARTY","DEN"))
      var mape=HashMap(("BILL"-> - 4.242f),("AL" -> - 3.535f),("JOHN" -> - 3.535f),("LYNDON1"-> - 2.121f),("DEN" -> 1.414f ))
      var mapl=HashMap(("PARTY"-> - 2.121f),("PRESIDENTOF" -> - 4.242f ))
      var sbatch  =  List(List("BILL","PRESIDENTOF","AL"),List("JOHN","PARTY","DEN"),List("JOHN","PRESIDENTOF","LYNDON1"))
      var bd=List[String]()
      var value12=List(List[Float]())
      var value1=List(List[Float]())
            var c:String=""
            var f:String=""
            var g:String=""
            var wr=List(List[String]())
            var one:Float=0
            var two:Float=0
            var three:Float=0
            def remove(num: List[String], list: List[String]) = list diff List(num)
              mape.foreach  
               {   
                 case (key, value) =>// println (key + " -> " + value)          
                  c=key
                  one= mape(key)  
                    mape.foreach  
                     {   
                      case (key, value) =>// println (key + " -> " + value) 
                      f=key
                      two=mape(key)        
                        if(c!=f){
                         mapl.foreach  
                           {
                               case (key, value) =>// println (key + " -> " + value)          
                               g=key
                               bd =List(c,g,f)
                               wr = bd  ::  wr
                               wr.distinct
                               
                                S.foreach{
                                   case (a)=>
                                        if(S.contains(a)){
                                        var v=wr.indexOf(a)
                                        val trunced = wr.take(v) ++ wr.drop(v + 1)
                                        wr.drop(v)      
                                        wr=trunced
                    
                                           }
                           
                     
                                       }
            
                                    if(key== wr(0)(1)){
                                      three=mapl(key)
                                      var abcd:Float=one + three  - two
                                      value12=List(abcd.toFloat)::value12 
                                             }
                                   else  if(key== wr(0)(1)){
                                      three=mapl(key)
                                      var abcd:Float=one + three  - two
                                      value12=List(abcd.toFloat)::value12      
                                             }
                                             }
                                             }
                                             } 
                                             }
    
       value12= value12.dropRight(1)
       wr= wr.dropRight(1)
       val result2 = value12.flatten.max
       val result12 =value12.flatten.indexOf(result2)
       println(wr)
       println(value12) 
       println(wr(result12))
       
   }  

}
