<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <c:set var="pageTitle" scope="request" value="Benutzer anzeigen" />
        <c:set var="disabled" scope="request" value="true" />
        <c:set var="btnLink" scope="request" value="/certifications/${cert.id}/edit" />
        <c:set var="btnText" scope="request" value="Bearbeiten" />
        <c:set var="btnIcon" scope="request" value="glyphicon glyphicon-pencil" />
        <jsp:include page="../util/page-heading.jsp" />

        <div class="panel panel-default">
            <div class="panel-heading">${pageTitle}</div>
            <div class="panel-body">
                <form:form commandName="user">
                    <jsp:include page="show-form.jsp" />
                </form:form>
            </div>
        </div>
        <jsp:include page="user-certs.jsp" />
        <jsp:include page="user-exams.jsp" />
    </tiles:putAttribute>
</tiles:insertDefinition>