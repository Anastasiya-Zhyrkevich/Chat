/**
 * Created by User on 16.05.15.
 */

function create(element, className) {
    var el = document.createElement(element);
    if(className) {
        el.classList.add(className);
    }
    return el;
}

function getEl(id) {
    return document.getElementById(id);
}


var successAuto = false;
var editing = false;

var users = {};
var messageToken;
var userName;
var usernameId;
var field = document.getElementById("field");
var textarea = document.getElementById("textarea");
var sendButton = document.getElementById("send-button");
var connectionButton = document.getElementById("connection-button");
var enterButton = document.getElementById("enter-button");


var host = "http://localhost";
var port = ":8080";
var adr = "/Servlet";