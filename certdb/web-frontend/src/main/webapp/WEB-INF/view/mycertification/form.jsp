<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <c:set var="pageTitle" scope="request" value="Zertifizierung abschließen" />
        <jsp:include page="../util/page-heading.jsp"></jsp:include>

        <div class="panel panel-default">
            <div class="panel-heading">${pageTitle}</div>
            <div class="panel-body">
            
                <form:form method="POST" enctype="multipart/form-data" action="/certdb/mycertifications" commandName="accomplishedCertification" class="form-horizontal" role="form">

                    <div class="form-group" ${certificationError ? 'has-error' : ''}>
                        <label class="col-sm-4 control-label" for="certification">Zertifizierung</label>
                        <div class="col-sm-8">
                            <form:input path="certification" class="form-control cert-typeahead typeahead" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">Nachweis</label>
                        <div class="col-sm-8">
                            <div class="fileinput fileinput-new input-group" data-provides="fileinput">
                                <div class="form-control" data-trigger="fileinput">
                                    <i class="glyphicon glyphicon-file fileinput-exists"></i> <span
                                        class="fileinput-filename"></span>
                                </div>
                                <span class="input-group-addon btn btn-default btn-file"><span
                                    class="fileinput-new">Browse</span><span class="fileinput-exists">Ändern</span><input
                                    type="file" name="file"></span> <a href="#"
                                    class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Entfernen</a>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-8">
                            <input class="btn btn-default" type="submit" value="Speichern" />
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>