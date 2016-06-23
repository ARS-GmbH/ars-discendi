<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-success">
    <div class="panel-heading">Abgelegte Tests</div>
    <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Erworben am</th>
                </tr>
            </thead>
            <tbody>
                 <c:forEach items="${userExams}" var="accomplished">
                     <tr>
                        <td>${accomplished.exam.title}</td>
                        <fmt:formatDate value="${accomplished.carryOutDate}" var="carryOutDate" pattern="dd.MM.yyyy" />
                        <td>${carryOutDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>