/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.sesion.facebook;

import com.google.appengine.repackaged.com.google.common.collect.ImmutableMap;
import es.redmoon.tetburyss.alta.SQLAltaServicio;
import es.redmoon.tetburyss.sesion.SQLSesion;
import org.apache.http.HttpEntity;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * A través de este Servlet se comunica el sistema de identificación federada con
 * nuestro servicio.
 * 
 * Una pagina de Google para hacer pruebas.
 * https://developers.google.com/oauthplayground/?code=4/ObFRYS3vTWkt1aRu-G4NXJ26EhRV.si5rBcsFiD0fOl05ti8ZT3bPeou1hAI
 * 
 * consola de administración
 * https://cloud.google.com/console?redirected=true#/project/1036825226740/apiui/app/WEB/1036825226740.apps.googleusercontent.com
 * 
 * @author antonio
 */
public class CallbackServlet extends HttpServlet {

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
        
       
   // if the user denied access, we get back an error, ex
   // error=access_denied&state=session%3Dpotatoes
    
   if (request.getParameter("error") != null) {
    response.getWriter().println(request.getParameter("error"));
    return;
   }
    
   // google returns a code that can be exchanged for a access token
   // google nos devuelve un codigo para ser intercambiado en la petición
   // de un token
   String code = request.getParameter("code");
    
   // get the access token by post to Google
   // Pedir un token a Google mediante un mensaje POST
   // scope= https://www.googleapis.com/auth/userinfo.email 
   //.put("scope","https://www.googleapis.com/auth/userinfo.profile")
   String body = post("https://accounts.google.com/o/oauth2/token", ImmutableMap.<String,String>builder()
     .put("code", code)
     .put("client_id", "1036825226740.apps.googleusercontent.com")
     .put("client_secret", "tlNwN-uPrroReUK7ry52Zxnh")
     .put("redirect_uri", "http://myempresa.eu/oauth2callback")
     .put("grant_type", "authorization_code").build());
 
    // ex. returns
    //   {
    //       "access_token": "ya29.AHES6ZQS-BsKiPxdU_iKChTsaGCYZGcuqhm_A5bef8ksNoU",
    //       "token_type": "Bearer",
    //       "expires_in": 3600,
    //       "id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjA5ZmE5NmFjZWNkOGQyZWRjZmFiMjk0NDRhOTgyN2UwZmFiODlhYTYifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwiZW1haWwiOiJhbmRyZXcucmFwcEBnbWFpbC5jb20iLCJhdWQiOiI1MDgxNzA4MjE1MDIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdF9oYXNoIjoieUpVTFp3UjVDX2ZmWmozWkNublJvZyIsInN1YiI6IjExODM4NTYyMDEzNDczMjQzMTYzOSIsImF6cCI6IjUwODE3MDgyMTUwMi5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImlhdCI6MTM4Mjc0MjAzNSwiZXhwIjoxMzgyNzQ1OTM1fQ.Va3kePMh1FlhT1QBdLGgjuaiI3pM9xv9zWGMA9cbbzdr6Tkdy9E-8kHqrFg7cRiQkKt4OKp3M9H60Acw_H15sV6MiOah4vhJcxt0l4-08-A84inI4rsnFn5hp8b-dJKVyxw1Dj1tocgwnYI03czUV3cVqt9wptG34vTEcV3dsU8",
    //       "refresh_token": "1/Hc1oTSLuw7NMc3qSQMTNqN6MlmgVafc78IZaGhwYS-o"
    //   }
    
   JSONObject jsonObject = null;
    
   // get the access token from json and request info from Google
   // Crear un objeto JSON a partir de la respuesta devuelta por Google
   try {
    jsonObject = (JSONObject) new JSONParser().parse(body);
   } catch (ParseException e) {
    throw new RuntimeException("Unable to parse json " + body);
   }
    
   // google tokens expire after an hour, 
   // but since we requested offline access we can get a new token without user involvement via the refresh token
   // Un token de Google tiene un tiempo de vida de uan hora
   // pero come hemos solicitado acceso offline podemos obtener un nuevo 
   // token sin participación del usuario a través de la actualización del token
   String accessToken = (String) jsonObject.get("access_token");
      
   // you may want to store the access token in session
   request.getSession().setAttribute("access_token", accessToken);
    
   // get some info about the user with the access token
   // Pedir la dirección de email mediante una petición GET
   String json = get(new StringBuilder("https://www.googleapis.com/oauth2/v2/userinfo?access_token=").append(accessToken).toString());
    
   // now we could store the email address in session
   /*
    {
        "id": "108068397209142441065",
        "email": "antonio.gialnet@gmail.com",
        "verified_email": true,
        "name": "antonio perez caballero",
        "given_name": "antonio",
        "family_name": "perez caballero",
        "link": "https://plus.google.com/108068397209142441065",
        "gender": "male",
        "locale": "es"
     }


    */
   JSONObject jsonObjectGoogle = null; 
   
   try {
       
    jsonObjectGoogle = (JSONObject) new JSONParser().parse(json);
    
   } catch (ParseException e) {
    throw new RuntimeException("Unable to parse json " + body);
   }
   
   String xMail = (String) jsonObjectGoogle.get("email");
   String xNombre = (String) jsonObjectGoogle.get("name");
   String xPais = (String) jsonObjectGoogle.get("locale");
   String xGenero = (String) jsonObjectGoogle.get("gender");
   String xPlus = (String) jsonObjectGoogle.get("link");
   
   request.getSession().setAttribute("email", xMail);
   
   // return the json of the user's basic info
   /*
   response.getWriter().println(json);
   response.getWriter().println(xMail);
   response.getWriter().println(xNombre);
   response.getWriter().println(xPais);*/
  
    // Obtenemos los valores de sesión
    // Leer los datos de procedencia HOST, IP, URL
     String IP= request.getRemoteAddr();
     String HOST= request.getRemoteHost();
     String URI= request.getRequestURI();

     SQLSesion mySesion= new SQLSesion();

     // grabar el log de acceso
     mySesion.LogSesion(IP, HOST, URI, xMail);
   
     // Mostrar la pantalla de bienvenida al servicio.
     
   
     // si ya existe la cuenta
    if (mySesion.CheckMailGoogle(xMail))
    {
        // Asignamos los valores de sesión y entramos
        // si no descargó el tokem.p12
        if (mySesion.getDescargo_token().equals("N"))
        {
        String sURL = "https://" + mySesion.getIp() 
                + ":8181/ServletSesion?xUser="+ xMail
                + "&xNombre="+ xNombre
                + "&xPass="+mySesion.getPassdatabase()
                + "&databasename="+mySesion.getDatabasename();

        //System.out.println(sURL);                

        response.sendRedirect(sURL);
        }
        else
        {
            // Mostrar la pantalla de login vía Token
            response.sendRedirect("https://myempresa.eu/index.jsp");
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
                            + "/ServletSesionGoogle?xUser="+ xMail
                            + "&xNombre="+ xNombre
                            + "&xPais="+xPais
                            + "&xGenero="+xGenero
                            + "&databasename="+mySesion.getDatabasename();

                    //System.out.println(sURL);

                    /*
                    RequestDispatcher rd =null;
                    rd=request.getRequestDispatcher(stURL);
                    rd.forward(request, response);*/
                
                    response.sendRedirect(stURL);
                    
                    }
                    else
                    {
                        // Mostrar la pantalla de login vía Token
                        response.sendRedirect("https://myempresa.eu/index.jsp");
                    }

                }
        }


}
    
 /**
  * makes a GET request to url and returns body as a string
  * @param url
  * @return
  * @throws ClientProtocolException
  * @throws IOException 
  */
 public String get(String url) throws ClientProtocolException, IOException {
  return execute(new HttpGet(url));
 }
  
 
 /**
  * makes a POST request to url with form parameters and returns body as a string
  * @param url
  * @param formParameters
  * @return
  * @throws ClientProtocolException
  * @throws IOException 
  */
 public String post(String url, Map<String,String> formParameters) throws ClientProtocolException, IOException {
  HttpPost request = new HttpPost(url);
    
  List <NameValuePair> nvps = new ArrayList <>();
   
  for (String key : formParameters.keySet()) {
   nvps.add(new BasicNameValuePair(key, formParameters.get(key)));
  }
 
  request.setEntity(new UrlEncodedFormEntity(nvps));
   
  return execute(request);
 }
  
 
 /**
  * makes request and checks response code for 200
  * @param request
  * @return
  * @throws ClientProtocolException
  * @throws IOException 
  */
 private String execute(HttpRequestBase request) throws ClientProtocolException, IOException {
     
  HttpClient httpClient = new DefaultHttpClient();
        org.apache.http.HttpResponse response = httpClient.execute(request);
      
  HttpEntity entity = response.getEntity();
  
  String body = EntityUtils.toString(entity);
 
  if (response.getStatusLine().getStatusCode() != 200) {
   throw new RuntimeException("Expected 200 but got " + response.getStatusLine().getStatusCode() + ", with body " + body);
  }
 
     return body;
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
            Logger.getLogger(CallbackServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(CallbackServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CallbackServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(CallbackServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Tomar los datos de la identidad federada";
    }// </editor-fold>
}
