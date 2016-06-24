<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        
        <c:set var="pageTitle" scope="request" value="Alle ablegbaren Tests"/>
        <c:set var="btnLink" scope="request" value="/exams/create"/>
        <c:set var="btnText" scope="request" value="Test anlegen"/>
        <jsp:include page="../util/page-heading.jsp"></jsp:include>

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
                        </tr>
                    </thead>
                    <tbody>
                        <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />
                        <c:forEach items="${exams}" var="exam">
                            <tr data-href="${isAdmin ? ''.concat(pageContext.request.contextPath).concat('/exams/edit/').concat(exam.id) : '#'}" >
                                <td>${exam.number}</td>
                                <td>${exam.title}</td>
                                <td>${exam.testDuration} min.</td>
                                <td>${exam.numberOfQuestions}</td>
                                <td>${exam.passingScorePercentage} %</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>