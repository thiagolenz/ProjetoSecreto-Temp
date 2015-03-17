define(["components/ReportBasic"], function (){
	function create (container) {
		Morris.Bar({
			element: container,
			data: [
			       { y: 'Setembro', a: 100, b: 20 },
			       { y: 'Outubro', a: 75,  b: 30 },
			       { y: 'Novembro', a: 90,  b: 20 }
			       ],
			       xkey: 'y',
			       ykeys: ['a', 'b'],
			       labels: ['Ativos', 'Vencidos']
		});
	}
	return {
		create : create
	};
});


