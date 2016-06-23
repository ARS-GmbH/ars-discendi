<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <div class="row page-header">
            <div class="col-xs-12 col-md-8">
                <h3>Meine Projektarbeiten</h3>
            </div>
            <div class="col-xs-12 col-md-4">
                <a href="${pageContext.request.contextPath}/users/${user.id}/proofs/projectparticipations/create">
                    <button type="button" class="btn btn-dark btn-hinzufuegen">
                        <span class="glyphicon glyphicon-plus"></span> Projektarbeit
                        hinzufügen
                    </button>
                </a>
            </div>
        </div>
        <c:if test="${empty allProjectParticipations}">
            <div class="error">Sie haben bisher keine Projektarbeit angelegt.</div>
        </c:if>
        <ul class="list-group">
            <c:forEach items="${allProjectParticipations}" var="participation">
                 <a href="${pageContext.request.contextPath}/users/${user.id}/proofs/projectparticipations/${participation.id}" class="list-group-item">${participation.project.name}</a>
            </c:forEach>
        </ul>
    </tiles:putAttribute>
</tiles:insertDefinition>