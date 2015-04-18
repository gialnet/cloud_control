
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>Login</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<!--[if IE 8]><link href="css/ie8.css" rel="stylesheet" type="text/css" /><![endif]-->
<!--[if IE 9]><link href="css/ie9.css" rel="stylesheet" type="text/css" /><![endif]-->
<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>

<script type="text/javascript" src="js/plugins/forms/jquery.uniform.min.js"></script>

<script type="text/javascript" src="js/files/bootstrap.min.js"></script>

<script type="text/javascript" src="js/files/login.js"></script>

</head>

<body class="no-background">

	<!-- Fixed top -->
	<div id="top">
		<div class="fixed">
			<a href="inicio.jsp" title="" class="logo"><img src="img/logo.png" alt="" /></a>
			<!--<ul class="top-menu">
				<li class="dropdown">
					<a class="login-top" data-toggle="dropdown"></a>
					<ul class="dropdown-menu pull-right">
						<li><a href="#" title=""><i class="icon-group"></i>Change user</a></li>
						<li><a href="#" title=""><i class="icon-plus"></i>New user</a></li>
						<li><a href="#" title=""><i class="icon-cog"></i>Settings</a></li>
						<li><a href="#" title=""><i class="icon-remove"></i>Go to the website</a></li>
					</ul>
				</li>
			</ul>-->
		</div>
	</div>
	<!-- /fixed top -->


    <!-- Login block -->
    <div class="login">
        <div class="navbar">
            <div class="navbar-inner">
                <h6><i class="icon-user"></i>Login page</h6>
                <div class="nav pull-right">
                    <a href="#" class="dropdown-toggle navbar-icon" data-toggle="dropdown"><i class="icon-cog"></i></a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="#"><i class="icon-plus"></i>registrar</a></li>
                        <li><a href="#"><i class="icon-refresh"></i>recuperar contraseña</a></li>
                        
                    </ul>
                </div>
            </div>
        </div>
        <%
                String subject = request.getHeader("x-ssl-subject");
                String serial = request.getHeader("x-ssl-client-serial");
                String verify = request.getHeader("x-client-verify");
                String varOU="";
                
                if (verify.equals("SUCCESS"))
                {
                    // /C=UK/ST=Gloucestershire/L=Tetbury/O=TETBURY SOFTWARE SERVICES Ltd/OU=myEmpresa1E/emailAddress=secure@retburyss.co.uk/CN=cdp001.myEmpresa.eu
                    int indice=subject.indexOf("/OU=");
                    if (indice>0)
                    {
                        varOU=subject.substring(indice+4, subject.indexOf("/emailAddress="));
                        response.sendRedirect("https://www.myempresa.eu/ServletSesionCert.servlet?varOU="+varOU+"&SerialNumber="+serial);
                        
                    }
                    
                    
                }

        %>
        <div class="well">
            <form action="ServletSesion.servlet" class="row-fluid" enctype="multipart/form-data" method="post">
                <%
                    String mensaje = request.getParameter("xMsj");
                    if( mensaje!=null && mensaje.length()>0)
                         out.write("<div class=\"control-group\"><p class=\"text-error\">"+mensaje+"</p></div>");
                %>
                <div class="control-group">
                    <label class="control-label">Username</label>
                    <div class="controls"><input class="span12" type="text" name="xUser" placeholder="tucorreo@eres.tu" /></div>
                </div>
                
                <div class="control-group">
                    <label class="control-label">Token:</label>
                    <div class="controls"><input class="span12" type="file" name="xToken" /></div>
                </div>

                <div class="control-group">
                    <div class="controls"><label class="checkbox inline"><input type="checkbox" name="checkbox1" class="styled" value="" checked="checked">Remember me</label></div>
                </div>

                <div class="login-btn"><input type="submit" value="Acceder" class="btn btn-danger btn-block" /></div>
            </form>
        </div>
    </div>
    <!-- /login block -->


	<!-- Footer -->
	<div id="footer">
            <div class="copyrights">2013 &copy;  Tetbury Software Services Ltd. <%= varOU %></div>
		<ul class="footer-links">
			<li><a href="" title=""><i class="icon-cogs"></i>Contactar con el administrador</a></li>
			<li><a href="" title=""><i class="icon-screenshot"></i>Reportar bug</a></li>
		</ul>
	</div>
	<!-- /footer -->

</body>
</html>
