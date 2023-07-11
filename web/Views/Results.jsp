<%-- 
    Document   : Results
    Created on : Mar 12, 2023, 10:35:58 PM
    Author     : tranm
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/x-icon" href="img/download.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Results</title>
    </head>
    <body class="w-75 mx-auto my-5">
        <table class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th scope="col" class="text-center">#</th>
                    <th scope="col">User</th>
                    <th scope="col">Time</th>
                    <th scope="col">Score</th>
                    <th scope="col">Details</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="i" value="1"></c:set>
                <c:forEach items="${results}" var="result">
                    <tr>
                        <th scope="row" class="text-center">${i}</th>
                        <td>${result.user}</td>                   
                        <td>${result.time}</td>                   
                        <td>${result.score}</td>
                        <td><a href="TestController?user=${result.user}&time=${result.time}" class="badge badge-info">View</a></td>
                    </tr>
                    <c:set var="i" value="${i+1}"></c:set>
                </c:forEach>
            <tbody>
        </table>
        <a href="HomeController" class="text-light"><button type="button" class="btn btn-outline-success">Back Home</button></a> 
    </body>
</html>
