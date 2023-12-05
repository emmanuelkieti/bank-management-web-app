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

public class CheckBalance extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    try{
      response.setContentType("text/html");

    PrintWriter r = response.getWriter();
    Header header = new Header("Account", "./assets/css/open-account.css");//get header
    r.print(header.headString());
    r.print(header.sessionHeader());
    r.print(header.sessionNav());
    r.print(header.renderNavString());

    Connection dbConnect = Dbh.getDbConnection();
    HttpSession session = request.getSession(false);
    Statement stmt = dbConnect.createStatement();
    int nationalId = Integer.parseInt((String)session.getAttribute("national"));   
    ResultSet accStatus = stmt.executeQuery("select a.accountNumber, a.accountName, a.dateCreated, a.balance,c.email,a.ownerId from accounts "+
      "as a inner join customers as c on c.customerId = a.ownerId where a.ownerId ="+nationalId);
    accStatus.next();
    r.print("<table><tr><th colspan='5'>Account status</th></tr>"+
    "<tr><th>Account Number</th><th>Account Name</th><th>Date Created</th><th>Balance</th>"+
    "<th>Email</th></tr><tr><td>OB"+accStatus.getString("accountNumber")+"</td><td>"+accStatus.getString("accountName")+"</td>"+
    "<td>"+accStatus.getString("dateCreated")+"</td><td>$ "+accStatus.getString("balance")+"</td><td>"+accStatus.getString("email")+"</td></tr></table>");

    r.close();
    dbConnect.close();
    } catch(Exception e){
      //e.printStackTrace();
    }
    
  }
}