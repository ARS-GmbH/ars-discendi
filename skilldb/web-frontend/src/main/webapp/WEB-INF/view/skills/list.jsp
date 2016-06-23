<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="bootstrapTemplate">
	<tiles:putAttribute name="body">
		<div class="row page-header">
			<div class="col-xs-12 col-md-8">
				<c:choose>
					<c:when test="${isCurrentUser}">
						<h3>Meine Skills</h3>
					</c:when>
					<c:otherwise>
						<h3>${user.fullName}</h3>
					</c:otherwise>
				</c:choose>
			</div>
			<c:if test="${isCurrentUser}">
				<div class="col-xs-12 col-md-4">
					<a href="${pageContext.request.contextPath}/users/${user.id}/skills/create">
						<button type="button" class="btn btn-dark btn-hinzufuegen">
							<span class="glyphicon glyphicon-plus"></span> Skill hinzufügen
						</button>
					</a>
				</div>
			</c:if>
		</div>
		<div>
			<jsp:include page="skill-table.jsp"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>