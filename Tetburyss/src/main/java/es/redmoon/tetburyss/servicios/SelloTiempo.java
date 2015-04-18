
package es.redmoon.tetburyss.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.tsp.TimeStampResp;
import org.bouncycastle.tsp.TSPAlgorithms;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;


/**
 *
 * @author antonio
 */
public class SelloTiempo {
    
    
    /**
     * Generar una petición de firma de un documento
     * @param doc
     * @return TimeStampRequest
     */
    public TimeStampRequest genera(byte[] doc)
    {
        // Creamos el generador de la peticion
        TimeStampRequestGenerator generadorPeticion = new TimeStampRequestGenerator();
        generadorPeticion.setCertReq(true);
        
        //TSPAlgorithms.SHA512
        TimeStampRequest peticion = generadorPeticion.generate(TSPAlgorithms.SHA512, doc);
        
        return peticion;
        
    }
    
    /**
     * 
     * @param tsa_url
     * @param longitud
     * @return 
     */
    private HttpURLConnection creaConexion(String tsa_url, int longitud){
		
		URL url;
		try {
			// Creamos la conexion http
			url = new URL(tsa_url);
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			
			// Configuramos la conexion
			conexion.setDoInput(true);
			conexion.setDoOutput(true);
			conexion.setRequestMethod("POST");
			conexion.setRequestProperty("Content-type", "application/timestamp-query"); 
			conexion.setRequestProperty("Content-length", String.valueOf(longitud));
			
			return conexion;
			
		} catch (MalformedURLException e) {
			String error = "Fallo al crear la URL para la conexión.";
			Logger.getLogger(SelloTiempo.class.getName()).log(Level.SEVERE, error, e);
			return null;
		} catch (IOException e) {
			String error = "Fallo al crear la conexión.";
			Logger.getLogger(SelloTiempo.class.getName()).log(Level.SEVERE, error, e);
			return null;
		}
	}
    

    /**
     * 
     * @param peticion
     * @param conexion
     * @return
     * @throws TSPException 
     */
    private static ASN1Sequence realizaPeticion(TimeStampRequest peticion, HttpURLConnection conexion) throws TSPException{
		
		try {
			// Obtenemos la peticion en formato ASN.1
			byte[] peticionASN1;
			
			peticionASN1 = peticion.getEncoded();
			OutputStream out = conexion.getOutputStream();
			out.write(peticionASN1);
			out.flush();
			
			// Comprobamos que todo ha ido bien
			if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK) { 
				throw new IOException("Received HTTP error: " + conexion.getResponseCode() + " - " + conexion.getResponseMessage()); 
			} 
			
			// Leemos el flujo de entrada
			InputStream in = conexion.getInputStream();
	        
			// Obtenemos la respuesta a nuestra peticion, el sello   
			ASN1InputStream asn1Is = new ASN1InputStream(in);
			ASN1Sequence secuencia = ASN1Sequence.getInstance(asn1Is.readObject());
			
			// Se valida que la respuesta sea la petición enviada
                        //PKIStatusInfo pkiStatusInfo = null;
			TimeStampResp tspResp = new TimeStampResp(secuencia);
			TimeStampResponse response = new TimeStampResponse(tspResp);
			response.validate(peticion);
			
			return secuencia;
			
		} catch (IOException | TSPException e) {
			String error = "Fallo al realizar la petición.";
			Logger.getLogger(SelloTiempo.class.getName()).log(Level.SEVERE, error, e);
			return null;
		}
	}
}
