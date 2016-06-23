<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <c:set var="pageTitle" scope="request" value="Zertifizierung anzeigen"/>
        <c:set var="disabled" scope="request" value="true"/>
        <c:set var="btnLink" scope="request" value="/certifications/${cert.id}/edit"/>
        <c:set var="btnText" scope="request" value="Bearbeiten"/>
        <c:set var="btnIcon" scope="request" value="glyphicon glyphicon-pencil"/>
        <jsp:include page="../util/page-heading.jsp"></jsp:include>


        <div class="row">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-heading">Zertifizierung aktuell</div>
                    <div class="panel-body">
                            <jsp:include page="show-form.jsp" />
                    </div>
                </div>
                <jsp:include page="accomplishedList.jsp" />
            </div>
            <div class="col-lg-6">
                <div class="panel panel-warning">
                    <div class="panel-heading">Zertifizierung vor letztem Update</div>
                    <div class="panel-body">
                        <jsp:include page="show-compare.jsp" /> 
                    </div>
                </div>
            </div>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>