define(["components/Grid"], 
function (grid){
	
	function create (config) {
		grid.create(config);
		_this.config = config;
		$("#" +_this.config.idTable).removeClass("table-striped");
	}
	
	function fill (data, config, totalRecordsCount) {
		grid.showLoading(config);
		_this.isLoading = true;
		setTimeout (function () {
			if (config)
				_this.config = config;
			grid.clear(_this.config);
			grid.createTBody(_this.config);
		
			_this.globalCount = 0;
			_this.data = data;
			for (var i = 0; i < data.length; i++) {
				if (canAddRow(data[i]) && (config.ignoreAtLeastOne || willAddAtLeastOne(data[i]))) {
					_this.globalCount ++;
					var className = "treegrid-"+_this.globalCount + (" "+ getDefaultRowStyleClass());
		
					var childs = data[i][_this.config.varChild];
					if (childs && childs.length > 0)
						className += getParentRowStyle();
					
					grid.addRowData (data[i], _this.config, className);
					
					if (childs)
						fillChilds(childs, _this.globalCount);
				}
			}
			$("#" +_this.config.idTable).treegrid();
			grid.createPagination (config, totalRecordsCount);
			grid.hideLoading(config);
			if (config.onFinish) 
				config.onFinish();
			_this.isLoading = false;
		}, 500); 
	}
	
	function fillChilds (childs, parent) {
		for (var i = 0; i < childs.length ; i++) {
			if (canAddRow(childs[i]) && willAddAtLeastOne(childs[i])) {
				_this.globalCount ++;
				var className = "treegrid-"+_this.globalCount + " treegrid-parent-"+parent + (" "+ getDefaultRowStyleClass()) ;
	
				var childsOther = childs[i][_this.config.varChild];			
				if (childsOther.length > 0)
					className += getParentRowStyle();
				
				grid.addRowData (childs[i], _this.config, className);
		
				if (childsOther)
					fillChilds(childsOther, _this.globalCount);
			}
		}
	} 
	
	function willAddAtLeastOne (child) {
		if (_this.config.canShowRow == undefined)
			return true;
		var childs = child[_this.config.varChild];
		var add = false;
		if (childs && childs.length > 0)
			for (var i = 0; i < childs.length ; i++) {
				var addSubChilds = willAddAtLeastOne(childs[i]);
				if (addSubChilds)
					return true;
		} else 
			return canAddRow(child);
		return add;
	}
	
	function getSelecteds () {
		var selecteds = new Array();
		var data = _this.data.slice(0); 
		for (var i = 0; i < data.length; i++) {
			var row = data[i];
			var childrens = getChildrenSelected(row);
			row.childrens = childrens;
			if (isRowSelected(data[i]) || childrens.length > 0)
				selecteds.push(row);
		}
		return selecteds;
	}
	
	function getChildrenSelected (row) {
		var selecteds = new Array();
		for (var i = 0; i < row.childrens.length; i++) {
			var child = row.childrens[i];
			var childrens = getChildrenSelected(child);
			child.childrens = childrens;
			if (isRowSelected(child) || childrens.length > 0)
				selecteds.push(child);
		}
		return selecteds;
	}
	
	function isRowSelected (row) {
		return $("#row_"+row.id).hasClass(getDefaultRowStyleClass());
	}
	
	function setActive (rowId) {
		var className = getDefaultRowStyleClass();
		var element  = $("#"+rowId);
		if (!$(element).hasClass(className))
			$(element).addClass(className);
	}
	
	function setInactive (rowId) {
		var className = getDefaultRowStyleClass();
		var element  = $("#"+rowId);
		if ($(element).hasClass(className))
			$(element).removeClass(className);
	}
	
	function getDefaultRowStyleClass () {
		if (_this.config.defaultRowStyle) 
			return _this.config.defaultRowStyle;
		return "";
	}
	
	function getParentRowStyle () {
		if (_this.config.haveParentRowStyle)
			return " row-parent";
		return "";
	}
	
	function canAddRow (row) {
		if (_this.config.canShowRow)
			return _this.config.canShowRow(row);
		return true;
	}
	
	function getSearchHeaders (gridConfig) {
		return grid.getSearchHeaders (gridConfig);
	}
	
	function getCurrentPage (gridConfig) {
		return grid.getCurrentPage(gridConfig);
	}

	_this = {
		create : create,
		fill : fill,
		setActive : setActive,
		setInactive : setInactive,
		getSelecteds : getSelecteds,
		setSelectedRow: grid.setSelectedRow,
		getSearchHeaders: getSearchHeaders,
		getCurrentPage : getCurrentPage
	};
	
	return _this;
});