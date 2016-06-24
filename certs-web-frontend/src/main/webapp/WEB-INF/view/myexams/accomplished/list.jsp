<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        
        <c:set var="pageTitle" scope="request" value="Meine abgelegten Tests"/>
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
                            <th>Abgelegt am</th>
                            <th>Dokument</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${exams}" var="accomplished">
                            <tr>
                                <td>${accomplished.exam.number}</td>
                                <td>${accomplished.exam.title}</td>
                                <td>${accomplished.exam.testDuration} min.</td>
                                <td>${accomplished.exam.numberOfQuestions}</td>
                                <td>${accomplished.exam.passingScorePercentage} %</td>
                                <fmt:formatDate value="${accomplished.carryOutDate}" var="carryOut" pattern="dd.MM.yyyy" />
                                <td>${carryOut}</td>
                                <td style="text-align: center"><a
                                    href="${pageContext.request.contextPath}/myexams/accomplished/${accomplished.id}/document"><span
                                        class="glyphicon glyphicon-download-alt"></span></a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>