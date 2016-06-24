<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="projectParticipation.user">Mitarbeiter</label>
    <div class="col-sm-7 col-lg-6">
        <form:input path="projectParticipation.user" class="form-control ${userError ? 'has-error'  : '' }" disabled="true"/>
        <form:errors path="projectParticipation.user" cssClass="error" />
    </div>
</div>

<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="projectParticipation.project">Projekt</label>
    <div class="col-sm-7 col-lg-6">
        <form:select path="projectParticipation.project" class="form-control ${projectError ? 'has-error'  : '' }" >
            <option value="-1">Projekt wählen</option>
            <c:forEach items="${projects}" var="pr">
                <form:option value="${pr.id}" selected="${pr.id == projectParticipationHelper.projectParticipation.project.id ? 'selected' : ''}">${pr.name}</form:option>                
            </c:forEach>
        </form:select>
        <form:errors path="projectParticipation.project" cssClass="error" />
    </div>
</div>

<div class="form-group">
    <label class="col-sm-4 col-md-3 col-lg-2 control-label" for="projectParticipation.duration">Geleistete Stunden</label>
    <div class="col-sm-7 col-lg-6">
        <form:input path="projectParticipation.duration" class="form-control ${durationError ? 'has-error'  : '' }" />
        <form:errors path="projectParticipation.duration" cssClass="error" />
    </div>
</div>

<div class="col-sm-11 col-md-9 col-lg-8">
	<div class="table-responsive">
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>Wissensgebiet</th>
					<th>Stunden <button id="addKnowledgeRow" type="button" class="btn btn-warning pull-right"><span class="glyphicon glyphicon-plus"></span></button></th>
				</tr>
			</thead>
			<tbody id="knowledgeTable">
			     <c:forEach items="${projectParticipationHelper.skillProofEdges}" var="proof" varStatus="i" begin="0">	
					<tr>
						<td><form:select path="skillProofEdges[${i.index}].skill" items="${skills }" itemValue="id" class="full-width"/></td>
						<td><form:input path="skillProofEdges[${i.index}].hours" class="full-width"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="form-group">
    <div class="col-sm-11 col-md-10 col-lg-8">
        <input class="btn btn-dark pull-right" type="submit" value="Speichern" />
    </div>
</div>
