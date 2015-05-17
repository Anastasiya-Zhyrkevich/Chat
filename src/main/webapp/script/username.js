function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null
}

function setUsername(username) {
    get('userdiv').innerText = username;
    get('userdiv').style.display = '';
}

function Background() {
    return create('div', 'modal-background');
}

function showUsernameForm(isChanging) {
    get('userdiv').style.display = 'none';

    var background = new Background();

    if (isChanging) {
        window.history.back(-1);
        var text = getURLParameter("loginName");
        if (!text) {
            return;
        }
        changeUsername(text);
    }
    else {
        var text = getURLParameter("loginName");
        if (!text) {
            return;
        }
        enter(text);
    }

    function enter(username) {
        var params = '?type=BASE_REQUEST&username=' + username;

        var xhr = new XMLHttpRequest();
        xhr.open('GET', host + port + adr + params, true);
        xhr.send();
        xhr.onreadystatechange = function () {
            if (xhr.status == 200) {
                showServerState(true);

                if (xhr.readyState == 4) {
                    var resp = JSON.parse(xhr.responseText);
                    usernameId = resp.currentUserId;
                    messageToken = resp.token;
                    startGettingMessages();
                }
            }
            else {
                showServerState(false);
            }
        }
    }


    function changeUsername(username) {
        var requestBody = {};
        requestBody.type = "CHANGE_USERNAME";
        requestBody.user = {};
        requestBody.user.userId = usernameId;
        requestBody.user.username = username;

        var xhr = new XMLHttpRequest();
        xhr.open('PUT', host + port + adr, true);
        xhr.send(JSON.stringify(requestBody));
        xhr.onreadystatechange = function () {
            if (xhr.status == 200) {
                showServerState(true);

                if (xhr.readyState == 4) {
                    document.body.removeChild(background);
                    textarea.focus();
                }
            }
            else {
                showServerState(false);
            }
        }
    }
}