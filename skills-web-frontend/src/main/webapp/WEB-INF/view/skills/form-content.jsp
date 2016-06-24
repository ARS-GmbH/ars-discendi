<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="form-group">
	<label class="col-sm-4 col-md-3 col-lg-2 control-label" for="knowledge">Wissensgebiet</label>
	<div class="col-sm-6 col-lg-3">
		<form:select path="knowledge"
			class="form-control ${knowledgeError ? 'has-error'  : '' }"
			disabled="${knowledgeDisabled}">
			<option value="-1">Wissensgebiet wählen</option>
			<form:options items="${allKnowledges}" itemValue="id" />
		</form:select>
		<form:errors path="knowledge" cssClass="error" />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-4 col-md-3 col-lg-2 control-label"
		for="skillLevel">Level</label>
	<div class="col-sm-6 col-lg-3">
		<form:select path="submittedSkillLevel" itemValue="id" items="${allSkillLevel}"
			class="form-control ${submittedSkillLevelError ? 'has-error'  : '' }"
			autofocus="true">
		</form:select>
		<form:errors path="submittedSkillLevel" cssClass="error" />
	</div>
	<div class="col-xs-3 col-sm-2 col-lg-5">
		<a href="${pageContext.request.contextPath}/skilllevel/info"
			target="_blank"> <span
			class="glyphicon glyphicon-question-sign questionMark"
			data-toggle="tooltip" data-placement="top"
			title="weitere Informationen"></span>
		</a>
	</div>
</div>
<div class="col-sm-12">
	<div class="error">${message}</div>
</div>
<div class="form-group">
	<div class="col-sm-10 col-md-9 col-lg-5">
		<input class="btn btn-dark pull-right" type="submit" value="Speichern" />
	</div>
</div>
<jsp:include page="/WEB-INF/view/shared/history.jsp" />