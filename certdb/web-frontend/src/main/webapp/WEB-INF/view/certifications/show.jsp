<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <c:set var="pageTitle" scope="request" value="Zertifizierung anzeigen"/>
        <c:set var="disabled" scope="request" value="true"/>
        <c:set var="btnLink" scope="request" value="/certifications/${cert.id}/edit"/>
        <c:set var="btnText" scope="request" value="Bearbeiten"/>
        <c:set var="btnIcon" scope="request" value="glyphicon glyphicon-pencil"/>
        <jsp:include page="../util/page-heading.jsp" />

        <div class="panel panel-default">
            <div class="panel-heading">
                Zertifizierung anzeigen
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <button
                    onclick="parent.location='${pageContext.request.contextPath}/certifications/${cert.id}/pathedit'"
                    type="button" class="btn btn-primary hidden-xs btn-sm pull-right"
                    style="margin-top: -5px; margin-left: 5px">Pfade bearbeiten</button>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_CERTMANAGER')">
                <button onclick="parent.location='${pageContext.request.contextPath}/certifications/${cert.id}/plan'"
                    type="button" class="btn btn-primary hidden-xs btn-sm pull-right" style="margin-top: -5px;">Für
                    Mitarbeiter planen</button>
                 </sec:authorize>
            </div>
            <div class="panel-body">
                <form:form commandName="cert">
                    <jsp:include page="show-form.jsp" />
                </form:form>
            </div>
        </div>
        <jsp:include page="accomplishedList.jsp" />
    </tiles:putAttribute>
</tiles:insertDefinition>