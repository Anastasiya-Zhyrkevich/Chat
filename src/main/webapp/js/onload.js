/**
 * Created by User on 16.05.15.
 */
document.body.onload = function() {
    run();
};


function run(){
    document.getElementById("enter-button").addEventListener('click', enterButtonEvent);
    appState.sendButton.disabled = true;                                //temporary
    document.getElementById("send-button").addEventListener("click", sendButtonEvent);
    document.getElementById("register-button").addEventListener("click", registerButtonEvent);
    document.getElementById("successReg").innerText = "";
}

function registerButtonEvent(evtObj) {
    if (evtObj.type === 'click') {
        var loginName = document.getElementById("reg-username-area").value;
        var passWord = document.getElementById("reg-pass-area").value;
        document.getElementById("reg-username-area").value = "";
        document.getElementById("reg-pass-area").value = "";
        if (loginName.length != 0){
            var substr = (loginName + "aaaaaa").substring(0,6);
            var params = '?type=REGISTER&username=' + loginName+ '&password=' + passWord;
            getR(appState.host + appState.port + appState.adr + params, successfullRegistration, unsuccessfullRegistration);
        }
    }
}

function successfullRegistration(username){
    document.getElementById("successReg").innerText = "Successfull registration of user " + username;
}

function unsuccessfullRegistration(){
    document.getElementById("successReg").innerText = "Already got such a user";
}



function enterButtonEvent(evtObj) {
    if (evtObj.type === 'click' && appState.successAuto == false)
    {
        var loginName = document.getElementById("username-area").value;
        var passWord = document.getElementById("pass-area").value;
        document.getElementById("username-area").value = "";
        document.getElementById("pass-area").value = "";
        if (loginName.length != 0) {
            var params = '?type=BASE_REQUEST&username=' + loginName+ '&password=' + passWord;
            getR(appState.host + appState.port + appState.adr + params, parseBaseResponse, continueWithError);
            appState.enterButton.innerText = "Change name";
            appState.successAuto = true;
        }
        return;
    }
    if (evtObj.type === 'click' && appState.successAuto == true)
    {
        var loginName = document.getElementById("username-area").value;
        document.getElementById("username-area").value = "";
        if (loginName.length != 0){
            var requestBody = {};
            requestBody.type = "CHANGE_USERNAME";
            requestBody.user = {};
            requestBody.user.userId = appState.usernameId;
            requestBody.user.username =  loginName;
            putR(appState.host + appState.port + appState.adr, requestBody, null, continueWithError);
        }
    }
}


function sendButtonEvent(evtObj){
    if (evtObj.type === 'click' && appState.editing == false){
        //var textarea = document.getElementById("textarea");
        //alert ( appState.textarea);
        if (appState.textarea.value) {
            var requestBody = {};
            requestBody.userId =  appState.usernameId;
            requestBody.messageText =  appState.textarea.value;
            post(appState.host + appState.port + appState.adr, requestBody, null, continueWithError);
            appState.textarea.value = "";
        }
    }
    if (evtObj.type === 'click' &&  appState.editing == true){
        //alert("after edit");
        if ( appState.textarea.value) {
            var requestBody = {};
            requestBody.type = "CHANGE_MESSAGE";
            requestBody.message = {};
            requestBody.message.messageId = appState.editingId;
            requestBody.message.messageText = appState.textarea.value;
            appState.textarea.value = "";
            appState.editing = false;
            appState.sendButton.innerText = "Send";
            putR(appState.host + appState.port + appState.adr, requestBody, null, continueWithError);
        }
    }
}

function editButtonEvent(evtObj){
    if (evtObj.type === 'click'){
        var messageNode = evtObj.target.parentNode.parentNode;
        appState.textarea.value = getTextNode(messageNode).innerText;
        appState.sendButton.innerText = "Edit";
        appState.editingId = messageNode.id;
        appState.editing = true;
    }
}


function deleteButtonEvent(evtObj){
    if (evtObj.type === 'click'){
        var messageNode = evtObj.target.parentNode.parentNode;
        var requestBody = [];
        requestBody.push(messageNode.id);
        //alert(requestBody);
        //alert(messageNode.id);
        del(appState.host + appState.port + appState.adr, requestBody, null, continueWithError);
    }
}


function getR(url, continueWith, continueWithError) {
    ajax('GET', url, null, continueWith, continueWithError);
}
function post(url, data, continueWith, continueWithError) {
    ajax('POST', url, data, continueWith, continueWithError);
}
function putR(url, data, continueWith, continueWithError) {
    ajax('PUT', url, data, continueWith, continueWithError);
}
function del(url, data, continueWith, continueWithError) {
    //alert("delete");
    ajax('DELETE', url, data, continueWith, continueWithError);
}

function ajax(method, url, data, continueWith, continueWithError){
    var xhr = new XMLHttpRequest();
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.status == 200) {
            showServerState(true);
            if (xhr.readyState == 4) {
                if (method == 'GET') {
                    continueWith(xhr.responseText);
                    return;
                }
            }
        }
        else {
            continueWithError(xhr.status);
            showServerState(false);
        }
    }
    xhr.ontimeout = function () {
        //alert("timeout");
        continueWithError('Server timed out !');
        showServerState(false);
        if (method == 'GET'){
            //alert("get timeout");
            startGettingMessages();
        }

    }
    xhr.onerror = function (e) {
        var errMsg = 'Server connection error ' + appState.host + '\n'+
       		'\n' +
        		'Check if \n'+
        			'- server is active\n'+
       		'- server sends header "Access-Control-Allow-Origin:*"';
      	continueWithError(errMsg);
	};

    xhr.send(JSON.stringify(data));
}

function showServerState(flag){
    if (flag == true)
        appState.connectionButton.disabled = false;
    else
        appState.connectionButton.disabled = true;
}




function continueWithError(error) {
    console.error(error);
    top.location = "error.jsp";
}

