<%-- 
    Document   : client
    Created on : 22-ene-2014, 19:46:17
    Author     : antonio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Hello Client Monkey 3</title>
<script type="text/javascript"
src="//static.twilio.com/libs/twiliojs/1.1/twilio.min.js"></script>
<script type="text/javascript"
src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js">
</script>
<link href="https://static0.twilio.com/packages/quickstart/client.css"
type="text/css" rel="stylesheet" />
<script type="text/javascript">
    Twilio.Device.setup("${token}", {debug: true});
    
    Twilio.Device.ready(function (device) {
        $("#log").text("Ready");
    });
    
    Twilio.Device.error(function (error) {
        $("#log").text("Error: " + error.message);
    });
    Twilio.Device.connect(function (conn) {
    $("#log").text("Successfully established call");
    });
    
    Twilio.Device.disconnect(function (conn) {
        $("#log").text("Call ended");
    });
    
    /* Listen for incoming connections */
    Twilio.Device.incoming(function (conn) {
        $("#log").text("Incoming connection from " + conn.parameters.From);
        // accept the incoming connection and start two-way audio
        conn.accept();
    });
    
    function call() {
        // get the phone number to connect the call to
        params = {"PhoneNumber": $("#number").val()};
        Twilio.Device.connect(params);
    }
    
    function hangup() {
        Twilio.Device.disconnectAll();
    }
</script>
</head>
<body>
<button class="call" onclick="call();">
Call
</button>
<button class="hangup" onclick="hangup();">
Hangup
</button>
    <input type="text" id="number" name="number"
placeholder="Enter a phone number to call"/>
<div id="log">Loading pigeons...</div>
</body>
</html>