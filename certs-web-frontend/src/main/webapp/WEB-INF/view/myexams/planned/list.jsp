<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        
        <c:set var="pageTitle" scope="request" value="Für mich geplante Tests"/>
        <jsp:include page="../../util/page-heading.jsp"></jsp:include>

        <div class="panel panel-default" style="max-width: 100%">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Kürzel</th>
                            <th>Bezeichnung</th>
                            <th>Dauer</th>
                            <th>Fragen</th>
                            <th>Passing Score</th>
                            <th>Abzulegen bis</th>
                            <th>Test-Planer</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${exams}" var="planned">
                            <tr data-href="${pageContext.request.contextPath}/myexams/planned/${planned.id}/accomplish">
                                <td>${planned.exam.number}</td>
                                <td>${planned.exam.title}</td>
                                <td>${planned.exam.testDuration} min.</td>
                                <td>${planned.exam.numberOfQuestions}</td>
                                <td>${planned.exam.passingScorePercentage} %</td>
                                <fmt:formatDate value="${planned.carryOutUntil}"
                                    var="carryOut" pattern="dd.MM.yyyy" />
                                <td>${carryOut}</td>
                                <td>${planned.planner}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>