<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/">TeachOnSnap</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="navbar-collapse-1">
      <ul class="nav navbar-nav navbar-left">
        <li><a href="#">Regístrate <span class="glyphicon glyphicon-pencil"></span></a></li>
        <li class="dropdown">
        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Explorar <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
            	<li><a href="#">Últimos vídeos</a></li>                
                <li class="divider"></li>
                <li><a href="#">Acerca de TeachOnSnap</a></li>                
			</ul>
        </li>
        <li><a href="#">Subir video <span class="glyphicon glyphicon-cloud-upload"></span></a></li>        
      </ul>
      <ul class="nav navbar-nav navbar-right">
      	<li class="dropdown">
        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">
        		<img alt="${browserLang.language}" src="/resources/img/ico/flag_${browserLang.language}.jpg"/> <span class="caret"></span>
       		</a>
            <ul class="dropdown-menu" role="menu">            	
            	<li><a href="#"><img alt="EN" src="/resources/img/ico/flag_uk.jpg"/> English UK</a></li>
            	<li><a href="#"><img alt="ES" src="/resources/img/ico/flag_es.jpg"/> Español ES</a></li>
			</ul>
        </li>
        <li><a href="#">Iniciar sesión <span class="glyphicon glyphicon-log-in"></span></a></li>        
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>