<header class="main-header hidden-print">
    <div class="logo hidden-xs"><img src="../resources/img/siteLogoPages.png" class="img-responsive" /></div>
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button--><a class="sidebar-toggle" href="#" data-toggle="offcanvas"></a>
        <!-- Navbar Right Menu-->
        <div class="navbar-custom-menu">
            <ul class="top-nav">
                <!-- User Menu-->
                <c:if test="${sessionScope.cart ne null}">
                    <li id="cartContainer"><a href="javascript:void(0);" id="cartLink"><i class="fa fa-shopping-basket"></i></a></li>
                </c:if>
                <li class="dropdown"><a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button"
                                        aria-haspopup="true" aria-expanded="false"><i class="fa fa-user fa-lg"></i></a>
                    <ul class="dropdown-menu settings-menu">
                        <%--<li><a href="page-user.html"><i class="fa fa-cog fa-lg"></i> Settings</a></li>--%>
                        <%--<li><a href="page-user.html"><i class="fa fa-user fa-lg"></i> Profile</a></li>--%>
                        <li>
                            <a onclick="logoutForm.submit();">
                                <c:url var="logoutUrl" value="/logout"/>
                                <form id="logoutForm" class="form-inline" action="${logoutUrl}" method="post">
                                    <i class="fa fa-sign-out fa-lg"></i>Logout
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>