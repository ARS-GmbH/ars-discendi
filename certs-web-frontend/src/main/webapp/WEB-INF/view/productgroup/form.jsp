<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="form-group ${nameError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="name">Name</label>
    <div class="col-sm-8">
        <form:input path="name" class="form-control" disabled="${requestScope.disabled}"/>
    </div>
</div>

<div class="form-group ${descriptionError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="description">Beschreibung</label>
    <div class="col-sm-8">
        <form:textarea path="description" class="form-control" disabled="${requestScope.disabled}" />
    </div>
</div>

<div class="form-group ${openError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="open">Ist Open?</label>
    <div class="col-sm-8">
        <form:checkbox path="open"/>
    </div>
</div>

<div class="form-group">
    <label class="col-sm-4 control-label">Readiness Status</label>
    <div class="col-sm-8" style="padding-top: 5px;">
        <span class="label readiness ${isReady ? 'label-success' : 'label-danger'}">${isReady ? 'READY' : 'NOT READY' }</span>
    </div>
</div>

<div class="form-group">
    <div class="col-sm-offset-4 col-sm-8">
        <input class="btn btn-default" type="submit" value="Speichern" />
    </div>
</div>