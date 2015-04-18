/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.alta.twilio;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.list.CallList;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author antonio
 */
public class ServletAuthTwilio extends HttpServlet {

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
        
        // Your own Auth Token
        final String AUTH_TOKEN = "317799e20c9d79467395e89383a03cfc";
        
        response.setContentType("text/html;charset=UTF-8");
        String accountSid = request.getParameter("AccountSid");
        
        // Store this account sid in your database, so you can retrieve it
        // later. You will need to write this section of the code.
        // Finally, redirect the user to your app after you've gathered their
        // SID
        TwilioRestClient client = new TwilioRestClient(accountSid, AUTH_TOKEN);
            
            Account mainAccount = client.getAccount();
            
            CallList calls = mainAccount.getCalls();
            
            for (Call call : calls) {
                    System.out.println("From: " + call.getFrom() + " To: " + call.getTo());
            }
            
        RequestDispatcher rd=request.getRequestDispatcher("https://secure.tetburyss.co.uk/ServletTwilio.servlet?AccountSid="+accountSid);
        rd.forward(request, response);
        //response.sendRedirect("https://secure.tetburyss.co.uk/ServletTwilio.servlet?AccountSid="+accountSid);
        
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
