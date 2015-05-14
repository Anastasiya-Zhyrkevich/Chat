function get(id) {
    return document.getElementById(id);
}
function create(element, className) {
    var el = document.createElement(element);
    if(className) {
        el.className = className;
    }
    return el;
}

var usernameId = '';
var selectedMessages = [];

var textarea = get('textarea');
var sendButton = get('send-message-button');

var messages = get('messages');
var emptyDiv = messages.lastElementChild;
var deleteButton = get('delete-button');

var hint = get('username-hint');

var users = {};
var messageToken;
var messageEditToken;
var messageDeleteToken;
var userToken;
var userChangeToken;

var host = "http://localhost";
var port = ":8080";
var adr = "/Servlet";