<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">

        <c:set var="pageTitle" scope="request" value="Alle erwerbbaren Zertifizierungen" />
        <c:set var="btnLink" scope="request" value="/certifications/create" />
        <c:set var="btnText" scope="request" value="Zertifizierung anlegen" />
        <jsp:include page="../util/page-heading.jsp"></jsp:include>
        
        <form action="${pageContext.request.contextPath}/certifications" enctype="text/plain" method="get">
        <div class="input-group" style="margin-bottom: 20px; max-width: 600px;">
            <input value="${query}" type="text" name="q" placeholder="Nach Zertifizierungen suchen..." class="form-control cert-typeahead"> 
            <span class="input-group-btn">
                <button type="submit" class="btn btn-default" type="button">
                    <span class="glyphicon glyphicon-search"></span>
                </button>
            </span>
        </div>
        </form>

        <c:set var="pagerUrl" scope="request" value="/certifications" />
        <jsp:include page="../util/pager.jsp" />

        <div class="panel panel-default" style="max-width: 100%">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Bezeichnung</th>
                            <th>Status</th>
                            <th>Ablaufdatum</th>
                            <th>Kürzel</th>
                            <th>Version</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${allCertifications}" var="certification">
                            <tr
                                class="${updatedCertifications.contains(certification) ? 'warning' : '' } ${addedCertifications.contains(certification) ? 'success' : '' }"
                                data-href="${pageContext.request.contextPath}/certifications/${certification.id}">
                                <td>${certification.name}</td>
                                <td>${certification.status.name}</td>
                                <fmt:formatDate value="${certification.expirationDate}" var="expDate"
                                    pattern="dd.MM.yyyy" />
                                <td>${expDate}</td>
                                <td>${certification.certificationCode}</td>
                                <td>${certification.version}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <jsp:include page="../util/pager.jsp" />
    </tiles:putAttribute>
</tiles:insertDefinition>