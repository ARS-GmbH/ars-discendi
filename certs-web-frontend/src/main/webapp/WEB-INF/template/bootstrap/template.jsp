<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
    content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description" content="">
<meta name="author" content="">
<title>ARS Zertifizierungsdatenbank</title>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/chosen.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/datepicker.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/jasny-bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/modernizr.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/extras.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/typeahead.config.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/certification-paths.js"></script>
</head>
<body>
    <div id="wrap">
        <div id="main" class="container-fluid">
            <div id="header" class="row">
                <tiles:insertAttribute name="header" />
            </div>
            <div id="content" class="row">
                <div id="sidebar-frame" class="col-sm-3 col-lg-3">
                    <tiles:insertAttribute name="sidebar" />
                </div>
                <div id="content-frame" class="col-sm-9 col-lg-9">
                    <div id="inner-content-frame" class="container-fluid tiny-shadow">
                        <tiles:insertAttribute name="flash-messages" />
                        <tiles:insertAttribute name="body" />
                    </div>
                    <div style="clear: both;"></div>
                </div>
            </div>
        </div>
    </div>

    <div id="footer">
        <div style="padding-top: 5px; text-align: center">&copy ARS Computer und Consulting GmbH</div>
    </div>
    <script src="${pageContext.request.contextPath}/resources/js/typeahead.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/chosen.jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jasny-bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootbox.min.js"></script>
</body>
</html>