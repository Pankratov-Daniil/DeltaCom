<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
      <li class="${fn:contains(pageContext.request.requestURI, 'user/index') ? 'active' : ''}"><a href="index"><i class="fa fa-dashboard"></i><span>Overview</span></a></li>
      <li class="${fn:contains(pageContext.request.requestURI, 'user/contracts') ? 'active' : ''}"><a href="contracts"><i class="fa fa-mobile"></i><span>Contracts</span></a></li>
      <li class="${fn:contains(pageContext.request.requestURI, 'user/tariffs') ? 'active' : ''}"><a href="tariffs"><i class="fa fa-wifi"></i><span>Tariffs</span></a></li>
  </sec:authorize>
</ul>
