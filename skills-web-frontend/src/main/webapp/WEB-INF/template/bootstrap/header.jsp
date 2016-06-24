<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="navbar navbar-inverse" style="margin-bottom: 0px;"
	role="navigation">
	<div class="navbar-header" style="margin: 0px;">
		<button id="hamburger" type="button" class="navbar-toggle"
			data-toggle="collapse" data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand"
			href="${pageContext.request.contextPath}/skills/"><span
			class="ars-logo ">ARS</span> Skill-Datenbank</a>
	</div>
	<ul class="nav navbar-nav navbar-right user-dropdown">
	   <li class="dropdown">
	       <a href="#" data-toggle="dropdown" class="dropdown-toggle"><i class="glyphicon glyphicon-user"></i> Name <span class="caret"></span></a>
	       <ul class="dropdown-menu dropdown-menu-right">
	           <jsp:include page="user-menu.jsp"></jsp:include>
	       </ul>
	   </li>
	</ul>
</div>