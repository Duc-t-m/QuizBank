<%-- 
    Document   : Home
    Created on : Feb 27, 2023, 11:00:21 AM
    Author     : tranm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="icon" type="image/x-icon" href="img/download.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home</title>
    </head>
    <body style="min-width: 730px;">
        <div class="my-5 d-flex flex-row-reverse">
            <div class="align-self-start">
                <!--<a href="UserController?go=changePassword"><button class="btn btn-outline-secondary">Change password</button></a>-->
                <a href="UserController?go=logout"><button class="btn btn-outline-info">Logout</button></a>
            </div>
            <div class="container w-75 mx-auto ">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th scope="col" class="text-center">#</th>
                            <th scope="col">Question</th>
                            <th scope="col">Topic</th>
                            <th scope="col" colspan="3">Author</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${sessionScope.qDAO.questions}" var="quiz" begin="${page.begin}" end="${page.end}">
                            <tr>
                                <th scope="row" class="text-center">${quiz.key}</th>
                                <td>${quiz.value.text}</td>                   
                                <td>${quiz.value.topic}</td>                   
                                <td>${quiz.value.author}</td>      
                                <c:if test="${quiz.value.author.equals(sessionScope.user.username)}">
                                    <td class="text-center"><a href= 'QuestionController?type=update&id=${quiz.key}' class="badge badge-info">Update</a></td>                   
                                    <td class="text-center"><a href= 'QuestionController?type=delete&id=${quiz.key}' class="badge badge-danger deleteBtn">Delete</a></td>   
                                </c:if>
                            </tr>
                        </c:forEach>
                    <tbody>
                </table>
                <form action="HomeController" method="post">
                    <input type="hidden" min="0" name="index" value="${page.index}">
                    <input type="hidden" min="0" name="totalPage" value="${page.totalPage}">
                    <button type="submit" name="btnHome" ${page.index==0?"hidden":""} class="btn btn-secondary">Home</button>
                    <button type="submit" name="btnPre" ${page.index==0?"hidden":""} class="btn btn-secondary">Pre</button>
                    <c:forEach var="p" begin="${page.pageStart}" end="${page.pageEnd}">
                        <button type="submit" name="btnIndex" value="${p}" class="${p==page.index?'btn btn-primary':'btn btn-secondary'}">${p+1}</button>
                    </c:forEach>
                    <button type="submit" name="btnNext" ${page.index==page.totalPage-1?"hidden":""} class="btn btn-secondary">Next</button>
                    <button type="submit" name="btnEnd" ${page.index==page.totalPage-1?"hidden":""} class="btn btn-secondary">End</button>
                </form>
                <c:if test="${sessionScope.user.isTeacher==1}">
                    <a href="QuestionController"><button type="button" class="btn btn-success mt-4">Add new question</button></a>
                </c:if>
                <a href="ResultController"><button type="button" class="btn btn-info mt-4">View all results</button></a>

                <div class="mt-4 border border-dark">
                    <h2 class="text-center">Start a new test</h2>
                    <form class="w-75 mx-auto mb-4" action="TestController" method="post" id="newTest">
                        <div class="form-group">
                            <h6>Choose topic(s):</h6>
                            <c:set value="1" var="i"></c:set>
                            <c:forEach items="${sessionScope.qDAO.topics}" var="topic">
                                <div class="form-check form-check-inline">
                                    <input type="checkbox" id="topic${i}" name="topic${i}" class="form-check-input" value="${topic}">
                                    <label class="form-check-label" for="topic${i}">${topic}</label>
                                    <c:set value="${i+1}" var="i"></c:set>
                                    </div>
                            </c:forEach>
                            <br>
                            <label for="number">Number of question</label>
                            <input type="number" class="form-control" id="number" name="number" min="3" max="50" required="">
                        </div>
                        <input type="submit" class="btn btn-success" value="Start now">
                    </form>
                </div>
            </div>
        </div>
    </body>
    <script>
        let form = document.getElementById('newTest');
        form.onsubmit = function () {
            let topics = document.querySelectorAll('input[type="checkbox"]:checked').length;
            if (topics < 1) {
                alert('You will start a practice test on every topic available');
                let boxes = document.querySelectorAll('input[type="checkbox"]');
                for (box of boxes)
                    box.setAttribute("checked", "");
                return true;
            }
        }
        let btns = document.getElementsByClassName('deleteBtn');
        let confirmIt = function (e) {
            if (!confirm('Are you sure?'))
                e.preventDefault();
        };
        for (btn of btns)
            btn.addEventListener('click', confirmIt, false);
    </script>
</html>
