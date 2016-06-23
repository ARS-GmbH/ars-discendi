<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="row form-row-readonly">
	<label class="col-sm-4 control-label">Name</label>
	<div class="col-sm-8">
		<span>${cert.name}</span>
	</div>
</div>

<div class="row form-row-readonly">
	<label class="col-sm-4 control-label">Status</label>
	<div class="col-sm-8">
		<span>${cert.status.name}</span>
	</div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Art</label>
    <div class="col-sm-8">
        <span>${cert.kind.name}</span>
    </div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Produktgruppen</label>
    <div class="col-sm-8">
        <c:forEach items="${cert.productGroups}" var="productGroup">
            <span>${productGroup.name}</span><br />
        </c:forEach>
    </div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Brands</label>
    <div class="col-sm-8">
        <c:forEach items="${cert.brands}" var="brand">
            <span>${brand.name}</span><br />
        </c:forEach>
    </div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Vorgänger</label>
    <div class="col-sm-8">
        <a href="${pageContext.request.contextPath}/certifications/${cert.predecessor.id}"><span>${cert.predecessor.name}</span></a>
    </div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Skill-Points</label>
    <div class="col-sm-8">
        <span>${cert.skillPoint}</span>
    </div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Läuft ab am</label>
    <div class="col-sm-8">
        <fmt:formatDate value="${cert.expirationDate}"
                                    var="expDate" pattern="dd.MM.yyyy" />
        <span>${expDate}</span>
    </div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Kürzel</label>
    <div class="col-sm-8">
        <span>${cert.certificationCode}</span>
    </div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Version</label>
    <div class="col-sm-8">
        <span>${cert.version}</span>
    </div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Link</label>
    <div class="col-sm-8">
        <span><a href="${cert.link}">${cert.link}</a></span>
    </div>
</div>
