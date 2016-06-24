<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="row form-row-readonly">
	<label class="col-sm-4 control-label">Vorname</label>
	<div class="col-sm-8">
		<span>${user.firstName}</span>
	</div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Nachname</label>
    <div class="col-sm-8">
        <span>${user.lastName}</span>
    </div>
</div>

<div class="row form-row-readonly">
    <label class="col-sm-4 control-label">Benutzername</label>
    <div class="col-sm-8">
        <span>${user.userName}</span>
    </div>
</div>