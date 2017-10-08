<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
        <li class="${fn:contains(pageContext.request.requestURI, 'user/contracts') ? 'active' : ''}"><a
                href="/DeltaCom/user/contracts"><i class="fa fa-mobile"></i><span>Contracts</span></a></li>
        <li class="${fn:contains(pageContext.request.requestURI, 'user/tariffs') ? 'active' : ''}"><a href="/DeltaCom/user/tariffs"><i
                class="fa fa-wifi"></i><span>Tariffs</span></a></li>
    </sec:authorize>

    <sec:authorize access="hasRole('MANAGER')">
        <li class="${fn:contains(pageContext.request.requestURI, 'manager/index') ? 'active' : ''}"><a href="/DeltaCom/manager/index"><i
                class="fa fa-dashboard"></i><span>Overview</span></a></li>
        <li class="${fn:contains(pageContext.request.requestURI, 'commons/addNewClient') ? 'active' : ''}"><a
                href="/DeltaCom/commons/addNewClient"><i class="fa fa-user-plus"></i><span>Add new client</span></a></li>
        <li class="${fn:contains(pageContext.request.requestURI, 'manager/browseAllClients') ? 'active' : ''}"><a
                href="/DeltaCom/manager/browseAllClients"><i class="fa fa-users"></i><span>Browse all clients</span></a></li>
        <li class="${fn:contains(pageContext.request.requestURI, 'manager/searchClient') ? 'active' : ''}"><a
                href="/DeltaCom/manager/searchClient"><i class="fa fa-search"></i><span>Search client</span></a></li>
        <li class="${fn:contains(pageContext.request.requestURI, 'manager/tariffsActions') ? 'active' : ''}"><a
                href="/DeltaCom/manager/tariffsActions"><i class="fa fa-mobile"></i><span>Tariffs actions</span></a></li>
        <li class="${fn:contains(pageContext.request.requestURI, 'manager/optionsActions') ? 'active' : ''}"><a
                href="/DeltaCom/manager/optionsActions"><i class="fa fa-filter"></i><span>Options actions</span></a></li>
        <li class="${fn:contains(pageContext.request.requestURI, 'manager/optionsCompatibility') ? 'active' : ''}"><a
                href="/DeltaCom/manager/optionsCompatibility"><i class="fa fa-plug"></i><span>Options compatibility</span></a></li>

    </sec:authorize>

    <sec:authorize access="hasRole('ADMIN')">
        <li class="${fn:contains(pageContext.request.requestURI, 'admin/index') ? 'active' : ''}"><a href="/DeltaCom/admin/index"><i
                class="fa fa-dashboard"></i><span>Overview</span></a></li>
        <li class="${fn:contains(pageContext.request.requestURI, 'commons/addNewClient') ? 'active' : ''}"><a
                href="/DeltaCom/commons/addNewClient"><i class="fa fa-user-plus"></i><span>Add new client</span></a></li>
        <li class="${fn:contains(pageContext.request.requestURI, 'admin/grantAccess') ? 'active' : ''}"><a
                href="/DeltaCom/admin/grantAccess"><i class="fa fa-user-secret"></i><span>Grant access</span></a></li>
    </sec:authorize>

    <sec:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
        <c:if test="${sessionScope.successClientCreation ne null or sessionScope.successContractCreation ne null}">
            <script type="text/javascript">
                var whatAdded = '';
                <c:if test='${sessionScope.successClientCreation ne null}'>
                    <c:remove var="successClientCreation" scope="session"/>
                    whatAdded = 'Client';
                </c:if>

                <c:if test='${sessionScope.successContractCreation ne null}'>
                    <c:remove var="successContractCreation" scope="session"/>
                    whatAdded = 'Contract';
                </c:if>

                $(document).ready(function() {
                    $.notify({
                        title: whatAdded+" successfully added!",
                        message: "",
                        icon: 'fa fa-check'
                    }, {
                        type: "info"
                    });
                });
            </script>
        </c:if>
    </sec:authorize>
</ul>
