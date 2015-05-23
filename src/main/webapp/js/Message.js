/**
 * Created by User on 16.05.15.
 */

function MessageNode(message){
    var messageNode = document.createElement("div");
    messageNode.id = message.messageId;
    messageNode.classList.add("message");

    var usernameNode = create('div', 'message-username');
    usernameNode.innerText = users[message.userId].username;

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
    timeNode.innerHTML = date.toLocaleTimeString() + "<br>" + date.toLocaleDateString();

    if(usernameId == message.userId) {
        messageNode.classList.add('my-message');
        messageNode.classList.add('pull-right');

        if(message.isDeleted == 0) {
            var editButtonFeatures = ["glyphicon-edit"]
            var editButton = new ChangeButton(editButtonFeatures);
            editButton.addEventListener('click', editButtonEvent);

            var deleteButtonFeatures = ["glyphicon-remove"]
            var deleteButton = new ChangeButton(deleteButtonFeatures);
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
    editButton.classList.add("btn");
    editButton.classList.add("btn-xs");
    editButton.classList.add("btn-default");
    editButton.classList.add("pull-right");
    editButton.classList.add("icon-padding");
    editButton.typeName = "button";
    var sp = document.createElement('span');
    sp.classList.add("glyphicon");
    sp.classList.add(features[0]);
    sp.setAttribute("area-hidden", "true");
    button.appendChild(sp);
    return button;
}

function addMessageToField(messageNode){
    field.scrollIntoView(true);
    field.appendChild(messageNode);
}

function drawMessage(message){
    var messageNode = new MessageNode(message);
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

function clearMessageContainer() {
    while (field .firstChild) {
        field .removeChild(field .firstChild);
    }
}


