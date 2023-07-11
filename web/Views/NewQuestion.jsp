<%-- 
    Document   : NewQuestion
    Created on : Mar 6, 2023, 10:22:29 PM
    Author     : tranm
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/x-icon" href="img/download.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add new question</title>
    </head>
    <body>
        <h2 class="text-center mt-4">
            <c:if test="${update==null}">Insert new question</c:if> 
            <c:if test="${update!=null}">Update question</c:if> 
        </h2>
        <form id="quizForm" class="my-5 w-75 mx-auto font-weight-bold" action="QuestionController" method="post">
            <div class="form-group">
                <label for="question">Question</label>
                <textarea name="question" class="form-control" id="question" rows="3" required maxlength="200">${update!=null?update.text:""}</textarea>

                <label for="topic">Topic</label>
                <input name="topic" type="text" class="form-control" id="topic" maxlength="20" value="${update!=null?update.topic:""}" required>

                <div id="option">
                    <c:set value="1" var="i"></c:set>
                    <c:forEach items="${answers}" var="answer">
                        <label for="option${i}" id="forOption${i}">Option ${i}</label>
                        <div class="input-group mb-3" id="divOption${i}">
                            <input name="option${i}" type="text" class="form-control" maxlength="200" required="" value="${answer.text}">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <input type="radio" name="isKey" ${answer.isKey==1?"checked":""} value="${i}" required="">
                                </div>
                            </div>
                        </div>
                        <c:set value="${i+1}" var="i"></c:set>
                    </c:forEach>

                </div>

                <input type="hidden" id="numOfOpt" name="numOfOpt">
                <input type="hidden" name="act" value=${update==null?"insert":"update"}>
                <input type="hidden" name="qid" value="${update.id}">
                <br>

                <input type="button" id="btnAddOpt" onclick="addOption();" class="btn btn-primary" value="Add option">
                <input type="button" id="btnRmOpt" onclick="RmOption();" class="btn btn-danger" value="Remove newest option">
                <input type="submit" class="btn btn-success" value="Confirm">
                <a href="HomeController" class="text-light"><button type="button" class="btn btn-info">Back Home</button></a>  
            </div>
        </form>
    </body>
    <script>
        var numOfOpt = document.querySelector('#option').querySelectorAll('input[type="text"]').length + 1;
        var numOfKey = 0;
        function newOption() {
            let divmb3 = document.createElement("div");
            divmb3.setAttribute("class", "input-group mb-3");
            divmb3.setAttribute("id", "divOption" + numOfOpt);

            let option = document.createElement("input");
            option.setAttribute("type", "text");
            option.setAttribute("name", "option" + numOfOpt);
            option.setAttribute("class", "form-control");
            option.setAttribute("maxlength", "200");
            option.setAttribute("required", "");

            let divpreend = document.createElement("div");
            divpreend.setAttribute("class", "input-group-prepend");

            let divtext = document.createElement("div");
            divtext.setAttribute("class", "input-group-text");

            let isKey = document.createElement("input");
            isKey.setAttribute("type", "radio");
            isKey.setAttribute("name", "isKey");
            isKey.setAttribute("value", numOfOpt);
            isKey.setAttribute("required", "");

            divmb3.appendChild(option);
            divmb3.appendChild(divpreend);
            divpreend.appendChild(divtext);
            divtext.appendChild(isKey);

            return divmb3;
        }
        function addOption() {
            if (numOfOpt - 1 === 4) {
                alert("Maximum 4 options per question!");
            } else {
                let option = newOption();

                let label = document.createElement("label");
                label.setAttribute("for", "option" + numOfOpt);
                label.setAttribute("id", "forOption" + numOfOpt);
                label.innerText = "Option " + numOfOpt;


                let divOption = document.getElementById("option");
                divOption.appendChild(label);
                divOption.appendChild(option);

                numOfOpt++;
            }
        }
        function RmOption() {
            let rm = document.getElementById("divOption" + (numOfOpt - 1));
            let label = document.getElementById("forOption" + (numOfOpt - 1));
            let divOption = document.getElementById("option");
            divOption.removeChild(rm);
            divOption.removeChild(label);
            numOfOpt--;
        }
        document.getElementById("quizForm").onsubmit = function () {
            if (numOfOpt - 1 < 2) {
                alert("There must be at least 2 options!");
                return false;
            }
            if (document.querySelectorAll('input[type="radio"]:checked').length !== 1) {
                alert("You must select exactly one option to be the key!");
                return false;
            }
            document.getElementById("numOfOpt").value = numOfOpt-1;
            return true;
        }
    </script>
</html>
