<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
<h3>Add / Delete client</h3>

<form:form method="post" action="regNewUser" modelAttribute="newUser">
    <div class="table-responsive">
        <table class="table table-bordered" style="width: 300px">
            <tr>
                <td>First Name :</td>
                <td><form:input type="text" path="firstName" /></td>
            </tr>
            <tr>
            <td>Last Name :</td>
                <td><form:input type="text" path="lastName" /></td>
            </tr>
            <tr>
                <td>Birth Date :</td>
                <td><form:input type="date" path="birthDate"/></td>
            </tr>
            <tr>
                <td>Passport :</td>
                <td><form:input type="text" path="passport" /></td>
            </tr>
            <tr>
                <td>Address :</td>
                <td><form:input type="text" path="address" /></td>
            </tr>
            <tr>
                <td>Mail :</td>
                <td><form:input type="text" path="email" /></td>
            </tr>
            <tr>
                <td>Password :</td>
                <td><form:input type="text" path="password" /></td>
            </tr>
            <tr>
                <td></td>
                <td><input class="btn btn-primary btn-sm" type="submit" value="Submit" /></td>
            </tr>
        </table>
    </div>
</form:form>
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