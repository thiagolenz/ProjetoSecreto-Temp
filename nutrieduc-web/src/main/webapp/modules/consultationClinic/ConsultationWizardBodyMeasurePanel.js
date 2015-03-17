define([
        "components/Binding", 
        "components/HtmlLoader",
        "i18n!modules/consultationClinic/nls/ConsultationClinic",
        "components/FieldsValidation",
        "components/Service",
        "components/DecimalInput",
        "components/Grid",
        "components/ImageCropper"
],
function (binding, htmlLoader, i18ns, fieldsValidation, service, decimalInput, grid, imageCropper) {
	
	var gridConfig = {
			idTable : "measuresGrid",
			placeAt : "gridMeasures",
			columns : [{label: "Medida", width: 3, customContent: measureName }, 
			           {label: "Valor", width: 1, customContent: createValueInput},
			        ]
	};
	
	function measureName (row) {
		return i18ns.consultation.bodyMeasure [row.measure];
	}
	
	function createValueInput (row) {
		var input = decimalInput.createDecimalInput({
			dataName : "bodyMeasure." + row.measure
		});
		return input;
	}
	
	function load (config) {
		_this.config = config;
		
		htmlLoader.load({
			container: config.container,
			file: "modules/consultationClinic/templates/ConsultationWizardBodyMeasurePanel.html",
			i18n: [i18ns]
		}, function () {
			onFinishLoad();
		});
		$(config.titleContainer).html(i18ns.consultation.clinicalDataPanel);
	}
	
	function onFinishLoad () {
		createGridMeasures();
		_this.consultation = _this.config.consultation;
		_this.userPatient = _this.consultation.userPatient;
		bindEvents ();
		setup();
		$($("#currentStepContainer input")[0]).focus();
	}
	
	function createGridMeasures () {
		grid.create(gridConfig);
		var data = { dataList : [{measure : "weight"},
		            {measure : "height"},
		            {measure : "bendingTriceps"},
		            {measure : "bendingBack"},
		            {measure : "bendingBiceps"},
		            {measure : "foldBelt"},
		            {measure : "abdominalTuck"},
		            {measure : "bendingThigh"},
		            {measure : "axillaryFold"},
		            {measure : "pectoralFold"},
		            {measure : "leftArmContracted"},
		            {measure : "rightArmContracted"},
		            {measure : "chestCircumference"},
		            {measure : "weistCircumference"},
		            {measure : "abdomenCircumference"},
		            {measure : "rightThighCircumference"},
		            {measure : "leftThighCircumference"},
		            {measure : "rightCalfCircumference"},
		            {measure : "leftCalfCircumference"}]
		};
		grid.fill(data, gridConfig);
	}
	
	function bindEvents () {
		imageCropper.config ({
			input : "#userAvatarInput",
			onSuccess: function (dataImage) {
				_this.accountPatient.avatarBase64 = dataImage;
				saveUserAvatar();
				updateImageUserAvatar();
			}
		});
		
		imageCropper.config ({
			input : "#frontPictureInput",
			onSuccess: function (dataImage) {
				uploadImage(dataImage, "frontPicture");
				updateFrontPicture(dataImage);
			}
		});
		
		imageCropper.config ({
			input : "#backPictureInput",
			onSuccess: function (dataImage) {
				uploadImage(dataImage, "backPicture");
				updateBackPicture(dataImage);
			}
		});
		
		imageCropper.config ({
			input : "#leftPictureInput",
			onSuccess: function (dataImage) {
				uploadImage(dataImage, "leftPicture");
				updateLeftPicture(dataImage);
			}
		});
		
		imageCropper.config ({
			input : "#rightPictureInput",
			onSuccess: function (dataImage) {
				uploadImage(dataImage, "rightPicture");
				updateRightPicture(dataImage);
			}
		});
	}
	
	function uploadImage (dataImage, fieldName) {
		$.ajax({
			url: 'convertFileUpload/userProfile/consultationUserPicture/' +  _this.userPatient.id,
			type: 'POST',
			data: dataImage,
			success: function (response) {
				_this [fieldName] = response;
			}
		});
	}
	
	function setup () {
		_this.accountPatient = _this.consultation.accountPatient;
		loadAvatar();
	}
	
	function loadAvatar () {
		if (_this.accountPatient.avatarBase64 == undefined) 
			_this.accountPatient.avatarBase64 = $("#defaultAvatar").val();
		updateImageUserAvatar();
	}
	
	function saveUserAvatar () {
		var request = {
			url : "userProfile/accountPatient/",
			data : _this.accountPatient,
			id : _this.accountPatient.id,
			success : function () {
				
			}
		};
		
		if (_this.accountPatient.id) 
			service.put(request);
		else
			service.post(request);
	}
	
	function validate(consultation, onSuccess) {
		var bodyMeasure = binding.copy("bodyMeasure");
			
		fieldsValidation.validateMandatory ({
			bean : bodyMeasure,
			source : "bodyMeasure",
			container : "body", 
			success : function() {
				consultation.bodyMeasure = bodyMeasure;
				bodyMeasure.frontPicture = _this.frontPicture;
				bodyMeasure.backPicture = _this.backPicture;
				bodyMeasure.leftPicture = _this.leftPicture;
				bodyMeasure.rightPicture = _this.rightPicture;
				onSuccess();
			}
		});
	}
	
	function updateFrontPicture (imageData) {
    	updateImage("#frontPicture", imageData);
	}
	
	function updateBackPicture (imageData) {
    	updateImage("#backPicture", imageData);
	}
	
	function updateLeftPicture (imageData) {
    	updateImage("#leftPicture", imageData);
	}
	
	function updateRightPicture (imageData) {
    	updateImage("#rightPicture", imageData);
	}
	
	function updateImageUserAvatar () {
    	updateImage("#userAvatar", _this.accountPatient.avatarBase64);
	}
	
	function updateImage (imageId, imageBase64) {
		var img = $(imageId);
		$(img).attr("src","data:image/png;base64,"+  imageBase64);
	}
	
	function unload () {
		_this.consultation = undefined;
		_this.config = undefined;
	}
	
	var _this = {
		load : load,
		validate : validate,
		unload : unload
	};
	
	return _this;
});