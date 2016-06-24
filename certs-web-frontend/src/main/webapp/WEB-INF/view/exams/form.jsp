<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="form-group ${numberError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="number">Nummer</label>
    <div class="col-sm-8">
        <form:input path="number" class="form-control" />
    </div>
</div>

<div class="form-group ${titleError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="title">Bezeichnung</label>
    <div class="col-sm-8">
        <form:input path="title" class="form-control" />
    </div>
</div>

<div class="form-group ${numberOfQuestionsError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="numberOfQuestions">Anzahl Fragen</label>
    <div class="col-sm-8">
        <form:input path="numberOfQuestions" class="form-control" />
        <form:errors path="numberOfQuestions" cssClass="error" />
    </div>
</div>

<div class="form-group ${testDurationError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="testDuration">Dauer (in Min.)</label>
    <div class="col-sm-8">
        <form:input path="testDuration" class="form-control" />
        <form:errors path="testDuration" cssClass="error" />
    </div>
</div>

<div class="form-group ${passingScorePercentageError ? 'has-error' : ''}">
    <label class="col-sm-4 control-label" for="passingScorePercentage">Passing Score (Prozent)</label>
    <div class="col-sm-8">
        <form:input path="passingScorePercentage" class="form-control" />
        <form:errors path="passingScorePercentage" cssClass="error" />
    </div>
</div>

<c:if test="${certLink != null}">
    <div class="form-group" ${certificationsError ? 'has-error' : ''}>
        <label class="col-sm-4 control-label"">Für Zertifizierung</label>
        <div class="col-sm-8" style="padding-top: 5px;">
            <a href="${certLink}">${certName}</a>
        </div>
    </div>
</c:if>

<div class="form-group">
    <div class="col-sm-offset-4 col-sm-8">
        <input class="btn btn-default" type="submit" value="Speichern" />
    </div>
</div>