/**
 * Created by User on 16.05.15.
 */
document.body.onload = function() {
    run();
};


function run(){
    document.getElementById("enter-button").addEventListener('click', enterButtonEvent);
    sendButton.disabled = true;                                //temporary
    document.getElementById("send-button").addEventListener("click", sendButtonEvent);
}

function enterButtonEvent(evtObj) {
    if (evtObj.type === 'click' && successAuto == false)
    {
        var enterButton = document.getElementById("enter-button");
        var loginName = document.getElementById("username-area").value;
        document.getElementById("username-area").value = "";
        if (loginName.length != 1) {
            var params = '?type=BASE_REQUEST&username=' + loginName;
            getR(host + port + adr + params, parseBaseResponse, continueWithError);
            enterButton.innerText = "Change name";
        }
        return;
    }
    if (evtObj.type === 'click' && successAuto == true)
    {
        var loginName = document.getElementById("username-area").innerText;
        document.getElementById("username-area").innerText = "";
        if (loginName.length != 1){
            var requestBody = {};
            requestBody.type = "CHANGE_USERNAME";
            requestBody.user = {};
            requestBody.user.userId = usernameId;
            requestBody.user.username = username;
            put(host + port + adr, requestBody, null, continueWithError);
        }
    }
}


function sendButtonEvent(evtObj){
    if (evtObj.type === 'click' && editing == false){
        //var textarea = document.getElementById("textarea");
        alert (textarea);
        if (textarea.value) {
            var requestBody = {};
            requestBody.userId = usernameId;
            requestBody.messageText = textarea.value;
            post(host + port + adr, requestBody, null, continueWithError);
            textarea.value = "";
        }
    }
    if (evtObj.type === 'click' && editing == true){
        if (textarea.value) {
            var requestBody = {};
            requestBody.type = "CHANGE_MESSAGE";
            requestBody.message.messageId = editingId;
            requestBody.message.messageText = textarea.value;
            put(host + port + adr, requestBody, null, continueWithError);
            textarea.value = "";
            editing = false;
            sendButton.innerText = "Send"
        }
    }
}

function editButtonEvent(evtObj){
    if (evtObj.type === 'click'){
        var messageNode = evtObj.target.parentNode;
        textarea.value = getTextNode(messageNode).innerText;
        sendButton.innerText = "Edit";
        editingId = messageNode.id;
        editing = true;
    }
}


function deleteButtonEvent(evtObj){
    if (evtObj.type === 'click'){
        var messageNode = evtObj.target.parentNode;
        requestBody = [messageNode.id];
        delete(host + port + adr, requestBody, null, continueWithError);
    }
}





function getR(url, continueWith, continueWithError) {
    ajax('GET', url, null, continueWith, continueWithError);
}
function post(url, data, continueWith, continueWithError) {
    ajax('POST', url, data, continueWith, continueWithError);
}
function put(url, data, continueWith, continueWithError) {
    ajax('PUT', url, data, continueWith, continueWithError);
}
function del(url, data, continueWith, continueWithError) {
    ajax('DELETE', url, data, continueWith, continueWithError);
}

function ajax(method, url, data, continueWith, continueWithError){
    var xhr = new XMLHttpRequest();
    xhr.open(method, url, true);
    xhr.send(JSON.stringify(data));
    xhr.onreadystatechange = function () {
        if (xhr.status == 200) {
            showServerState(true);
            if (xhr.readyState == 4) {
                if (method == "GET")
                    continueWith(xhr.responseText);
            }
        }
        else {
            continueWithError(xhr.status)
            showServerState(false);
        }
    }

}

function showServerState(flag){
    if (flag == true)
        connectionButton.disabled = false;
    else
        connectionButton.disabled = true;
}


function continueWithError(error){
    alert("Some Error on the server" + error);
}

