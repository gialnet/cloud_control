/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.conn;

/**
 *
 * @author antonio
 */
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
//import org.checkthread.annotations.ThreadSafe;

/**
 *
 * @author antonio
 */
//@ThreadSafe
public abstract class PoolConn {
    
    private static DataSource datasource;
    //protected Connection connection;
    
    public PoolConn() throws SQLException, NamingException {
        
            Context ctx = new javax.naming.InitialContext();

            // 	 jdbc/myEmpresa001
            datasource = (DataSource) ctx.lookup("jdbc/mySecure");

    }
    
    /**
     * entregar una conexión a PostgreSQL desde el Pool de Glassfish
     * @return una conexión JDBC a PostgreSQL 9.2
     * @throws SQLException
     */
    public static Connection PGconectar() throws SQLException
    {

            Connection connection = null;

            synchronized (datasource) {
			connection = datasource.getConnection();
			}
            
            if (connection == null) {
                System.out.println("no se obtuvo la conexion");
            }
            
        return connection;
    }
    
}

