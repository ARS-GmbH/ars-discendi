<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${successMessage != null}">
    <div class="alert alert-success" role="alert">
        ${successMessage}
    </div>
</c:if>

<c:if test="${errorMessage != null}">
    <div class="alert alert-danger" role="alert">
        ${errorMessage}
    </div>
</c:if>