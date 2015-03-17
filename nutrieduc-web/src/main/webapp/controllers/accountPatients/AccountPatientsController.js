define (["components/HtmlLoader",
         "i18n!modules/accountPatients/nls/AccountPatients",
         "i18n!controllers/accountPatients/nls/AccountPatientsManagement",
         "modules/accountPatients/AccountPatientsGrid",
         "modules/accountPatients/AccountPatientsForm",
         "components/AlphabetFilter"
],
function (htmlLoader, i18nsAccount, i18nsAccountManager, accountPatientsGrid, accountPatientsForm, alphabetFilter){
	function load () {
		htmlLoader.load({
			container: "#body",
			file: "controllers/accountPatients/templates/AccountPatientsLayout.html",
			i18n: [i18nsAccount, i18nsAccountManager]
		}, onFinishLoad);
	}
	
	function onFinishLoad () {
		alphabetFilter.create ({
			container : "#alphabetFilter",
			onChoose : filterByLetter
		});
		accountPatientsGrid.create("gridContainer");
		accountPatientsGrid.loadData();
		bindEvents();
	}
	
	function filterByLetter (letter) {
		$("#nameFilter").val("");
		var filter = {
			letter: letter
		};
		accountPatientsGrid.loadData(filter, true);
	}
	
	function bindEvents () {	
		$("#btnSearch").click (function () {
			alphabetFilter.clear ({container :  "#alphabetFilter"});
			var filter = {
				name : $("#nameFilter").val()
			};
			accountPatientsGrid.loadData(filter, true);
		});
	}
	
	function afterCloseAccountModal () {
		accountPatientsGrid.loadData();
	}

	var _this = {
		load : load ,
		onCloseModal: afterCloseAccountModal
	};
	
	return _this;
});