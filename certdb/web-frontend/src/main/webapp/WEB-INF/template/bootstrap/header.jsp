<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="navbar navbar-inverse" style="margin-bottom: 0px;" role="navigation">
    <div>
        <div class="navbar-header" style="margin: 0px;">
            <button id="hamburger" type="button" class="navbar-toggle" data-toggle="collapse"
                data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/certifications"><span class="navbar-heading">ARS</span> CertDB</a>
        </div>
        <ul class="nav navbar-nav navbar-right user-dropdown hidden-xs">
       <li class="dropdown">
           <a href="#" data-toggle="dropdown" class="dropdown-toggle"><i class="glyphicon glyphicon-user"></i>${loggedInUser.firstName} ${loggedInUser.lastName}<span class="caret" style="margin-left: 5px;"></span></a>
           <ul class="dropdown-menu dropdown-menu-right">
               <jsp:include page="user-menu.jsp"></jsp:include>
           </ul>
       </li>
    </ul>
    </div>
</div>