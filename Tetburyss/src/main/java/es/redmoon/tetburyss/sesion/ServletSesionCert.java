/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.sesion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author antonio
 */
public class ServletSesionCert extends HttpServlet {

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
             
        // Leer los datos de procedencia HOST, IP, URL
        String IP= request.getRemoteAddr();
        String HOST= request.getRemoteHost();
        String URI= request.getRequestURI();
        
        String varOU = request.getParameter("varOU");
        String SerialNumber = null;
        
        
        if (varOU != null)
        {
            RequestDispatcher rd =null;
            
            SerialNumber = request.getParameter("SerialNumber");

            // Obtenemos los valores de sesión
            SQLSesion mySesion= new SQLSesion();

            // grabar el log de acceso
            mySesion.LogSesion(IP, HOST, URI, varOU);

            if (mySesion.CheckLoginCert(varOU, SerialNumber))
            {
                // Asignamos los valores de sesión
                String sURL = "https://" + mySesion.getIp() 
                        + "/ServletSesion.servlet?xUser="+ mySesion.getxMail()
                        + "&xPass="+mySesion.getPassdatabase()
                        + "&ip="+mySesion.getIp()
                        + "&databasename="+mySesion.getDatabasename();

                //System.out.println(sURL);                

                //rd=request.getRequestDispatcher(sURL);
                //rd.forward(request, response);
                response.sendRedirect(sURL);

            }
            else
            {
                //System.err.println("Error en login usuario:"+xUser);
                rd=request.getRequestDispatcher("index.jsp?xMsj=Error en usuario o contraseña.");
                rd.forward(request, response);
            }
        }
        
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
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(ServletSesionCert.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(ServletSesionCert.class.getName()).log(Level.SEVERE, null, ex);
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
