<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:forEach items="${pathListCommand.paths}" var="path" varStatus="i" begin="0">
    <div class="panel panel-info">
        <div id="path[${i.index}]" class="panel-heading">Pfad ${i.index + 1}<c:if test="${i.index != 0}"><button id="deletePath[${i.index}]" type="button" class="btn btn-danger btn-sm pull-right" style="margin-top: -5px;">Pfad entfernen</button></c:if>
        </div>
        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-4 control-label" for="pathExams">Benötigte Tests</label>
                <div class="col-sm-8">
                    <form:hidden path="paths[${i.index}].id" />
                    <form:select path="paths[${i.index}].necessaryExams" itemValue="title" multiple="true"
                        items="${pathListCommand.paths[i.index].necessaryExams}" cssClass="chosen-select chosen-exam-typeahead-${i.index}" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-4 control-label" for="choosablePathExams">Plus einer dieser Tests</label>
                <div class="col-sm-8">
                <form:select path="paths[${i.index}].choosableExams" itemValue="title" multiple="true"
                        items="${pathListCommand.paths[i.index].choosableExams}" cssClass="chosen-select chosen-ch-exam-typeahead-${i.index}" />
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-4 control-label" ><button onclick="parent.location='${pageContext.request.contextPath}/exams/create?forcert=${certification.id}'"
            type="button" style="margin-left: 10px;" class="btn btn-primary hidden-xs btn-xs pull-right">Test anlegen</button></label>
                <div></div>
            </div>

            <div class="form-group">
                <label class="col-sm-4 control-label" for="pathCertifications">Benötigte Zertifizierungen</label>
                <div class="col-sm-8">
                    <form:select path="paths[${i.index}].necessaryCertifications" itemValue="name" multiple="true"
                        items="${pathListCommand.paths[i.index].necessaryCertifications}" cssClass="chosen-select chosen-cert-typeahead-${i.index}" />
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<c:if test="${!requestScope.disabled}">
<div class="form-group">
	<div class="col-sm-offset-4 col-sm-8">
		<input class="btn btn-default" type="submit" value="Speichern" />
	</div>
</div>
</c:if>