import java.io.*;
import java.sql.*;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Loans extends HttpServlet{
  String amount = null;
  PrintWriter r = null;
  Statement stmt = null;
  HttpSession session = null;

  String applyAmount = null;
  String payAmount = null;

  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    try {
      String qString = request.getQueryString();
      response.setContentType("text/html");
      r = response.getWriter();
      Header header = new Header("Loans", "./assets/css/loans.css");//get header
      r.print(header.headString());
      r.print(header.sessionHeader());
      r.print(header.sessionNav());
      r.print(header.renderNavString());

      if(qString.contains("apply-loan"))
        r.print("<div class='apply-loan'></div>");
      
      if(qString.contains("pay-loan"))
        r.print("<div class='pay-loan'></div>");
      
      if(qString.contains("loan-status")) {
        Connection dbConnect = Dbh.getDbConnection();
        Statement stmt = dbConnect.createStatement();
        HttpSession session = request.getSession(false);
        int id = Integer.parseInt((String)session.getAttribute("national"));
        ResultSet loan = stmt.executeQuery("select * from loans where customerId="+id);
        if(loan.next()) {
          r.print("<table><tr><th colspan='5'>Loan status</th></tr>");
          r.print("<tr><th>Customer Id</th><th>Principal</th><th>Interest</th><th>Amount Due</th><th>Date Taken</th></tr>");
          r.print("<tr><td>"+loan.getString("customerId")+"</td><td>"+loan.getString("principal")+"</td><td>"+loan.getString("interest")+"</td>");
          r.print("<td>"+loan.getString("amountDue")+"</td><td>"+loan.getString("dateTaken")+"</td></tr>");
          r.print("</table>");

          r.close();
          dbConnect.close();
        } else {
          r.print("<p>You are not servicing any loan.</p>");
        }
        
      }

      r.print("<script src='./assets/js/loans.js'></script>");
      r.close();
    }catch(Exception e){
      //e.printStackTrace();
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    try{
      Connection dbConnect = Dbh.getDbConnection();
      stmt = dbConnect.createStatement();
      session = request.getSession(false);

      applyAmount = request.getParameter("applyAmount");
      payAmount = request.getParameter("payAmount");

      response.setContentType("text/html");
      r = response.getWriter();
      Header header = new Header("Loans", "./assets/css/loans.css");//get header
      r.print(header.headString());
      r.print(header.sessionHeader());
      r.print(header.sessionNav());
      r.print(header.renderNavString());
      
      if(applyAmount != null) apply();
      
      if(payAmount != null) pay();

      r.close();
      dbConnect.close();     
    }catch(Exception e){
      //e.printStackTrace();
    }
    
  }

  void apply(){
      Double b = Double.parseDouble((String)session.getAttribute("accountBalance"));
      if(Double.parseDouble(applyAmount) > (0.75*b)){
        r.print("<script>location.assign('/obank/loans?apply-loan=0&error=high-amount')</script>");
        return;
      } else {
        try{
          int id = Integer.parseInt((String)session.getAttribute("national"));
          //check if customer has uncleared loan
          ResultSet customerLoanTest = stmt.executeQuery("select * from loans where customerId ="+id);
          if(customerLoanTest.next())
            //delete loan if amount due equals 0.0
            if(Math.round(Double.parseDouble(customerLoanTest.getString("amountDue"))) == 0)
              stmt.executeUpdate("delete from loans where customerId="+id);
            
          ResultSet customerLoan = stmt.executeQuery("select * from loans where customerId ="+id);
          if(customerLoan.next()) {
            r.print("<script>location.assign('/obank/loans?apply-loan=0&error=existing-loan')</script>");
            return;
          } else {
            Double amt = Double.parseDouble(applyAmount);
            Double newBal = b+amt;
            stmt.executeUpdate("update accounts set balance ="+newBal+" where ownerId="+id);

            //insert into loans
            stmt.executeUpdate("insert into loans (customerId, principal,interest,amountDue) values"+
            "("+id+","+amt+","+0.08*amt+","+1.08*amt+")");

            r.print("<script>location.assign('/obank/loans?apply-loan=1')</script>");
          }
          
        }catch(Exception e){
          //
        }
      }
      
  }

  void pay(){
        try {
          int id = Integer.parseInt((String)session.getAttribute("national"));
          ResultSet bal = stmt.executeQuery("select * from accounts where ownerId ="+id);
          bal.next();
          Double b = Double.parseDouble(bal.getString("balance"));
          if(Double.parseDouble(payAmount) > b){
              r.print("<script>location.assign('/obank/loans?pay-loan=0&error=balance-low')</script>");
              return;
          } else {
          ResultSet loan = stmt.executeQuery("select * from loans where customerId ="+id);
          if(loan.next()){
            Double amtDue = Double.parseDouble(loan.getString("amountDue"));
            if(amtDue == 0.0) {
              stmt.executeUpdate("delete from loans where customerId="+id);
              r.print("<script>location.assign('/obank/loans?pay-loan=0&error=fully-paid')</script>");
              return;
            }
            Double newAmtDue = 0.0;
            Double newAcBalance = 0.0;
            ResultSet ac = stmt.executeQuery("select * from accounts where ownerId ="+id);
            ac.next();
            Double acBal = Double.parseDouble(ac.getString("balance"));
            if(Double.parseDouble(payAmount) > amtDue) {
              newAmtDue = 0.0;
              newAcBalance = acBal - amtDue;
            } else {
              newAmtDue = amtDue - Double.parseDouble(payAmount);
              newAcBalance = acBal - Double.parseDouble(payAmount);
            }
            //update loan and account
            stmt.executeUpdate("update accounts set balance = "+newAcBalance+" where ownerId ="+id);
            stmt.executeUpdate("update loans set amountDue = "+newAmtDue+" where customerId ="+id);
            
            r.print("<script>location.assign('/obank/loans?pay-loan=1&error=no-error')</script>");
          } else {
            r.print("<script>location.assign('/obank/loans?pay-loan=0&error=no-loan')</script>");
          }
        } 
      } catch(Exception e){
          //e.printStackTrace;
        }
  }
}