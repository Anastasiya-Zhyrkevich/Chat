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

var appState = {
    successAuto : false,
    editing : false,
    editingId : false,
    users : {},
    messageToken: "0",
    userName : "",
    usernameId : "",
    field : document.getElementById("field"),
    textarea : document.getElementById("textarea"),
    sendButton : document.getElementById("send-button"),
    connectionButton : document.getElementById("connection-button"),
    enterButton : document.getElementById("enter-button"),
    host : "http://localhost",
    port : ":8081",
    adr : "/Servlet"
};
