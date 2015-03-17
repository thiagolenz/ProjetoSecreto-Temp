define(["components/ReportBasic"], function (){
	function create (container, donutData) {
		$("#"+container).html("");
		Morris.Donut({
			element: container,
			data: donutData
		});
	}
	return {
		create : create
	};
});