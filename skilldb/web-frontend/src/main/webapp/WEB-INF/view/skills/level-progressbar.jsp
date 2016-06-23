<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="progress">
	<c:choose>
		<c:when test="${skillLevel.internalValue > 75}">
			<div class="progress-bar progress-bar-success" role="progressbar"
				aria-valuenow="${skillLevel.internalValue}" aria-valuemin="0"
				aria-valuemax="100"
				style="width: ${skillLevel.internalValue}%">
				${skillLevel.internalValue} ${skillLevel.name}</div>
		</c:when>
		<c:when test="${skillLevel.internalValue > 30}">
			<div class="progress-bar progress-bar-warning" role="progressbar"
				aria-valuenow="${skillLevel.internalValue}" aria-valuemin="0"
				aria-valuemax="100"
				style="width: ${skillLevel.internalValue}%">
				${skillLevel.internalValue} ${skillLevel.name}</div>
		</c:when>
		<c:otherwise>
			<div class="progress-bar progress-bar-danger" role="progressbar"
				aria-valuenow="${skillLevel.internalValue}" aria-valuemin="0"
				aria-valuemax="100"
				style="width: ${skillLevel.internalValue}%"></div>${skillLevel.internalValue} ${skillLevel.name}
        </c:otherwise>
	</c:choose>
</div>
