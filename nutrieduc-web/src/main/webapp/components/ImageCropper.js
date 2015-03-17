define (["components/HtmlLoader", 
         "scripts/cropper"
], function (htmlLoader){
	function config (options) {
		$(options.input).on ("change", function (e){
			prepareUpload(e, options);
		});
	}

	function prepareUpload (e, options) {		
		var files = event.target.files;
		if (files.length > 0) {
			var data = new FormData();
			$.each(files, function(key, value){
				data.append(key, value);
			});
			uploadAndConvert(data, options);
		}
	}

	function uploadAndConvert  (dataForm, options) {
		$.ajax({
			url: 'convertBase64/',
			type: 'POST',
			data: dataForm,
			cache: false,
			processData: false, 
			contentType: false,
			success: function (data) {
				onDataConvert(data, options);
			}
		});
	}
	
	function onDataConvert (data, options) {
		var container = document.createElement ("div");
		container.id = "containerImageCrop";
		$("body").append (container);
		htmlLoader.load({	
			container : "#containerImageCrop",
			file: "components/templates/ImageCropDialog.html",
			i18n: []
		}, function () {
			$(".btnOk").click (function (){
				cropImage(options);
			});
			$("#btnZoomPlus").click (zoomPlus);
			$("#btnZoomMinus").click (zoomMinus);
			$("#imageToCrop").attr("src","data:image/png;base64,"+ data);
			$("#imageModalCrop").modal();
			configureCropper();
		});
	}
	
	function zoomPlus () {
		$("#imageToCrop").cropper('zoom', 0.1);
	}
	
	function zoomMinus () {
		$("#imageToCrop").cropper('zoom', -0.1);
	}

	function configureCropper() {
		var image = $("#imageToCrop");
		var imageData = undefined, cropBoxData = undefined;
		image.cropper({
			aspectRatio: 6 / 7,
			global: false,
			guides : false,
			resizable : false,
			built: function () {
				image.cropper('setImageData', imageData);
				image.cropper('setCropBoxData', cropBoxData);
				image.cropper('zoom', -0.2);
			}
		});
	}

	function cropImage (options) {
		var content = $("#imageToCrop").cropper('getDataURL');
		content =  content.split (",") [1];
		$("#imageModalCrop").modal("hide");
		$("#containerImageCrop").remove ();
		options.onSuccess (content);
	}

	var _this = {
		config : config
	};
	return _this;
});