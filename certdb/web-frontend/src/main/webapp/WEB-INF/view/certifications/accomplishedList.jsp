<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-info">
    <div class="panel-heading">Mitarbeiter mit dieser Zertifizierung</div>
    <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Mitarbeiter</th>
                    <th>Erworben am</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${accomplished}" var="certification">
                    <tr>
                        <td>${certification.user.firstName} ${certification.user.lastName}</td>
                        <fmt:formatDate value="${certification.carryOutDate}" var="carryOutDate" pattern="dd.MM.yyyy" />
                        <td>${carryOutDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>