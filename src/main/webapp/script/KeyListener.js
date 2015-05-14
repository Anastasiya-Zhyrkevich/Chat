textarea.onkeypress = function(event) {
    if(event.keyCode == 10) {
        sendButton.onclick();
    }
};