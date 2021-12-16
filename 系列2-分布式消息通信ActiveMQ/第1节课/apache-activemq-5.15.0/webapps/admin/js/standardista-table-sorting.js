/*
 * Copyright 2021-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
var standardistaTableSorting = {

	that: false,
	isOdd: false,

	sortColumnIndex : -1,
	lastAssignedId : 0,
	newRows: -1,
	lastSortedTable: -1,

	/**
	 * Initialises the Standardista Table Sorting module
	 **/
	init : function() {
		// first, check whether this web browser is capable of running this script
		if (!document.getElementsByTagName) {
			return;
		}
		
		this.that = this;
		
		this.run();
		
	},
	
	/**
	 * Runs over each table in the document, making it sortable if it has a class
	 * assigned named "sortable" and an id assigned.
	 **/
	run : function() {
		var tables = document.getElementsByTagName("table");
		
		for (var i=0; i < tables.length; i++) {
			var thisTable = tables[i];
			
			if (css.elementHasClass(thisTable, 'sortable')) {
				this.makeSortable(thisTable);
			}
		}
	},
	
	/**
	 * Makes the given table sortable.
	 **/
	makeSortable : function(table) {
	
		// first, check if the table has an id.  if it doesn't, give it one
		if (!table.id) {
			table.id = 'sortableTable'+this.lastAssignedId++;
		}
		
		// if this table does not have a thead, we don't want to know about it
		if (!table.tHead || !table.tHead.rows || 0 == table.tHead.rows.length) {
			return;
		}
		
		// we'll assume that the last row of headings in the thead is the row that 
		// wants to become clickable
		var row = table.tHead.rows[table.tHead.rows.length - 1];
		
		for (var i=0; i < row.cells.length; i++) {
		
			// create a link with an onClick event which will 
			// control the sorting of the table
			var linkEl = createElement('a');
			linkEl.href = '#';
			linkEl.onclick = this.headingClicked;
			linkEl.setAttribute('columnId', i);
			linkEl.title = 'Click to sort';
			
			// move the current contents of the cell that we're 
			// hyperlinking into the hyperlink
			var innerEls = row.cells[i].childNodes;
			for (var j = 0; j < innerEls.length; j++) {
				linkEl.appendChild(innerEls[j]);
			}
			
			// and finally add the new link back into the cell
			row.cells[i].appendChild(linkEl);

			var spanEl = createElement('span');
			spanEl.className = 'tableSortArrow';
			spanEl.appendChild(document.createTextNode('\u00A0\u00A0'));
			row.cells[i].appendChild(spanEl);

		}
	
		if (css.elementHasClass(table, 'autostripe')) {
			this.isOdd = false;
			var rows = table.tBodies[0].rows;
		
			// We appendChild rows that already exist to the tbody, so it moves them rather than creating new ones
			for (var i=0;i<rows.length;i++) { 
				this.doStripe(rows[i]);
			}
		}
	},
	
	headingClicked: function(e) {
		
		var that = standardistaTableSorting.that;
		
		// linkEl is the hyperlink that was clicked on which caused
		// this method to be called
		var linkEl = getEventTarget(e);
		
		// directly outside it is a td, tr, thead and table
		var td     = linkEl.parentNode;
		var tr     = td.parentNode;
		var thead  = tr.parentNode;
		var table  = thead.parentNode;
		
		// if the table we're looking at doesn't have any rows
		// (or only has one) then there's no point trying to sort it
		if (!table.tBodies || table.tBodies[0].rows.length <= 1) {
			return false;
		}

		// the column we want is indicated by td.cellIndex
		var column = linkEl.getAttribute('columnId') || td.cellIndex;
		//var column = td.cellIndex;
		
		// find out what the current sort order of this column is
		var arrows = css.getElementsByClass(td, 'tableSortArrow', 'span');
		var previousSortOrder = '';
		if (arrows.length > 0) {
			previousSortOrder = arrows[0].getAttribute('sortOrder');
		}
		
		// work out how we want to sort this column using the data in the first cell
		// but just getting the first cell is no good if it contains no data
		// so if the first cell just contains white space then we need to track
		// down until we find a cell which does contain some actual data
		var itm = ''
		var rowNum = 0;
		while ('' == itm && rowNum < table.tBodies[0].rows.length) {
			itm = that.getInnerText(table.tBodies[0].rows[rowNum].cells[column]);
			rowNum++;
		}
		var sortfn = that.determineSortFunction(itm);

		// if the last column that was sorted was this one, then all we need to 
		// do is reverse the sorting on this column
		if (table.id == that.lastSortedTable && column == that.sortColumnIndex) {
			newRows = that.newRows;
			newRows.reverse();
		// otherwise, we have to do the full sort
		} else {
			that.sortColumnIndex = column;
			var newRows = new Array();

			for (var j = 0; j < table.tBodies[0].rows.length; j++) { 
				newRows[j] = table.tBodies[0].rows[j]; 
			}

			newRows.sort(sortfn);
		}

		that.moveRows(table, newRows);
		that.newRows = newRows;
		that.lastSortedTable = table.id;
		
		// now, give the user some feedback about which way the column is sorted
		
		// first, get rid of any arrows in any heading cells
		var arrows = css.getElementsByClass(tr, 'tableSortArrow', 'span');
		for (var j = 0; j < arrows.length; j++) {
			var arrowParent = arrows[j].parentNode;
			arrowParent.removeChild(arrows[j]);

			if (arrowParent != td) {
				spanEl = createElement('span');
				spanEl.className = 'tableSortArrow';
				spanEl.appendChild(document.createTextNode('\u00A0\u00A0'));
				arrowParent.appendChild(spanEl);
			}
		}
		
		// now, add back in some feedback 
		var spanEl = createElement('span');
		spanEl.className = 'tableSortArrow';
		if (null == previousSortOrder || '' == previousSortOrder || 'DESC' == previousSortOrder) {
			spanEl.appendChild(document.createTextNode(' \u2191'));
			spanEl.setAttribute('sortOrder', 'ASC');
		} else {
			spanEl.appendChild(document.createTextNode(' \u2193'));
			spanEl.setAttribute('sortOrder', 'DESC');
		}
		
		td.appendChild(spanEl);
		
		return false;
	},

	getInnerText : function(el) {
		
		if ('string' == typeof el || 'undefined' == typeof el) {
			return el;
		}
		
		if (el.innerText) {
			return el.innerText;  // Not needed but it is faster
		}

		var str = el.getAttribute('standardistaTableSortingInnerText');
		if (null != str && '' != str) {
			return str;
		}
		str = '';

		var cs = el.childNodes;
		var l = cs.length;
		for (var i = 0; i < l; i++) {
			// 'if' is considerably quicker than a 'switch' statement, 
			// in Internet Explorer which translates up to a good time 
			// reduction since this is a very often called recursive function
			if (1 == cs[i].nodeType) { // ELEMENT NODE
				str += this.getInnerText(cs[i]);
				break;
			} else if (3 == cs[i].nodeType) { //TEXT_NODE
				str += cs[i].nodeValue;
				break;
			}
		}
		
		// set the innertext for this element directly on the element
		// so that it can be retrieved early next time the innertext
		// is requested
		el.setAttribute('standardistaTableSortingInnerText', str);
		
		return str;
	},

	determineSortFunction : function(itm) {
		
		var sortfn = this.sortCaseInsensitive;
		
		if (itm.match(/^\d\d[\/-]\d\d[\/-]\d\d\d\d$/)) {
			sortfn = this.sortDate;
		}
		if (itm.match(/^\d\d[\/-]\d\d[\/-]\d\d$/)) {
			sortfn = this.sortDate;
		}
		if (itm.match(/^[�$]/)) {
			sortfn = this.sortCurrency;
		}
		if (itm.match(/^\d?\.?\d+$/)) {
			sortfn = this.sortNumeric;
		}
		if (itm.match(/^[+-]?\d*\.?\d+([eE]-?\d+)?$/)) {
			sortfn = this.sortNumeric;
		}
    		if (itm.match(/^([01]?\d\d?|2[0-4]\d|25[0-5])\.([01]?\d\d?|2[0-4]\d|25[0-5])\.([01]?\d\d?|2[0-4]\d|25[0-5])\.([01]?\d\d?|2[0-4]\d|25[0-5])$/)) {
        		sortfn = this.sortIP;
   		}

		return sortfn;
	},
	
	sortCaseInsensitive : function(a, b) {
		var that = standardistaTableSorting.that;
		
		var aa = that.getInnerText(a.cells[that.sortColumnIndex]).toLowerCase();
		var bb = that.getInnerText(b.cells[that.sortColumnIndex]).toLowerCase();
		if (aa==bb) {
			return 0;
		} else if (aa<bb) {
			return -1;
		} else {
			return 1;
		}
	},
	
	sortDate : function(a,b) {
		var that = standardistaTableSorting.that;

		// y2k notes: two digit years less than 50 are treated as 20XX, greater than 50 are treated as 19XX
		var aa = that.getInnerText(a.cells[that.sortColumnIndex]);
		var bb = that.getInnerText(b.cells[that.sortColumnIndex]);
		
		var dt1, dt2, yr = -1;
		
		if (aa.length == 10) {
			dt1 = aa.substr(6,4)+aa.substr(3,2)+aa.substr(0,2);
		} else {
			yr = aa.substr(6,2);
			if (parseInt(yr) < 50) { 
				yr = '20'+yr; 
			} else { 
				yr = '19'+yr; 
			}
			dt1 = yr+aa.substr(3,2)+aa.substr(0,2);
		}
		
		if (bb.length == 10) {
			dt2 = bb.substr(6,4)+bb.substr(3,2)+bb.substr(0,2);
		} else {
			yr = bb.substr(6,2);
			if (parseInt(yr) < 50) { 
				yr = '20'+yr; 
			} else { 
				yr = '19'+yr; 
			}
			dt2 = yr+bb.substr(3,2)+bb.substr(0,2);
		}
		
		if (dt1==dt2) {
			return 0;
		} else if (dt1<dt2) {
			return -1;
		}
		return 1;
	},

	sortCurrency : function(a,b) { 
		var that = standardistaTableSorting.that;

		var aa = that.getInnerText(a.cells[that.sortColumnIndex]).replace(/[^0-9.]/g,'');
		var bb = that.getInnerText(b.cells[that.sortColumnIndex]).replace(/[^0-9.]/g,'');
		return parseFloat(aa) - parseFloat(bb);
	},

	sortNumeric : function(a,b) { 
		var that = standardistaTableSorting.that;

		var aa = parseFloat(that.getInnerText(a.cells[that.sortColumnIndex]));
		if (isNaN(aa)) { 
			aa = 0;
		}
		var bb = parseFloat(that.getInnerText(b.cells[that.sortColumnIndex])); 
		if (isNaN(bb)) { 
			bb = 0;
		}
		return aa-bb;
	},

	makeStandardIPAddress : function(val) {
		var vals = val.split('.');

		for (x in vals) {
			val = vals[x];

			while (3 > val.length) {
				val = '0'+val;
			}
			vals[x] = val;
		}

		val = vals.join('.');

		return val;
	},

	sortIP : function(a,b) { 
		var that = standardistaTableSorting.that;

		var aa = that.makeStandardIPAddress(that.getInnerText(a.cells[that.sortColumnIndex]).toLowerCase());
		var bb = that.makeStandardIPAddress(that.getInnerText(b.cells[that.sortColumnIndex]).toLowerCase());
		if (aa==bb) {
			return 0;
		} else if (aa<bb) {
			return -1;
		} else {
			return 1;
		}
	},

	moveRows : function(table, newRows) {
		this.isOdd = false;

		// We appendChild rows that already exist to the tbody, so it moves them rather than creating new ones
		for (var i=0;i<newRows.length;i++) { 
			var rowItem = newRows[i];

			this.doStripe(rowItem);

			table.tBodies[0].appendChild(rowItem); 
		}
	},
	
	doStripe : function(rowItem) {
		if (this.isOdd) {
			css.addClassToElement(rowItem, 'odd');
		} else {
			css.removeClassFromElement(rowItem, 'odd');
		}
		
		this.isOdd = !this.isOdd;
	}

}

function standardistaTableSortingInit() {
	standardistaTableSorting.init();
	if (document.getElementsByTagName("TH")[0] != null) {
		var e = {};
		var obj = document.getElementsByTagName("TH")[0].firstChild; // 0 points to the first column, use 3 if you want to sort on the 4th column
		e.target = obj;
		standardistaTableSorting.headingClicked(e);  // will sort Ascending
	}
}

addEvent(window, 'load', standardistaTableSortingInit)