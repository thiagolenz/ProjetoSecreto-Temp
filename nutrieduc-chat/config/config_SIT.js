exports.configApp = function (){
	return  {
		socket : {
			clientUrl : "http://ec2-54-94-173-226.sa-east-1.compute.amazonaws.com",
			justSecure : false
		},
		appServer : {
			host: 'ec2-54-94-211-66.sa-east-1.compute.amazonaws.com'
		}
	} 
}