package wp2531.mvc__intro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This servlet makes point hitting test 
 * in coordinate plane and generating 
 * HTML-page with test results. Must 
 * service all queries which contain information 
 * about dot coordinates and area radius.
 * @author comeillfoo
 */
public class AreaCheckServlet extends HttpServlet {
  
  public static final double EPSILON = Double.MIN_VALUE;
  public static final String KEY_WORD = "check";
  public static final String RESULTS_ATTR_NAME = "results";
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
    response.setContentType("text/html;charset=UTF-8");
    try ( PrintWriter out = response.getWriter()) {
      // printing beginning of HTML-page
      out.println("<!DOCTYPE html>");
      out.println("<html lang='RU-ru'>");
      out.println("  <head>");
      out.println("    <title>Точки на области | Результаты</title>");
      out.println("    <link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/assets/stylesheets/main.css'>");
      out.println("    <link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/assets/stylesheets/results.css'>");
      out.println("  </head>");
      out.println("");
      out.println("  <body>");
      out.println("    <table class='wrapper'>");
      out.println("      <caption id='results' class='title__text--capital'>таблица результатов</caption>");
      out.println("");
      out.println("      <tbody>");
      out.println("        <tr>");
      out.println("          <td>");
      out.println("            <table class='results__table--fill'>");
      out.println("              <thead>");
      out.println("                <tr class='results__row--heading'>");
      out.println("                  <th class='cell__text--capital'>дата проверки</th>");
      out.println("                  <th>Время, мс</th>");
      out.println("                  <th class='cell__text--capital'>x</th>");
      out.println("                  <th class='cell__text--capital'>y</th>");
      out.println("                  <th class='cell__text--capital'>r</th>");
      out.println("                  <th class='cell__text--capital'>результат</th>");
      out.println("                </tr>");
      out.println("              </thead>");
      out.println("");
      out.println("              <tbody>");
      
      // printing revious results
      HttpSession currentSession = request.getSession();
      List<ResultBean> previousResults = (List<ResultBean>) currentSession.getAttribute(RESULTS_ATTR_NAME);
      if (previousResults != null)
        previousResults.forEach((bean)-> {
          out.println("                <tr class='results__row--data'>");
          out.println("                  <td class='results__cell--offset'>" + bean.getDate() + "</td>");
          out.println("                  <td class='results__cell--offset'>" + bean.getTime() + "</td>");
          out.println("                  <td class='results__cell--offset'>" + bean.getX() + "</td>");
          out.println("                  <td class='results__cell--offset'>" + bean.getY() + "</td>");
          out.println("                  <td class='results__cell--offset'>" + bean.getR() + "</td>");
          out.println("                  <td class='results__cell--offset'>" + (bean.isHit() ? "да" : "нет") + "</td>");
          out.println("                </tr>");
        });
      else previousResults = new ArrayList<>();
      
      // go to checking parameters and decide which page part should be printed next: results or errors
      try {
        long started = new Date().getTime(); // starts measuring processing time
        String errMsg = "";
        ArgumentsChecker tester = new ArgumentsChecker(EPSILON);
        
        // filling point coordinates and area radius from GET-parameters
        String[] realParamsNames = new String[]{"x", "y", "r"};
        Map<String, Double> realParams = new HashMap<>();
        for (String argName : realParamsNames) {
          String argURL = request.getParameter(argName);
          String paramErrMsg = tester.checkRealParameter(argURL, argName.toUpperCase());
          if (paramErrMsg.isEmpty())
            realParams.put(argName, Double.parseDouble(argURL));
          else errMsg += paramErrMsg;
        }
        
        // if some parameters weren't parsed then return ERROR-page
        if (!errMsg.isEmpty()) throw new Exception(errMsg);
        
        // checking use of form from main page
        if (isNotFormUsed(request, currentSession.getId()))
          throw new Exception(errMsg + "FNU: не использована форма;<br>");
        
        // check X parameter value
        String xURL = request.getParameter(realParamsNames[0]);
        double x = realParams.get(realParamsNames[0]);
        tester.setAcceptable(new double[]{-5, -4, -3, -2, -1, 0, 1, 2, 3});
        if (!tester.isAcceptable(x))
          errMsg += "NFE: параметр X не удовлетворяет своему условию<br>";
        
        // check Y parameter value
        String yURL = request.getParameter(realParamsNames[1]);
        double y = realParams.get(realParamsNames[1]);
        tester.setInterval(-3, 5);
        if (!tester.isInInterval(y))
          errMsg += "NFE: параметр Y не удовлетворяет своему условию<br>";
        
        // check R parameter value
        String rURL = request.getParameter(realParamsNames[2]);
        double r = realParams.get(realParamsNames[2]);
        tester.setAcceptable(new double[]{1, 2, 3, 4, 5});
        if (!tester.isAcceptable(r))
          errMsg += "NFE: параметр R не удовлетворяет своему условию<br>";
        
        if (!errMsg.isEmpty()) throw new Exception(errMsg);
        
        // checking if point hit the area
        Area area = new Area(r);
        boolean hit = area.isHit(x, y, EPSILON);
        long finished = new Date().getTime(); // ends measuring processing time
        
        // filling processed result
        ResultBean query = new ResultBean();
        query.setDate(new Date().toString());
        query.setTime(finished - started);
        query.setX(xURL);
        query.setY(yURL);
        query.setR(rURL);
        query.setHit(hit);
        
        // adding new query to current session
        previousResults.add(query);
        currentSession.setAttribute(RESULTS_ATTR_NAME, previousResults);
        
        // all tests passed so printing current query processing
        out.println("                <tr class='results__row--data'>");
        out.println("                  <td class='results__cell--offset'><div class=\"results__cell--scrollable\">" + query.getDate() + "</div></td>");
        out.println("                  <td class='results__cell--offset'><div class=\"results__cell--scrollable\">" + query.getTime() + "</div></td>");
        out.println("                  <td class='results__cell--offset'><div class=\"results__cell--scrollable\">" + query.getX() + "</div></td>");
        out.println("                  <td class='results__cell--offset'><div class=\"results__cell--scrollable\">" + query.getY() + "</div></td>");
        out.println("                  <td class='results__cell--offset'><div class=\"results__cell--scrollable\">" + query.getR() + "</div></td>");
        out.println("                  <td class='results__cell--offset'><div class=\"results__cell--scrollable\">" + (query.isHit() ? "да" : "нет") + "</div></td>");
        out.println("                </tr>");
        
      } catch (Exception exp) {
        // printing errors while processing query and its parameters
        out.println("                <tr class='results__row--data'>");
        out.println("                  <th colspan='2' class='title__errors title__text--capital title__after--colon'>ошибки обработки текущего запроса</th>");
        out.println("                  <td colspan='4' class='title__errors'>" + exp.getMessage() + "</td>");
        out.println("                </tr>");
        
      } finally {
        // in both cases we must print end of the page
        out.println("              </tbody>");
        out.println("            </table>");
        out.println("          </td>");
        out.println("        </tr>");
      }
      
      out.println("");
      out.println("        <tr>");
      out.println("          <td>");
      out.println("            <a class='btn btn__size--return' href='" + request.getContextPath() + "'>вернуться</a>");
      out.println("          </td>");
      out.println("        </tr>");
      out.println("      </tbody>");
      out.println("    </table");
      out.println("  </body>");
      out.println("</html>");
    }
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   * 
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try ( PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("  <head>");
      out.println("    <title>Попадание точек | Результаты [ошибка]</title>");      
      out.println("  </head>");
      out.println("");
      out.println("  <body>");
      out.println("    <h1>Недопустимый запрос</h1>");
      out.println("  </body>");
      out.println("</html>");
    }
  }

  /**
   * Returns a short description
   * of the servlet.
   * 
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Check point hitting";
  }// </editor-fold>
  
  /**
   * Accessor of using form by client
   * 
   * @param request client request
   * @param sessionId identificator of current session
   * @return 
   */
  private boolean isNotFormUsed(HttpServletRequest request, String sessionId) {
    return !KEY_WORD.equals(request.getParameter(sessionId));
  }
}
