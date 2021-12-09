package example

import java.io._
import java.io.IOException
import scala.io.Source
import scala.io.StdIn.readLine
import scala.io.StdIn.readChar
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
object HdfsDemo {

    val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/"
    def copyFromLocal():Unit=
        {
            val src = "file:///home/maria_dev/FoodDataCenter.csv"
            val target = path + "FoodDataCenter.csv"
            println(s"Copying local file $src to $target ...")

            val conf = new Configuration()
            val fs = FileSystem.get(conf)

            val localpath = new Path(src)
            val hdfspath = new Path(target)

            fs.copyFromLocalFile(false, localpath, hdfspath)
            println(s"Done copying local file $src to $target ...")
        }

    def createFile():Unit=
        {
            val filename = path + "FoodDataCenter.csv"
            println(s"Creating file $filename")

            val conf = new Configuration()
            val fs = FileSystem.get(conf)

            println("Checking if it already exists...")
            val filepath = new Path(filename)
            val isExisting = fs.exists(filepath)
            if(isExisting)
            {
                println("Yes it does exist. Deleting it...")
                fs.delete(filepath, false)
            }
        val output = fs.create(new Path(filename))

        val writer = new PrintWriter(output)
        writer.write(contentParse.retrieveWrite())
        writer.close()

        println(s"Done creating file $filename ...")
    }
    def query():Unit=
        {
            println("Completing Query...\n...")
            selectAll()
        }

    def editUser(userIndex:Int):Unit=
        {
            //val file = Source.fromFile("User_Credentials")
            val file = Source.fromFile("/home/maria_dev/zacheryFolder2/User_Credentials")
            var users = file.getLines().toArray
            print("Password: ")
            if(users(userIndex+1)==readLine())
            {
                println("Update...\n" +
                        "1)Password\n" +
                        "2)Username")
                readChar() match
                    {
                        case '1'=>
                            print("new Password: ")
                            users(userIndex+1)=readLine();
                            //val wfile= new File("User_Credentials")
                            val wfile= new File("/home/maria_dev/zacheryFolder2/User_Credentials")
                            val fw = new BufferedWriter(new FileWriter(wfile))
                            var i = 0;
                            while(i< users.length)
                            {
                                fw.write(users(i)+"\n")
                                i+=1
                            }
                            fw.close()

                        case '2'=>
                            print("new Username: ")
                            users(userIndex)=readLine();
                            //val wfile= new File("User_Credentials")
                            val wfile= new File("/home/maria_dev/zacheryFolder2/User_Credentials")
                            val fw = new BufferedWriter(new FileWriter(wfile))
                            var i = 0;
                            while(i< users.length)
                            {
                                fw.write(users(i)+"\n")
                                i+=1
                            }
                            fw.close()
                    }
            }
        }
    def selectAll():Unit=
        {
            var con: java.sql.Connection = null;
            var driverName = "org.apache.hive.jdbc.HiveDriver"
            val conStr = "jdbc:hive2://sandbox-hdp.hortonworks.com:10000/default"
            Class.forName(driverName)
            con = DriverManager.getConnection(conStr, "", "")
            val stmt = con.createStatement()
            println("Returning Table...\n...")
            Thread.sleep(1000)
            var res = stmt.executeQuery("select * from foods");
            while (res.next()) 
            {
               System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
            }
        }

    def selectCount(str:String):Unit=
        {
            var con: java.sql.Connection = null;
            var driverName = "org.apache.hive.jdbc.HiveDriver"
            val conStr = "jdbc:hive2://sandbox-hdp.hortonworks.com:10000/default"
            Class.forName(driverName)
            con = DriverManager.getConnection(conStr, "", "")
            val stmt = con.createStatement()
            println("Returning Table...\n...")
            Thread.sleep(1000)
            var res = stmt.executeQuery(s"SELCET $str from foods");
            while(res.next())
            {
               System.out.println(String.valueOf(res.getString(1)));
            }
        }

    def dropTable():Unit=
        {
            var con: java.sql.Connection = null;
            var driverName = "org.apache.hive.jdbc.HiveDriver"
            val conStr = "jdbc:hive2://sandbox-hdp.hortonworks.com:10000/default";
            Class.forName(driverName)
            con = DriverManager.getConnection(conStr, "", "")
            val stmt = con.createStatement();

            stmt.execute("DROP TABLE foods")

        }


    def createTable():Unit=
        {
            var con: java.sql.Connection = null;
            var driverName = "org.apache.hive.jdbc.HiveDriver"
            val conStr = "jdbc:hive2://sandbox-hdp.hortonworks.com:10000/default";
            Class.forName(driverName)
            con = DriverManager.getConnection(conStr, "", "")
            val stmt = con.createStatement();

            println(s"Creating TABLE...")
            stmt.execute("CREATE TABLE IF NOT EXISTS foods" +
              " (fdcId string, descript string, type string, pubDate string, code string)" +
              " row format delimited fields terminated by ','")
        }

    def intoTable():Unit=
        {
            var con: java.sql.Connection = null;
            var driverName = "org.apache.hive.jdbc.HiveDriver"
            val conStr = "jdbc:hive2://sandbox-hdp.hortonworks.com:10000/default";
            Class.forName(driverName)
            con = DriverManager.getConnection(conStr, "", "")
            val stmt = con.createStatement();

            println("INSERTING...")
            stmt.execute("load data inpath '/user/maria_dev/FoodDataCenter.csv' into table foods");
            con = null;
            con.close()

        }
}
