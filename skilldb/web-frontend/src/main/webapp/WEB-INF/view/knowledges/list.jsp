<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="bootstrapTemplate">
    <tiles:putAttribute name="body">
        <div class="row page-header">
            <div class="col-xs-12 col-md-6">
                <h3>Alle Wissensgebiete</h3>
            </div>
             <div class="col-xs-12 col-md-3">
                <a href="${pageContext.request.contextPath}/knowledges/categories/create">
                    <button type="button"
						class="btn btn-warning btn-hinzufuegen" style="color:#333333">
						<span class="glyphicon glyphicon-plus"></span> Kategorie
						hinzufügen</button>
				</a>
            </div>
            <div class="col-xs-12 col-md-3">
                <a href="${pageContext.request.contextPath}/knowledges/create">
                    <button type="button" class="btn btn-dark btn-hinzufuegen">
                        <span class="glyphicon glyphicon-plus"></span> Wissensgebiet
                        hinzufügen
                    </button>
                </a>
            </div>
        </div>
        
        <div id="knowledgeTree">
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>