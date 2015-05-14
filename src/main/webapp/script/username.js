function setUsername(username) {
    get('userdiv').innerText = username;
    get('userdiv').style.display = '';
}

function Background() {
    return create('div', 'modal-background');
}

function UsernameForm() {
    var container = create('div', 'entering');

    var p = create('p');
    p.innerText = 'Username:';

    var input = create('input');
    input.type = 'text';
    input.placeholder = 'Enter username';


    var button = create('button');
    button.innerText = 'Enter';

    container.appendChild(p);
    container.appendChild(input);
    container.appendChild(button);

    return {
        "container":container,
        "input":input,
        "button":button
    };
}

function showUsernameForm(isChanging) {
    get('userdiv').style.display = 'none';

    var background = new Background();
    var usernameForm = new UsernameForm();

    background.appendChild(usernameForm.container);
    document.body.appendChild(background);

    usernameForm.input.focus();

    if(isChanging) {
        usernameForm.button.onclick = function() {
            var text = usernameForm.input.value;
            if(!text) {
                return;
            }

            changeUsername(text);
        }
    }
    else {
        usernameForm.button.onclick = function() {
            var text = usernameForm.input.value;
            if(!text) {
                return;
            }

            enter(text);
        }
    }

    function enter(username) {
        var params = '?type=BASE_REQUEST&username=' + username;

        var xhr = new XMLHttpRequest();
        xhr.open('GET', host + port + adr + params, true);
        xhr.send();
        xhr.onreadystatechange = function() {
            if(xhr.status == 200) {
                showServerState(true);

                if(xhr.readyState == 4) {
                    var resp = JSON.parse(xhr.responseText);
                    usernameId = resp.currentUserId;
                    messageToken = resp.messageToken;
                    messageEditToken = resp.messageEditToken;
                    messageDeleteToken = resp.messageDeleteToken;
                    userToken = resp.userToken;
                    userChangeToken = resp.userChangeToken;
                    document.body.removeChild(background);
                    textarea.focus();
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

        alert('PUT-request:\n' + JSON.stringify(requestBody));

        var xhr = new XMLHttpRequest();
        xhr.open('PUT', host + port + adr, true);
        xhr.send(JSON.stringify(requestBody));
        xhr.onreadystatechange = function() {
            if(xhr.status == 200) {
                showServerState(true);

                if(xhr.readyState == 4) {
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