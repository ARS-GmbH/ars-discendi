<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-primary">
    <div class="panel-heading">Abgelegte Zertifizierungen</div>
    <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Erworben am</th>
                    <th>Status</th>
                    <th>Läuft aus</th>
                </tr>
            </thead>
            <tbody>
                 <c:forEach items="${userCerts}" var="accomplished">
                     <tr>
                        <td>${accomplished.certification.name}</td>
                        <fmt:formatDate value="${accomplished.carryOutDate}" var="carryOutDate" pattern="dd.MM.yyyy" />
                        <td>${carryOutDate}</td>
                        <td>${accomplished.certification.status.name}</td>
                        <fmt:formatDate value="${accomplished.certification.expirationDate}" var="expDate" pattern="dd.MM.yyyy" />
                        <td>${expDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>