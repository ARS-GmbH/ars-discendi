<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row header-row">
    <div class="col-sm-8">
        <h3>${requestScope.pageTitle}</h3>
    </div>
    <c:if test="${requestScope.btnLink != null || requestScope.btnText != null}">
        <sec:authorize access="hasRole('ROLE_ADMIN')">
        <div class="col-sm-4 btn-add">
            <a href="${pageContext.request.contextPath}${requestScope.btnLink}" class="pull-right btn btn-default hidden-xs">
                <span class="${requestScope.btnIcon == null ? 'glyphicon glyphicon-plus' : requestScope.btnIcon}"></span> ${requestScope.btnText}
            </a>
        </div>
        </sec:authorize>
    </c:if>
</div>