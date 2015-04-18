/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.redmoon.tetburyss.alta.twilio;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import com.twilio.sdk.client.TwilioCapability;
import com.twilio.sdk.client.TwilioCapability.DomainException;

public class TwilioServlet extends HttpServlet {

    public static final String ACCOUNT_SID = "ACd1fb54d3e380838a26c15d8a5461c32e";

    public static final String AUTH_TOKEN = "317799e20c9d79467395e89383a03cfc";

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        // This is a special Quickstart application sid - or configure your own
        // at twilio.com/user/account/apps
        String applicationSid = "PNc59b4c7614d9ca8dc31228675521675b";
        TwilioCapability capability = new TwilioCapability(ACCOUNT_SID, AUTH_TOKEN);
        
        capability.allowClientOutgoing(applicationSid);
        capability.allowClientIncoming("jenny");
        
        String token = null;
        try {
            token = capability.generateToken();
        } catch (DomainException e) {
            e.printStackTrace();
        }
        // Forward the token information to a JSP view
        response.setContentType("text/html");
        request.setAttribute("token", token);
        RequestDispatcher view = request.getRequestDispatcher("client.jsp");
        view.forward(request, response);
     }
}