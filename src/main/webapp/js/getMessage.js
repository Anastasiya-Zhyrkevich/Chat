/**
 * Created by User on 19.05.15.
 */


function startGettingMessages() {
    var params = '?type=GET_UPDATE' + '&token=' + appState.messageToken;
    getR(appState.host + appState.port + appState.adr + params, parseGetResponse, continueWithError);
}

function parseBaseResponse(responseText){
    //alert("Base");
    var resp = JSON.parse(responseText);
    appState.usernameId = resp.currentUserId;
    //alert(appState.usernameId);
    appState.messageToken = resp.token;
    appState.textarea.focus();
    appState.sendButton.disabled = false;
    appState.successAuto = true;
    startGettingMessages();
}


function parseGetResponse(responseText){
    //alert("GetResponse");
    var resp = JSON.parse(responseText);
    JSON.parse(resp.users).forEach(function (user) {
            appState.users[user.userId] = {
                "username": user.username,
                "userImage": user.userImage
            };

            if (appState.usernameId == user.userId) {
                if (user.username) {
                    setUsername(user.username);
                }
            }
        });
        var editedUsersId = [];

        JSON.parse(resp.changedUsers).forEach(function (user) {
            appState.users[user.userId] = {
                "username": user.username
            };
            if (appState.usernameId == user.userId) {
                if (user.username) {
                    setUsername(user.username);
                }
            }
            editedUsersId.push(user.userId);
        });
    if (editedUsersId.length != 0) {
        for (var i = 0; i < appState.field.childNodes.length; i++) {
            if (editedUsersId.indexOf(appState.field.childNodes[i].userId) != -1) {
                changeUserName(appState.field.childNodes[i]);
            }
        }
    }


    appState.messageToken = resp.token;
        JSON.parse(resp.messages).forEach(function (message) {
            drawMessage(message);
        });

        JSON.parse(resp.editedMessages).forEach(function (editing) {
            setMessageText(editing.messageId, editing.messageText);
        });


        JSON.parse(resp.deletedMessagesIds).forEach(function (id) {
            makeMessageDeleted(id);
        });

    startGettingMessages();
}

