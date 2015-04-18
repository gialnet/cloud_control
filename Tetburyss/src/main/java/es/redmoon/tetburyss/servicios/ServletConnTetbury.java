

package es.redmoon.tetburyss.servicios;

import com.google.gson.Gson;
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
 * Para comunicar los nodos clientes con Tetbury
 * vía mensajes en JSON
 * 
 * @author antonio
 */
public class ServletConnTetbury extends HttpServlet {

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
        
        String accion = request.getParameter("accion");
        String email = request.getParameter("email");
        String mensajeJSON = request.getParameter("xVarJSON");
        
        Gson gson = new Gson();
        
        SQLMensajes msj= new SQLMensajes();
        
        /*
         * Pedir la lista de servicios de una cuenta
         */
         switch (accion) {
             case "ServiciosAutorizados":
                {
                    // devuelve en JSON la lista de servicios disponibles
                    String msg = msj.ServiciosAutorizados(email);
                    response.getWriter().write(msg);
                    break;
                }
             case "UpdateDatosPer":
                {
                    // Actualizar los datos del cliente en Tetbury
                    
                    
                    response.getWriter().write(msj.UpdateDatosPer(mensajeJSON, email));
                    break;
                }
             default:
                response.getWriter().write("Error, mensaje no conteplado: "+accion);
                break;
         }
        
         accion=null;
         email=null;
         mensajeJSON=null;
         msj=null;
        
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
            Logger.getLogger(ServletConnTetbury.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ServletConnTetbury.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServletConnTetbury.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ServletConnTetbury.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Mensajes de secure.tetburyss.co.uk";
    }// </editor-fold>
}
