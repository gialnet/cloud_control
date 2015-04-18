/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.sesion.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author antonio
 */
public class ServletFBOauth extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        /*
         * App ID:         463814247061914
         * App Secret:         e9f005770c6e97e075566cd9210c4bcf
         */
        
        FacebookClient facebookClient = new DefaultFacebookClient("CAACEdEose0cBAPx6bq8quJCLHfiXL9pKakKvZBLoTw3fx9bPNJXhvjPcckzLTVj14QQvPw5kiZAtRDl3F8bGcavNYyg5xC8gZCyRdINCsMqDmsjkoh2KB3ig51g4mpxbykweQX3XOjP7M6E0NA4i0ZBiUdIOZCGUlKK6QYZAAH2bvzoWJrHqI1fFhUmewa734ZD");
        
        FacebookClient publicOnlyFacebookClient = new DefaultFacebookClient();
        User user = facebookClient.fetchObject("me", User.class);
        System.out.println("User name: " + user.getName());
        System.out.println("User name: " + user.getEmail());

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
