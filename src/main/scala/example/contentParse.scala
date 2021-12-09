package example

import java.io._
import scala.io.Source
import scala.io.StdIn.readLine
import scala.io.StdIn.readChar
import net.liftweb.json._


object contentParse {
  
 def writeLine1(str:String):String=
    {
      var str1="";
      var str2="";
      str1 = str.dropRight(str.length() - str.indexOf('['))
      str2 = str.drop(str.indexOf('['))
      var arr = str1.split(':')
      var fdcId = arr(0).dropRight(14).toInt;
      var description = arr(1).dropRight(11).filterNot(_==',').filterNot(_=='"');
      var dataType= arr(2).dropRight(18).filterNot(_=='"');
      var pubDate = arr(3).dropRight(arr(3).length() - arr(3).indexOf(',')).filterNot(_=='"');
      var foodCode= arr(4).dropRight(arr(4).length - arr(4).indexOf(',')).filterNot(_=='"');
      return s"$fdcId,$description,$dataType,$pubDate,$foodCode\n"
    }
 def writeLine(str:String):String=
    {
      var str1="";
      var str2="";
      str1 = str.dropRight(str.length() - str.indexOf('['))
      str2 = str.drop(str.indexOf('['))
      var arr = str1.split(':')
//var fdcId = arr(0).dropRight(arr(0).length - arr(0).indexOf(','))
      var fdcId = arr(0).dropRight(14).toInt;
      var description = arr(1).dropRight(11).filterNot(_==',').filterNot(_=='"');
      var dataType= arr(2).dropRight(18).filterNot(_=='"');
      var pubDate = arr(3).dropRight(11).filterNot(_=='"');
      var foodCode= arr(4).dropRight(arr(4).length - arr(4).indexOf(',') - 1).filterNot(_=='"');
      var foodNutrient= str.drop(str.indexOf('[')).dropRight(4);
      return s"$fdcId,$description,$dataType,$pubDate,$foodCode,$foodNutrient,"

    }  

 def retrieveWrite():String=
    {
      val url = scala.io.Source.fromURL("https://api.nal.usda.gov/fdc/v1/foods/list?api_key=QN8O0zaQhKIooFDOWv7ZJDjED79XljEm19NMoqye")
      //val wfile = new File("/home/maria_dev/zacheryFolder2/FoodDataCenter.csv")
      //val wfile= new File("FoodDataCenter.csv")
      //val fw = new BufferedWriter(new FileWriter(wfile))
      println("File Opened...")
      val ar = url.getLines().mkString.split("fdcId\":")
      var e = 1
      
      var lstring =""
      while(e< ar.length)
      {
        lstring = lstring + writeLine1(ar(e))
        e+=1
      }
      
      Thread.sleep(2000)
      url.close()
      return lstring
    }
}
