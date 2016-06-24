<div class="navbar-ars sidebar sidebar-shadow" role="navigation">
    <div class="sidebar-nav navbar-collapse collapse">
        <ul class="nav" id="side-menu">
            
            <li><a href="${pageContext.request.contextPath}/users/80/skills/"><i class="glyphicon glyphicon-wrench"></i> Meine Skills</a>
            <li><a href="${pageContext.request.contextPath}/users/80/proofs/"><i class="glyphicon glyphicon-file"></i> Meine Nachweise</a>
            
            <li class="item-separator"></li>
            
            <li class="nav-header">
                <a href="${pageContext.request.contextPath}/search/user" data-toggle="collapse" data-target="#search" class="collapsed">
                    <i class="fa fa-spinner"></i> Suche 
                </a>
            </li>
            <li class="item-separator"></li>
            <li class="nav-header">
                <a href="javascript:void(0)" data-toggle="collapse" data-target="#manage" class="collapsed">
                    <i class="glyphicon glyphicon-list-alt"></i> Verwalten
                </a>
                <ul class="collapse in nav nav-second-level" id="manage">
                    <li><a href="${pageContext.request.contextPath}/projects/"><i class="glyphicon glyphicon-briefcase"></i> Projekte</a></li>
                    <li><a href="${pageContext.request.contextPath}/customers/"><i class="glyphicon glyphicon-tags"></i> Kunden</a></li>
                    <li><a href="${pageContext.request.contextPath}/knowledges/"><i class="glyphicon glyphicon-book"></i> Wissensgebiete</a></li>
                    <li><a href="${pageContext.request.contextPath}/skilllevel/"><i class="glyphicon glyphicon-stats"></i> Skill-Level</a></li>
                    <li><a href="${pageContext.request.contextPath}/approval/"><i class="glyphicon glyphicon-ok"></i> Freigaben</a></li>
                </ul>
            </li>
            <li class="visible-xs">
                <ul class="nav" id="userMenu">
                    <jsp:include page="user-menu.jsp" />
                </ul>
            </li>
        </ul>
    </div>
</div>