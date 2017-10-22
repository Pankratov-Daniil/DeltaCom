<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<aside class="main-sidebar hidden-print">
    <section class="sidebar">
        <div class="user-panel">
            <div class="pull-left image"><img class="img-circle" src="../resources/img/user.png" alt="User Image"></div>
            <div class="pull-left info">
                <p class="designation">Welcome,</p>
                <c:out value="${clientName}"/>
            </div>
        </div>
        <ul class="sidebar-menu">
            <sec:authorize access="hasRole('USER')">
                <li class="${fn:contains(pageContext.request.requestURI, 'user/index') ? 'active' : ''}"><a href="/DeltaCom/user/index"><i
                        class="fa fa-dashboard"></i><span>Overview</span></a></li>
                <li id="contractSidebarList" class="${fn:contains(pageContext.request.requestURI, 'user/contracts') ? 'active' : ''}"><a
                        href="/DeltaCom/user/contracts"><i class="fa fa-mobile"></i><span>Contracts</span></a></li>
            </sec:authorize>

            <sec:authorize access="hasRole('MANAGER')">
                <li class="${fn:contains(pageContext.request.requestURI, 'manager/index') ? 'active' : ''}"><a href="/DeltaCom/manager/index"><i
                        class="fa fa-dashboard"></i><span>Overview</span></a></li>
                <li class="${fn:contains(pageContext.request.requestURI, 'manager/browseAllClients') ? 'active' : ''}"><a
                        href="/DeltaCom/manager/browseAllClients"><i class="fa fa-users"></i><span>Clients</span></a></li>
                <li class="${fn:contains(pageContext.request.requestURI, 'manager/tariffsActions') ? 'active' : ''}"><a
                        href="/DeltaCom/manager/tariffsActions"><i class="fa fa-mobile"></i><span>Tariffs</span></a></li>
                <li class="${fn:contains(pageContext.request.requestURI, 'manager/optionsActions') ? 'active' : ''}"><a
                        href="/DeltaCom/manager/optionsActions"><i class="fa fa-filter"></i><span>Options</span></a></li>
            </sec:authorize>

            <sec:authorize access="hasRole('ADMIN')">
                <li class="${fn:contains(pageContext.request.requestURI, 'admin/index') ? 'active' : ''}"><a href="/DeltaCom/admin/index"><i
                        class="fa fa-dashboard"></i><span>Overview</span></a></li>
                <li class="${fn:contains(pageContext.request.requestURI, 'commons/addNewClient') ? 'active' : ''}"><a
                        href="/DeltaCom/commons/addNewClient"><i class="fa fa-user-plus"></i><span>Add new client</span></a></li>
                <li class="${fn:contains(pageContext.request.requestURI, 'admin/grantAccess') ? 'active' : ''}"><a
                        href="/DeltaCom/admin/grantAccess"><i class="fa fa-user-secret"></i><span>Grant access</span></a></li>
            </sec:authorize>
        </ul>
    </section>
</aside>
