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
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="customer">Kunde</label>
    <div class="col-sm-6 col-lg-3">
        <form:select path="customer" class="form-control ${customerError ? 'has-error'  : '' }" >
            <option value="-1">Kunde wählen</option>
            <form:options items="${customers}" itemValue="id" />
        </form:select>
        <form:errors path="customer" cssClass="error" />
    </div>
</div>
<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="users">beteiligte Mitarbeiter</label>
    <div class="col-sm-7 col-lg-6">
        <form:select path="users" items="${users}"
            class="chosen-select form-control ${usersError ? 'has-error' : '' }" itemValue="id"
            multiple="true" />
        <form:errors path="users" cssClass="error" />
    </div>
</div>
<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="knowledges">Wissensgebiete</label>
    <div class="col-sm-7 col-lg-6">
        <form:select path="knowledges" items="${knowledges}"
            class="chosen-select form-control ${knowledgesError ? 'has-error' : '' }" itemValue="id"
            multiple="true" />
        <form:errors path="knowledges" cssClass="error" />
    </div>
</div>
<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="start">Beginn</label>
    <div class="col-sm-7 col-lg-6">
        <form:input path="start" type="date" class="form-control datepicker ${startError ? 'has-error'  : '' }" />
    </div>
</div>
<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="end">Ende</label>
    <div class="col-sm-7 col-lg-6">
        <form:input path="end" type="date" class="form-control datepicker ${endError ? 'has-error'  : '' }" />
    </div>
</div>
<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="shortDescription">Kurze Beschreibung</label>
    <div class="col-sm-8 col-md-9 col-lg-10">
        <form:textarea path="shortDescription" class="form-control ${shortDescriptionError ? 'has-error'  : '' }" rows="10" />
        <form:errors path="shortDescription" cssClass="error" />
    </div>
</div>
<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="longDescription">Ausführliche Beschreibung</label>
    <div class="col-sm-8 col-md-9 col-lg-10">
        <form:textarea path="longDescription" class="form-control ${longDescriptionError ? 'has-error'  : '' }" rows="10" />
        <form:errors path="longDescription" cssClass="error" />
    </div>
</div>
<div class="form-group">
    <div class="col-sm-12">
        <input class="btn btn-dark pull-right" type="submit" value="Speichern" />
    </div>
</div>
<jsp:include page="/WEB-INF/view/shared/history.jsp" />