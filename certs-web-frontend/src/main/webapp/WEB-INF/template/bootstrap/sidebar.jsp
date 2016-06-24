<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="navbar-ars sidebar sidebar-shadow" role="navigation">
    <div class="sidebar-nav navbar-collapse collapse">

        <ul class="nav" id="side-menu">
            
            <li>
                <a href="${pageContext.request.contextPath}/certifications"><i class="glyphicon glyphicon-list-alt"></i>
                            Erwerbbare Zertifizierungen</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/mycertifications"><i class="glyphicon glyphicon-certificate"></i>
                            Meine Zertifizierungen</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/exams/"><i class="glyphicon glyphicon-edit"></i>
                                Alle ablegbaren Tests</a>
                </li>
            <li>
                <a href="${pageContext.request.contextPath}/myexams"><i class="glyphicon glyphicon-check"></i>
                            Meine abgelegten Tests</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/myexams/planned"><i class="glyphicon glyphicon-edit"></i>
                            Für mich geplante Tests</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/mycertifications/planned"><i class="glyphicon glyphicon-edit"></i>
                            Für mich geplante Zertifizierungen</a>
            </li>
            <sec:authorize access="hasRole('ROLE_CERTMANAGER')">
            
                <li class="item-seperator"></li>
                <li>
                    <a href="${pageContext.request.contextPath}/certifications/employees"><i class="glyphicon glyphicon-user"></i>
                                Zertifizierungen der Mitarbeiter</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/exams/plan"><i class="glyphicon glyphicon-share"></i>
                                Test planen</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/readiness/"><i class="glyphicon glyphicon-stats"></i>
                                Readiness Status</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/users/"><i class="glyphicon glyphicon-user"></i>
                                Alle Angestellten</a>
                </li>
            </sec:authorize>
            <!-- <li class="nav-header"><a href="javascript:void(0)"
                data-toggle="collapse" data-target="#userMenu" class="collapsed"><i
                            class="glyphicon glyphicon-chevron-right"></i> User
            </a>
                <ul class="collapse nav nav-second-level" id="userMenu">
                    <li><a href="#"><i class="glyphicon glyphicon-envelope"></i>
                            Nachrichten</a></li>
                    <li><a href="#"><i class="glyphicon glyphicon-cog"></i>
                            Einstellungen</a></li>
                </ul></li> -->
        </ul>
    </div>
</div>