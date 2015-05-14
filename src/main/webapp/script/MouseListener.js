messages.onclick = messagesClick;

function messagesClick(event) {
    var target = event.target;
    if(target.classList.contains('my-message') && !isDeleted(target)) {
        var messageNode = target;
        toggleSelection(messageNode);
    }
    else if(target.classList.contains('edit-button')) {
        editMessage(target.parentNode);
    }
    return false;
}

document.body.onmousemove = function(event) {
    var target = event.target;
    if(!target.classList.contains('message-img')) {
        hint.style.display = 'none';
        return;
    }

    var userId = target.getAttribute('usernameId');
    hint.innerText = users[userId].username;
    hint.style.right = 'calc(100% - ' + event.pageX + 'px + 2px)';
    hint.style.top = 'calc(' + (event.pageY - scrollY) + 'px - 1em - 10px)';
    hint.style.display = '';
};

deleteButton.onmouseover = function() {
    this.style.opacity = '1';
};
deleteButton.onmouseout = function() {
    this.style.opacity = '.3';
};
deleteButton.onclick = function() {
    deleteSelected();
};

sendButton.onclick = sendMessage;

get('userdiv').onclick = function() {
    showUsernameForm(true);
};

get('exit').onclick = function() {
    stopGettingMessages();
    clearMessageContainer();
    showUsernameForm();
};