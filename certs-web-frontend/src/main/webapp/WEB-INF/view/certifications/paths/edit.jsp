<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <c:set var="pageTitle" scope="request" value="Zertifizierungspfade bearbeiten"/>
        <jsp:include page="../../util/page-heading.jsp"></jsp:include>
        
        <div class="panel panel-default">
	        <div class="panel-heading">${pageTitle}: ${certification.name}
            <button id="addPath" type="button" class="btn btn-info btn-sm pull-right" style="margin-top: -5px;">Pfad hinzufügen</button></div>
	        <div class="panel-body">
	           <form:form method="POST" action="/certdb/certifications/${certId}/pathedit"
                commandName="pathListCommand" class="form-horizontal" role="form">
                    <jsp:include page="form.jsp" />
                </form:form>
	        </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>