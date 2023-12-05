import java.io.*;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Welcome extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response)
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

    ServletContext context = getServletContext();

    String firstName = (String)context.getAttribute("firstName");
    String accName = (String)context.getAttribute("accName");
    int accNumber = (Integer)context.getAttribute("accNumber");
    String email = (String)context.getAttribute("email");
    String phone = (String)context.getAttribute("phone");

    r.write("<div class='welcome'><p>Welcome "+firstName+"<br/>Your account has successfully been created.</p>");
        r.write("<p>Account Name: "+ accName+"<br/>"+
        "Account Number: OB"+accNumber+"<br/>"+
        "Balance: $ 00.00<br/>"+
        "Email: "+email+"<br/>"+
        "Phone Number :"+phone+"<br/><br/>");
        r.write("Proceed to <a href='/obank/login'><button type='button'>Log in</button></a> or back to <a href='/obank'><button>Home page</button></a></p>");
        r.write("</div>");

        r.close();
  }
}