<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <div class="row page-header">
            <div class="col-xs-12 col-md-8">
                <h3>Alle Projekte</h3>
            </div>
            <div class="col-xs-12 col-md-4">
                <a href="${pageContext.request.contextPath}/projects/create">
                    <button type="button" class="btn btn-dark btn-hinzufuegen">
                        <span class="glyphicon glyphicon-plus"></span> Projekt
                        hinzufügen
                    </button>
                </a>
            </div>
        </div>
        <jsp:include page="projectList.jsp"></jsp:include>
    </tiles:putAttribute>
</tiles:insertDefinition>