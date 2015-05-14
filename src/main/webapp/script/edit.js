function editMessage(messageNode) {
    var bottom = get('bottom');
    var background = new Background();

    var rect = messageNode.getBoundingClientRect();

    transformBottom(true,
        rect.bottom + pageYOffset,
        messageNode.getElementsByClassName('message-text')[0].innerText);

    messageNode.style.zIndex = 1001;
    messages.onclick = null;
    document.body.appendChild(background);


    function transformBottom(startEditing, bottomPosition, text) {
        if(startEditing) {
            sendButton.innerText = 'save';
            bottomPosition += 135;
            bottom.style.position = 'absolute';
            bottom.style.bottom = 'calc(100% - ' + bottomPosition + 'px)';
            bottom.style.zIndex = 1001;

            textarea.value = addLineDividers(text, true);
            textarea.focus();

            sendButton.onclick = function() {
                var text = textarea.value;
                text = addLineDividers(text);
                if(!text) {
                    return;
                }

                putEditing(messageNode.id, text);
            }
        }
        else {
            sendButton.innerText = 'Send';
            bottom.style.position = 'fixed';
            bottom.style.bottom = 0;
            bottom.style.zIndex = 100;

            messageNode.style.zIndex = null;
            messages.onclick = messagesClick;
            sendButton.onclick = sendMessage;
            document.body.removeChild(background);
            textarea.value = '';
        }
    }

    function putEditing(messageId, text) {
        var body = {
            "type":"CHANGE_MESSAGE",
            "message": {
                "messageId":messageId,
                "messageText":text
            }
        };

        var xhr = new XMLHttpRequest();
        xhr.open('PUT', host + port + adr, true);
        xhr.send(JSON.stringify(body));
        xhr.onreadystatechange = function() {

            if(xhr.status == 200) {
                showServerState(true);

                if(xhr.readyState == 4) {
                    transformBottom(false);
                }
            }
            else {
                showServerState(false);
            }
        }
    }
}