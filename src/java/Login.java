import java.io.*;
import java.sql.*;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Login extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    try {
      response.setContentType("text/html");

    PrintWriter r = response.getWriter();
    Header header = new Header("Account", "./assets/css/open-account.css");//get header
    r.print(header.headString());
    r.print(header.headerString());
    r.print(header.navString());
    r.print(header.renderNavString());
    r.print("<p>To Access more services like loans, please login to your account.</p>");
    r.print("<main><div class='login-form'></div></main>");
    r.print("<script src='./assets/js/login.js'></script>");

    r.close();
    }catch(Exception e){
      //e.printStackTrace();
    }
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    Connection dbConnect = Dbh.getDbConnection();
    try {
      response.setContentType("text/html");
    PrintWriter r = response.getWriter();

    String accNumber = request.getParameter("accNumber");
    String email = request.getParameter("email");

    //get database email and accountNumber values
    Statement stmt = dbConnect.createStatement();
    ResultSet dbEmail = stmt.executeQuery("select * from customers where email="+"'"+email+"'");
    if(dbEmail.next()) {
      int nationalId = dbEmail.getInt("customerId");
      ResultSet dbAccountRow = stmt.executeQuery("select * from accounts where ownerId="+nationalId);
      dbAccountRow.next();
      String dbAccount = "OB"+dbAccountRow.getString("accountNumber");
      if(dbAccount.equals(accNumber)) {
        HttpSession session = request.getSession();
        session.setAttribute("national",""+nationalId);
        session.setAttribute("accountBalance",dbAccountRow.getString("balance"));
        session.setAttribute("accName",dbAccountRow.getString("accountNumber"));
        r.print("<script>location.assign('/obank/home')</script>");
      } else {
        String location = "/obank/login?login=incorrect";
        r.print("<script>location.assign("+"'"+location+"'"+")</script>");
      }
    } else {
      String location = "/obank/login?login=incorrect";
      r.print("<script>location.assign("+"'"+location+"'"+")</script>");
    }
    }catch(Exception e){
      //e.printStackTrace();
    } finally {
      try{
        dbConnect.close(); 
      } catch(Exception e) {
        //
      }
    }
  }
}