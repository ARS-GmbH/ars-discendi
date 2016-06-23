<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <c:set var="pageTitle" scope="request" value="Zertifizierung bearbeiten" />
        <jsp:include page="../util/page-heading.jsp"></jsp:include>

        <div class="panel panel-default">
            <div class="panel-heading">
                Zertifizierung bearbeiten
                <button
                    onclick="parent.location='${pageContext.request.contextPath}/certifications/${cert.id}/pathedit'"
                    type="button" class="btn btn-primary btn-sm pull-right" style="margin-top: -5px;">Pfade
                    bearbeiten</button>
            </div>
            <div class="panel-body">
                <form:form method="POST" action="/certdb/certifications/save" commandName="cert" class="form-horizontal"
                    role="form">
                    <jsp:include page="form.jsp" />
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>