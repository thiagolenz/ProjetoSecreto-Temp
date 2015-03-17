define (["components/HtmlLoader",
         "components/Service",
         "i18n!modules/consultationClinic/nls/ConsultationClinic",
         "i18n!components/nls/Generic",
         "components/ViewValueCopy",
], 
function (htmlLoader, service, i18ns, i18ns_g, viewValueCopy){
	function load (container, consultation) {
		htmlLoader.load({
			container: container,
			file: "modules/consultationView/templates/ConsultationViewLifeHabitPanel.html",
			i18n: [i18ns, i18ns_g]
		}, function (){
			onFinishLoad(consultation);
		});
	}
	
	function onFinishLoad (consultation) {
		var lifeHabit = consultation.lifeHabit;
		viewValueCopy.copyTextValue("lifeHabit.doFisicalActivities", lifeHabit.doFisicalActivities ? i18ns_g.yes : i18ns_g.no);
		viewValueCopy.copyTextValue("lifeHabit.smoke", lifeHabit.smoke ? i18ns_g.yes : i18ns_g.no);
		viewValueCopy.copyTextValue("lifeHabit.drink", lifeHabit.drink ? i18ns_g.yes : i18ns_g.no);
		viewValueCopy.copyTextValue("lifeHabit.fisicalActivitiesFrequency", lifeHabit.fisicalActivitiesFrequency);
		viewValueCopy.copyTextValue("lifeHabit.trainingTime", lifeHabit.trainingTime);
		viewValueCopy.copyTextValue("lifeHabit.nightHoursSleeping", lifeHabit.nightHoursSleeping);
		viewValueCopy.copyTextValue("lifeHabit.litersOfWaterPerDay", lifeHabit.litersOfWaterPerDay);
		loadAnxietyType(lifeHabit);
	}
	
	function loadAnxietyType (lifeHabit) {
		service.get ({
			url : "userProfile/anxietyType/retrieveTypes",
			success : function (data) {
				for (var i = 0; i < data.length; i++)
					if (data[i].enumValue == lifeHabit.anxietyType) {
						viewValueCopy.copyTextValue("lifeHabit.anxietyType", data[i].description);
						break;
					}
			} 
		});	
	}
	
	return {
		load : load
	};
});