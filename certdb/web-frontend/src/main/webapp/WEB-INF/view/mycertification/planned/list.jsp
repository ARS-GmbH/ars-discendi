<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        
        <c:set var="pageTitle" scope="request" value="F¸r mich geplante Zertifizierungen"/>
        <jsp:include page="../../util/page-heading.jsp"></jsp:include>

        <div class="panel panel-default" style="max-width: 100%">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Bezeichnung</th>
                            <th>Abzulegen bis</th>
                            <th>Planer</th>
                            <th>Abschlieﬂen</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${myPlannedCertifications}" var="planned">
                            <tr>
                                <td>${planned.path.destination.name}</td>
                                <fmt:formatDate value="${planned.carryOutUntil}" var="carryOut" pattern="dd.MM.yyyy" />
                                <td>${carryOut}</td>
                                <td>${planned.plannerUserName}</td>
                                <td><button type="button" class="btn btn-primary btn-xs"
                                        onClick="parent.location='${pageContext.request.contextPath}/mycertifications/create?id=${planned.path.destination.id}'">Abschlieﬂen</button></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>