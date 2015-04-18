/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.servicios;

import es.redmoon.tetburyss.conn.PoolConn;
import static es.redmoon.tetburyss.conn.PoolConn.PGconectar;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author antonio
 */
public class SQLMensajes extends PoolConn {

    // Peticiones de información de los clientes a datos de Tetbury
    
    public SQLMensajes() throws SQLException, NamingException {
        super();
    }
    /**
     * Devuelve un String formato JSON
     * @param mail
     * @return
     * @throws SQLException 
     */
    public String ServiciosAutorizados(String mail) throws SQLException
    {
        // Lista de servicios autorizados a un cliente
        
        Connection conn = PGconectar();
        String servicios="";
        
        try {
            //System.err.println("Error en login usuario:"+xUser);
            PreparedStatement st = conn.prepareStatement("select * from TiposCuentas mail=?");
            st.setString(1, mail.trim());
            
            ResultSet rs = st.executeQuery();

                if (rs.next()) {

                    servicios=rs.getString("servicios");
                    
                }
                
           
           rs.close();
        }
        catch (SQLException e) {
            System.out.println("SELECT * from TiposCuentas Connection Failed!");
        }
        finally{
            conn.close();
        }
        
        return servicios;
    }

    
    /**
     * Añade o actualiza los datos de facturación de un cliente
     * @param xVarJSON
     * @throws SQLException 
     */
    public String UpdateDatosPer(String xVarJSON, String email) throws SQLException
    {
        
        Connection conn = PGconectar();
        String result="Ok";
        
        try {

            CallableStatement st = conn.prepareCall("{ call AddCliente(?,?) }");
            st.setString(1, xVarJSON);
            st.setString(2, email);
            st.execute();
            st.close();
            conn.close();
        }
        catch (SQLException e) {
            System.out.println("call AddCliente Connection Failed!");
            result="Error";
        }
        finally{
            conn.close();
        }
        return result;
        
    }
    
}
