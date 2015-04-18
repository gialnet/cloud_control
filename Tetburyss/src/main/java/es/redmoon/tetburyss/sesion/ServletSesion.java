

package es.redmoon.tetburyss.sesion;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author antonio
 */
public class ServletSesion extends HttpServlet {

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
            throws ServletException, IOException, SQLException, NamingException, FileUploadException, NoSuchAlgorithmException, NoSuchProviderException {
        
        response.setContentType("text/html;charset=UTF-8");
             
        // Leer los datos de procedencia HOST, IP, URL
        String IP= request.getRemoteAddr();
        String HOST= request.getRemoteHost();
        String URI= request.getRequestURI();
        
        
        
        byte[] token = null;
        String xDocu = null;        
        String xUser = null; //request.getParameter("xUser");
        String xPass = null; // request.getParameter("xPass");
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
        if (!isMultipart)
        {
            RequestDispatcher rd=request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
            //response.sendRedirect("error.jsp");
        }
        
        // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload();

            // Parse the request
            FileItemIterator iter = upload.getItemIterator(request);
            
            while (iter.hasNext()) {
                
                FileItemStream item = iter.next();
                
                String name = item.getFieldName();
                
                InputStream stream = item.openStream();
                
                if (item.isFormField()) {
                    /*System.out.println("Form field " + name + " with value "
                        + Streams.asString(stream) + " detected.");*/
                    if (name.equals("xUser"))
                        xUser=Streams.asString(stream);
                    else if (name.equals("xPass"))
                        xPass=Streams.asString(stream);
                    else if (name.equals("xDocu"))
                        xDocu=Streams.asString(stream);
                    
                } else {
                    /*System.out.println("File field " + name + " with file name "
                        + item.getName() + " detected.");*/
                    // Process the input stream
                    token = IOUtils.toByteArray(stream);
                    stream.close();
                
                }
            }
                
        
            
        // comprobar los datos aportados
            
        try {
            
            RequestDispatcher rd =null;
            
            // Obtenemos los valores de sesión
            SQLSesion mySesion= new SQLSesion();
            
            // grabar el log de acceso
            mySesion.LogSesion(IP, HOST, URI, xUser);
        
            if (mySesion.CheckLogin(xUser, token))
            {
                // Asignamos los valores de sesión
                String sURL = "https://" + mySesion.getIp() 
                        + "/ServletSesion.servlet?xUser="+ xUser
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
            
             
            
        }catch (ServletException e) 
        {
            System.err.println("Error en login");
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
            Logger.getLogger(ServletSesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ServletSesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileUploadException ex) {
            Logger.getLogger(ServletSesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServletSesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ServletSesion.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServletSesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ServletSesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileUploadException ex) {
            Logger.getLogger(ServletSesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServletSesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ServletSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login process";
    }// </editor-fold>
}
