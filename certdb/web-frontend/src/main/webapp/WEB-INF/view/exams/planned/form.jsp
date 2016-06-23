<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="form-group ${userError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="user">Mitarbeiter</label>
    <div class="col-sm-8">
        <form:input path="user" class="form-control user-typeahead typeahead" />
    </div>
</div>

<div class="form-group" ${examError ? 'has-error' : ''}>
    <label class="col-sm-4 control-label" for="exam">Test</label>
    <div class="col-sm-8">
        <form:input path="exam" class="form-control exam-typeahead typeahead" />
    </div>
</div>

<div class="form-group ${carryOutUntilError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="carryOutUntil">Abzulegen bis</label>
    <div class="col-sm-8">
        <form:input path="carryOutUntil" class="form-control datepicker" />
    </div>
</div>

<div class="form-group">
    <div class="col-sm-offset-4 col-sm-8">
        <input class="btn btn-default" type="submit" value="Speichern" />
    </div>
</div>