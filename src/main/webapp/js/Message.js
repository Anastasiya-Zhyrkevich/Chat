/**
 * Created by User on 16.05.15.
 */


function MessageNode(message){
    var messageNode = document.createElement("div");
    messageNode.id = message.messageId;
    messageNode.userId = message.userId;
    messageNode.classList.add("message");

    var usernameNode = create('div', 'message-username');
    usernameNode.innerText = appState.users[message.userId].username;

    var textNode = create('div', 'message-text');
    if(message.isDeleted == 1) {
        messageNode.classList.add('deleted-message');
        textNode.innerText = 'Message was deleted...';
    }
    else{
        textNode.innerText = message.messageText;
    }

    var timeNode = create('div', 'message-time');
    var date = new Date(+message.messageTime);
    timeNode.innerHTML = date.toLocaleTimeString() + " " + date.toLocaleDateString();

    if(appState.usernameId == message.userId) {
        messageNode.classList.add('my-message');
        messageNode.classList.add('pull-right');

        if(message.isDeleted == 0) {
            var editButtonFeatures = ["glyphicon-edit"]
            var editButton = ChangeButton(editButtonFeatures);
            editButton.disabled = false;
            editButton.addEventListener('click', editButtonEvent);

            var deleteButtonFeatures = ["glyphicon-remove"]
            var deleteButton = ChangeButton(deleteButtonFeatures);
            deleteButton.disabled = false;
            deleteButton.addEventListener('click', deleteButtonEvent);

            messageNode.appendChild(editButton);
            messageNode.appendChild(deleteButton);
        }
    }
    else {
        messageNode.classList.add('friend-message');
        messageNode.classList.add('pull-left');
    }


    messageNode.appendChild(usernameNode);
    messageNode.appendChild(textNode);
    messageNode.appendChild(timeNode);
    return messageNode;
}

function ChangeButton(features){
    var button = document.createElement('button');
    button.classList.add("btn");
    button.classList.add("btn-xs");
    button.classList.add("btn-default");
    button.classList.add("pull-right");
    button.classList.add("icon-padding");
    button.typeName = "button";
    var sp = document.createElement('span');
    sp.classList.add("glyphicon");
    sp.classList.add(features[0]);
    sp.setAttribute("area-hidden", "true");
    button.appendChild(sp);
    return button;
}

function addMessageToField(messageNode){
    appState.field.appendChild(messageNode);
    messageNode.scrollIntoView(true);
}

function drawMessage(message){
    var messageNode = MessageNode(message);
    addMessageToField(messageNode);
}

function getTextNode(messageNode){
    var messageTextNode;
    if (messageNode.childNodes.length == 3)
        messageTextNode = messageNode.childNodes[1];
    else
        messageTextNode = messageNode.childNodes[3];
    return messageTextNode;
}

function editMessageText(message){
    var messageNode = document.getElementById(message.messageId);
    getTextNode(messageNode).innerText = message.messageText;
}

function setMessageText(id, text){
    var messageNode = document.getElementById(id);
    getTextNode(messageNode).innerText = text;
}

function makeMessageDeleted(id){
    var messageNode = document.getElementById(id);
    messageNode.classList.add('deleted-message');
    getTextNode(messageNode).innerText = 'Message was deleted';
    if (messageNode.classList.contains("my-message")){
        messageNode.childNodes[0].disabled = true;
        messageNode.childNodes[1].disabled = true;
    }
}

function changeUserName(messageNode){
    if (messageNode.childNodes.length == 3)
        messageNode.childNodes[0].innerText = appState.users[messageNode.userId].username;
    else
        messageNode.childNodes[2].innerText = appState.users[messageNode.userId].username;
}

