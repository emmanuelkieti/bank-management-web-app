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

public class MoneyTransfer extends HttpServlet {
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
    
    r.print("<main><div class='mny-transfer-form'></div></main>");
    r.print("<script src='./assets/js/money-transfer.js'></script>");
    r.close();
    } catch(Exception e){
      response.setContentType("text/html");
      PrintWriter r = response.getWriter();
      r.print("<p>Unexpected Error occured.</p>");
      r.close();
    }finally {
      //
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    Connection dbConnect = Dbh.getDbConnection();
    try {
      response.setContentType("text/html");

    PrintWriter r = response.getWriter();
    Header header = new Header("Account", "./assets/css/open-account.css");//get header
    r.print(header.headString());
    r.print(header.sessionHeader());
    r.print(header.sessionNav());
    r.print(header.renderNavString());

    String acto = request.getParameter("acto");
    String acfrom = request.getParameter("acfrom");
    String amount = request.getParameter("amount");
    String submit = request.getParameter("submitting");

    //block anyone who tries to transfer money unless through the transfer form
    if(!submit.equals("submit")) r.print("<script>location.assign('/obank/money-transfer?transfer=unsuccessful');</script>");
    
    String actoString = "";
    String acfromString = "";
    for(int i = 2; i < acto.length();i++) actoString += acto.charAt(i);
    for(int i = 2; i < acfrom.length();i++) acfromString += acfrom.charAt(i);
    
    Statement stmt = dbConnect.createStatement();
    ResultSet actoAvailable = stmt.executeQuery("select * from accounts where accountNumber="+Integer.parseInt(actoString));
    if(actoAvailable.next()) {
      HttpSession session = request.getSession(false);
      String user = (String)session.getAttribute("accName");
      if(!acfrom.equals("OB"+user)){
        r.print("<script>location.assign('/obank/money-transfer?transfer=unsuccessful&error=not-user-account');</script>");
        return;
      }
      //Do not use amount in the "accountbalance" attribute
      ResultSet availableBalance = stmt.executeQuery("select * from accounts where accountNumber ="+Integer.parseInt(acfromString));
      availableBalance.next();
      Double bal = Double.parseDouble(availableBalance.getString("balance"));
      if(bal < Double.parseDouble(amount)) {
        r.print("<script>location.assign('/obank/money-transfer?transfer=unsuccessful&error=low-funds');</script>");
        return;
      } else {
        PreparedStatement insertBalance = dbConnect.prepareStatement("update accounts set balance = ? where accountNumber = ?");
        Double newBal1 = bal - Double.parseDouble(amount);
        ResultSet bal2 = stmt.executeQuery("select * from accounts where accountNumber ="+Integer.parseInt(actoString));
        bal2.next();
        Double newBal2 = Double.parseDouble(bal2.getString("balance")) + Double.parseDouble(amount);
        insertBalance.setDouble(1,newBal1);      
        insertBalance.setInt(2,Integer.parseInt(acfromString));
        insertBalance.executeUpdate();

        insertBalance.setDouble(1,newBal2);
        insertBalance.setInt(2,Integer.parseInt(actoString));
        insertBalance.executeUpdate();
        r.print("<p>You have successfully transferred $ "+amount+" from your account to "+bal2.getString("accountName")+"</p>");
      }
    } else r.print("<script>location.assign('/obank/money-transfer?transfer=unsuccessful&error=account-not-found');</script>");
    dbConnect.close();
    r.close();
    }catch(Exception e) {
      //e.printStackTrace();
    }
  }
}