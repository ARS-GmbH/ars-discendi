<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="bootstrapTemplate">
	<tiles:putAttribute name="body">
		<div class="container createOuter">
			<div class="panel panel-dark">
				<div class="panel-heading">
					<h3>
						Suche Mitarbeiter nach Skill
						<jsp:include page="search-type.jsp" />
					</h3>
				</div>
				<div class="panel-body">
					<div class="createInner">
						<form:form method="GET" action="/skilldb/search/user"
							class="form-horizontal" role="form">
							<div class="form-group">
								<label class="col-sm-4 col-md-3 col-lg-2 control-label"
									for="knowledge">Wissensgebiet</label>
								<div class="col-sm-7 col-lg-4">
									<select name="knowledge" class="form-control" id="knowledge">
										<option value="">Wissensgebiet wählen</option>
										<c:forEach var="knowledge" items="${allKnowledges}">
											<c:choose>
												<c:when test="${selectedKnowledge == knowledge.name}">
													<option value="${knowledge.name}" selected="selected">${knowledge.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${knowledge.name}">${knowledge.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 col-md-3 col-lg-2 control-label"
									for="skillLevel">Mindest-Skill-Level</label>
								<div class="col-sm-7 col-lg-4">
									<select name="skillLevel" class="form-control" id="skillLevel">
										<c:forEach var="skillLevel" items="${allSkillLevel}">
											<c:choose>
												<c:when test="${selectedLevel == skillLevel.internalValue}">
													<option value="${skillLevel.internalValue}"
														selected="selected">${skillLevel.internalValue}
														${skillLevel.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${skillLevel.internalValue}">${skillLevel.internalValue}
														${skillLevel.name}</option>
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
				<div class="table-responsive">
					<table
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>Name</th>
								<th>Skill-Level</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${skills}" var="skill">
								<tr data-href="${pageContext.request.contextPath}/users/${skill.user.id}/skills/">
									<td>${skill.user.fullName}</td>
									<td>
									    <c:set var="skillLevel" value="${skill.skillLevel}" scope="request"></c:set>
									    <jsp:include page="../skills/level-progressbar.jsp"></jsp:include>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>