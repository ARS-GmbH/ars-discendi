<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../../assets/ico/favicon.ico">
<title>ARS CertDB Anmeldung</title>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet">
</head>
<body style="background-color: #F0F0F0">
    <div id="wrap" class="container">
        <div id="header" class="container arslogo">
            <img src="${pageContext.request.contextPath}/resources/images/ars-logo.svg" />
        </div>
        <div id="main" style="max-width: 400px" class="container">
            <c:if test="${successMessage != null}">
                <div class="alert alert-success" role="alert">${successMessage}</div>
            </c:if>

            <c:if test="${errorMessage != null}">
                <div class="alert alert-danger" role="alert">${errorMessage}</div>
            </c:if>
            <form class="form-signin" role="form" method="post" action="${pageContext.request.contextPath}/login.do">
                <input class="form-control" placeholder="vorname.nachname" name="username" value="" maxlength=256
                    size=40 required autofocus> 
                <input style="margin-top: 3px" type="password"
                    class="form-control" placeholder="Notes-Passwort" name="password" value="" maxlength=256 size=40
                    required>
                <button style="margin-top: 20px; background-color: #BF2C2C; color: #ffffff"
                    class="btn btn-lg btn-default btn-block" type="submit">Anmelden</button>
            </form>
            <div style="margin-top: 160px; text-align: center">ARS Computer und Consulting GmbH - Ridlerstraße 55
                -</div>
            <div style="text-align: center">80339 München - Tel: 089 32468-00 - Fax -288</div>
            <div style="text-align: center">
                <a href="mailto:info@ars.de">info@ars.de</a> - <a href="http://www.ars.de">www.ars.de</a>
            </div>
        </div>
    </div>
    <div id="footer" class="footer-background">
        <div style="padding-top: 5px; text-align: center">&copy ARS Computer und Consulting GmbH</div>
    </div>
</body>
</html>