<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="bootstrapTemplate">
	<tiles:putAttribute name="body">
		<div class="row page-header">
			<div class="col-xs-12 col-md-8">
				<h3>Freigaben</h3>
			</div>
		</div>
		<div>
	        <c:forEach items="${users}" var="user">
	           <div><h4>${user.fullName}</h4></div>
	               <c:set var="user" value="${user}" scope="request"/>
	               <jsp:include page="../skills/skill-table.jsp" />        
	        </c:forEach>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>