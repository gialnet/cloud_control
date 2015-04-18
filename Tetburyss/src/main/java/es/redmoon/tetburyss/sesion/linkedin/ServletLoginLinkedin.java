/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.sesion.linkedin;

import es.redmoon.tetburyss.alta.SQLAltaServicio;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Verificar identidad desde la cuenta de Linked in
 * @author antonio
 */
public class ServletLoginLinkedin extends HttpServlet {

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
            throws ServletException, IOException, SQLException, NamingException {
        response.setContentType("text/html;charset=UTF-8");

        // leer los valores devueltos por Linked IN
        String xCode = request.getParameter("code");
        String xState = request.getParameter("state");
        
        System.out.println("codigo de autorizacion: "+xCode);
        System.out.println("url_wellcome: "+xState);
        
        // YOUR_REDIRECT_URI/?error=access_denied&error_description=the+user+denied+your+request&state=STATE
        
        String post="https://www.linkedin.com/uas/oauth2/accessToken?grant_type=authorization_code"+
                                           "&code="+ xCode +
                                           "&redirect_uri=https://myempresa.eu/ServletLoginLinkedin"+
                                           "&client_id=wh4v786eloxw"+
                                           "&client_secret=udbfDCUoGRuFxKFe";
        System.out.println(post);
        
        response.sendRedirect(post);
        
        /*
        // La dirección de email asignarla a una base de datos en caso
        // de que aún no este.
        String xNombre = request.getParameter("xNombre");
        String xMail = request.getParameter("xMail");
        String xPais = request.getParameter("xPais");
        
        SQLAltaServicio as = new SQLAltaServicio();
        
        int resultado = as.AltaServicio(xNombre, xMail, Integer.parseInt(xPais));
        
        // Devuelve el número único de cliente
        if (resultado > 0)
        {
        
            RequestDispatcher rd =null;
            rd=request.getRequestDispatcher("DescargaCertificados.jsp");
            rd.forward(request, response);
        }*/
        
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletLoginLinkedin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ServletLoginLinkedin.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletLoginLinkedin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ServletLoginLinkedin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login LinkedIn";
    }// </editor-fold>
}
