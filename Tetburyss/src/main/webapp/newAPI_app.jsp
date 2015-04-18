<%-- 
    Document   : newAsesor
    Created on : 30-nov-2013, 6:20:55
    Author     : antonio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
        <link href="css/main.css" rel="stylesheet" type="text/css" />
        <title>Añadir Asesor</title>
        <!--[if IE 8]><link href="css/ie8.css" rel="stylesheet" type="text/css" /><![endif]-->
        <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>

            <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
            <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
            

            <script type="text/javascript" src="js/plugins/charts/excanvas.min.js"></script>
            <script type="text/javascript" src="js/plugins/charts/jquery.flot.js"></script>
            <script type="text/javascript" src="js/plugins/charts/jquery.flot.resize.js"></script>
            <script type="text/javascript" src="js/plugins/charts/jquery.sparkline.min.js"></script>

            <script type="text/javascript" src="js/plugins/ui/jquery.easytabs.min.js"></script>
            <script type="text/javascript" src="js/plugins/ui/jquery.collapsible.min.js"></script>
            <script type="text/javascript" src="js/plugins/ui/jquery.mousewheel.js"></script>
            <script type="text/javascript" src="js/plugins/ui/prettify.js"></script>
            <script type="text/javascript" src="js/plugins/ui/jquery.bootbox.min.js"></script>
            <script type="text/javascript" src="js/plugins/ui/jquery.colorpicker.js"></script>
            <script type="text/javascript" src="js/plugins/ui/jquery.timepicker.min.js"></script>
            <script type="text/javascript" src="js/plugins/ui/jquery.jgrowl.js"></script>
            <script type="text/javascript" src="js/plugins/ui/jquery.fancybox.js"></script>
            <script type="text/javascript" src="js/plugins/ui/jquery.fullcalendar.min.js"></script>
            <script type="text/javascript" src="js/plugins/ui/jquery.elfinder.js"></script>

            <script type="text/javascript" src="js/plugins/uploader/plupload.js"></script>
            <script type="text/javascript" src="js/plugins/uploader/plupload.html4.js"></script>
            <script type="text/javascript" src="js/plugins/uploader/plupload.html5.js"></script>
            <script type="text/javascript" src="js/plugins/uploader/jquery.plupload.queue.js"></script>

            <script type="text/javascript" src="js/plugins/forms/jquery.uniform.min.js"></script>
            <script type="text/javascript" src="js/plugins/forms/jquery.autosize.js"></script>
            <script type="text/javascript" src="js/plugins/forms/jquery.inputlimiter.min.js"></script>
            <script type="text/javascript" src="js/plugins/forms/jquery.tagsinput.min.js"></script>
            <script type="text/javascript" src="js/plugins/forms/jquery.inputmask.js"></script>
            <script type="text/javascript" src="js/plugins/forms/jquery.select2.min.js"></script>
            <script type="text/javascript" src="js/plugins/forms/jquery.listbox.js"></script>
            <script type="text/javascript" src="js/plugins/forms/jquery.validation.js"></script>
            <script type="text/javascript" src="js/plugins/forms/jquery.validationEngine-en.js"></script>
            <script type="text/javascript" src="js/plugins/forms/jquery.form.wizard.js"></script>
            <script type="text/javascript" src="js/plugins/forms/jquery.form.js"></script>
            <script type="text/javascript" src="js/plugins/tables/jquery.dataTables.min.js"></script>
            <script type="text/javascript" src="js/files/bootstrap.min.js"></script>
            <script type="text/javascript" src="js/files/functions.js"></script>
            
</head>
<body>

<!-- Fixed top -->
<div id="top">
            <div class="fixed">
                <a href="inicio.jsp" title="" class="logo"><img src="img/logo.png" alt="" /></a>
                <ul class="top-menu">
                    <li><a class="fullview"></a></li>


                    <li class="dropdown">
                        <a class="user-menu" data-toggle="dropdown"><!--<img src="img/userpic.png" alt="" />-->
                            <img src="img/person.png" width="20" height="20" alt="" /><span></span></a>
                        <ul class="dropdown-menu">

                            <li><a href="DatosFacturacion.jsp" title=""><i class="icon-cog"></i>Mi Configuraci&oacute;n</a></li>
                            <li><a href="logout.jsp" title=""><i class="icon-remove"></i>Salir</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
<!-- /fixed top -->


<!-- Content container -->
<div id="container">

    <!-- Sidebar -->
    <div id="sidebar">

        <div class="sidebar-tabs">
            <ul class="tabs-nav two-items">
                <li><a href="#general" title=""><i class="icon-reorder"></i></a></li>
                <li><a href="#stuff" title=""><i class="icon-cogs"></i></a></li>
            </ul>

            <div id="general">		            

                <!-- Main navigation -->
               
                <!-- /main navigation -->

            </div>

            <div id="stuff">
                <!-- Datepicker -->
                <div class="widget">
                    <h6 class="widget-name"><i class="icon-calendar"></i>Calendario</h6>
                    <div class="inlinepicker datepicker-liquid"></div>
                </div>
                <!-- /datepicker -->
            </div>

        </div>
    </div>
    <!-- /sidebar -->


    <!-- Content -->
    <div id="content">

        <!-- Content wrapper -->
        <div class="wrapper">

            <!-- Breadcrumbs line -->
            <div class="crumbs">
                <ul id="breadcrumbs" class="breadcrumb"> 
                    <li><a href="DatosFacturacion.jsp">Datos de la Empresa</a></li>
                    <li class="active"><a href="#">Alta de asesor</a></li>
                    <!--<li class="active"><a href="calendar.html" title="">Calendar</a></li>-->
                </ul>

                <ul class="alt-buttons">

                </ul>
            </div>
            <!-- /breadcrumbs line -->

            <!-- Page header -->
            <div class="page-header">
                <div class="page-title">

                    <h5 id="xTitulo">Alta de una aplicación</h5>

                </div>			    	
            </div>
            
            <div class="row-fluid">

                <div class="span12">

    <form class="form-horizontal" action="ServletAPI.do" method="post" accept-charset="UTF-8">
        <div  class="well">

    <fieldset>

        <div class="control-group">
            <label class="control-label" for="xNombre">Nombre de la aplicación:</label>
            <div class="controls">
                <input type="text" name="xNombre" id="xNombre" maxlength="90" class="span12"
                   placeholder="Nombre de la aplicación" required="required">
            </div>

        </div>

        <div class="control-group">
            <label class="control-label" for="xURI">URI:</label>
            <div class="controls">
            <input type="text" name="xURI" maxlength="90" id="xURI"
                   placeholder="URI ejemplo: https://www.miweb.com" class="span12"
                   required="required">
                   
            </div>

        </div>
        
        <div class="control-group">
            <label class="control-label" for="xToken">Token de la aplicación:</label>
            <div class="controls">
                <input type="text" name="xToken" id="xToken" maxlength="90" class="span12">
            </div>

        </div>

        <div class="control-group">
            <label class="control-label" for="xKey">Clave de la aplicación:</label>
            <div class="controls">
            <input type="text" name="xKey" maxlength="90" id="xKey" class="span12">
                   
            </div>

        </div>
        
        <div class="form-actions align-right">

        <input class="btn btn-primary" type="submit" value="Guardar" />
        </div>
        </fieldset>
        </div>
        </form>
                    
                    </div>
                </div>
            </div>
            <!-- /content wrapper -->

        </div>
        <!-- /content -->

    </div>
    <!-- /content container -->


    <script>
        selectItemMenu('itemConfig');
    </script>


</body>
</html>