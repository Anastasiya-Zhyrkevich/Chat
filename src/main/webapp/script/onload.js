document.body.onload = function() {
    showUsernameForm();
};

function startGettingMessages() {
    doGet();
}

function stopGettingMessages() {
    clearTimeout(timer);
}

function doGet() {
    var params = 	'?type=GET_UPDATE' +
        '&token=' + messageToken;

    var xhr = new XMLHttpRequest();
    xhr.open('GET', host + port + adr + params, true);
    xhr.send();
    xhr.onreadystatechange = function() {
        if(xhr.status == 200) {
            showServerState(true);
            if(xhr.readyState == 4) {
                var resp = JSON.parse(xhr.responseText);

                timer = setTimeout(function func() {
                    doGet(); }, 0);

                    JSON.parse(resp.users).forEach(function(user) {
                        users[user.userId] = {
                            "username":user.username,
                            "userImage":user.userImage
                        };

                        if(usernameId == user.userId) {
                            if(user.username) {
                                setUsername(user.username);
                            }
                            if(user.userImage) {
                                get('img').style.backgroundImage = 'url(' + user.userImage + ')';
                            }
                        }
                    });




                    JSON.parse(resp.changedUsers).forEach(function(user) {
                        users[user.userId] = {
                            "username":user.username,
                            "userImage":user.userImage
                        };
                        if(usernameId == user.userId) {
                            if(user.username) {
                                setUsername(user.username);
                            }
                            if(user.userImage) {
                                get('img').style.backgroundImage = 'url(' + user.userImage + ')';
                                var divs = document.querySelectorAll('[usernameId="' + user.userId + '"]');
                                divs.forEach = [].forEach;
                                divs.forEach(function(div) {
                                    div.style.backgroundImage = 'url(' + user.userImage + ')';
                                });
                            }
                        }
                    });


                    messageToken = resp.token;
                    JSON.parse(resp.messages).forEach(function(message) {
                        drawMessage(message);
                    });


                    JSON.parse(resp.editedMessages).forEach(function(editing) {
                        setMessageText(editing.messageId, editing.messageText);
                    });



                    JSON.parse(resp.deletedMessagesIds).forEach(function(id) {
                        makeMessageDeleted(id);
                    });





                    JSON.parse(resp.changedUsers).forEach(function(user) {
                        users[user.userId] = {
                            "username":user.username,
                            "userImage":user.userImage
                        };
                        if(usernameId == user.userId) {
                            if(user.username) {
                                setUsername(user.username);
                            }
                            if(user.userImage) {

                            }
                        }
                    });

            }
        }
        else {
            showServerState(false);
        }
    }
}

function setMessageText(messageId, text) {
    get(messageId).getElementsByClassName('message-text')[0].innerHTML = text;
}

function drawMessage(message) {
    var messageNode = new MessageNode(message);

    messages.insertBefore(messageNode, emptyDiv);
    messageNode.scrollIntoView();
}

function clearMessageContainer() {
    while(messages.firstElementChild != emptyDiv) {
        messages.removeChild(messages.firstElementChild);
    }
}