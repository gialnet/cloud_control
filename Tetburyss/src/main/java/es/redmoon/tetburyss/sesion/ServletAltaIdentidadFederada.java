
package es.redmoon.tetburyss.sesion;

import es.redmoon.tetburyss.alta.SQLAltaServicio;
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
public class ServletAltaIdentidadFederada extends HttpServlet {

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
        
        String xPais = request.getParameter("locale");
        String xMail = request.getParameter("email");
        String xNombre = request.getParameter("name");
        String xGenero = request.getParameter("gender");
        String xPlus = request.getParameter("link");
        
        // Leer los datos de procedencia HOST, IP, URL
        String IP= request.getRemoteAddr();
        String HOST= request.getRemoteHost();
        String URI= request.getRequestURI();
        
        SQLSesion mySesion= new SQLSesion();
        mySesion.LogSesion(IP, HOST, URI, xMail);
        
         // si ya existe la cuenta
    if (mySesion.CheckMailGoogle(xMail))
    {
        // Asignamos los valores de sesión y entramos
        // si no descargó el tokem.p12
        if (mySesion.getDescargo_token().equals("N"))
        {
        String sURL = "https://" + mySesion.getIp() 
                + "/ServletSesion.servlet?xUser="+ xMail
                + "&xNombre="+ xNombre
                + "&xPass="+mySesion.getPassdatabase()
                + "&databasename="+mySesion.getDatabasename();

        //System.out.println(sURL);                

        //RequestDispatcher rd=request.getRequestDispatcher(sURL);
        //rd.forward(request, response);
        response.sendRedirect(sURL);
        }
        else
        {
            // Mostrar la pantalla de login vía Token
            RequestDispatcher rd=request.getRequestDispatcher("https://www.myempresa.eu/index.jsp");
            rd.forward(request, response);
            //response.sendRedirect("https://www.myempresa.eu/index.jsp");
        }

    }
    else
    {
        // No exite como usuario en la base de datos
        // asignar una base de datos nueva
        SQLAltaServicio as = new SQLAltaServicio();

        int resultado = as.AltaServicioGoogle(xNombre, xMail, xPais, xGenero, xPlus);
        // resultado -1 país aún no soportado
        // resultado -2 no hay bases de datos disponible
        // resultado > 0 base de datos asignada correctamente
        if (resultado==-1)
        {
            // enviar un mail indicando el problema
            response.getWriter().println("<h1>Su país aún no está disponible, le notificaremos cuando sea una realidad<h1>");
        }
        else if (resultado==-2)
        {
            // enviar un mail indicando el problema
            response.getWriter().println("<h1>No tengo bases de datos disponibles en este momento, le notificaré cuando sea posible<h1>");
        }
        else
        {
            // ir a la pantalla de bienvenida
            if (mySesion.CheckMailGoogle(xMail))
                {
                    // Asignamos los valores de sesión y entramos
                    // si no descargó el tokem.p12
                    
                    String stURL = "https://" + mySesion.getIp() 
                            + "/ServletSesionGoogle.servlet?xUser="+ xMail
                            + "&xNombre="+ xNombre
                            + "&xPais="+xPais
                            + "&xGenero="+xGenero
                            + "&databasename="+mySesion.getDatabasename();

                    //System.out.println(sURL);

                    
                    //RequestDispatcher rd=request.getRequestDispatcher(stURL);
                    //rd.forward(request, response);
                
                    response.sendRedirect(stURL);
                    
                    }
                    else
                    {
                        // Mostrar la pantalla de login vía Token
                        RequestDispatcher rd=request.getRequestDispatcher("https://www.myempresa.eu/index.jsp");
                        rd.forward(request, response);
                        //response.sendRedirect("https://myempresa.eu/index.jsp");
                    }

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
        } catch (SQLException ex) {
            Logger.getLogger(ServletAltaIdentidadFederada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ServletAltaIdentidadFederada.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServletAltaIdentidadFederada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ServletAltaIdentidadFederada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Control de la identidad federada";
    }// </editor-fold>
}
