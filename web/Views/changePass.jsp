<%-- 
    Document   : changePass
    Created on : Mar 15, 2023, 1:19:48 PM
    Author     : tranm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="img/download.png">
        <title>Change pass</title>
    </head>
    <body>
        <form action="UserController" method="POST" id="form">
            <div class="form-group w-50 mx-auto mt-5">
                <input type="hidden" name="go" value ="changePassword">
                <label for ="old">Old password</label>
                <input type="password" name="old" id="old" class="form-control" required minlength="8" maxlength="14" placeholder="Your old password">
                <label for ="password">Password</label>
                <input type="password" name="password" id="password" class="form-control" required minlength="8" maxlength="14" placeholder="8-14 characters with at least one number, one uppcase and one lowercase letter">
                <label for ="confirm">Confirm password</label>
                <input type="password" name="confirm" id="confirm" class="form-control" required minlength="8" maxlength="14" placeholder="Confirm your password">
                <div class="mt-4">
                    <button type="submit" name="submitChange" class="btn btn-success">Change</button>
                    <button type="reset" class="btn btn-info">Reset</button>
                    <a href="HomeController"><button type="button" class="btn btn-secondary">Back to Home</button></a>
                </div>
            </div>
        </form>
        <span hidden id="oldpass">${old}</span>
    </body>
    <script>
        let form = document.getElementById('form');
        form.onsubmit = function () {
            let pass = document.getElementById('password').value;
            let confirm = document.getElementById('confirm').value;
            let oldpass = document.getElementById('oldpass').innerText;
            let old = document.getElementById('old').value;
            if(oldpass != old){
                alert('You entered wrong old password!!');
                return false; 
            }
            if (pass !== confirm) {
                alert('Your password does not match!!');
                return false;
            }
            return true;
        }
    </script>
</html>
