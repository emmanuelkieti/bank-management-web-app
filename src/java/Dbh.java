import java.sql.*;
public class Dbh {
  private Dbh(){}
  public static Connection getDbConnection(){
    Connection dbConnect = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      dbConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/obank","root","");
    } catch(Exception e){
      //e.printStackTrace();
    }
    return dbConnect;
  }
}