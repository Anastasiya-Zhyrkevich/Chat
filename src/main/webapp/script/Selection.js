function toggleSelection(message) {
    if(!message.classList.contains('selected-message')) {
        selectedMessages.push(message.id);
        message.classList.add('selected-message');
    }
    else {
        selectedMessages.splice(selectedMessages.indexOf(message.id), 1);
        message.classList.remove('selected-message');
    }
    checkDeleteButtonState();
}
function checkDeleteButtonState() {
    if(selectedMessages.length) {
        deleteButton.style.display = null;
        deleteButton.style.opacity = '.3';
        deleteButton.innerText = 'Delete selected (' + selectedMessages.length + ')';
    }
    else {
        deleteButton.style.opacity = null;
        deleteButton.style.display = 'none';
    }
}