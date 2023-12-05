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

public class OpenAccount extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    HttpSession session = request.getSession(false);
    response.setContentType("text/html");

    PrintWriter r = response.getWriter();
    Header header = new Header("Account", "./assets/css/open-account.css");//get header
    r.print(header.headString());
    
    if(session != null) {
      r.print(header.sessionHeader());
      r.print(header.sessionNav());
    } else {
      r.print(header.headerString());
      r.print(header.navString());
    }

    r.print(header.renderNavString());
    r.print("<p>Wecome to Obank, Thanks for choosing our services.</p>");
    r.print("<main><div class='open-account-form'></div></main>");
    r.print("<script src='./assets/js/open-account.js'></script>");

    r.close();
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{ 
    Connection dbConnect = Dbh.getDbConnection();
    HttpSession session = request.getSession(false);
    try {
      response.setContentType("text/html");
      PrintWriter r = response.getWriter();
      Header header = new Header("Account", "./assets/css/open-account.css");
      r.print(header.headString());
      
      if(session != null) {
        r.print(header.sessionHeader());
        r.print(header.sessionNav());
      } else {
        r.print(header.headerString());
        r.print(header.navString());
      }

      r.print(header.renderNavString());

      int nationalId = Integer.parseInt(request.getParameter("idNumber"));
      String firstName = request.getParameter("fname");
      String email = request.getParameter("email");
      String phone = request.getParameter("phone");
      String accName = request.getParameter("accountName");

      Statement stmt = dbConnect.createStatement();
      ResultSet customerEmail = stmt.executeQuery("select email from customers where email="+"'"+email+"' or customerId="+nationalId);     
      if(customerEmail.next()){    
        r.print("<p style='color:white;background-color:red;width:250px;margin:3% auto'>National Id or email already exists!!</p>");
        r.print("<p>Click to <a href='/obank/open-account'>Try again</a></p>");
      } else {
        PreparedStatement insertCustomer = dbConnect.prepareStatement("insert into customers values(?,?,?,?)");
        PreparedStatement insertAccount = dbConnect.prepareStatement("insert into accounts(ownerId,accountName) values(?,?)");

        insertCustomer.setInt(1,nationalId);
        insertCustomer.setString(2,firstName);
        insertCustomer.setString(3,email);
        insertCustomer.setString(4,phone);
        insertCustomer.executeUpdate();

        insertAccount.setInt(1,nationalId);
        insertAccount.setString(2,accName);
        insertAccount.executeUpdate();

        //get account number;
        int num = 0;
        ResultSet ac = stmt.executeQuery("select accountNumber from accounts where ownerId="+"'"+nationalId+"'");
        while(ac.next()) num = ac.getInt("accountNumber");
        
        dbConnect.close();

        //set attributes
        ServletContext context = getServletContext();
        context.setAttribute("firstName", firstName);
        context.setAttribute("accName", accName);
        context.setAttribute("accNumber", num);
        context.setAttribute("email", email);
        context.setAttribute("phone", phone);

        RequestDispatcher rd = request.getRequestDispatcher("/welcome");
        rd.forward(request, response);     
        r.close();
      }
    } catch(Exception e){
      //e.printStackTrace();
    }
  }
}

/*sql create table
      create table accounts(
          accountNumber int(11) not null unique auto_increment primary key,
          ownerId int(11) not null,
          accountName varchar(20),
          balance double(10,2) not null default 00.00,
          dateCreated timestamp not null default current_timestamp,
          foreign key(ownerId) references customers(customerId) on delete cascade
      );

      alter table accounts auto_increment = 1000;

      create table customers (
          customerId int(11) not null primary key,
          fname varchar(20) not null,
          email varchar(20) not null,
          phone char(10) not null
      );

      create table loans(
          loanId int auto_increment primary key,
          customerId int not null,
          dateTaken datetime default current_timestamp,
          principal double(10,2),
          interest double(10,2),
          amountDue double(10,2),
          foreign key (customerId) references customers(customerId) on delete cascade);

      create table staff(
          staffId int auto_increment primary key,
          passcode varchar(20) not null);
    */