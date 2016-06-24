<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="bootstrapTemplate">
	<tiles:putAttribute name="body">
		<div class="container createOuter">
			<div class="panel panel-dark">
				<div class="panel-heading">
					<h3>Kunde bearbeiten</h3>
				</div>
				<div class="panel-body">
					<div class="createInner">
						<form:form method="POST" action="/skilldb/customers/${customer.id}"
							commandName="customer" class="form-horizontal" role="form">
							<jsp:include page="form-content.jsp" />
						</form:form>
						<div>
							<jsp:include page="/WEB-INF/view/shared/history.jsp" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<label class="col-sm-4 col-md-3 col-lg-2 control-label">Projekte:</label>
			</div>
			<c:choose>
			     <c:when test="${empty allProjects}">
			         <div class="error">Es existieren noch keine Projekte bei
                    diesem Kunden.</div>
			     </c:when>
			     <c:otherwise>
			         <jsp:include page="../projects/projectList.jsp"></jsp:include>
			     </c:otherwise>
			</c:choose>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>