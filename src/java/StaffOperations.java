import java.io.*;
import java.sql.*;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StaffOperations extends HttpServlet{
  Connection dbConnect = Dbh.getDbConnection();
  String accountNumber = null;
  String amount = null;
  String submit = null;
  String clientId = null;
  PrintWriter r = null;

  boolean deposit = false;
  boolean withdraw = false;
  boolean deleteClient = false;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    try {
      String qString = request.getQueryString();
      response.setContentType("text/html");
      r = response.getWriter();
      Header header = new Header("Staff Account", "./assets/css/staff.css");//get header
      r.print(header.headString());
      r.print(header.sessionHeader());
      r.print(header.staffNav());
      r.print(header.renderNavString());

      //deposit
      if(qString.contains("deposit")) {
        r.print("<div class='deposit-money-form'></div>");
        deposit = true;
      }

      if(qString.contains("withdraw")) {
        r.print("<div class='withdraw-money-form'></div>");
        withdraw = true;
      }

      if(qString.contains("allClients")) {
        r.print(allData("customers", new String[]{"customerId","fname","email","phone"}, "Our Customers", new String[]{"Customer Id","First Name","Email","Phone"}));
      }

      if(qString.contains("allAccounts")) {
        r.print(allData("accounts", new String[]{"accountNumber","ownerId","accountName","balance", "dateCreated"},
        "All Accounts", new String[]{"Acc Number","Owner Id","Acc Name","Balance", "Date Created"}));
      }

      if(qString.contains("allStaff")) {
        r.print(allData("staff", new String[]{"staffId","passcode"}, "Our Staff", new String[]{"Staff Id","Pass code"}));
      }

      if(qString.contains("allLoans")) {
        r.print(allData("loans", new String[]{"loanId","customerId","dateTaken","principal","interest","amountDue"}, "All Loans",
        new String[]{"Loan Id","Customer Id", "Date Approved", "Principal", "Interest", "Amount Due"}));
      }

      if(qString.contains("delete-client")) {
        r.print("<div class='delete-client-form'></div>");
        deleteClient = true;
      }
      
      r.print("<script src='./assets/js/staff-operations.js'></script>");
    }catch(Exception e){
      //e.printStackTrace();
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    try {
      accountNumber = request.getParameter("accNumber");
      amount = request.getParameter("amount");
      submit = request.getParameter("submitting");
      clientId = request.getParameter("nationalId");

      response.setContentType("text/html");
      r = response.getWriter();
      Header header = new Header("Staff Account", "./assets/css/staff.css");//get header
      r.print(header.headString());
      r.print(header.sessionHeader());
      r.print(header.staffNav());
      r.print(header.renderNavString());

      //run deposit
      if(deposit) {
        deposit();
        deposit = false;
      }
      //withdraw
      if(withdraw) {
        withdraw();
        withdraw = false;
      }
      //delete user
      if(deleteClient) {
        deleteClient();
        deleteClient = false;
      }
    }catch(Exception e) {}
    }

  void deposit(){
    try {
      Statement stmt = dbConnect.createStatement();
      //block anyone who tries to deposit money unless through the deposit form
    if(!submit.equals("submit")){
      r.print("<script>location.assign('/obank/staff-operations?deposit=unsuccessful');</script>");
      return;
    }
    
    String actoString = "";
    for(int i = 2; i < accountNumber.length();i++) actoString += accountNumber.charAt(i);
    ResultSet actoAvailable = stmt.executeQuery("select * from accounts where accountNumber="+Integer.parseInt(actoString));
    if(actoAvailable.next()) {
      Double newAmt = Double.parseDouble(amount)+ Double.parseDouble(actoAvailable.getString("balance"));
      stmt.executeUpdate("update accounts set balance ="+newAmt+"where accountNumber="+Integer.parseInt(actoString));
      r.print("<p> Deposit Successful</p>");
    } else r.print("<script>location.assign('/obank/staff-operations?deposit=unsuccessful&error=account-not-found');</script>");
    }catch(Exception e){
      //e.printStackTrace();
    }
  }

  void withdraw(){
    try {
      Statement stmt = dbConnect.createStatement();
      //block anyone who tries to withdraw money unless through the withdraw form
    if(!submit.equals("submit")){
      r.print("<script>location.assign('/obank/staff-operations?withdraw=unsuccessful');</script>");
      return;
    }
    String acfromString = "";
    for(int i = 2; i < accountNumber.length();i++) acfromString += accountNumber.charAt(i);
    ResultSet acfromAvailable = stmt.executeQuery("select * from accounts where accountNumber="+Integer.parseInt(acfromString));
    if(acfromAvailable.next()) {
      if(Double.parseDouble(amount) >= (Double.parseDouble(acfromAvailable.getString("balance")))) { 
        r.print("<script>location.assign('/obank/staff-operations?withdraw=unsuccessful&error=insufficient-funds');</script>");
        return;
        } else {
        Double newAmt = Double.parseDouble(acfromAvailable.getString("balance")) - Double.parseDouble(amount);
        stmt.executeUpdate("update accounts set balance ="+newAmt+"where accountNumber="+Integer.parseInt(acfromString));
        r.print("<p> Successful withdrawal</p>");
      }
    } else r.print("<script>location.assign('/obank/staff-operations?withdraw=unsuccessful&error=account-not-found');</script>");
    }catch(Exception e){
      //e.printStackTrace();
    }
  }

  String allData (String dbTable, String[] columns, String displayTableHeader, String[] displayTableHeaders){
    String s = "";
    try {
      Statement stmt = dbConnect.createStatement();
      s+="<table><tr><th colspan="+"'"+columns.length+"'"+">"+displayTableHeader+"</th></tr>";
      s+="<tr>";
      for(int i = 0; i < columns.length; i++) 
        s+="<th>"+displayTableHeaders[i]+"</th>";
      s+="</tr>";

      ResultSet allData = stmt.executeQuery("select * from "+dbTable);
    while(allData.next()){
      s+="<tr>";
      for(int i = 0; i < columns.length; i++) 
        s+="<td>"+allData.getString(i+1)+"</td>";
      s+="</tr>";
    }
    s += "</table>";
    }catch(Exception e){
      //e.printStackTrace();
    }
    return s;
  }

  void deleteClient(){
    try {
      Statement stmt = dbConnect.createStatement();
    if(!submit.equals("submit")){
      r.print("<script>location.assign('/obank/staff-operations?deposit=unsuccessful');</script>");
      return;
    }
    int del = stmt.executeUpdate("delete from customers where customerId="+Integer.parseInt(clientId));
    if(del > 0) {
      r.print("<p> Client deleted successfully.</p>");
    } else r.print("<script>location.assign('/obank/staff-operations?delete-client-form=unsuccessful&error=id-not-found');</script>");
    }catch(Exception e){
      //
    }
  }
}