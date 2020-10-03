package wp2531.mvc__intro;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller component that control 
 * HTML-pages and delegate some dynamic
 * pages generation to other servlets
 * @author comeillfoo
 */
public class ControllerServlet extends HttpServlet {
  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    // getting parameters from URL
    String x = request.getParameter("x"); // retrieve x-changing
    String y = request.getParameter("y"); // retrieve y-changing
    String r = request.getParameter("r"); // retrieve r-changing
    String submit = request.getParameter(request.getSession().getId()); // retrieve submit btn pressing
    if (submit == null || y == null || r == null || x == null)
      doPost(request, response);
    String path = "results/";
    request.getRequestDispatcher(path).forward(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   * Just forwards to base page with the form
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String path = "WEB-INF/index.jsp";
    request.getRequestDispatcher(path).forward(request, response);
  }

  /**
   * Returns a short description of the servlet.
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Controls web-application";
  }// </editor-fold>

}
