define (["components/HtmlLoader",
         "i18n!modules/consultationHistory/nls/ConsultationHistory",
         "i18n!controllers/consultationHistory/nls/ConsultationHistoryManagement",
         "modules/consultationHistory/ConsultationHistoryGrid",
         "components/AlphabetFilter",
         "components/Service",
         "components/Formatter"
],
function (htmlLoader, i18nsAccount, i18nsAccountManager, consultationHistoryGrid, alphabetFilter, service, formatter){
	function load (args) {
		htmlLoader.load({
			container: "#body",
			file: "controllers/consultationHistory/templates/ConsultationHistoryLayout.html",
			i18n: [i18nsAccount, i18nsAccountManager]
		}, function (){
			onFinishLoad(args);
		});
	}
	
	function onFinishLoad (args) {
		formatter.initializeDatePicker();
		loadSelectedUser (args);
		bindEvents();
	}
	
	function loadSelectedUser (args) {
		var cookieName = "consultationHistory_selectedUser";
		if (args && args.userId) {
			loadUserById(args.userId);
			Cookie.saveCookie (cookieName, args.userId);
		} else 
			loadUserById(Cookie.getCookie (cookieName));
	}
	
	function loadUserById (userId) {
		if (userId) {
			service.get ({
				url : "userProfile/user/"+ userId,
				success : function (user) {
					$("#userPatientName").html (user.name);
					$("#userPatientEmail").html (user.email);
					loadUserInfoProfile(userId);
					consultationHistoryGrid.create ("gridContainer", user);
					consultationHistoryGrid.loadData();
				}
			});
		}
	}
	
	function loadUserInfoProfile (userId) {
		service.get({
			url :  "userProfile/userInfoProfile/"+userId,
			success : function (userInfoProfile) {
				$("#userPatientAge").html (userInfoProfile.age);
			}
		});
	}
	
	function bindEvents () {	
		$("#btnSearch").click (function () {
			var filter = {
				name : $("#nameFilter").val()
			};
			consultationHistoryGrid.loadData(filter, true);
		});
	}

	var _this = {
		load : load 
	};
	
	return _this;
});