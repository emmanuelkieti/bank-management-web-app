import java.io.*;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Logout extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException{
    try {
      HttpSession session = request.getSession();
      session.invalidate();
      RequestDispatcher rd = request.getRequestDispatcher("/home");
      rd.forward(request, response);
    } catch(Exception e){
      //e.printStackTrace();
    }
  }
}