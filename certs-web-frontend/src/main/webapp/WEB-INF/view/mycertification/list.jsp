<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <c:set var="pageTitle" scope="request" value="Meine erworbenen Zertifizierungen"/>
        <c:set var="btnLink" scope="request" value="/mycertifications/create"/>
        <c:set var="btnText" scope="request" value="Zertifizierung abschließen"/>
        <div class="row header-row">
            <div class="col-sm-8">
                <h3>${requestScope.pageTitle}</h3>
            </div>
            <div class="col-sm-4 btn-add">
                <a href="${pageContext.request.contextPath}${requestScope.btnLink}"
                    class="pull-right btn btn-default hidden-xs"> <span
                    class="${requestScope.btnIcon == null ? 'glyphicon glyphicon-plus' : requestScope.btnIcon}"></span>
                    ${requestScope.btnText}
                </a>
            </div>
        </div>

        <div class="panel panel-default" style="max-width: 100%">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Bezeichnung</th>
                            <th>Status</th>
                            <th>Erworben am</th>
                            <th>Ablaufdatum</th>
                            <th>Dokument</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${accomplishedCertifications}" var="accomplished">
                            <tr>
                                <td>${accomplished.certification.name}</td>
                                <td>${accomplished.certification.status.name}</td>
                                <fmt:formatDate value="${accomplished.carryOutDate}" var="carryout" pattern="dd.MM.yyyy" />
                                <td>${carryout}</td>
                                <fmt:formatDate value="${accomplished.certification.expirationDate}" var="expDate"
                                    pattern="dd.MM.yyyy" />
                                <td>${expDate}</td>
                                <td style="text-align: center"><a
                                    href="${pageContext.request.contextPath}/mycertifications/${accomplished.id}/document"><span
                                        class="glyphicon glyphicon-download-alt"></span></a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>