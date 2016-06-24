<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>ARS Skill-Datenbank</title>
		<link
		    href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
		    rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/main.css"
		    rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/fonts/font-awesome-4.1.0/css/font-awesome.min.css"
			rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/chosen.min.css"
            rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datepicker.css">
        <link rel="stylesheet/less" type="text/css" href="${pageContext.request.contextPath}/resources/less/datepicker.less" />
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/modernizr.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jstree.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/chosen.jquery.min.js" charset="utf-8"></script>
	</head>
	<body>
	    <div id="wrap">
	        <div id="main" class="container-fluid">
	            <div id="header" class="row">
	                <tiles:insertAttribute name="header" />
	            </div>
	            <div id="content" class="row">
	                <div id="sidebar-frame" class="col-sm-3 col-lg-2"
	                    style=>
	                    <tiles:insertAttribute name="sidebar" />
	                </div>
	                <div id="content-frame" class="col-sm-9 col-lg-10"
	                    style=>
	                    <div id="inner-content-frame" class="container-fluid tiny-shadow">
	                        <tiles:insertAttribute name="flash-message" />
	                        <tiles:insertAttribute name="body" />
	                    </div>
	                    <div style="clear: both;"></div>
	                </div>
	            </div>
	        </div>
	    </div>
	    <div id="footer">
	        <div style="padding-top: 5px; text-align: center">&copy ARS
	            Computer und Consulting GmbH</div>
	    </div>
	    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	</body>
</html>