<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-success">
    <div class="panel-heading">Abgeschlossene <strong>Sales</strong> Zertifizierungen</div>
    <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Bezeichnung</th>
                    <th>Angestellter</th>
                    <th>Erworben am</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${accomplishedSales}" var="sales">
                    <tr>
                        <td>${sales.certification.name}</td>
                        <td>${sales.user.firstName} ${sales.user.lastName}</td>
                        <fmt:formatDate value="${sales.carryOutDate}" var="carryOutDateSales" pattern="dd.MM.yyyy" />
                        <td>${carryOutDateSales}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="panel panel-success">
    <div class="panel-heading">Abgeschlossene <strong>Technical</strong> Zertifizierungen</div>
    <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Bezeichnung</th>
                    <th>Angestellter</th>
                    <th>Erworben am</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${accomplishedTechnical}" var="technical">
                    <tr>
                        <td>${technical.certification.name}</td>
                        <td>${technical.user.firstName} ${technical.user.lastName}</td>
                        <fmt:formatDate value="${technical.carryOutDate}" var="carryOutDateTec" pattern="dd.MM.yyyy" />
                        <td>${carryOutDateTec}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="panel panel-info">
    <div class="panel-heading">Zertifizierungen dieser Produktgruppe</div>
    <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Bezeichnung</th>
                    <th>Status</th>
                    <th>Ablaufdatum</th>
                    <th>Typ</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${certifications}" var="certification">
                    <tr>
                        <td data-href="${pageContext.request.contextPath}/certifications/${certification.id}">${certification.name}</td>
                        <td>${certification.status.name}</td>
                        <fmt:formatDate value="${certification.expirationDate}" var="expDate" pattern="dd.MM.yyyy" />
                        <td>${expDate}</td>
                        <td>${certification.kind.name}</td>
                        <td>
                            <c:if test="${certification.status.name != 'Ausgelaufen' || certification.status.name != 'Verworfen'}">
                                <button type="button" class="btn btn-primary btn-xs" onClick="parent.location='${pageContext.request.contextPath}/certifications/${certification.id}/plan'">Planen</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>