<%-- 
    Document   : register
    Created on : Mar 9, 2023, 12:16:46 AM
    Author     : ACER NITRO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="img/download.png">
        <title>Sign up</title>
    </head>
    <body>
        <form action="UserController" method="POST" id="form">
            <div class="form-group w-50 mx-auto mt-5">
                <input type="hidden" name="go" value ="register">
                <label for="username">Username</label>
                <input type="text" name="username" id="username" class="form-control" required placeholder="3-50 characters" minlength="3" maxlength="50">
                <label for ="password">Password</label>
                <input type="password" name="password" id="password" class="form-control" required minlength="8" maxlength="14" placeholder="8-14 characters with at least one number, one uppcase and one lowercase letter">
                <label for ="confirm">Confirm password</label>
                <input type="password" name="confirm" id="confirm" class="form-control" required minlength="8" maxlength="14" placeholder="Confirm your password">
                <label for="email">Email</label>
                <input type="email" name="email" id="email" class="form-control" required placeholder="abc@gmail.com">
                <span>Role</span>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="isTeacher" id="Teacher" value="1">
                    <label class="form-check-label" for="Teacher">Teacher</label>
                </div>
                <div class="form-check form-check-inline"> 
                    <input class="form-check-input" type="radio" name="isTeacher" id="Student" value="0" checked>
                    <label class="form-check-label" for="Student">Student</label>
                </div>
                <br>
                <div class="mt-4">
                    <button type="submit" name="submitRegister" class="btn btn-success">Register</button>
                    <button type="reset" class="btn btn-info">Reset</button>
                    <a href="UserController?go=login"><button type="button" class="btn btn-secondary">Back to login</button></a>
                </div>
            </div>
        </form>
    </body>
    <script>
        let form = document.getElementById('form');
        form.onsubmit = function () {
            let pass = document.getElementById('password').value;
            let confirm = document.getElementById('confirm').value;
            if(pass!==confirm){
                alert('Your password does not match!!');
                return false;
            }
            return true;
        }
    </script>
</html>
