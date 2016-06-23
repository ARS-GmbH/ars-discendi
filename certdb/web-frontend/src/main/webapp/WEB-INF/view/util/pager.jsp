<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${paginationIsNeeded}">
<ul class="pagination">
    <li>
        <c:choose>
            <c:when test="${pagerIsAtStart}">
                <span>&laquo;</span>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}${requestScope.pagerUrl}?${query != null ? 'q='.concat(query).concat('&') : ''}page=${page - 1}&size=${size}">&laquo;</a>
            </c:otherwise>
        </c:choose>
    </li>

    <c:forEach begin="0" end="${pagingItems}" varStatus="loop">
        <li class="${startItem + loop.index == page ? 'active' : ''}"><a
            href="${pageContext.request.contextPath}${requestScope.pagerUrl}?${query != null ? 'q='.concat(query).concat('&') : ''}page=${startItem + loop.index}&size=${size}">${startItem + loop.index}</a></li>
    </c:forEach>

    <li>
        <c:choose>
            <c:when test="${pagerIsAtEnd}">
                <span>&raquo;</span>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}${requestScope.pagerUrl}?${query != null ? 'q='.concat(query).concat('&') : ''}page=${page + 1}&size=${size}">&raquo;</a>
            </c:otherwise>
        </c:choose>
    </li>
</ul>
</c:if>