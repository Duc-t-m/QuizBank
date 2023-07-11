<%-- 
    Document   : Test
    Created on : Mar 11, 2023, 10:20:46 AM
    Author     : tranm
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="img/download.png">
        <title>Practice your test</title>
    </head>
    <body style="background-color: #FFA500; min-width: 460px">
        <h1 class="text-center mt-4">Practice Test</h1>
        <form method="post" action="TestController" id="test">
            <div class="d-flex flex-row justify-content-around flex-wrap">
                <div class="form-group w-75">

                    <c:forEach items="${sessionScope.test}" var="quiz">
                        <div class="mb-3 border border-dark rounded bg-light text-wrapr">
                            <p class="my-auto mx-3">${quiz.key.text}</p>
                            <c:forEach items="${quiz.value}" var="answer"> 
                                <div class="form-check mx-3 my-auto text-wrap">
                                    <input required class="form-check-input" type="radio" name="answer${answer.qid}" id="answer${answer.qid}${answer.choice}" 
                                           value="${answer.choice}${answer.isKey}" ${answer.choice==results.get(quiz.key.id).choice?"checked":""} 
                                           ${results!=null?"disabled":""}>
                                    <label class='form-check-label ${results!=null&&answer.isKey==1?"text-success font-weight-bold":""}' for="answer${answer.qid}${answer.choice}">
                                        <c:out value="${answer.text}"></c:out>
                                        </label>
                                    </div>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </div>
                <div class="position-sticky align-self-start mb-5" style="top: 100px;">
                    <c:if test="${results==null}">
                        <button type="submit" class="btn btn-success">Submit</button>
                    </c:if>
                    <a href=${results==null?"HomeController":"ResultController"} class="text-light" id="getOut">
                        <button type="button" class="btn btn-danger">
                            ${results==null?"Give up":"Go back"}
                        </button>
                    </a>
                </div>
            </div>

            <input type="hidden" value="${sessionScope.test.size()}" id="testSize">
            <input type="hidden" id="score" name="score">
            <input type="hidden" value="finish" name="finish">
        </form>
    </body>
    <script>
        let test = document.getElementById('test');
        test.onsubmit = function () {
            let ans = document.querySelectorAll('input[type="radio"]:checked');
            let size = document.getElementById('testSize').value;
            let score = 0;
            for (answer of ans)
                if (answer.value.substring(1) == 1)
                    score++;
            score = score / size * 100;
            alert('Your score is: ' + score);
            document.getElementById('score').value = score;
            return true;
        };
        let giveUp = document.getElementById('getOut');
        console.log(giveUp);
        let confirmIt = function (e) {
            if (!confirm('Are you sure?'))
                e.preventDefault();
        };
        giveUp.addEventListener('click', confirmIt, false);
    </script>
</html>
