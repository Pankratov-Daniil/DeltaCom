<%@ page language="java" contentType="text/html; charset=utf8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Registration page</title>
    <link href="./resources/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<h3>Add / Delete client</h3>

<form method="post" action="regNewUser" id="newUser" acceptCharset="utf8">
    <div class="table-responsive">
        <table class="table table-bordered" style="width: 300px">
            <tr>
                <td>First Name :</td>
                <td><input type="text" name="firstName" /></td>
            </tr>
            <tr>
            <td>Last Name :</td>
                <td><input type="text" name="lastName" /></td>
            </tr>
            <tr>
                <td>Birth Date :</td>
                <td><input type="date" name="birthDate"/></td>
            </tr>
            <tr>
                <td>Passport :</td>
                <td><input type="text" name="passport" /></td>
            </tr>
            <tr>
                <td>Address :</td>
                <td><input type="text" name="address" /></td>
            </tr>
            <tr>
                <td>Mail :</td>
                <td><input type="email" name="email" /></td>
            </tr>
            <tr>
                <td>Password :</td>
                <td><input type="text" name="password" /></td>
            </tr>
            <tr>
                <td></td>
                <td><input class="btn btn-primary btn-sm" type="submit" value="Submit" /></td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
</form>
<br>
<h3>List of Clients</h3>
<table class="table table-bordered" style="width: 300px">
    <tr>
        <th>Id</th>
        <th>FirstName</th>
        <th>LastName</th>
        <th>Birth date</th>
        <th>Passport</th>
        <th>Address</th>
        <th>Email</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${clientsList}" var="client">
        <tr>
            <td width="60" align="center">${client.id}</td>
            <td width="60" align="center">${client.firstName}</td>
            <td width="60" align="center">${client.lastName}</td>
            <td width="60" align="center">${client.birthDate}</td>
            <td width="60" align="center">${client.passport}</td>
            <td width="60" align="center">${client.address}</td>
            <td width="60" align="center">${client.email}</td>
            <td width="60" align="center"><a href="delete/${client.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
