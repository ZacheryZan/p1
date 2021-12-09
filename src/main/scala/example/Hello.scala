/*
**      *********   *************    **                                       **      *********   *************    **                                       
**    ****    **** ** **   ** ***  ***                                        **    ****    **** ** **   ** ***  ***                                        
**  ** ***** ******* **   ** ********                                         **  ** ***** ******* **   ** ********                                         
****  **    **  *** **   ** ** ** **                                          ****  **    **  *** **   ** ** ** **                                          
**   ***** **   ** ******* **    **                                           **   ***** **   ** ******* **    **                                           
                                       **      *********   *************    **
                                       **    ****    **** ** **   ** ***  *** 
                                       **  ** ***** ******* **   ** ********  
                                       ****  **    **  *** **   ** ** ** **   
                                       **   ***** **   ** ******* **    **    
**      *********   *************    **                                       **      *********   *************    **                                       
**    ****    **** ** **   ** ***  ***                                        **    ****    **** ** **   ** ***  ***                                        
**  ** ***** ******* **   ** ********                                         **  ** ***** ******* **   ** ********                                         
****  **    **  *** **   ** ** ** **                                          ****  **    **  *** **   ** ** ** **                                          
**   ***** **   ** ******* **    **                                           **   ***** **   ** ******* **    ** 
                                       **      *********   *************    **
                                       **    ****    **** ** **   ** ***  *** 
                                       **  ** ***** ******* **   ** ********  
                                       ****  **    **  *** **   ** ** ** **   
                                       **   ***** **   ** ******* **    **                                           
0)[String] = 51
1)[String] = fdcIdContent
2)[String] =              -]fdcIdCategories_foods_Type -]foodNutrients_elements_nucleotides_fats and protiens
3)[String] = fdcIdContent
4)[String] = fdcIdContent
5)[String] = fdcIdContent
CODE IN PROGRESS
CREATE TABLE IN HIVE.
STILL TRYING TO ORGANIZE DATA
*/


package example

import java.io._
import scala.io.Source
import scala.io.StdIn.readLine
import scala.io.StdIn.readChar
import net.liftweb.json._


object Hello extends App 
{
  var strUser = -1
  var adminStatus =false
  case class Food(list:net.liftweb.json.JValue)
  case class Food1(fdcId:Int, desc:String, dataTypeL: String, pubDate: String, foodCode: Int, FoodNutrients: net.liftweb.json.JValue)
  case class Food2(number: Int, name: String, amount: Double, unitName: String)

  def logPrompt():Int=
    {
      val file = Source.fromFile("/home/maria_dev/zacheryFolder2/User_Credentials")
      var users = file.getLines().toList
      file.close()
      var str1=""
      var str2=""
      print("Username: ")
      var userName=readLine()
      print("Password: ")
      var password=readLine()
      if(users.contains(userName))
      {
        if(users(users.indexOf(userName)+1)==password)
        {
          return users.indexOf(userName)
        }
      }
      println("User/password was not found\nReturning to Main Menu")
      return -1
    }

  def user(userAuth: Boolean):Int=
    {
      var userauth = userAuth
      while(userauth)
      {
      println("1)Query\n" +
              "2)Update Credentials\n" +
              "3)Update Table\n" +
              " )Exit")
      readChar() match
        {
          case '1'=>HdfsDemo.selectAll();
          case '2'=>HdfsDemo.editUser(strUser);
          case '3'=>val file = Source.fromFile("/home/maria_dev/zacheryFolder2/User_Credentials")
                    var users = file.getLines().toList;
                    println("checking user credentials...")
                    if('a'==users(strUser+2).charAt(0))
                    {
                      println("Checks out!")
                      HdfsDemo.createFile();print("Just a moment... the ink is seeping in")
                      Thread.sleep(2000);println(" ...Done, File loaded")
                      HdfsDemo.createTable();println("Table foods Created");println("inserting values")
                      HdfsDemo.intoTable()
                    }
                    else
                    {
                      println("none admin credentials. Retuning to Menu...\n...")
                    }
          case '4'=>HdfsDemo.selectCount(readLine())
          case '5'=>HdfsDemo.dropTable()
          case _=> userauth = false
        }
      }
      return -1
    }
  
//START
  println("Welcome Main Menu")
  println("1)LOGIN")
  println("9)END")
  while(readChar()=='1')
  {
    strUser = logPrompt()
    strUser = user(strUser>(-1))
    println("1)LOGIN")
    println("9)END")
  }
//END
}

