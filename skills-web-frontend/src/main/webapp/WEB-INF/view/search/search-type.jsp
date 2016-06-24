<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="float:right">
	<div class="dropdown">
		<button id="dLabel" class="btn btn-reverse" type="button" data-toggle="dropdown"
			aria-haspopup="true" role="button" aria-expanded="false">
			Suche nach...<span class="caret"></span>
		</button>
		<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
		  <li role="presentation">
		      <a role="menuitem" tabindex="-1" href="${pageContext.request.contextPath}/search/user">Mitarbeiter</a>
		  </li>
		  <li role="presentation">
              <a role="menuitem" tabindex="-1" href="${pageContext.request.contextPath}/search/skills">Skills</a>
          </li>
		</ul>
	</div>
</div>