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

public class StaffLogin extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    try {
      response.setContentType("text/html");
      PrintWriter r = response.getWriter();
      Header header = new Header("Staff Account", "./assets/css/staff.css");//get header
      r.print(header.headString());
      r.print(header.headerString());
      r.print(header.navString());
      r.print(header.renderNavString());
      r.print("<p>Your are logging in as staff</p>");
      r.print("<main><div class='staff-login-form'></div></main>");
      r.print("<script src='./assets/js/staff-login.js'></script>");

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

      String staffId = request.getParameter("staffid");
      String passcode = request.getParameter("passcode");
      String submit = request.getParameter("submitting");

       //block anyone who tries to transfer money unless through the transfer form
      if(!submit.equals("submit")) r.print("<script>location.assign('/obank/staff-login?login=incorrect');</script>");

      //get database values
      Statement stmt = dbConnect.createStatement();
      ResultSet staffDetails = stmt.executeQuery("select * from staff where staffId="+staffId+" and passcode="+"'"+passcode+"'");
      if(staffDetails.next()) {
        HttpSession session = request.getSession();
        session.setAttribute("staff",""+staffId);
        String location = "/obank/staff-operations";
        r.print("<script>location.assign("+"'"+location+"'"+")</script>");
      } else {
        String location = "/obank/staff-login?login=incorrect";
        r.print("<script>location.assign("+"'"+location+"'"+")</script>");
      }

      dbConnect.close();
      r.close();
    }catch(Exception e){
      //e.printStackTrace();
    }
  }
}