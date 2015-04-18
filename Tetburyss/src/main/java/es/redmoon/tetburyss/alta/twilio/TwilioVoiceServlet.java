/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.alta.twilio;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.Dial;
import com.twilio.sdk.verbs.Client;
import com.twilio.sdk.verbs.Number;

public class TwilioVoiceServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String phoneNumber = request.getParameter("PhoneNumber");
         /* Use this as the caller ID when making calls from a browser. */
        String callerId = "+14105551234";
        
        TwiMLResponse twiml = new TwiMLResponse();
        
        Dial dial = new Dial();
        
        Client client = new Client("jenny");
        
        try {
             if (phoneNumber != null) {
                dial.append(new Number(phoneNumber));
                } 
             else {
                dial.append(new Client("jenny"));
                dial.setCallerId(callerId);
                }
            twiml.append(dial);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
        
        response.setContentType("application/xml");
        response.getWriter().print(twiml.toXML());
    }
}