/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.AnswerDAO;
import dao.AttempDAO;
import dao.QuestionDAO;
import Models.Answer;
import Models.Attemp;
import Models.Question;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

/**
 *
 * @author tranm
 */
public class TestController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("UserController");
        } else {
            String user = request.getParameter("user");
            Timestamp time = Timestamp.valueOf(request.getParameter("time"));
            AttempDAO attDao = new AttempDAO();
            Map<Integer, Attemp> results = attDao.loadAttemps(user, time);
   
            QuestionDAO qDao = (QuestionDAO)request.getSession().getAttribute("qDAO");
            List<Question> quizzes = qDao.loadQuestions(results.keySet());
            AnswerDAO aDao = new AnswerDAO();
            Map<Question, List<Answer>> test = aDao.loadAnswers(quizzes);
            
            request.setAttribute("results", results);
            request.getSession().setAttribute("test", test);
            
            request.getRequestDispatcher("Views/Test.jsp").forward(request, response);
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
        String finish = request.getParameter("finish");
        HttpSession session = request.getSession();

        if (finish == null) {
            QuestionDAO qDao = (QuestionDAO) session.getAttribute("qDAO");
            int number = Integer.parseInt(request.getParameter("number"));

            List<String> topics = new ArrayList<>();
            for (int i = 1; i <= qDao.getTopics().length; i++) {
                String topic = request.getParameter("topic" + i);
                if (topic != null) {
                    topics.add(topic);
                }
            }
            List<Question> quizzes = qDao.loadQuestions(topics, number);
            AnswerDAO aDao = new AnswerDAO();
            Map<Question, List<Answer>> test = aDao.loadAnswers(quizzes);

            session.setAttribute("test", test);
            request.getRequestDispatcher("Views/Test.jsp").forward(request, response);
        } else {
            String user = ((User) session.getAttribute("user")).getUsername();
            Timestamp time = new Timestamp(System.currentTimeMillis());
            float score = Float.parseFloat(request.getParameter("score"));
            Map<Question, List<Answer>> test = (Map<Question, List<Answer>>) session.getAttribute("test");

            AttempDAO attDao = new AttempDAO();
            attDao.insertResult(user, time, score);
            for (Map.Entry<Question, List<Answer>> entry : test.entrySet()) {
                int qid = entry.getKey().getId();
                String selection = request.getParameter("answer" + qid);
                String choice = selection.substring(0, 1);
                int isCorrect = Integer.parseInt(selection.substring(1));
                attDao.insertAttemp(user, qid, isCorrect, time, choice);
            }
            session.removeAttribute("test");
            response.sendRedirect("HomeController");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
