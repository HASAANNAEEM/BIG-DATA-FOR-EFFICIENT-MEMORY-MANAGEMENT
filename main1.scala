

object main1 {
    def main(args: Array[String] ): Unit={
   
   
   var S=List(List("BILL","PRESIDENTOF","AL"),List("AL","PARTY","DEN"),List("JOHN","PARTY","DEN"),List("JOHN","PRESIDENTOF","LYNDON1"),List("LYNDON1","PARTY","DEN"))
 
  var E=Array("BILL","AL","JOHN","LYNDON1","DEN")
   var e=Array[Float](- 4.242f,- 3.535f,- 2.828f,- 2.121f, 1.414f)
    var l=Array[Float](- 2.121f,- 4.242f)
    var L=Array("PARTY","PRESIDENTOF")

   
   
    var sbatch=List(List("BILL","PRESIDENTOF","AL"),List("JOHN","PARTY","DEN"),List("JOHN","PRESIDENTOF","LYNDON1"))
    var tbatch=""
   var bd=List[String]()
   var value=List(List[Float]())
     var value1=List(List[Float]())
        def remove(num: List[String], list: List[String]) = list diff List(num)

   for(i <- 0 to 2){
     for(j <- 0 to 4){
     if(S(j)==sbatch(i)){
       var wr=List(List[String]())
       val ab = List(sbatch(i))
         for(d <- 0 to 2){
      var c=E(d)
    for(p <- 2 to 4){      
      var f=E(p)
      if((c != f)){
      bd =List(c,ab(0)(1),f)
  
      
  
      
       if(bd != S(j) ){
    val r=S(1)
        if(bd==List("AL","PARTY","DEN")){
      remove(List("AL","PARTY","DEN"),bd)
     
      }
        else if(bd==List("JOHN","PRESIDENTOF","LYNDON1")){
      remove(List("JOHN","PRESIDENTOF","LYNDON1"),bd)
     
      }
        else {
   wr = bd  ::  wr
        
        

    
   
      
 var one:Float=0
   var two:Float=0
    
   for(absdf <- 0 to 4){
      
      if(E(absdf)== wr(0)(0)){
          one=e(absdf) 
    //     println(one)
        
     }
       if(E(absdf)== wr(0)(2)){
         two=e(absdf)  
      //    println(two)
    
      }   
       }
  
    if(L(0)== wr(0)(1)){
           var abcd:Float=one +l(0) - two
        // println(abcd)
             value=List(abcd.toFloat) :: value
           
          }
      if(L(1)== wr(0)(1)){
            var abcd:Float=one +l(1) - two
          // println(abcd)
            value=List(abcd.toFloat) :: value
      
           }
       
      
    }
      }
    
    }
    }
         }
    // value1 = value.filter(List(),value)
     value= value.dropRight(1)
     
      wr=wr.dropRight(1)
      


    println(wr.distinct)
     println(value)
      val result2 = value.flatten.min
     
      
     println(result2)
      var value2=List(List[Float]())
      value=value2
     
  
     }
     }
   
     
   }
    
    }   
}