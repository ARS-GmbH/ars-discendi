<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="tableOuter">
	<div id="tablePanel" class="panel panel-default">
	    <div class="table-responsive">
	        <table class="table table-striped table-bordered table-hover">
	            <thead>
	                <tr>
	                    <th>Name</th>
	                    <th>Kunde</th>
	                    <th>Beginn</th>
	                    <th>Ende</th>
	                </tr>
	            </thead>
	            <tbody>
	                <c:forEach items="${allProjects}" var="project">
	                  <tr data-href="${pageContext.request.contextPath}/projects/${project.id}">
	                      <td>${project.name}</td>
	                      <td>${project.customer.name}</td>
	                      <td>${project.start}</td>
	                      <td>${project.end}</td>
	                  </tr>
	              </c:forEach>
	            </tbody>
	        </table>
	    </div>
	</div>
</div>