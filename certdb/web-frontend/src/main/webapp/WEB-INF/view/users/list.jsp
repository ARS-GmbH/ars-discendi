<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">

        <c:set var="pageTitle" scope="request" value="Alle Benutzer" />
        <jsp:include page="../util/page-heading.jsp"></jsp:include>
        
        <c:set var="pagerUrl" scope="request" value="/users" />
        <jsp:include page="../util/pager.jsp" />

        <div class="panel panel-default" style="max-width: 100%">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Vorname</th>
                            <th>Nachname</th>
                            <th>Username</th>
                            <th>E-Mail</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${users}" var="user">
                            <tr data-href="${pageContext.request.contextPath}/users/${user.id}">
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.userName}</td>
                                <td>${user.email}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <jsp:include page="../util/pager.jsp" />
    </tiles:putAttribute>
</tiles:insertDefinition>