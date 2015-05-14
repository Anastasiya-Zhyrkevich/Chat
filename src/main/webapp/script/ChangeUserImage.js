
function callCloudinaryUploader(){
    var imageUrl = "";
    cloudinary.openUploadWidget({upload_preset: 'vua5pobs', theme: "white"},
        function(error, result) {
            if(error == null){
                imageUrl = result[0].secure_url;
                alert(imageUrl);
                changeUserImage(imageUrl);
            }
        });

}

function changeUserImage(url) {
    var requestBody = {};
    requestBody.type = "CHANGE_USER_IMAGE";
    alert(usernameId);

    requestBody.struct = {};
    requestBody.struct.url = url;
    requestBody.struct.id = usernameId;



    var xhr = new XMLHttpRequest();
    xhr.open('PUT', host + port + adr, true);
    xhr.send(JSON.stringify(requestBody));
    xhr.onreadystatechange = function() {
        if(xhr.status == 200) {
            showServerState(true);

            if(xhr.readyState == 4) {

            }
        }
        else {
            showServerState(false);
        }
    }
}
