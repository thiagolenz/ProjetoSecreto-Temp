define (["components/Service"], 
function (service) {
	var template = "<div class='popover tour tour-box'>"+
	  "<div class='arrow'></div> "+
	  "<h3 class='popover-title tour-title'></h3>"+
	  "<div class='popover-content margin10'></div>"+
	  "<div class='popover-navigation'>"+
	  	"<button class='btn btn-default' data-role='end'>Finalizar</button>"+
	    "<button class='btn btn-secondary' data-role='next'>Próximo</button>"+
	  "</div>"+
	"</div>";
	
	function create (options) {
		filterSteps(options.steps, function (steps) {
			var tour = new Tour({
				container: options.container ? options.container : "body",
				steps: steps, 
				template: template,
				storage : false,
				orphan : true,
				backdrop: options.backdrop,
				onEnd : saveAll,
				onNext: saveCurrentStep
			});
			tour.init();
			options.onFinish (tour);
		});
	}
	
	function filterSteps (steps, callback) {
		if (!_this.stepDoneList) {
			service.get ({
				url : "userProfile/userStepDone/",
				success : function (data) {
					_this.stepDoneList = new Array();
					_this.doneStepsNow = {};
					for (var i = 0 ; i< data.length; i++) {
						var item = data [i];
						_this.stepDoneList.push (item.step);
					}
					callback (filterData(steps));
				}
			})
		} else 
			callback (filterData(steps));
	}
	
	function filterData (steps) {
		var filteredArray = new Array ();
		for (var i = 0; i < steps.length ; i++) {
			var item = steps[i];
			if (_this.stepDoneList.indexOf (item.stepId) <= -1)
				filteredArray.push (item);
		}
		return filteredArray;
	}
	
	function saveAll (tour) {
		for (var i = 0 ; i < tour._options.steps.length; i++ ) {
			var step = tour.getStep (i);
			if (!_this.doneStepsNow [step.stepId]) 
				saveStep(step);
		}
	}
	
	function saveCurrentStep (tour) {
		var step = tour.getStep(tour.getCurrentStep());
		saveStep(step);
	}
	
	function saveStep (step) {
		_this.stepDoneList.push(step.stepId);
		service.post ({
			url : "userProfile/userStepDone/",
			skipLoading : true,
			data : {
				step: step.stepId
			},
			success : function () {
				step.saved = true;
				_this.doneStepsNow [step.stepId] = step;
			}
		});
	}

	var _this = {
		create : create
	};
	
	return _this;
});