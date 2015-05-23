<!DOCTYPE html>
<html lang="en">
    <head>
    
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>ChatterBox</title>

        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/my_css_frontpage.css" rel="stylesheet">
		<script src="js/bootstrap.min.js"></script>


	</head>
	<body>

		<div class = "container">
		<div class = "main-title-block col-md-12">
			<p class = "pull-center title-style"> Welcome to your chat </p>	
		</div>

		<div class = "col-md-3 avatar">
			<div class="centered">
				<img class = "my-avatar-photo" src="img/images.jpeg" >
				<textarea id = "username-area"></textarea>
				<button id = "enter-button" type = "button" class="btn btn-success btn-lg btn-block" > Enter ! </button>
				<div id = "userName" class = "userName"></div>
			</div>

		</div>
		<div class = "col-md-9">
			<div id = "field" class="message-area">
				<div class = "message friend-message pull-left">
					<div class = "message-username"> Anastasiya </div>
				 	<div class = "message-text"> Hellojffhsbfhsbdkjfhksdjvfkdsghvfkgdsvkfhgvskhdgfvksghdvfkghsvdfkgsvdkfgvsdkgfvskhgdvfkghsdvfkgvsdkfg </div> 
					<div class = "message-time"> 13.02 </div>
				</div>

				<div class = "message my-message pull-right">
					<div class = "message-username"> Anastasiya </div>
					<button type="button" class="btn btn-xs btn-default pull-right icon-padding">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                              </button>
					<button type="button" class="btn btn-xs btn-default pull-right icon-padding">
                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                              </button>
				 	<div class = "message-text"> Helfjkgndfkjgndflkjgbdlfkjbgldfhbgldfjhbgdjfhbgdjhfbgjhdfbglhsdbg;jhdf;ghrdiuthrpiughirughkjfdbjhdfbglhfbgllo </div> 
					<div class = "message-time"> 13.02 </div>
				</div>
			</div>
			<div class="row">
                		<div class="col-md-6">
                    		<textarea id = "textarea" placeholder="New message" class="form-control message-field pull-left centered" rows="3"></textarea>
                		</div>
                		<div class="col-md-3 pull-right">
                        	<button id = "connection-button" type="button" class="btn btn-success btn-lg btn-block" disabled>Server Connection</button>
                        	<button id = "send-button" type="button" class="btn btn-primary btn-lg btn-block" disabled>Send message</button>
                		</div>
			</div>
            </div> 


	</div>



	
	  </div>


	<script src="js/script.js"></script>
	<script src="js/onload.js"></script>
	<script src="js/userName.js"></script>
	<script src="js/getMessage.js"></script>
	<script src="js/Message.js"></script>

    </body>
</html>
