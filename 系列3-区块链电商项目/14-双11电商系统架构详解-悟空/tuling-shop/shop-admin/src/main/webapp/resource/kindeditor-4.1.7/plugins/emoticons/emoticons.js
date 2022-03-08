/*
 * Copyright 2021-2022 the original author or authors
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

KindEditor.plugin('emoticons', function(K) {
	var self = this, name = 'emoticons',
		path = (self.emoticonsPath || self.pluginsPath + 'emoticons/images/'),
		allowPreview = self.allowPreviewEmoticons === undefined ? true : self.allowPreviewEmoticons,
		currentPageNum = 1;
	self.clickToolbar(name, function() {
		var rows = 5, cols = 9, total = 135, startNum = 0,
			cells = rows * cols, pages = Math.ceil(total / cells),
			colsHalf = Math.floor(cols / 2),
			wrapperDiv = K('<div class="ke-plugin-emoticons"></div>'),
			elements = [],
			menu = self.createMenu({
				name : name,
				beforeRemove : function() {
					removeEvent();
				}
			});
		menu.div.append(wrapperDiv);
		var previewDiv, previewImg;
		if (allowPreview) {
			previewDiv = K('<div class="ke-preview"></div>').css('right', 0);
			previewImg = K('<img class="ke-preview-img" src="' + path + startNum + '.gif" />');
			wrapperDiv.append(previewDiv);
			previewDiv.append(previewImg);
		}
		function bindCellEvent(cell, j, num) {
			if (previewDiv) {
				cell.mouseover(function() {
					if (j > colsHalf) {
						previewDiv.css('left', 0);
						previewDiv.css('right', '');
					} else {
						previewDiv.css('left', '');
						previewDiv.css('right', 0);
					}
					previewImg.attr('src', path + num + '.gif');
					K(this).addClass('ke-on');
				});
			} else {
				cell.mouseover(function() {
					K(this).addClass('ke-on');
				});
			}
			cell.mouseout(function() {
				K(this).removeClass('ke-on');
			});
			cell.click(function(e) {
				self.insertHtml('<img src="' + path + num + '.gif" border="0" alt="" />').hideMenu().focus();
				e.stop();
			});
		}
		function createEmoticonsTable(pageNum, parentDiv) {
			var table = document.createElement('table');
			parentDiv.append(table);
			if (previewDiv) {
				K(table).mouseover(function() {
					previewDiv.show('block');
				});
				K(table).mouseout(function() {
					previewDiv.hide();
				});
				elements.push(K(table));
			}
			table.className = 'ke-table';
			table.cellPadding = 0;
			table.cellSpacing = 0;
			table.border = 0;
			var num = (pageNum - 1) * cells + startNum;
			for (var i = 0; i < rows; i++) {
				var row = table.insertRow(i);
				for (var j = 0; j < cols; j++) {
					var cell = K(row.insertCell(j));
					cell.addClass('ke-cell');
					bindCellEvent(cell, j, num);
					var span = K('<span class="ke-img"></span>')
						.css('background-position', '-' + (24 * num) + 'px 0px')
						.css('background-image', 'url(' + path + 'static.gif)');
					cell.append(span);
					elements.push(cell);
					num++;
				}
			}
			return table;
		}
		var table = createEmoticonsTable(currentPageNum, wrapperDiv);
		function removeEvent() {
			K.each(elements, function() {
				this.unbind();
			});
		}
		var pageDiv;
		function bindPageEvent(el, pageNum) {
			el.click(function(e) {
				removeEvent();
				table.parentNode.removeChild(table);
				pageDiv.remove();
				table = createEmoticonsTable(pageNum, wrapperDiv);
				createPageTable(pageNum);
				currentPageNum = pageNum;
				e.stop();
			});
		}
		function createPageTable(currentPageNum) {
			pageDiv = K('<div class="ke-page"></div>');
			wrapperDiv.append(pageDiv);
			for (var pageNum = 1; pageNum <= pages; pageNum++) {
				if (currentPageNum !== pageNum) {
					var a = K('<a href="javascript:;">[' + pageNum + ']</a>');
					bindPageEvent(a, pageNum);
					pageDiv.append(a);
					elements.push(a);
				} else {
					pageDiv.append(K('@[' + pageNum + ']'));
				}
				pageDiv.append(K('@&nbsp;'));
			}
		}
		createPageTable(currentPageNum);
	});
});
