<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div class="form-group">
    <label class="col-sm-4 control-label" for="certification">Zertifizierung</label>
    <div class="col-sm-8" style="padding-top: 5px;">
        <a href="${certification.link}">${certification.name}</a>
    </div>
</div>

<div class="form-group ${userError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="user">Mitarbeiter</label>
    <div class="col-sm-8">
        <form:input path="user" class="form-control user-typeahead typeahead" />
    </div>
</div>

<div class="form-group ${carryOutUntilError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="carryOutUntil">Abzulegen bis</label>
    <div class="col-sm-8">
        <form:input path="carryOutUntil" class="form-control datepicker" />
    </div>
</div>

<div class="form-group ${pathError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="path">Pfade 
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <button
                onclick="parent.location='${pageContext.request.contextPath}/certifications/${certId}/pathedit?planning=true'"
                type="button" style="margin-left: 10px;" class="btn btn-primary hidden-xs btn-xs pull-right">bearbeiten</button>
        </sec:authorize>
    </label>
    <div class="col-sm-8 path-radio-btns">
        <form:radiobuttons htmlEscape="" cssClass="pathList" path="path" items="${paths}" itemValue="id" />
    </div>
</div>

<div class="form-group">
    <div class="col-sm-offset-4 col-sm-8">
        <input class="btn btn-default" type="submit" value="Speichern" />
    </div>
</div>