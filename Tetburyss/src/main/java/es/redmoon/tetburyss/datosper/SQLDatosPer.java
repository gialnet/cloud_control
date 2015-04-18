/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.datosper;

import es.redmoon.tetburyss.conn.PoolConn;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.naming.NamingException;

/**
 *
 * @author antonio
 */
public class SQLDatosPer extends PoolConn  {

    private int cont_facturas;
    private int cont_albaranes;
    private String Nif;
    private String Nombre;
    private String Direccion;
    private String Objeto;
    private String Poblacion;
    private String Movil;
    private String Mail;
    private String perido;
    private String fiscal_year;
    private BigDecimal irpf_profesionales;
    private String forma_juridica;
    private BigDecimal carga_impositiva;
    private String  Sociedades;
    private int DiasEndMonth;
    private int DiasPeriodo;
    private int DiasEndYear;
    private int NumeroMes;
    private int Doy;
    private String Fecha;
    private String income_account;
    private byte[] myPDF;

    public BigDecimal getIrpf_profesionales() {
        return irpf_profesionales;
    }
    
    public int getCont_facturas() {
        return cont_facturas;
    }

    public int getCont_albaranes() {
        return cont_albaranes;
    }

    public String getIncome_account() {
        return income_account;
    }

    public byte[] getMyPDF() {
        return myPDF;
    }

    public String getNif() {
        return Nif;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public String getObjeto() {
        return Objeto;
    }

    public String getPoblacion() {
        return Poblacion;
    }

    public String getMovil() {
        return Movil;
    }

    public String getMail() {
        return Mail;
    }

    public String getPerido() {
        return perido;
    }

    public String getFiscal_year() {
        return fiscal_year;
    }

    public String getForma_juridica() {
        return forma_juridica;
    }

    public BigDecimal getCarga_impositiva() {
        return carga_impositiva;
    }

    public String getSociedades() {
        return Sociedades;
    }

    public int getDiasPeriodo() {
        return DiasPeriodo;
    }
    
    public int getDiasEndYear() {
        return DiasEndYear;
    }


    public int getDiasEndMonth() {
        return DiasEndMonth;
    }

    public int getNumeroMes() {
        return NumeroMes;
    }

    public int getDoy() {
        return Doy;
    }

    public String getFecha() {
        return Fecha;
    }
    

    
    /**
     * 
     * @throws SQLException 
     */
    public SQLDatosPer() throws SQLException, NamingException
    {
        //connFact = ConnFact.create();
        //Connection conn = connFact.PGconectar();
        Connection conn = PGconectar();
        
        try {
            
            PreparedStatement st = conn.prepareStatement("SELECT * from datosper where id=1");
                //st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();

                if (rs.next()) {

                    this.income_account=rs.getString("income_account");
                    this.myPDF=rs.getBytes("plantilla_factura");
                    
                    this.Nif=rs.getString("nif");
                    this.Nombre=rs.getString("nombre");
                    this.Direccion=rs.getString("direccion");
                    this.Objeto=rs.getString("objeto");
                    this.Poblacion=rs.getString("poblacion");
                    this.Movil=rs.getString("movil");
                    this.Mail=rs.getString("mail");
                    this.fiscal_year=rs.getString("fiscal_year");
                    this.perido=rs.getString("periodo");
                    this.irpf_profesionales=rs.getBigDecimal("irpf_profesionales");
                    this.forma_juridica=rs.getString("forma_juridica");
                    this.Sociedades=rs.getString("sociedades");
                    this.carga_impositiva=rs.getBigDecimal("carga_impositiva");

                }
        
           this.DiasPeriodo = FaltanDias();
           this.DiasEndYear = FaltanEndYear();
           this.NumeroMes = Mes();
           this.DiasEndMonth = FinalMes();
           this.Doy=DiaYear();
           
           DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
           this.Fecha = df.format(new Date());
           
           df=null;
           rs.close();
        }
        catch (SQLException e) {
            System.out.println("SELECT * from datosper where id=1 Connection Failed!");
        }
        finally{
            conn.close();
        }
    }

    /**
     * Día del año en curso
     * @return 
     */
    private int DiaYear()
    {
        Calendar c1 = Calendar.getInstance(TimeZone.getDefault(), Locale.GERMANY);
        //c1.set(2013, 9, 5);
        int doy=c1.get(Calendar.DAY_OF_YEAR);
        return doy;
    }
    
    /**
     * Número de días para el final del trimestre
     * @return 
     */
    private int EndTrimestre()
    {
        Calendar c1 = Calendar.getInstance(TimeZone.getDefault(), Locale.GERMANY);
        int doy=0;
        
        
        // el campo mes tiene como base 0
        
        switch (this.perido.trim())
        {
            case "1":
            {
                c1.set(Integer.parseInt(fiscal_year), 3, 20);
                doy=c1.get(Calendar.DAY_OF_YEAR);
                break;
            }
            case "2":
            {
                c1.set(Integer.parseInt(fiscal_year), 6, 20);
                doy=c1.get(Calendar.DAY_OF_YEAR);
                break;
            }
            case "3":
            {
                c1.set(Integer.parseInt(fiscal_year), 9, 20);
                doy=c1.get(Calendar.DAY_OF_YEAR);
                break;
            }
            
            case "4":
            {
               doy=c1.getMaximum(Calendar.DAY_OF_YEAR) + 20;
                break;
            }
        }
        
        
        return doy;
    }
    
    /**
     * Días para que finalice el trimestre
     * @return 
     */
    private int FaltanDias()
    {
        int doy = DiaYear();
        int termina = EndTrimestre();
        return termina - doy;
    }
    
    /**
     * Número del mes
     * @return 
     */
    private int Mes()
    {
        Calendar c1 = Calendar.getInstance(TimeZone.getDefault(), Locale.GERMANY);
       
        return c1.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Días que falta para el final de mes
     * @return 
     */
    private int FinalMes()
    {
        Calendar c1 = Calendar.getInstance(TimeZone.getDefault(), Locale.GERMANY);
        //c1.set(2013, 9, 5);
        int dom = c1.get(Calendar.DAY_OF_MONTH);
        int finalmes = c1.getMaximum(Calendar.DAY_OF_MONTH);
        
        return finalmes-dom;
    }
    
    /**
     * Días que faltan para el final de año
     * @return 
     */
    private int FaltanEndYear()
    {
        int doy = DiaYear();
        
        return 365 -doy;
    }
    
    /**
     * Actualizar el porcentaje de retención para los servicios profesionales
     * @param irpf_profe
     * @throws SQLException 
     */
    public void UpdateIrpfProfesionales(BigDecimal irpf_profe) throws SQLException
    {
        Connection conn = PGconectar();

        try {


            PreparedStatement st = conn.prepareStatement("UPDATE DatosPer SET irpf_profesionales=? where id=1");
            st.setBigDecimal(1, irpf_profe);
            
            st.executeQuery();
            
            st.close();

        } catch (SQLException e) {

            System.out.println("UPDATE DatosPer Connection Failed!");


        } finally {

            conn.close();
        }
    }
    /**
     * Actualizar los datos de la empresa
     * @param xNIF
     * @param xNombre
     * @param xDireccion
     * @param xObjeto
     * @param xPoblacion
     * @param xMovil
     * @param xMail
     * @throws SQLException 
     */
    public void UpdateRazon(String xNIF, String xNombre, String xDireccion, String xObjeto, String xPoblacion,
            String xMovil, String xMail,String xCapital_social) throws SQLException
    {
        Connection conn = PGconectar();
       // System.out.println("capital:"+xCapital_social);
        try {

            PreparedStatement st = conn.prepareStatement("UPDATE DatosPer SET nif=?,nombre=?,direccion=?,objeto=?,poblacion=?,movil=?,mail=?,capital_social=? where id=1");
            st.setString(1, xNIF);
            st.setString(2, xNombre);
            st.setString(3, xDireccion);
            st.setString(4, xObjeto);
            st.setString(5, xPoblacion);
            st.setString(6, xMovil);
            st.setString(7, xMail);
            st.setBigDecimal(8, new BigDecimal(xCapital_social) );
            
            st.executeQuery();
            
            
            st.close();

        } catch (SQLException e) {

            System.out.println("Error SQLDatosPer UpdateRazon Connection Failed! Check output console");


        } finally {

            conn.close();
        }
    }
    
    /**
     * Cambiar el año fiscal y el periodo tributario
     * @param xYear
     * @param xPeriodo
     * @throws SQLException 
     */
    public void UpdateYearPeriodo(String xYear,String xPeriodo) throws SQLException
    {
        Connection conn = PGconectar();

        try {


            PreparedStatement st = conn.prepareStatement("UPDATE DatosPer SET fiscal_year=?,periodo=? where id=1");
            st.setString(1, xYear);
            st.setString(2, xPeriodo);
            
            st.executeQuery();
            
            
            st.close();

        } catch (SQLException e) {

            System.out.println("UPDATE DatosPer Connection Failed!");


        } finally {

            conn.close();
        }
    }
    /**
     * Cambiar de año de trabajo
     * @param xYear
     * @throws SQLException 
     */
    public void UpdateYear(String xYear) throws SQLException
    {
        Connection conn = PGconectar();

        try {


            PreparedStatement st = conn.prepareStatement("UPDATE DatosPer SET fiscal_year=? where id=1");
            st.setString(1, xYear);
            
            st.executeQuery();
            
            
            st.close();

        } catch (SQLException e) {

            System.out.println("UPDATE DatosPer Connection Failed!");


        } finally {

            conn.close();
        }
    }
    
    /**
     * Cambiar de trimestre de trabajo 1/2/3/4
     * @param xPeriodo
     * @throws SQLException 
     */
    public void UpdatePeriodo(String xPeriodo) throws SQLException
    {
        Connection conn = PGconectar();

        try {


            PreparedStatement st = conn.prepareStatement("UPDATE DatosPer SET periodo=? where id=1");
            st.setString(1, xPeriodo);
            
            st.executeQuery();
            
            st.close();

        } catch (SQLException e) {

            System.out.println("UPDATE DatosPer Connection Failed!");


        } finally {

            conn.close();
        }
    }
    /**
     * 
     * @throws SQLException 
     */
    public void nextFactura()  throws SQLException, NamingException
    {

        Connection conn = PGconectar();
        
        try {
            PreparedStatement st = null;
            ResultSet rs = null;
            
            st = conn.prepareStatement("select nextval('cont_facturas')");
            
            rs = st.executeQuery();

                if (rs.next()) {

                    cont_facturas=rs.getInt(1);

                }
                
           rs.close();
        }
        catch (SQLException e) {
            System.out.println("select nextval('cont_facturas') Connection Failed!");
        }
        finally{
            conn.close();
        }
    }
    
    /**
     * 
     * @throws SQLException 
     */
    public void nextAlbaran()  throws SQLException, NamingException
    {
        Connection conn = PGconectar();
        
        try {
            PreparedStatement st = null;
            ResultSet rs = null;
            
            st = conn.prepareStatement("select nextval('cont_albaranes')");
            
            rs = st.executeQuery();

                if (rs.next()) {

                    cont_facturas=rs.getInt(1);

                }
                
           rs.close();
        }
        catch (SQLException e) {
            System.out.println("select nextval('cont_albaranes') Connection Failed!");
        }
        finally{
            conn.close();
        }
    }
}
