<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        
        <c:set var="pageTitle" scope="request" value="Alle Mitarbeiter-Zertifizierungen"/>
        <jsp:include page="../util/page-heading.jsp"></jsp:include>

        <div class="panel panel-default" style="max-width: 100%">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Zertifizierung</th>
                            <th>Abgelegt am</th>
                            <th>Läuft ab am</th>
                            <th>Mitarbeiter</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${accomplishedCertifications}" var="accomplished">
                            <tr>
                                <td>${accomplished.certification.name}</td>
                                <fmt:formatDate value="${accomplished.carryOutDate}" var="carryout" pattern="dd.MM.yyyy" />
                                <td>${carryout}</td>
                                <fmt:formatDate value="${accomplished.certification.expirationDate}" var="expDate"
                                    pattern="dd.MM.yyyy" />
                                <td>${expDate}</td>
                                <td>${accomplished.user.firstName} ${accomplished.user.lastName}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>