/**
 * Created by User on 19.05.15.
 */

function stopGettingMessages() {
    clearTimeout(timer);
}

function startGettingMessages() {
    var params = '?type=GET_UPDATE' + '&token=' + messageToken;
    getR(host + port + adr + params, parseGetResponse, continueWithError);
    timer = setTimeout(function  func () {
        startGettingMessages();
        timer = setTimeout(func, 10000);
    },  10000);
}

function parseBaseResponse(responseText){
    alert("Base");
    var resp = JSON.parse(responseText);
    usernameId = resp.currentUserId;
    alert(usernameId);
    messageToken = resp.token;
    textarea.focus();
    sendButton.disabled = false;
    successAuto = true;
    startGettingMessages();
}


function parseGetResponse(responseText){
    //alert("GetResponse");
    var resp = JSON.parse(responseText);
    JSON.parse(resp.users).forEach(function (user) {
            users[user.userId] = {
                "username": user.username,
                "userImage": user.userImage
            };

            if (usernameId == user.userId) {
                if (user.username) {
                    setUsername(user.username);
                }
            }
        });

        JSON.parse(resp.changedUsers).forEach(function (user) {
            users[user.userId] = {
                "username": user.username,
                "userImage": user.userImage
            };
            if (usernameId == user.userId) {
                if (user.username) {
                    setUsername(user.username);
                }
            }
        });


        messageToken = resp.token;
        JSON.parse(resp.messages).forEach(function (message) {
            drawMessage(message);
        });

        JSON.parse(resp.editedMessages).forEach(function (editing) {
            setMessageText(editing.messageId, editing.messageText);
        });


        JSON.parse(resp.deletedMessagesIds).forEach(function (id) {
            makeMessageDeleted(id);
        });

        JSON.parse(resp.changedUsers).forEach(function (user) {
            users[user.userId] = {
                "username": user.username,
                "userImage": user.userImage
            };
            if (usernameId == user.userId) {
                if (user.username) {
                    setUsername(user.username);
                }
                if (user.userImage) {

                }
            }
        });


}

