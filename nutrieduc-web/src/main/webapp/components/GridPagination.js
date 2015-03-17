define(["components/HtmlLoader"], 
function (htmlLoader) {

	function createPagination (config) {
		if (config.rowPerPage != undefined)
			createGroupPagination(config.container, function (pagination){
				createItemPagination(config, pagination);
				bindEvents(config);
			});
	}
	
	function clear (config) {
		$("#"+config.container + " .pagination").remove();
	}
	
	function hidePagination (container) {
		$("#"+container + "_pagination").addClass("hide");
	}
	
	function showPagination (container) {
		$("#"+container + "_pagination").removeClass("hide");
	}

	function createGroupPagination (container, callback) {
		var pagination = document.createElement("div");
		pagination.id = container + "_pagination";
		$("#"+container).append(pagination);
		htmlLoader.load({
			container: "#" + pagination.id,
			file: "components/templates/GridPagination.html",
		}, function (){
			callback(pagination);
		});
	}
	
	function bindEvents (config) {
		var container = "#"+config.container;
		$(container).find(".btnNext").click(function() {
			if (!$(this).hasClass("disabled"))
				nextPage(config);
		});
		
		$(container).find(".btnPrevious").click(function() {
			if (!$(this).hasClass("disabled"))
				previousPage(config);
		});
		
		$(container).find(".btnFirst").click(function() {
			if (!$(this).hasClass("disabled"))
				firstPage(config);
		});
		
		$(container).find(".btnLast").click(function() {
			if (!$(this).hasClass("disabled"))
				lastPage(config);
		});
	}
	
	function nextPage (config) {
		var currentPage = _this.getCurrentPage(config.container);
		updateCurrentPage(getTotalPages (config), ++currentPage, config);
		firePageChange(config);
	}
	
	function previousPage (config) {
		var currentPage = _this.getCurrentPage(config.container);
		updateCurrentPage(getTotalPages (config), --currentPage, config);
		firePageChange(config);
	}
	
	function firstPage (config) {
		updateCurrentPage(getTotalPages (config), 0, config);
		firePageChange(config);
	}
	
	function lastPage (config) {
		var pagesNumber = getTotalPages(config);
		updateCurrentPage(getTotalPages (config), --pagesNumber, config);
		firePageChange(config);
	}

	function createItemPagination (config, pagination) {
		var numberPages = defineAndSetNumberPages(config);
		var currentPage = _this.getCurrentPage(config.container);
		updateCurrentPage(numberPages, currentPage, config);
		$("#"+config.container).find(".totalItens").html(config.totalRows);
		updateButtons(config);
	}
	
	function firePageChange (config) {
		config.onPageChange();
	}
	
	function updateButtons (config) {
		var container = config.container;
		var currentPage = getCurrentPage(container);
		var totalPages = getTotalPages(config);
		container = $("#"+container);
		if (currentPage == 0)
			$(container).find(".backButton").addClass("disabled");
		else 
			$(container).find(".backButton").removeClass("disabled");
		
		if (currentPage == --totalPages)
			$(container).find(".nextButton").addClass("disabled");
		else 
			$(container).find(".nextButton").removeClass("disabled");
	}
	
	function defineAndSetNumberPages (config) {
		var numberPages = Math.ceil(config.totalRows / config.rowPerPage);
		$($("#"+config.container)).attr("data-numberpages", numberPages);
		return numberPages;
	}
	
	function getTotalPages (config) {
		return $($("#"+config.container)).attr("data-numberpages");
	}
	
	function updateCurrentPage (numberPages, currentPage, config) {
		var showUp = parseInt(currentPage)+1 ;
		var currentPageOfTotal = showUp +" de " + numberPages;
		$("#"+config.container).find(".currentPageOfTotal").html(currentPageOfTotal);
		setCurrentPage(config, currentPage);
	}
	
	function setCurrentPage (config, page) {
		$($("#"+config.container)).attr("data-currentpage", page);
	}
	
	function getCurrentPage (container) {
		var page = $($("#"+container)).attr("data-currentpage");
		page = page ? page : 0;
		return parseInt(page);
	}
	
	function resetPagination (container) {
		$("#"+container).attr("data-currentpage", 0);
	}

	var _this = {
			createPagination: createPagination,
			getCurrentPage : getCurrentPage,
			resetPagination : resetPagination,
			showPagination : showPagination,
			hidePagination : hidePagination
	};

	return _this;
});