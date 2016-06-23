<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <c:set var="pageTitle" scope="request" value="Zertifizierung planen"/>
        <jsp:include page="../../util/page-heading.jsp"></jsp:include>
        
        <div class="panel panel-default">
            <div class="panel-heading">${pageTitle}</div>
            <div class="panel-body">
                <form:form method="POST" action="/certdb/certifications/${id}/plan" commandName="plannedCertification" class="form-horizontal" role="form">
                    <jsp:include page="form.jsp" />
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>