define(["components/LoadingMessages"],function (loading){

	function post (req) {		
		req.type = "POST";
		doAjax(req);
	}

	function get (req) {
		req.type = "GET";
		doAjax(req);
	}

	function put (req) {
		req.type = "PUT";
		req.url = req.url + "/"+req.id;
		doAjax(req);
	}

	function remove (req) {
		req.type = "DELETE";
		req.url = req.url + "/"+req.id;
		doAjax(req);
	}

	function doAjax (req) {
		if (req.headers == undefined)
			req.headers = {};
		if (!req.skipLoading)
			loading.loading({
				inModal:req.inModal
			});
		$.ajax({
			url: "rest/"+ req.url,
			type : req.type,
			data: req.type != "GET" && req.type != "DELETE" ? JSON.stringify(req.data) : null,
			dataType : "json",
			headers: req.headers
		}).done(function(data){
			success(req, data);
		}).fail(function(data){
			error(req, data);
		});
	}
	
	function success (req, data) {
		loading.hideLoading({
			inModal: req.inModal
		});
		req.success(data);
	}
	
	function error (req, data) {
		loading.hideLoading({
			inModal: req.inModal
		});
		if (data.status == 401)
			window.location = "login";
		if (req.error)
			req.error(prepareErrorData(data));
	}
	
	function prepareErrorData (data) {
		var errorData = data.responseJSON;
		if (!errorData)
			errorData = {};
		errorData.httpStatus = data.status;
		errorData.statusText = data.statusText;
		return errorData;
	}
	
	function download (config) {
		console.log ('start ');
		window.location.href = "download/"+config.url;
		console.log ('end ');
	}

	return {
		post: post,
		get : get,
		put : put,
		remove: remove,
		download : download
	};
});