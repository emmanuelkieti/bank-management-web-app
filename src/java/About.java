import java.io.*;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class About extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    HttpSession session = request.getSession(false);
    response.setContentType("text/html");

    PrintWriter r = response.getWriter();
    Header header = new Header("About", "./assets/css/about.css");
    r.print(header.headString());
    
    if(session != null) {
      r.print(header.sessionHeader());
      r.print(header.sessionNav());
    } else {
      r.print(header.headerString());
      r.print(header.navString());
    }

    r.print(header.renderNavString());

    r.print("<main><script src='./assets/js/about.js'></script></main>");
    r.print("<footer id='site-footer'><script src='./assets/js/footer.js'></script></footer></body</html>");
    r.close();
  }
}