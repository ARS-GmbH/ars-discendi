<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="bootstrapTemplate">
	<tiles:putAttribute name="body">
		<div class="container createOuter">
			<div class="panel panel-dark">
				<div class="panel-heading">
					<h3>
						Suche alle Skills eines Mitarbeiters
						<jsp:include page="search-type.jsp" />
					</h3>
				</div>
				<div class="panel-body">
					<div class="createInner">
						<form:form method="GET" action="/skilldb/search/skills"
							class="form-horizontal" role="form">
							<div class="form-group">
								<label class="col-sm-4 col-md-3 col-lg-2 control-label"
									for="user">Mitarbeiter</label>
								<div class="col-sm-7 col-lg-4">
									<select name="user" class="form-control" id="user" >
										<option value="">Mitarbeiter wählen</option>
										<c:forEach var="user" items="${allUsers}">
											<c:choose>
												<c:when test="${selectedUser == user.id}">
													<option value="${user.id}" selected="selected">${user.fullName}</option>
												</c:when>
												<c:otherwise>
													<option value="${user.id}">${user.fullName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>
							<jsp:include page="search-btn.jsp" />
						</form:form>
					</div>
				</div>
			</div>
		</div>
		<div class="tableOuter">
			<div id="tablePanel" class="panel panel-default"
				style="visibility: ${tableVisibility}">
				<jsp:include page="../skills/skill-table.jsp"></jsp:include>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>