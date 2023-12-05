import java.io.*;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Home extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    HttpSession session = request.getSession(false);
    try{
      response.setContentType("text/html");

      PrintWriter r = response.getWriter();
      Header header = new Header("Account", "./assets/css/main.css");//get header
      r.print(header.headString());

      if(session != null) {
        r.print(header.sessionHeader());
        r.print(header.sessionNav());
      } else {
        r.print(header.headerString());
        r.print(header.navString());
      }

      r.print(header.renderNavString());

      r.print("<main><div class='banner'><div class='banner-content'></div></div><div class='ad'></div></main>");
      r.print("<script src='./assets/js/home.js'></script>");
      r.print("<footer id='site-footer'><script src='./assets/js/footer.js'></script></footer></body</html>");

      r.close();
    } catch (Exception e) {
      //e.printStackTrace();
    }
  }
}