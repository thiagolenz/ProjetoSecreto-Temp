define(["components/ReportBasic",
        "scripts/Chart.min"], 
        function (basicReport, Chart){
	function create (container) {
		var data = {
				labels: ["Jan", "Feb", "Mar", "Abr", "Mai", "Jun", "Jul"],
				datasets: [
				           {
				        	   label: "Entradas",
				        	   fillColor: "rgba(92,184,92,0.2)",
				        	   strokeColor: "rgba(92,184,92,1)",
				        	   pointColor: "rgba(92,184,92,1)",
				        	   pointStrokeColor: "#fff",
				        	   pointHighlightFill: "#fff",
				        	   pointHighlightStroke: "rgba(92,184,92,1)",
				        	   data: [100, 120, 110, 105, 130, 110, 122]
				           },
				           {
				        	   label: "Saídas",
				        	   fillColor: "rgba(226,73,47,0.2)",
				        	   strokeColor: "rgba(226,73,47,1)",
				        	   pointColor: "rgba(226,73,47,1)",
				        	   pointStrokeColor: "#fff",
				        	   pointHighlightFill: "#fff",
				        	   pointHighlightStroke: "rgba(226,73,47,1)",
				        	   data: [80, 90, 85, 100, 95, 87, 90]
				           }
				           ]
		};
		var options = {
			
		};
		Chart.defaults.global.responsive = true;
		var ctx = document.getElementById(container).getContext("2d");
		new Chart(ctx).Line(data, options);
	}
	return {
		create : create
	};
});


