<%-- 
    Document   : login
    Created on : Mar 7, 2023, 11:00:18 PM
    Author     : ACER NITRO
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="icon" type="image/x-icon" href="img/download.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
    </head>
    <body>
        <form action="UserController" method="POST" id="form">
            <div class="form-group w-50 mx-auto mt-5">
                <input type="hidden" name="go" value ="login">
                <label for="username">Username</label>
                <input type="text" name="username" id="username" class="form-control" required placeholder="Username" maxlength="50">
                <label for ="password">Password</label>
                <input type="password" name="password" id="password" class="form-control" required minlength="8" maxlength="14" placeholder="Password">
                <c:if test="${msg!=null}">
                    <p>${msg}</p>
                </c:if>
                <div class="mt-4">
                    <button type="submit" name="submitRegister" class="btn btn-success">Login</button>
                    <a href="UserController?go=register"><button type="button" class="btn btn-info">Register</button></a>
                </div>
            </div>
        </form>
    </body>
</html>
