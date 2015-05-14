


cloudinary.setCloudName('dnfei4skc');

var widget = cloudinary.createUploadWidget({ upload_preset: 'vua5pobs', theme: 'white'},
    function(error, result) {
        console.log(error, result);
        alert(result[0].secure_url);
    });



