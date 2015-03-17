define (["components/Grid", 
         "components/Service", 
         "components/Messages", 
         "i18n!modules/patientsSchedule/nls/PatientsSchedule",
         "i18n!components/nls/Generic",
         "components/Confirmation",
         "modules/patientsSchedule/PatientsScheduleForm",
         "moment"
], 
function (grid, service, messages, i18ns, i18ns_g, confirmation, patientsScheduleForm, moment){
	var gridConfig = {
			idTable : "patientsScheduleGrid",
			onPageChange : loadData,
			columns : [{label: i18ns.patientsSchedule.patient, width: 250, customContent : nameRender }, 
			           {label: i18ns.patientsSchedule.description, width: 200, field : "description"},
			           {label: i18ns.patientsSchedule.datetime, width: 200, customContent : dateHourRender},
			           {label: i18ns_g.change, 
			        	   width: 100, 
			        	   isButton: true, 
			        	   contentLabel: i18ns_g.change, 
			        	   className : "btn-default",
			        	   onClick: change},
			           {label: i18ns_g.remove, 
			        	   width: 100, 
			        	   isButton: true, 
			        	   contentLabel: i18ns_g.remove, 
			        	   className : "btn-default",
			        	   onClick: remove}
			        ]
	};
	
	function nameRender (row) {
		if (row.patientUser)
			return row.patientUser.name;
		else
			return "-";
	}
	
	function dateHourRender (row) {
		var hourStart = moment(row.startDate).format('HH:mm');
		var hourFinish = moment(row.finishDate).format('HH:mm');
		
		return hourStart +" - " + hourFinish ;
	}
	
	function create (placeAt) {
		gridConfig.placeAt = placeAt,
		grid.create (gridConfig);
	}
	
	function loadData (filter) {
		_this.filter = filter;
		var headers = grid.getSearchHeaders (gridConfig);
		service.post({
			url : "userProfile/appointment/search",
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
	
	function change (row) {
		patientsScheduleForm.edit ({
			onSuccess : function () {
				loadData(_this.filter);
			}
		},row);
	}
	
	function remove (row) {
		confirmation.showYesNo({
			title: i18ns_g.remove,
			message: i18ns.patientsSchedule.grid.questionRemove,
			onYes : function () {
				service.remove({
					url: "userProfile/appointment",
					id: row.id,
					success: function () {
						loadData(_this.filter);
					}
				});
			}
		});
		
	}

	var _this = {
		create : create,
		loadData : loadData,
		onCloseModal : loadData
	};
	
	return _this;
});