<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Chat</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

<div id="username-hint" style="display:none;"></div>
<div id="message-container"></div>
<div id="messages">
	<div style="clear: both;"></div>
</div>

<div id="delete-button" style="display: none;"></div>

<div id="head">
	<div id="userdiv"></div>
	<div id="server">Server is available</div>
	<div id="exit">Exit</div>
</div>
<div id="bottom">
	<div id="img"></div>
	<textarea id="textarea" placeholder="Enter your message"></textarea>
	<div id="send-message-button">Send</div>
</div>




<script type="text/javascript" src="script/script.js"></script>
<script type="text/javascript" src="script/username.js"></script>

<script type="text/javascript" src="script/SendMessage.js"></script>
<script type="text/javascript" src="script/KeyListener.js"></script>
<script type="text/javascript" src="script/Selection.js"></script>
<script type="text/javascript" src="script/MouseListener.js"></script>
<script type="text/javascript" src="script/Delete.js"></script>
<script type="text/javascript" src="script/onload.js"></script>
<script type="text/javascript" src="script/edit.js"></script>

<script type="text/javascript" src="script/Server.js"></script>
</body>
</html>