<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="bootstrapTemplate">
	<tiles:putAttribute name="body">
		<div class="container createOuter">
			<div class="panel panel-dark">
				<div class="panel-heading">
					<h3>Projektarbeit bearbeiten</h3>
				</div>
				<div class="panel-body">
					<div class="createInner">
						<form:form method="POST" action="/skilldb/users/${user.id}/proofs/projectparticipations/${projectParticipationHelper.projectParticipation.id}"
							commandName="projectParticipationHelper" class="form-horizontal" role="form">
							<jsp:include page="form-content.jsp" />
						</form:form>
						<div>
							<jsp:include page="/WEB-INF/view/shared/history.jsp" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>