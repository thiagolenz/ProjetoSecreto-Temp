define (["components/Grid", 
         "components/Service", 
         "components/Messages", 
         "moment",
         "i18n!modules/consultationHistory/nls/ConsultationHistory",
         "i18n!components/nls/Generic",
         "components/Confirmation",
         "components/ModuleEvent"
], 
function (grid, service, messages, moment, i18ns, i18ns_g, confirmation, moduleEvent){
	var gridConfig = {
			idTable : "consultationHistoryGrid",
			recordsRange: 5,
			onPageChange : function () {
				loadData(_this.filter, false);
			},
			columns : [{label: i18ns.consultationHistory.objective, width: 3, field : "objective" },
			           {label: i18ns.consultationHistory.nutritionist, width: 2, customContent: nutritionistRenderer},
			           {label: i18ns.consultationHistory.consultDate, width: 1, customContent: dateRenderer},
			           {label: "Visualizar", 
			        	   width: 1, 
			        	   isButton: true, 
			        	   contentLabel: "Visualizar Avaliação", 
			        	   className : "btn-success",
			        	   onClick: showConsultationDetailView}
			        ]
	};
	
	function dateRenderer (row) {
		return moment(row.consultDate).format('L');
	}
	
	function nutritionistRenderer (row) {
		if (row.userNutritionist)
			return row.userNutritionist.name;
	}
	
	function create (placeAt, user) {
		_this.user = user;
		gridConfig.placeAt = placeAt,
		grid.create (gridConfig);
	}
	
	function showConsultationDetailView (row) {
		moduleEvent.loadModule ("consultationView", {consultation : row});
	}
	
	function loadData (filter, resetPatination) {
		if (filter == undefined)
			filter = {};
		_this.filter = filter;
		if (resetPatination)
			grid.resetPagination (gridConfig);
		_this.filter.userPatient = _this.user;
		var headers = grid.getSearchHeaders (gridConfig);
		service.post({
			url : "userProfile/consultation/retrieve",
			data: filter,
			success: onLoadSuccess,
			error: error, 
			headers: headers 
		});
	}
	
	function onLoadSuccess (data) {
		grid.fill(data, gridConfig);
	} 
	
	function error (data) {
		messages.error({
			title: i18ns_g.error,
			message: data
		});
	}

	var _this = {
		create : create,
		loadData : loadData,
		onCloseModal : loadData
	};
	
	return _this;
});