<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">

        <div class="row page-header">
            <div class="col-xs-12 col-md-8">
                <h3>Alle verfügbaren Skill-Level</h3>
            </div>
            <div class="col-xs-12 col-md-4">
                <a href="${pageContext.request.contextPath}/skilllevel/create">
                    <button type="button" class="btn btn-dark btn-hinzufuegen">
                        <span class="glyphicon glyphicon-plus"></span> Skill-Level
                        hinzufügen
                    </button>
                </a>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Wert</th><th>Bezeichnung</th><th>Beschreibung</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${allSkillLevel}" var="skillLevel">
                            <tr data-href="${pageContext.request.contextPath}/skilllevel/${skillLevel.id}">
			                    <td>${skillLevel.internalValue}</td>
			                    <td>${skillLevel.name}</td>
			                    <td>${skillLevel.description}</td>
		                    </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>