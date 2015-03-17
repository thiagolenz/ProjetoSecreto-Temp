define (["components/HtmlLoader",
         "components/Service",
         "i18n!modules/consultationClinic/nls/ConsultationClinic",
         "i18n!components/nls/Generic",
         "components/ViewValueCopy"
], 
function (htmlLoader, service, i18ns, i18ns_g, viewValueCopy){
	function load (container, consultation) {
		htmlLoader.load({
			container: container,
			file: "modules/consultationView/templates/ConsultationViewEatingHabitPanel.html",
			i18n: [i18ns, i18ns_g]
		}, function (){
			onFinishLoad(consultation);
		});
	}
	
	function onFinishLoad (consultation) {
		var eatingHabit = consultation.eatingHabit;
		viewValueCopy.copyTextValue("eatingHabit.energyFoods", eatingHabit.energyFoods);
		viewValueCopy.copyTextValue("eatingHabit.mostHungryTime", eatingHabit.mostHungryTime);
		viewValueCopy.copyTextValue("eatingHabit.hungryTimeFood", eatingHabit.hungryTimeFood);
		viewValueCopy.copyTextValue("eatingHabit.notLikeFood", eatingHabit.notLikeFood);
		viewValueCopy.copyTextValue("eatingHabit.notRemovableFoods", eatingHabit.notRemovableFoods);
		viewValueCopy.copyTextValue("eatingHabit.mostDificultyDietThing", eatingHabit.mostDificultyDietThing);
	}
	
	return {
		load : load
	};
});