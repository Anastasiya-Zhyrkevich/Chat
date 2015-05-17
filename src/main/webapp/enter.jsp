<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href='enter.css' rel='stylesheet' type='text/css'>
<link href='enter.css' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="script/username.js"></script>

<html>
<head>
    <title>Welcome to chat</title>
</head>
<body>
<div class="testbox">
  <h1>Welcome to our chat</h1>

  <form action="homepage.jsp">
    <input type="hidden" name="type" value="BASE_REQUEST">
    <input type="text" name="loginName" id="name" placeholder="Name" required/>
    <input type="submit" class="button" value=" Enter !"/>
  </form>
</div>
</body>
</html>
