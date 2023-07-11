/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.QuestionDAO;
import Models.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author tranm
 */
public class HomeController extends HttpServlet {

    QuestionDAO qDAO;

    @Override
    public void init() {
        qDAO = new QuestionDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MainController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MainController at " + request.getContextPath() + "</h1>");
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
        
        response.setHeader("Cache-Control", "private, no-cache, no-store, must-revalidate");
        response.setHeader("Expires", "-1");
        response.setHeader("Pragma", "no-cache");
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("UserController");
        } else {
            qDAO.loadQuestions();
            session.setAttribute("qDAO", qDAO);

            int index = 0;
            if (request.getAttribute("index") != null) {
                index = (int) request.getAttribute("index");
            }
            Paging page = new Paging(qDAO.getQuestions().size(), 10, index);
            page.calculate();
            request.setAttribute("page", page);
            request.getRequestDispatcher("Views/Home.jsp").forward(request, response);
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
        int index = Integer.parseInt(request.getParameter("index"));

        int totalPage = Integer.parseInt(request.getParameter("totalPage"));
        if (request.getParameter("btnHome") != null) //click home
        {
            index = 0;
        }
        if (request.getParameter("btnEnd") != null) //click end
        {
            index = totalPage - 1;
        }
        if (request.getParameter("btnPre") != null) //click pre
        {
            index--;
        }
        if (request.getParameter("btnNext") != null) //click next
        {
            index++;
        }
        if (request.getParameter("btnIndex") != null) // click button i
        {
            index = Integer.parseInt(request.getParameter("btnIndex"));
        }
        request.setAttribute("index", index);
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
