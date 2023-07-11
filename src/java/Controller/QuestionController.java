/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.AnswerDAO;
import dao.QuestionDAO;
import Models.Answer;
import Models.Question;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author tranm
 */
public class QuestionController extends HttpServlet {

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
            out.println("<title>Servlet QuestionController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QuestionController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("UserController");
        } else {
            QuestionDAO qDAO = (QuestionDAO) request.getSession().getAttribute("qDAO");
            AnswerDAO aDAO = new AnswerDAO();
            String type = request.getParameter("type");
            if (type == null) {
                request.getRequestDispatcher("Views/NewQuestion.jsp").forward(request, response);
            } else {
                if (type.equals("delete")) {
                    String id = request.getParameter("id");
                    if (id != null) {
                        qDAO.delete(id);
                        response.sendRedirect("HomeController");
                    }
                } else if (type.equals("update")) {
                    String id = request.getParameter("id");
                    if (id != null) {
                        Question update = qDAO.getQuestions().get(Integer.parseInt(id));
                        List<Answer> answers = aDAO.loadAnswers(Integer.parseInt(id));
                        request.setAttribute("answers", answers);
                        request.setAttribute("update", update);
                        request.getRequestDispatcher("Views/NewQuestion.jsp").forward(request, response);
                    }
                } else {
                    response.sendRedirect("HomeController");
                }
            }
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
        HttpSession session = request.getSession();
        String question = request.getParameter("question");
        String topic = request.getParameter("topic");
        String user = ((User) session.getAttribute("user")).getUsername();
        String act = request.getParameter("act");
        int numOfOpt = Integer.parseInt(request.getParameter("numOfOpt"));

        AnswerDAO aDao = new AnswerDAO();
        QuestionDAO qDao = (QuestionDAO) request.getSession().getAttribute("qDAO");
        int qid;
        int isKey = Integer.parseInt(request.getParameter("isKey"));
        if (act.equals("insert")) {
            qDao.insert(question, topic, user);
            qid = QuestionDAO.getMaxID();
            for (int i = 1; i <= numOfOpt; i++) {
                String text = request.getParameter("option" + i);
                if(i==isKey)
                    aDao.insert(qid, String.valueOf((char) ('A' + i - 1)), 1, text);
                else
                    aDao.insert(qid, String.valueOf((char) ('A' + i - 1)), 0, text);
            }
        } else if (act.equals("update")) {
            qid = Integer.parseInt(request.getParameter("qid"));
            qDao.update(qid, question, topic, user);
            for (int i = 1; i <= 4; i++) {
                if (i <= numOfOpt) {
                    String text = request.getParameter("option" + i);
                    if(i==isKey)
                        aDao.update(qid, String.valueOf((char) ('A' + i - 1)), 1, text);
                    else
                        aDao.update(qid, String.valueOf((char) ('A' + i - 1)), 0, text);
                } else if (i > numOfOpt) {
                    if (i == 3) {
                        aDao.delete(qid, "C");
                    }
                    aDao.delete(qid, "D");
                }
            }
        }
        doGet(request, response);
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
