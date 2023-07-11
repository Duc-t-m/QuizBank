/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.UserDAO;
import Models.User;
import Validation.RegisterValidate;
import Validation.SendEmail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;

public class UserController extends HttpServlet {

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
            UserDAO dao = new UserDAO();
            String go = request.getParameter("go");
            HttpSession session=request.getSession();
            if(go==null)
                request.getRequestDispatcher("Views/login.jsp").forward(request, response);
            if(go.equals("login")){
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                Vector<User> vec = dao.getAllUsers("select * from users where "
                        + "username ='"+username+"' and password ='"+password+"'");
                if(vec.size() != 0){
                    session.setAttribute("user", vec.get(0));
                    session.setMaxInactiveInterval(60*60*4);
                    response.sendRedirect("HomeController");
                }
                else{
                    String msg = "Incorrect username or password!";
                    request.setAttribute("msg", msg);
                    request.getRequestDispatcher("Views/login.jsp").forward(request, response);
                }
            }
            
            if(go.equals("logout")){
                session.invalidate();
                request.getRequestDispatcher("Views/login.jsp").forward(request, response);
            }
            
            if(go.equals("register")){
                String submit = request.getParameter("submitRegister");
                if(submit == null){
                    request.getRequestDispatcher("Views/register.jsp").include(request, response);
                }
                else{
                    String username = request.getParameter("username"),
                        usernameErr = RegisterValidate.usernameValidate(username),
                        password = request.getParameter("password"),
                        passwordErr = RegisterValidate.passwordValidate(password),
                        email = request.getParameter("email");
                    int isTeacher = Integer.parseInt(request.getParameter("isTeacher"));
                    if(usernameErr != null|| passwordErr != null){
                        request.setAttribute("msgUsername",usernameErr);
                        request.setAttribute("msgPassword", passwordErr);
                        request.getRequestDispatcher("Views/register.jsp").include(request, response);
                    }
                    else{
                        User user = new User(username, password, email, isTeacher);
                        SendEmail send = new SendEmail();
                        String code = send.generateRandCode();
                        session.setAttribute("code", code);
                        session.setAttribute("RegisAccount", user);
                        send.sendEmail(user, code);
                        request.getRequestDispatcher("Views/codeValidate.jsp").forward(request, response);
                    }
                }
            }
            if(go.equals("changePassword")){
                String submit = request.getParameter("submitChange");
                String user = ((User)request.getSession().getAttribute("user")).getUsername();
                if(submit==null){
                    String pass = dao.getPass(user);
                    request.setAttribute("old", pass);
                    request.getRequestDispatcher("Views/changePass.jsp").forward(request, response);
                }
                else{
                    String password = request.getParameter("password");
                    String passwordErr = RegisterValidate.passwordValidate(password);
                    if(passwordErr!=null){
                        request.setAttribute("msgPassword", passwordErr);
                        request.getRequestDispatcher("Views/changePass.jsp").include(request, response);
                    }
                    else{
                        dao.update(user, password);
                        response.sendRedirect("HomeController");
                    }
                }
            }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
