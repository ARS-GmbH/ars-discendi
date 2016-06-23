<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel-group container-fluid" id="accordion" role="tablist" aria-multiselectable="true">
	<c:forEach items="${user.skills}" var="skill">
		<div class="panel panel-custom row">
			
			<div class="panel-heading col-sm-11" role="tab" id="heading${skill.id}" data-toggle="collapse" data-parent="#accordion"
				href="#collapse${skill.id}" aria-expanded="false"
				aria-controls="collapse${skill.id}">
				<div class="row">
					<div class="panel-title col-sm-5 col-lg-3">${skill.knowledge.name}</div>
					<div class="col-sm-6 col-lg-8">
						<c:choose>
							  <c:when test="${not empty skill.skillLevel }">
						          <c:set var="skillLevel" value="${skill.skillLevel}" scope="request"></c:set>
                                  <jsp:include page="level-progressbar.jsp"></jsp:include>
							  </c:when>
							  <c:otherwise>
							     neuer Skill
							  </c:otherwise>
						</c:choose>
                        <c:if test="${not empty skill.submittedSkillLevel}">
	                        <c:set var="submittedSkillLevel" value="${skill.submittedSkillLevel}" scope="request"></c:set>
	                        <jsp:include page="submittedLevel-progressbar.jsp"></jsp:include>
                        </c:if>
                        
					</div>
				</div>
			</div>
			<div class="col-sm-1 col-lg-1">
				<c:choose>
					<c:when test="${isCurrentUser}">
						<a
							href="${pageContext.request.contextPath}/users/${user.id}/skills/${skill.id}">
							<button type="button" class="btn btn-dark">
								<span class="glyphicon glyphicon-pencil"></span>
							</button>
						</a>
					</c:when>
					<c:when test="${isManager}">
					   <a
                            href="${pageContext.request.contextPath}/approval/${skill.id}">
                            <button type="button" class="btn btn-dark">
                                <span class="glyphicon glyphicon-ok"></span>
                            </button>
                        </a>
					</c:when>
				</c:choose>
			</div>

			<div id="collapse${skill.id}" class="panel-collapse collapse out col-sm-11"
				role="tabpanel" aria-labelledby="heading${skill.id}">
				<div class="panel-body">
		            <c:choose>
			            <c:when test="${not empty skill.skillProofEdges}">
				            <div class="tableOuter">
							    <div id="tablePanel" class="panel panel-default">
							        <div class="table-responsive">
							            <table class="table table-striped table-bordered table-hover">
							                <thead>
							                    <tr>
							                        <th>Bezeichnung</th>
							                        <th>Typ</th>
							                    </tr>
							                </thead>
							                <tbody>
							                    <c:forEach items="${skill.skillProofEdges}" var="skillProofEdge">
							                      <c:choose>
				                                      <c:when test="${skillProofEdge.proof.type == 'ProjectParticipation'}">
				                                          <tr data-href="${pageContext.request.contextPath}/users/${user.id}/proofs/projectparticipations/${skillProofEdge.proof.id}">
				                                              <td>${skillProofEdge.proof.title}</td>
		                                                      <td>${skillProofEdge.proof.type}</td>
				                                          </tr>
				                                      </c:when>
							                      </c:choose>
							                     </c:forEach>
							                </tbody>
							            </table>
							        </div>
							    </div>
							</div>
					    </c:when>
					    <c:otherwise>
					        <div>Zu diesem Skill wurde noch kein Nachweis angelegt.</div>
					    </c:otherwise>
				    </c:choose>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
