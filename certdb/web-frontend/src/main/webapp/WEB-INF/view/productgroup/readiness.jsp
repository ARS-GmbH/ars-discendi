<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">

        <c:set var="pageTitle" scope="request" value="Readiness Dashboard" />
        <jsp:include page="../util/page-heading.jsp"></jsp:include>
        
        <div class="panel panel-default" style="max-width: 100%">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Produktgruppe</th>
                            <th>Sales / Technical</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${readinessStatus}" var="status">
                            <tr data-href="${pageContext.request.contextPath}/productgroups/${status.productGroup.id}/edit">
                                <td>${status.productGroup.name} 
                                    <c:if test="${status.productGroup.open}">
                                        <span style="margin-left: 5px;" class="label label-primary">OPEN</span>
                                    </c:if>
                                </td>
                                <c:set value="&#x25a0;" var="bullet" />
                                <c:set value="" var="salesCertsTooltip" />
                                <c:forEach items="${status.salesCerts}" var="salesCert">
                                    <c:set value="${salesCertsTooltip.concat(bullet).concat(' ').concat(salesCert.name).concat('<br />')}"
                                        var="salesCertsTooltip" />
                                </c:forEach>
                                <c:set value="" var="technicalCertsTooltip" />
                                <c:forEach items="${status.technicalCerts}" var="technicalCert">
                                    <c:set value="${technicalCertsTooltip.concat(bullet).concat(' ').concat(technicalCert.name).concat('<br />')}"
                                        var="technicalCertsTooltip" />
                                </c:forEach>
                                <td><a href="#" 
                                    data-html="true" data-toggle="tooltip" title="${salesCertsTooltip}"
                                    data-placement="left">${status.salesCerts.size()} </a> / 
                                    
                                    <a href="#"
                                    data-html="true" data-toggle="tooltip" title="${technicalCertsTooltip}"
                                    data-placement="bottom">${status.technicalCerts.size()} </a></td>
                                <td><span
                                    class="readiness label ${status.ready ? 'label-success' : 'label-danger'}">${status.ready ? 'READY' : 'NOT READY' }</span></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>