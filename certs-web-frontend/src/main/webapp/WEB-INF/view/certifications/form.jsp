<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="form-group ${nameError ? 'has-error' : ''}">
	<label class="col-sm-4 control-label" for="name">Name</label>
	<div class="col-sm-8">
		<form:input path="name" class="form-control" disabled="${requestScope.disabled}"/>
	</div>
</div>

<div class="form-group ${statusError ? 'has-error' : ''}">
	<label class="col-sm-4 control-label" for="status">Status</label>
	<div class="col-sm-8">
		<form:select path="status" items="${status}" itemLabel="name" class="form-control" disabled="${requestScope.disabled}" />
	</div>
</div>

<div class="form-group ${kindError ? 'has-error' : ''}">
	<label class="col-sm-4 control-label" for="kind">Art</label>
	<div class="col-sm-8">
		<form:select path="kind"  class="form-control" disabled="${requestScope.disabled}" >
            <option>-- Bitte wählen --</option>
            <form:options itemLabel="name" items="${kinds}"/>
        </form:select>
	</div>
</div>

<div class="form-group ${productGroupsError ? 'has-error' : ''}">
	<label class="col-sm-4 control-label" for="productGroups">Produktgruppen</label>
	<div class="col-sm-8">
		<form:select path="productGroups" itemValue="name" multiple="true" cssClass="chosen-select"
			items="${productGroups}" itemLabel="name" disabled="${requestScope.disabled}" />
	</div>
</div>

<div class="form-group ${brandsError ? 'has-error' : ''}">
	<label class="col-sm-4 control-label" for="brands">Brands</label>
	<div class="col-sm-8">
		<form:select path="brands" itemValue="name" multiple="true" cssClass="chosen-select"
			items="${brands}" itemLabel="name" disabled="${requestScope.disabled}" />
	</div>
</div>

<div class="form-group ${predecessorError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="predecessor">Vorgänger</label>
    <div class="col-sm-8">
        <form:input path="predecessor" class="form-control cert-typeahead typeahead" disabled="${requestScope.disabled}" />
    </div>
</div>

<div class="form-group ${skillPointError ? 'has-error' : ''}">
	<label class="col-sm-4 control-label" for="skillPoint">Skill
		Points</label>
	<div class="col-sm-8">
		<form:input path="skillPoint" class="form-control" disabled="${requestScope.disabled}" />
	</div>
</div>

<div class="form-group ${expirationDateError ? 'has-error' : ''}">
	<label class="col-sm-4 control-label" for="expirationDate">Gültig
		bis</label>
	<div class="col-sm-8">
		<form:input path="expirationDate" class="form-control datepicker" disabled="${requestScope.disabled}" />
	</div>
</div>

<div class="form-group ${certificationCodeError ? 'has-error' : ''}">
	<label class="col-sm-4 control-label" for="certificationCode">Zertifizierungs-Code</label>
	<div class="col-sm-8">
		<form:input path="certificationCode" class="form-control" disabled="${requestScope.disabled}" />
	</div>
</div>

<div class="form-group ${versionError ? 'has-error' : ''}">
	<label class="col-sm-4 control-label" for="version">Version</label>
	<div class="col-sm-8">
		<form:input path="version" class="form-control" disabled="${requestScope.disabled}" />
	</div>
</div>

<div class="form-group ${descriptionError ? 'has-error' : ''}">
	<label class="col-sm-4 control-label" for="description">Beschreibung</label>
	<div class="col-sm-8">
		<form:textarea path="description" class="form-control" disabled="${requestScope.disabled}" />
	</div>
</div>

<div class="form-group ${linkError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="link">Link</label>
    <div class="col-sm-8">
        <c:choose>
            <c:when test="${requestScope.disabled}">
                <div class="control-label" style="text-align: left" ><a href="${cert.link}">${cert.link}</a></div>
            </c:when>
            <c:otherwise>
                <form:input path="link" class="form-control" disabled="${requestScope.disabled}" />
            </c:otherwise>
        </c:choose>
    </div>
</div>


<div class="form-group ${lastModifiedError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="lastModified">Zuletzt geändert</label>
    <div class="col-sm-8">
        <form:input path="lastModified" class="form-control datepicker" disabled="true" />
    </div>
</div>

<c:if test="${!requestScope.disabled}">
<div class="form-group">
	<div class="col-sm-offset-4 col-sm-8">
		<input class="btn btn-default" type="submit" value="Speichern" />
	</div>
</div>
</c:if>