<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <div class="panel" style="max-width: 750px">
            <img alt="403 forbidden" src="${pageContext.request.contextPath}/resources/images/403.jpg">
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>