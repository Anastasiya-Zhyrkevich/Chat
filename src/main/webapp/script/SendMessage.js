function MessageNode(message) {
    /*
     <div class="message" id=123>
     <div class="message-img" usernameId="Id"></div>
     <div class="message-text"></div>
     <time></time>

     <div class="edit-button"></div>
     </div>
     */
    var msgNode = create('div', 'message');

    var img = create('div', 'message-img');
    img.setAttribute('usernameId', message.userId);
    img.style.backgroundImage = 'url(' + users[message.userId].userImage + ')';

    var text = create('div', 'message-text');
    if(message.isDeleted == 1) {
        msgNode.classList.add('deleted-message');
        text.innerText = 'Message was deleted';
    }
    else {
        text.innerHTML = message.messageText;
    }

    var time = create('time');
    var date = new Date(+message.messageTime);
    time.innerHTML = date.toLocaleTimeString() + "<br>" + date.toLocaleDateString();

    msgNode.id = message.messageId;

    msgNode.appendChild(img);
    msgNode.appendChild(text);
    msgNode.appendChild(time);

    if(usernameId == message.userId) {
        msgNode.classList.add('my-message');

        if(message.isDeleted == 0) {
            var editButton = create('div', 'edit-button');
            msgNode.appendChild(editButton);
        }
    }

    return msgNode;
}

function addLineDividers(text, fromHtml) {
    if(fromHtml) {
        return text.replace( /<br>+/g, '\n');
    }
    return text.replace( /^\s+|\s+$/g, '' ).replace(/\r?\n+/g, '<br>');
}

function sendMessage() {
    function MessageToPost(text) {
        return {
            "userId":usernameId,
            "messageText":text
        };
    }

    var text = textarea.value;
    text = addLineDividers(text);

    if(!text) {
        return;
    }

    var body = JSON.stringify(new MessageToPost(text));

    var xhr = new XMLHttpRequest();
    xhr.open('POST', host + port + adr, true);
    xhr.send(body);
    xhr.onreadystatechange = function() {
        if(xhr.status == 200) {
            showServerState(true);

            if(xhr.readyState == 4) {
                textarea.value = '';
            }
        }
        else {
            showServerState(false);
        }
    }
}