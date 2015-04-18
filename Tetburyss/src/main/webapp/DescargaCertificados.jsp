<%-- 
    Document   : DescargaCertificados
    Created on : 06-nov-2013, 23:47:51
    Author     : antonio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
            <link href="css/main.css" rel="stylesheet" type="text/css" />
            <title>Descargar certificados</title>
            <!--[if IE 8]><link href="css/ie8.css" rel="stylesheet" type="text/css" /><![endif]-->
            <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>

                <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
                <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
                <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&amp;sensor=false"></script>

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
                <script type="text/javascript" src="js/charts/graph.js"></script>
                <script type="text/javascript" src="js/charts/chart1.js"></script>
                <script type="text/javascript" src="js/charts/chart2.js"></script>
                <script type="text/javascript" src="js/charts/chart3.js"></script>

                <link rel="stylesheet" type="text/css" href="css/formato.css">
                    

                    <% 
                        
                    %> 

</head>


<body class="clean">

                        <!-- Fixed top -->
                        <div id="top">
                            <div class="fixed">
                                <a href="inicio.jsp" title="" class="logo"><img src="img/logo.png" alt="" /></a>
                               
                            </div>
                        </div>
                        <!-- /fixed top -->
                        <!-- Content container -->
                        <div id="container">

                           


                            <!-- Content -->
                            <div id="content">

                                <!-- Content wrapper -->
                                <div class="wrapper">

                                    <!-- Breadcrumbs line -->
                                    <div class="crumbs">
                                        <ul id="breadcrumbs" class="breadcrumb"> 
                                            <li class="active"><a href="#">Solicitar alta</a></li>
                                            <!--<li class="active"><a href="calendar.html" title="">Calendar</a></li>-->
                                        </ul>

                                        <ul class="alt-buttons">

                                        </ul>
                                    </div>
                                    <!-- /breadcrumbs line -->

                                    <!-- Page header -->
                                    <div class="page-header">
                                        <div class="page-title">

                                            <h5 id="xTitulo">Tetbury Software Service</h5>

                                        </div>			    	
                                    </div>
                                    <!-- /page header -->

                                    <div class="row-fluid">

                                        <div class="span5" >

                                            <div>
                                          Le hemos enviado un mail con las instrucciones del servicio.
                                          Aquí puedes descargar los certificados:
                                          El certificado raíz de la Autoridad de Certificación
                                          El certificado de identificación de usuario.
                                          </div>
                                          <br>
                                          <div>
                                          Sólo quien posea este certificado instalado en su navegador podrá acceder a la información de su base de datos.
                                          Cada cuenta tiene su propia base de datos única y no la comparte con ninguna otra cuenta.
                                          </div>
                                          <br>
                                          <p>
                                              Este servicio utiliza métodos criptográficos, para proteger la información. <b>Tú eres único dueño de tus datos</b>.
                                          La información se podrá compartir con quien decida de modo seguro y confidencial, entregando le un
                                          certificado de acceso.
                                          </p>
                                          <p>
                                              El concepto de este servicio es el de una oficina sin papeles. La gestión cotidiana de nuestro negocio.
                                              Se vuelve virtual y va con nosotros allá donde estemos está nuestra empresa.
                                          </p>

                                          <p>
                                              Pulsa aquí para iniciar.
                                          </p>
                                        </div>
                                    </div>
                                </div>
                                <!-- /content wrapper -->

                            </div>
                            <!-- /content -->

                        </div>
                        <!-- /content container -->



                    </body>
</html>