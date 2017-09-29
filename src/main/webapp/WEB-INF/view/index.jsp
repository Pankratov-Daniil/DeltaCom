<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title>Delta Communications</title>
    <spring:url value="resources/css/bootstrap.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet" />
</head>
<body>
    <h2>Добро пожаловать на страницу мобильного оператора DeltaCom.</h2>
    <div id="links">
        <ul>
            <li><a href="/DeltaCom/registration">Registration</a></li>
            <li><a href="/DeltaCom/login">Login</a></li>
        </ul>
    </div>
</body>
</html>