define ([], function () {
	
	function create (label, maxSize) {
		var cuted = label;
		if (label.length > maxSize)
			cuted = label.substr(0, maxSize) + " ...";
		return cuted;
	}
	
	return {
		create : create
	};
});