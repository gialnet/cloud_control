/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.alta;

import es.redmoon.tetburyss.conn.PoolConn;
import static es.redmoon.tetburyss.conn.PoolConn.PGconectar;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author antonio
 */
public class SQLAltaServicio extends PoolConn {

    public SQLAltaServicio() throws SQLException, NamingException {
        super();
    }

    /**
     * Asignar una base de datos disponible a un nuevo usuario
     * @param xNombre
     * @param xMail
     * @param xPais
     * @return
     * @throws SQLException 
     */
    public int AltaServicio(String xNombre, String xMail, int xPais) throws SQLException
    {
         Connection conn = PGconectar();
        
         int id_newuser=0;
         byte[] adjunto=null;
         String cuerpo="";
         String url = "https://www.myempresa.eu/ServletVerifyMail?url_wellcome=";
         
         System.out.println(url);
         
        try {
            
                PreparedStatement st = conn.prepareStatement("Select xidlibre,xurl_wellcome,xtoken from AltaServicio(?,?,?)");

                //st.registerOutParameter(1,Types.INTEGER);
                st.setString(1, xNombre);
                st.setString(2, xMail);
                st.setInt(3, xPais);

                ResultSet rs = st.executeQuery();
                
                if (rs.next())
                {
                    id_newuser = rs.getInt("xidlibre");
                    cuerpo = url + rs.getString("xurl_wellcome");
                    adjunto = rs.getBytes("xtoken");
                    
                    cuerpo = "Copie esta url en su navegador: " + cuerpo ;
                            
                    System.out.println(cuerpo);
                    System.out.println(adjunto.length);
                    
                    // enviar un mail de confirmación
                    SendEmail se = new SendEmail();
                    se.EnviarWithAdjunto(xMail, "Mensaje de bienvenida a myEmpresa.eu", cuerpo, adjunto);
                    
                }
                
                
                st.close();
            
            
        } catch (SQLException e) {
            System.out.println("AltaServicio() Connection Failed!"+ e.toString());
        }
        finally{
            conn.close();
        }
        
        return id_newuser;
    }
    
    /**
     * 
     * @param xNombre
     * @param xMail
     * @param xPais
     * @return
     * @throws SQLException 
     */
    public int AltaServicioGoogle(String xNombre, String xMail, String xPais, String xGenero, String xPlus) throws SQLException
    {
         Connection conn = PGconectar();
        
         int id_newuser=0;
         
         
        try {
            
                PreparedStatement st = conn.prepareStatement("Select xidlibre,xurl_wellcome from AltaServicioGoogle(?,?,?,?,?)");

                //st.registerOutParameter(1,Types.INTEGER);
                st.setString(1, xNombre);
                st.setString(2, xMail);
                st.setString(3, xPais);
                st.setString(4, xGenero);
                st.setString(5, xPlus);

                ResultSet rs = st.executeQuery();
                
                if (rs.next())
                {
                    id_newuser = rs.getInt("xidlibre");
                                        
                }
                
                st.close();
            
            
        } catch (SQLException e) {
            System.out.println("AltaServicio() Connection Failed!"+ e.toString());
        }
        finally{
            conn.close();
        }
        
        return id_newuser;
    }
    
    /**
     * Pedir una url de bienvenida
     * @return
     * @throws SQLException 
     */
    public String AltaServicioLinkedIn() throws SQLException
    {

        // Pedir una base de datos disponible mediante su url de bienvenida

        
        String url_wellcome=null;
        
        try (Connection conn = PGconectar()) {


            CallableStatement st = conn.prepareCall("{ ? = call PeticionAltaServicioLinkedIn() }");
            st.registerOutParameter(1,Types.VARCHAR);
            
            st.execute();
            
            url_wellcome=st.getString(1);
            
            conn.close();
        }
        
        return url_wellcome;
    }
    
    /**
     * Alta en el servicio vía Google
     * @return
     * @throws SQLException 
     */
    public String AltaServicioGoogle() throws SQLException
    {

        // Pedir una base de datos disponible mediante su url de bienvenida

        
        String url_wellcome=null;
        
        try (Connection conn = PGconectar()) {


            CallableStatement st = conn.prepareCall("{ ? = call PeticionAltaServicioGoogle() }");
            st.registerOutParameter(1,Types.VARCHAR);
            
            st.execute();
            
            url_wellcome=st.getString(1);
            
            conn.close();
        }
        
        return url_wellcome;
    }
    
    /**
     * 
     * @throws SQLException 
     */
    public List<TuplasPaises> getPaises() throws SQLException{
        Connection conn = PGconectar();

        List<TuplasPaises> Tuplas = new ArrayList<>();

        try {

            //st = conn.createStatement();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM customers_pais");
            
            
            ResultSet rs = st.executeQuery();


            while (rs.next()) {

                Tuplas.add(new TuplasPaises.Builder().
                        Id(rs.getInt("id")).
                        Descripcion(rs.getString("descripcion")).
                        ISO_3166_1(rs.getString("iso_3166_1")).
                        Cuenta_cliente(rs.getString("cuenta_cliente")).
                        Cuenta_ventas(rs.getString("cuenta_ventas")).
                        build()
                            );

            }
            
            st.close();

        } catch (SQLException e) {

            System.out.println("customers_pais Connection Failed!");
            return null;

        } finally {

            conn.close();
        }
        return Tuplas;
    }
}
