define(["components/HtmlLoader",
        "i18n!modules/consultationClinic/nls/ConsultationClinic",
        "i18n!components/nls/Generic",
], 
function (htmlLoader,  i18ns, i18ns_g){
	function load (placeAt, callback) {
		htmlLoader.load({
			container: placeAt,
			file: "modules/consultationView/templates/ConsultationViewPrint.html",
			i18n: [i18ns, i18ns_g]
		}, callback);
	}
	
	function print () {
		var mywindow = window.open('', 'Imprimir Avaliação', 'height=800,width=1000');
        mywindow.document.write('<html><head><title>Avaliação</title>');
        mywindow.document.write('<link rel="stylesheet" href="css/print.css" type="text/css" />');

        mywindow.document.write('</head><body style="padding-top: 0px">');
        mywindow.document.write($("#printContainer").html());
        mywindow.document.write('</body></html>');
        
        mywindow.document.close(); // necessary for IE >= 10
        mywindow.focus(); // necessary for IE >= 10

        
        setTimeout (function(){
        	mywindow.print();
        	mywindow.close();
        }, 800); 
	}
	
	return {
		print : print,
		load : load 
	};
});