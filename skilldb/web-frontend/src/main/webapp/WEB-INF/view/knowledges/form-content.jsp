<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="name">Name</label>
    <div class="col-sm-7 col-lg-6">
        <form:input path="name" class="form-control ${nameError ? 'has-error'  : '' }"  autofocus="true"/>
        <form:errors path="name" cssClass="error" />
    </div>
</div>
<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="description">Beschreibung</label>
    <div class="col-sm-7 col-lg-6">
        <form:textarea path="description" class="form-control ${descriptionError ? 'has-error'  : '' }" rows="6" />
        <form:errors path="description" cssClass="error" />
    </div>
</div>
<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="categories">Kategorien</label>
    <div class="col-sm-7 col-lg-6">
		<form:select path="categories" items="${categories}"
			class="chosen-select form-control ${categoriesError ? 'has-error' : '' }" itemValue="id" itemLabel="name"
			multiple="true" />
		<form:errors path="categories" cssClass="error" />
    </div>
</div>
<div class="form-group">
    <div class="col-sm-11 col-md-10 col-lg-8">
        <input class="btn btn-dark pull-right" type="submit" value="Speichern" />
    </div>
</div>
<jsp:include page="/WEB-INF/view/shared/history.jsp" />