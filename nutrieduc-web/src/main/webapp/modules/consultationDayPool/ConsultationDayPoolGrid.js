define (["components/Grid", 
         "components/Service", 
         "components/Messages", 
         "moment",
         "i18n!modules/consultationDayPool/nls/ConsultationDayPool",
         "i18n!components/nls/Generic",
         "components/Confirmation",
         "components/ModuleEvent"
], 
function (grid, service, messages, moment, i18ns, i18ns_g, confirmation, moduleEvent){
	var gridConfig = {
			idTable : "consultationDayPoolGrid",
			recordsRange: 5,
			onPageChange : function () {
				loadData(_this.filter, false);
			},
			columns : [{label: i18ns.consultationDayPool.userPatient, width: 3, customContent: patientRenderer},
			           {label: i18ns.consultationDayPool.objective, width: 2, field : "objective" },
			           {label: "Horário de Avaliação", width: 2, customContent: dateRenderer},
			           {label: "Iniciar Consulta", 
			        	   width: 1, 
			        	   isButton: true, 
			        	   contentLabel: "Iniciar Consulta", 
			        	   className : "btn-success",
			        	   onClick: showConsultationDetailView}
			        ]
	};
	
	var gridDoneConfig = {
			idTable : "consultationDayDoneGrid",
			recordsRange: 5,
			onPageChange : function () {
				loadData(_this.filter, false);
			},
			columns : [{label: i18ns.consultationDayPool.userPatient, width: 3, customContent: patientRenderer},
			           {label: i18ns.consultationDayPool.objective, width: 2, field : "objective" },
			           {label: "Horário de Avaliação", width: 1, customContent: dateRenderer}
			        ]
	};
	
	function dateRenderer (row) {
		return moment(row.createDate).format('LT');
	}
	
	function patientRenderer (row) {
		return row.userPatient.name;
	}
	
	function create (placeAt, placeAtDone) {
		gridConfig.placeAt = placeAt,
		grid.create (gridConfig);
		
		gridDoneConfig.placeAt = placeAtDone,
		grid.create (gridDoneConfig);
	}
	
	function showConsultationDetailView (row) {
		moduleEvent.loadModule ("consultationView", {consultation : row});
	}
	
	function loadData (filter, resetPatination) {
		var headers = grid.getSearchHeaders (gridConfig);
		service.post({
			url : "userProfile/consultation/retrieveConsultationPoool",
			data: {},
			success: onLoadSuccessPending,
			error: error, 
			headers: headers 
		});
		
		headers = grid.getSearchHeaders (gridDoneConfig);
		service.post({
			url : "userProfile/consultation/retrieveConsultationDone",
			data: {},
			success: onLoadSuccessDone,
			error: error, 
			headers: headers 
		});
	}
	
	function onLoadSuccessPending (data) {
		grid.fill(data, gridConfig);
	} 
	
	function onLoadSuccessDone (data) {
		grid.fill(data, gridDoneConfig);
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