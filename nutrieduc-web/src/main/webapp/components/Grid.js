define(["components/GridPagination", 
        "components/ToggleButton"],
function (gridPagination, toggleButton) {
		
	function create (config) {
		createTableTag(config);
	}
	
	function createTableTag (config) {
		var divResponsible = createResponsibleDiv(config);
		
		var table = document.createElement("table");
		table.id = config.idTable;
		$(table).addClass("table table-hover");
		$(table).append(createHeader(config));
		configClickEvents(config, table);
		$(divResponsible).append(table);
		$("#"+config.placeAt).html(divResponsible);
	}
	
	function configClickEvents (config, table) {
		if (config.canSelectRow) {
			$(table).on('click', 'tbody tr', function(event) {
			    $(this).addClass('info').siblings().removeClass('info');
			    if (config.onRowSelect) {
			    	var index = $("tr", table).index(this) ;
			    	var row = getRowByIndex(table, index);
			    	config.onRowSelect(row);
			    }
			});
		}
		if (config.doubleClick){
			$(table).on('dblclick', 'tbody tr', function(event) {
				var index = $("tr", table).index(this) ;
		    	var row = getRowByIndex(table, index);
		    	config.doubleClick(row);
			});
		}
	}
	
	function getRowByIndex (table, index) {
		return _this.data[table.id][index - 1];
	}
	
	function setSelectedRow (tr) {
		 $(tr).addClass('info').siblings().removeClass('info');
	}
	
	function createResponsibleDiv (config) {
		var divResponsible = document.createElement("div");
		divResponsible.className = "table-responsive";
		if (config.scrollHeightSize) {
			$(divResponsible).css("height", config.scrollHeightSize);
			$(divResponsible).css("overflow-y", "scroll");
		}
		return divResponsible;
	}
	
	function createHeader (config) {
		var head = document.createElement("thead");
		var tr = document.createElement("tr");
		for (var i = 0 ; i< config.columns.length; i++)
			$(tr).append(createColumnHeader(config.columns[i]));
		$(head).append(tr);
        return head;
	}
	
	function createColumnHeader (column) {
		var th = document.createElement("th");
		$(th).addClass("col-md-"+column.width);
		if (column.align == "right")
			$(th).addClass("text-right");
		$(th).html(column.label);
		return th;
	} 
	
	function resetPagination (config) {
		gridPagination.resetPagination(config.placeAt);
	}
	
	function fill (result, config) {
		var start = new Date().getTime();
		var data = result.dataList;
		clear(config);
		createTBody(config);
		var tbody = $("#"+config.idTable).find("tbody");
		for (var i = 0 ; i < data.length ; i++) 
			$(tbody).append(createRow(data[i], config, i));
	
		createPagination(config,  result.totalRecordsCount);
		
		var total = new Date().getTime() - start;
		console.log("time to add " + total);
	}
	
	function createPagination (config, totalRecordsCount) {
		gridPagination.createPagination({
			container: config.placeAt,
			totalRows : totalRecordsCount,
			rowPerPage : config.recordsRange,
			onPageChange : config.onPageChange
		});
	}
	
	function addRowData (data, config, rowClass) {
		var tbody = $("#"+config.idTable).find("tbody");
		var rowCount = $(tbody).data("rowcount");
		rowCount = rowCount ? rowCount : 0;
		$(tbody).append(createRow(data, config, rowClass, rowCount));
		$(tbody).data("rowcount", ++rowCount);
	}
	
	function createTBody (config) {
		var tbody = document.createElement("tbody");
		$("#"+config.idTable).append(tbody);
	}
	
	function createRow (data, config, rowClass, rowIndex) {
		var tr = document.createElement("tr");
		tr.id = "row_"+data.id;
		_this.data[config.idTable].push(data);
		$(tr).addClass(rowClass);
		for (var j = 0 ; j < config.columns.length ; j++) {
			var td = document.createElement("td");
			var columnConfig = config.columns[j];
			var content = createOtherCellContent(j, data, config, rowIndex);
	
			if (content == null)
				content = data[columnConfig.field];
			$(td).html(content);
			$(tr).append(td);
			createToolTip(columnConfig, td, data);
		}
		if (config.onAddRow)
			config.onAddRow (data);
		return tr;
	} 
	
	function createOtherCellContent (columnIndex, row, config, rowIndex) {
		var columnConfig = config.columns[columnIndex];
		var content = null;
		
		if (isButton (columnIndex, columnConfig, row)) 
			content = createButtonContent(columnIndex, columnConfig, row, rowIndex);
		else if (isCustomContent(columnConfig.customContent))
			content = getCustomContent(columnIndex, columnConfig, row, rowIndex);
		else if (isCheckbox (columnIndex, columnConfig, row))
			content = createCheckboxContent(columnIndex, columnConfig, row, rowIndex);
		return content;
	}
	
	function createButtonContent (columnIndex, columnConfig, row, rowIndex) {
		if (columnConfig.showWhen)
			if (columnConfig.showWhen (row) == false)
				return ;
		var button = document.createElement("button");
		button.className = "btn " + columnConfig.className;
			button.innerHTML = getContentLabel(columnIndex, columnConfig, row, rowIndex);
		$(button).on("click", function () { 
			columnConfig.onClick(row);
		});
		return button;
	}
	
	function isButton (columnIndex, columnConfig, row) {
		if (typeof columnConfig.isButton == 'function') 
			return columnConfig.isButton (row, columnIndex);
		else 
			return columnConfig.isButton;
	}
	
	function isCheckbox (columnIndex, columnConfig, row) {
		if (typeof columnConfig.isCheckbox == 'function') 
			return columnConfig.isCheckbox (row, columnIndex);
		else 
			return columnConfig.isCheckbox;
	}
	
	function getContentLabel (columnIndex, columnConfig, row) {
		if (typeof columnConfig.contentLabel == 'function') 
			return columnConfig.contentLabel (row, columnIndex);
		else 
			return columnConfig.contentLabel;
	}
	
	function isCustomContent (customContent) {
		return typeof customContent == 'function' || customContent != null;
	}
	
	function getCustomContent (columnIndex, columnConfig, row, rowIndex) {
		if (typeof columnConfig.customContent == 'function') 
			return columnConfig.customContent (row, rowIndex);
		else 
			return columnConfig.customContent;
	}
	
	function createCheckboxContent (columnIndex, columnConfig, row, rowIndex) {
		if (columnConfig.showWhen)
			if (columnConfig.showWhen (row) == false)
				return ;
		var div = document.createElement("div");
		var yesNo = toggleButton.create ({
			name : "name_"+ rowIndex,
			varname : "description",
			defaultValue: getCheckboxValue(row, columnConfig),
			data : columnConfig.data,
			onChange : function (value) {
				if (columnConfig.onChange)
					columnConfig.onChange(value, row);
			}
 		});
		$(div).append(yesNo);
		$(div).append(row[columnConfig.field]);
		return div;
	}
	
	function getCheckboxValue (row, columnConfig) {
		var value = undefined; 
		if (typeof columnConfig.checkField == 'function')
			value = columnConfig.checkField (row);
		else 
			value = row[columnConfig.checkField];
		return value == undefined ? columnConfig.defaultValue: value; 
	}
	
	function createToolTip (columnConfig, td, row) {
		if (columnConfig.tooltip) {
			var text = columnConfig.tooltip (row);
			$(td).attr("data-toggle", "tooltip");
			$(td).attr("data-placement", "bottom");
			$(td).attr("title", text);
//			$(td).tooltip('show');
		}
		//<button type="button" class="btn btn-default" data-toggle="tooltip" data-placement="bottom" title="Tooltip on bottom">Tooltip on bottom</button>	
	}
	
	function clear (config) {
		$("#"+config.idTable).find('tbody').remove();
		_this.data[config.idTable] = new Array();
	} 
	
	function getSearchHeaders (config) {
		return {
			"rf-pagination-currentpage": gridPagination.getCurrentPage(config.placeAt),
			"rf-pagination-recordsrange": config.recordsRange ? config.recordsRange : 10
		};
	}
	
	function getCurrentPage (config) {
		return gridPagination.getCurrentPage(config.placeAt);
	}
	
	function showLoading (config) {
		var container = "#"+config.placeAt;
		$(container).find(".table-responsive").addClass("hide");
		var loading = document.createElement("div");
		$(loading).css("height", 100);
		$(loading).css("width", $(container).css("width"));
		$(loading).addClass("gridLoading");
		var i = document.createElement ("i");
		$(i).addClass("fa fa-spinner fa-spin fa-5x spinLoadingGrid");
		$(loading).append(i);
		var div = document.createElement("div");
		$(div).html("Aguarde alguns instantes..");
		$(loading).append(div);
		$(container).append(loading);
		gridPagination.hidePagination(config.placeAt);
	}
	
	function hideLoading (config) {
		var container = "#"+config.placeAt;
		$(container).find(".gridLoading").remove();
		$(container).find(".table-responsive").removeClass("hide");
		gridPagination.showPagination(config.placeAt);
	}
	
	var _this = {
		create : create,
		fill : fill,
		clear : clear,
		getSearchHeaders: getSearchHeaders,
		resetPagination : resetPagination,
		createTBody : createTBody,
		addRowData : addRowData,
		setSelectedRow: setSelectedRow,
		showLoading : showLoading,
		hideLoading : hideLoading,
		createPagination : createPagination,
		getCurrentPage : getCurrentPage,
		data: {},
	};
	
	return _this;
});