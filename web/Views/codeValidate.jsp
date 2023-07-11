<%-- 
    Document   : codeValidate
    Created on : Mar 8, 2023, 11:54:25 PM
    Author     : ACER NITRO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/x-icon" href="img/download.png">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <title>Validate</title>
    </head>
    <body>
        <form action="codeValidateURL" method="POST">
            <div class="form-group w-50 mx-auto mt-5">
                <label for="code">Enter code:</label>
                <input type="text" name="code" id="code" class="form-control" required>
                <p>${msg}</p>
                <button type="submit" value="Enter" class="btn btn-success form-control">Enter</button>
                <a href="UserController" class="text-light"><button type="button" class="btn btn-danger form-control">Back to login</button></a>
            </div>
        </form>   
    </body>
</html>
