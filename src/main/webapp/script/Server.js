function showServerState(isAvailable) {
    if(isAvailable) {
        get('server').innerText = 'Server is available';
    }
    else {
        get('server').innerText = 'Server is not available';
    }
}