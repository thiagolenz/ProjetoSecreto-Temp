define(["components/HtmlLoader", 
        "i18n!components/nls/Generic",
        "components/Service",
        "components/Messages"], 
function (htmlLoader, i18ns_g, service, messages){

	function load () {
		htmlLoader.load ({
			container: "#createAccountContainer",
			file: "modules/login/template/ForgotPasswordForm.html",
			i18n : i18ns_g
		}, function (){
			bindEvents();
		});
	}

	function bindEvents () {
		$("#btnCreateAccount").click (validateAndSave);
	}
	
	function validateAndSave () {
		if (validateAllRegisterFields())
			$("#formCreateAccount").submit ();
	}
	
	function validateAllRegisterFields () {
	    var inputs = $("#formCreateAccount input");
	    messages.clear ();
	    for (var i = 0; i < inputs.length ; i++ )
	        if (getValue(inputs[i]) == undefined) {
	        	error ({
		        	value : "Desculpe! Os campos abaixo não foram preenchidos."
		        });
	            return false;
	        }
	    if (!isValidEmail ($(inputs[1]).val())) {
	    	error ({
	        	value : "E-mail inválido"
	        });
	        return false;
	    } else {
	        $("#registerModal").find(".invalidEmail").addClass("hide");
	    }

	    return true;
	}
	
	function getValue (element) {
	    var value = $(element).val();
	    var placeholder = $(element).attr("placeholder");
	    if (value == placeholder)
	        return undefined;
	    else 
	        return value == "" ? undefined : value;
	}
	
	function isValidEmail(emailAddress) {
	    var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
	    return pattern.test(emailAddress);
	};
	
	function error (data) {
		messages.error({
			title: i18ns_g.error,
			message: data
		});
	}

	return {
		load : load
	};
});