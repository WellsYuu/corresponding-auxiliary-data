/*!
 * dtGrid v1.1.9
 *
 * includes: jquery, bootstrap, fontawesome, My97 DatePicker
 * Copyright 2015, http://www.dtgrid.com, http://www.dlshouwen.com
 */
(function($) {
	$.fn.DtGrid = {
		//初始化方法
		init : function(options, name) {
			/**
			 * 1. 初始化参数
			 */
			for(var i=0; i<options.columns.length; i++){
				//初始化列参数
				options.columns[i] = $.extend({}, $.fn.DtGrid.defaultOptions.column, options.columns[i]);  
			}
			//初始化表格参数
			options = $.extend({}, $.fn.DtGrid.defaultOptions.grid, options);
			//如果没有定义编号则设置默认GUID编号
			if(!options.id){
				options.id = $.fn.DtGrid.tools.guid();
			}
			//如果语言错误则默认为英文
			if(!$.fn.DtGrid.lang[options.lang]){
				options.lang = 'en';
			}
			/**
			 * 2. 定义对象
			 */
			var dtGridObject = {
				/**
				 * 参数相关
				 */
				//初始化参数
				init : {
					//工具条是否初始化加载
					toolsIsInit : false,
					//打印窗体是否初始化
					printWindowIsInit : false,
					//导出窗体是否初始化
					exportWindowIsInit : {},
					//快速查询窗体是否初始化
					fastQueryWindowIsInit : false,
					//高级查询窗体是否初始化
					advanceQueryWindowIsInit : false,
					reloadPagerParameter:true
				},
				//页面参数对象
				pager : {
					//每页显示条数
					pageSize : 0,
					//开始记录数
					startRecord : 0,
					//当前页数
					nowPage : 0,
					//总记录数
					recordCount : 0,
					//总页数
					pageCount : 0
				},
				//表格参数对象
				option : options,
				//原始数据集
				originalDatas : null,
				//基础数据集
				baseDatas : null,
				//展现数据集
				exhibitDatas : null,
				//排序参数
				sortParameter : {
					//排序列编号
					columnId : '',
					//排序类型：0-不排序；1-正序；2-倒序
					sortType : 0
				},
				//排序缓存的原生数据
				sortOriginalDatas : null,
				//参数列表
				parameters : null,
				//快速查询的参数列表
				fastQueryParameters : null,
				//快速查询缓存的原生数据
				fastQueryOriginalDatas : null,
				//高级查询的参数列表
				advanceQueryParameter : {
					//高级查询条件信息
					advanceQueryConditions : null,
					//高级查询排序信息
					advanceQuerySorts : null
				},
				//打印列
				printColumns : null,
				//导出列
				exportColumns : null,
				//导出数据
				exportDatas : null,
				/**
				 * 构件表格相关
				 */
				//构建表格方法
				load : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//显示工具条
					dtGridReflectionObj.showProcessBar();
					//首次加载
					if(dtGridReflectionObj.init.toolsIsInit==false){
						//设置初始化完成
						dtGridReflectionObj.init.toolsIsInit = true;
						//加载工具按钮
						dtGridReflectionObj.constructGridToolBar();
						//设置初始分页属性：每页显示数量、开始记录、当前页号
						dtGridReflectionObj.pager.pageSize = dtGridReflectionObj.option.pageSize;
						if(dtGridReflectionObj.init.reloadPagerParameter) {
							dtGridReflectionObj.pager.startRecord = 0;
							dtGridReflectionObj.pager.nowPage = 1;
						}
						//如果不是ajax加载，则处理所有数据
						if(!dtGridReflectionObj.option.ajaxLoad){
							dtGridReflectionObj.hideProcessBar(function(){
								//处理原始数据集
								dtGridReflectionObj.originalDatas = dtGridReflectionObj.option.datas;
								dtGridReflectionObj.originalDatas = dtGridReflectionObj.originalDatas?dtGridReflectionObj.originalDatas:new Array();
								//处理基础数据集
								dtGridReflectionObj.baseDatas = dtGridReflectionObj.originalDatas.slice(0, dtGridReflectionObj.originalDatas.length);
								//处理分页属性
								dtGridReflectionObj.pager.recordCount = dtGridReflectionObj.baseDatas.length;
								dtGridReflectionObj.pager.pageCount = Math.floor((dtGridReflectionObj.pager.recordCount-1)/dtGridReflectionObj.pager.pageSize)+1;
								//获取展现数据集
								dtGridReflectionObj.exhibitDatas = dtGridReflectionObj.baseDatas.slice(dtGridReflectionObj.pager.startRecord, dtGridReflectionObj.pager.startRecord+dtGridReflectionObj.pager.pageSize);
								//获取排序数据集备份
								dtGridReflectionObj.sortOriginalDatas = dtGridReflectionObj.exhibitDatas.slice(0, dtGridReflectionObj.exhibitDatas.length);
								//构建表格、工具条
								dtGridReflectionObj.constructGrid();
								dtGridReflectionObj.constructGridPageBar();
							});
							return;
						}else{
							//如果是一次加载，则加载所有数据到原始数据
							if(dtGridReflectionObj.option.loadAll){
								var url = dtGridReflectionObj.option.loadURL;
								$.ajax({
									type:'post',
									url:url,
									data:null,
									contentType: "application/x-www-form-urlencoded; charset=utf-8",
									beforeSend: function(xhr) {xhr.setRequestHeader("__REQUEST_TYPE", "AJAX_REQUEST");},
									success:function(datas){
										dtGridReflectionObj.hideProcessBar(function(){
											//处理原始数据集
											dtGridReflectionObj.originalDatas = $.parseJSON(datas);
											dtGridReflectionObj.originalDatas = dtGridReflectionObj.originalDatas?dtGridReflectionObj.originalDatas:new Array();
											//处理基础数据集
											dtGridReflectionObj.baseDatas = dtGridReflectionObj.originalDatas.slice(0, dtGridReflectionObj.originalDatas.length);
											//处理分页属性
											dtGridReflectionObj.pager.recordCount = dtGridReflectionObj.baseDatas.length;
											dtGridReflectionObj.pager.pageCount = Math.floor((dtGridReflectionObj.pager.recordCount-1)/dtGridReflectionObj.pager.pageSize)+1;
											//获取展现数据集
											dtGridReflectionObj.exhibitDatas = dtGridReflectionObj.baseDatas.slice(dtGridReflectionObj.pager.startRecord, dtGridReflectionObj.pager.startRecord+dtGridReflectionObj.pager.pageSize);
											//获取排序数据集备份
											dtGridReflectionObj.sortOriginalDatas = dtGridReflectionObj.exhibitDatas.slice(0, dtGridReflectionObj.exhibitDatas.length);
											//构建表格、工具条
											dtGridReflectionObj.constructGrid();
											dtGridReflectionObj.constructGridPageBar();
											$("[data-toggle='popover']").popover();
										});
									},
									error:function(XMLHttpRequest, textStatus, errorThrown){
										dtGridReflectionObj.hideProcessBar(function(){
											$.fn.DtGrid.tools.toast($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].errors.ajaxLoadError, 'error', 5000);
											//构建表格、工具条
											dtGridReflectionObj.constructGrid();
											dtGridReflectionObj.constructGridPageBar();
										});
									}
								});
								return;
							}
						}
					}
					//非初始化运行
					if(!dtGridReflectionObj.option.ajaxLoad||dtGridReflectionObj.option.loadAll){
						dtGridReflectionObj.hideProcessBar(function(){
							//处理快速查询及高级查询
							if(dtGridReflectionObj.fastQueryParameters||dtGridReflectionObj.advanceQueryParameter){
								//传递所有数据
								dtGridReflectionObj.baseDatas = dtGridReflectionObj.originalDatas;
								//处理快速查询
								if(dtGridReflectionObj.fastQueryParameters){
									dtGridReflectionObj.baseDatas = dtGridReflectionObj.doFaseQueryDatasFilter(dtGridReflectionObj.baseDatas, dtGridReflectionObj.fastQueryParameters);
								}
								//处理高级查询
								if(dtGridReflectionObj.advanceQueryParameter){
									dtGridReflectionObj.baseDatas = dtGridReflectionObj.doAdvanceQueryDatasFilter(dtGridReflectionObj.baseDatas, dtGridReflectionObj.advanceQueryParameter);
								}
							}
							//记录数、页数重算
							dtGridReflectionObj.pager.recordCount = dtGridReflectionObj.baseDatas.length;
							dtGridReflectionObj.pager.pageCount = dtGridReflectionObj.pager.recordCount==0?0:(Math.floor((dtGridReflectionObj.pager.recordCount-1)/dtGridReflectionObj.pager.pageSize)+1);
							//如果当前页数大于现在的总页数，则显示最后一页
							if(dtGridReflectionObj.pager.nowPage>dtGridReflectionObj.pager.pageCount){
								dtGridReflectionObj.pager.nowPage = dtGridReflectionObj.pager.pageCount;
								dtGridReflectionObj.pager.startRecord = dtGridReflectionObj.pager.pageSize*(dtGridReflectionObj.pager.nowPage-1);
							}
							//重新计算开始记录
							dtGridReflectionObj.pager.startRecord = dtGridReflectionObj.pager.pageSize*(dtGridReflectionObj.pager.nowPage-1);
							//如果没有数据，则重设开始记录、当前页
							if(dtGridReflectionObj.baseDatas.length==0){
								dtGridReflectionObj.pager.nowPage = 1;
								dtGridReflectionObj.pager.startRecord = 0;
							}
							//获取展现数据集
							dtGridReflectionObj.exhibitDatas = dtGridReflectionObj.baseDatas.slice(dtGridReflectionObj.pager.startRecord, dtGridReflectionObj.pager.startRecord+dtGridReflectionObj.pager.pageSize);
							//获取排序数据集备份
							dtGridReflectionObj.sortOriginalDatas = dtGridReflectionObj.exhibitDatas.slice(0, dtGridReflectionObj.exhibitDatas.length);
							//构建表格、工具条
							dtGridReflectionObj.constructGrid();
							dtGridReflectionObj.constructGridPageBar();
						});
					}else{
						//将参数传递后台AJAX获取数据
						var url = dtGridReflectionObj.option.loadURL;
						var pager = new Object();
						pager.isExport = false;
						pager.pageSize = dtGridReflectionObj.pager.pageSize;
						pager.startRecord = dtGridReflectionObj.pager.startRecord;
						pager.nowPage = dtGridReflectionObj.pager.nowPage;
						pager.recordCount = dtGridReflectionObj.pager.recordCount?dtGridReflectionObj.pager.recordCount:-1;
						pager.pageCount = dtGridReflectionObj.pager.pageCount?dtGridReflectionObj.pager.pageCount:-1;
						pager.parameters = dtGridReflectionObj.parameters?dtGridReflectionObj.parameters:new Object();
						pager.fastQueryParameters = dtGridReflectionObj.fastQueryParameters?dtGridReflectionObj.fastQueryParameters:new Object();
						pager.advanceQueryConditions = (dtGridReflectionObj.advanceQueryParameter&&dtGridReflectionObj.advanceQueryParameter.advanceQueryConditions)?dtGridReflectionObj.advanceQueryParameter.advanceQueryConditions:new Array();
						pager.advanceQuerySorts = (dtGridReflectionObj.advanceQueryParameter&&dtGridReflectionObj.advanceQueryParameter.advanceQuerySorts)?dtGridReflectionObj.advanceQueryParameter.advanceQuerySorts:new Array();
						var params = new Object();
						params.dtGridPager = JSON.stringify(pager);
						$.ajax({
							type:'post',
							url:url,
							data:params,
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
							beforeSend: function(xhr) {xhr.setRequestHeader("__REQUEST_TYPE", "AJAX_REQUEST");},
							success:function(pager){
								/*pager = $.parseJSON(pager);*/
								//如果出错表示有可能是程序问题或高级查询方案配置有误
								if(!pager.isSuccess){
									$.fn.DtGrid.tools.toast($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].errors.ajaxLoadError, 'error', 5000);
									dtGridReflectionObj.hideProcessBar();
									return;
								}
								dtGridReflectionObj.hideProcessBar(function(){
									//处理展示数据和分页相关信息
									dtGridReflectionObj.exhibitDatas = pager.exhibitDatas;
									//获取排序数据集备份
									dtGridReflectionObj.sortOriginalDatas = dtGridReflectionObj.exhibitDatas.slice(0, dtGridReflectionObj.exhibitDatas.length);
									//处理分页信息
									dtGridReflectionObj.pager.pageSize = pager.pageSize;
									dtGridReflectionObj.pager.startRecord = pager.startRecord;
									dtGridReflectionObj.pager.nowPage = pager.nowPage;
									dtGridReflectionObj.pager.recordCount = pager.recordCount;
									dtGridReflectionObj.pager.pageCount = pager.pageCount;
									//构建表格、工具条
									dtGridReflectionObj.constructGrid();
									dtGridReflectionObj.constructGridPageBar();
									$("[data-toggle='popover']").popover();
								});
							},
							error:function(XMLHttpRequest, textStatus, errorThrown){
								dtGridReflectionObj.hideProcessBar(function(){
									$.fn.DtGrid.tools.toast($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].errors.ajaxLoadError, 'error', 5000);
									//构建表格、工具条
									dtGridReflectionObj.constructGrid();
									dtGridReflectionObj.constructGridPageBar();
								});
							}
						});
					}
				},
				//构建主体内容
				constructGrid : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//获取扩展列列头
					var extraColumnClass = dtGridReflectionObj.getExtraColumnClass();
					//构件表头
					var gridContent = '';
					gridContent += '<table class="dt-grid '+dtGridReflectionObj.option.tableClass+'" id="dt_grid_'+dtGridReflectionObj.option.id+'" style="'+dtGridReflectionObj.option.tableStyle+'">';
					if(dtGridReflectionObj.option.showHeader!=false){
						var columns = dtGridReflectionObj.option.columns;
						gridContent += '<thead>';
						gridContent += '	<tr class="dt-grid-headers">';
						gridContent += '		<th class="extra-column '+extraColumnClass+'"></th>';
						if(dtGridReflectionObj.option.check){
							gridContent += '	<th class="check-column"><input type="checkbox" id="dt_grid_'+dtGridReflectionObj.option.id+'_check" value="check"></th>';
						}
						for(var i=0; i<columns.length; i++){
							gridContent += '	<th columnNo="'+i+'" columnId="'+columns[i].id+'" class="dt-grid-header '+dtGridReflectionObj.getColumnClassForHide(columns[i])+' '+columns[i].headerClass+' can-sort" style="'+columns[i].headerStyle+'">';
							if(dtGridReflectionObj.sortParameter&&dtGridReflectionObj.sortParameter.columnId&&dtGridReflectionObj.sortParameter.columnId==columns[i].id){
								if(dtGridReflectionObj.sortParameter.sortType=='1'){
									gridContent += '<span class="dt-grid-sort">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].sortColumn.asc+'</span>';
								}
								if(dtGridReflectionObj.sortParameter.sortType=='2'){
									gridContent += '<span class="dt-grid-sort dt-grid-sort-desc">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].sortColumn.desc+'</span>';
								}
							}
							gridContent += '		'+columns[i].title;
							gridContent += '	</th>';
						}
						gridContent += '	</tr>';
						gridContent += '</thead>';
					}
					//构建表格
					gridContent += '	<tbody>';
					if(dtGridReflectionObj.exhibitDatas!=null){
						if(dtGridReflectionObj.sortParameter&&dtGridReflectionObj.sortParameter.columnId){
							if(dtGridReflectionObj.sortParameter.sortType!=0){
								dtGridReflectionObj.exhibitDatas = dtGridReflectionObj.exhibitDatas.sort(function(record1, record2){
									var value1 = record1[dtGridReflectionObj.sortParameter.columnId];
									var value2 = record2[dtGridReflectionObj.sortParameter.columnId];
									if(value1&&value2){
										//数值比较
										if(!isNaN(value1)&&!isNaN(value2)){
											if(dtGridReflectionObj.sortParameter.sortType==1){
												return value1-value2;
											}
											if(dtGridReflectionObj.sortParameter.sortType==2){
												return value2-value1;
											}
										}
										//日期比较
										if(value1 instanceof Date&&value2 instanceof Date){
											if(dtGridReflectionObj.sortParameter.sortType==1){
												return value1.getTime()-value2.getTime();
											}
											if(dtGridReflectionObj.sortParameter.sortType==2){
												return value2.getTime()-value1.getTime();
											}
										}
										//普通比较
										if(dtGridReflectionObj.sortParameter.sortType==1){
											return value1.localeCompare(value2);
										}
										if(dtGridReflectionObj.sortParameter.sortType==2){
											return value2.localeCompare(value1);
										}
									}
									return 0;
								});
							}else{
								dtGridReflectionObj.exhibitDatas = dtGridReflectionObj.sortOriginalDatas.slice(0, dtGridReflectionObj.sortOriginalDatas.length);
							}
						}
						for(var i=0; i<dtGridReflectionObj.exhibitDatas.length; i++){
							gridContent += '	<tr class="dt-grid-row" dataNo="'+i+'">';
							gridContent += '		<td class="extra-column extra-column-event '+extraColumnClass+'" dataNo="'+i+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].extraColumn.open+'</td>';
							if(dtGridReflectionObj.option.check){
								gridContent += '	<td class="check-column text-center"><input type="checkbox" dataNo="'+i+'" id="dt_grid_'+dtGridReflectionObj.option.id+'_check_'+i+'" class="dt-grid-check" value="'+i+'"></td>';
							}
							var columns = dtGridReflectionObj.option.columns;
							for(var j=0; j<columns.length; j++){
								gridContent += '	<td dataNo="'+i+'" columnNo="'+j+'" class="dt-grid-cell '+dtGridReflectionObj.getColumnClassForHide(columns[j])+' '+columns[j].columnClass+'" style="'+columns[j].columnStyle+'">';
								var value = dtGridReflectionObj.exhibitDatas[i][columns[j].id];
								value = value==null?'':value;
								if(columns[j].resolution){
									gridContent += columns[j].resolution(value, dtGridReflectionObj.exhibitDatas[i], columns[j], dtGridReflectionObj, i, j);
								}else{
									gridContent += dtGridReflectionObj.formatContent(columns[j], value);
								}
								gridContent += '	</td>';
							}
							gridContent += '	</tr>';
							gridContent += '	<tr id="dt_grid_'+dtGridReflectionObj.option.id+'_extra_columns_'+i+'" class="dt-grid-extra-columns hidden">';
							gridContent += '		<td dataNo="'+i+'" colspan="'+(columns.length+1+(dtGridReflectionObj.option.check?1:0))+'">';
							for(var j=0; j<columns.length; j++){
								if(columns[j].extra==false){
									continue;
								}
								gridContent += '		<p dataNo="'+i+'" columnNo="'+j+'" class="dt-grid-cell '+dtGridReflectionObj.getExtraColumnClassForHide(columns[j])+'">';
								gridContent += '			'+columns[j].title+' : ';
								var value = dtGridReflectionObj.exhibitDatas[i][columns[j].id];
								value = value==null?'':value;
								if(columns[j].resolution){
									gridContent += columns[j].resolution(value, dtGridReflectionObj.exhibitDatas[i], columns[j], dtGridReflectionObj, i, j);
								}else{
									gridContent += dtGridReflectionObj.formatContent(columns[j], value);
								}
								gridContent += '		</p>';
							}
							gridContent += '		</td>';
							gridContent += '	</tr>';
						}
					}
					gridContent += '	</tbody>';
					gridContent += '</table>';
					$('#dt_grid_'+dtGridReflectionObj.option.id).remove();
					$('#'+dtGridReflectionObj.option.gridContainer).append(gridContent);
					//备份gridId
					var gridId = dtGridReflectionObj.option.id;
					//绑定单元格单击方法
					if(dtGridReflectionObj.option.onCellClick){
						$('#dt_grid_'+gridId+' .dt-grid-cell').click(function(e){
							dtGridReflectionObj.bindCellEvent(dtGridReflectionObj.option.onCellClick, this, e);
						});
					}
					//绑定单元格双击方法
					if(dtGridReflectionObj.option.onCellDblClick){
						$('#dt_grid_'+gridId+' .dt-grid-cell').dblclick(function(e){
							dtGridReflectionObj.bindCellEvent(dtGridReflectionObj.option.onCellDblClick, this, e);
						});
					}
					//绑定单元格鼠标滑过方法
					if(dtGridReflectionObj.option.onCellMouseOver){
						$('#dt_grid_'+gridId+' .dt-grid-cell').mouseover(function(e){
							dtGridReflectionObj.bindCellEvent(dtGridReflectionObj.option.onCellMouseOver, this, e);
						});
					}
					//绑定单元格鼠标移动方法
					if(dtGridReflectionObj.option.onCellMouseMove){
						$('#dt_grid_'+gridId+' .dt-grid-cell').mousemove(function(e){
							dtGridReflectionObj.bindCellEvent(dtGridReflectionObj.option.onCellMouseMove, this, e);
						});
					}
					//绑定单元格鼠标滑出方法
					if(dtGridReflectionObj.option.onCellMouseOut){
						$('#dt_grid_'+gridId+' .dt-grid-cell').mouseout(function(e){
							dtGridReflectionObj.bindCellEvent(dtGridReflectionObj.option.onCellMouseOut, this, e);
						});
					}
					//绑定单元格鼠标按下方法
					if(dtGridReflectionObj.option.onCellMouseDown){
						$('#dt_grid_'+gridId+' .dt-grid-cell').mousedown(function(e){
							dtGridReflectionObj.bindCellEvent(dtGridReflectionObj.option.onCellMouseDown, this, e);
						});
					}
					//绑定单元格鼠标释放方法
					if(dtGridReflectionObj.option.onCellMouseUp){
						$('#dt_grid_'+gridId+' .dt-grid-cell').mouseup(function(e){
							dtGridReflectionObj.bindCellEvent(dtGridReflectionObj.option.onCellMouseUp, this, e);
						});
					}
					//绑定行单击方法
					if(dtGridReflectionObj.option.onRowClick){
						$('#dt_grid_'+gridId+' .dt-grid-cell').click(function(e){
							dtGridReflectionObj.bindRowEvent(dtGridReflectionObj.option.onRowClick, this, e);
						});
					}
					//绑定行双击方法
					if(dtGridReflectionObj.option.onRowDblClick){
						$('#dt_grid_'+gridId+' .dt-grid-cell').dblclick(function(e){
							dtGridReflectionObj.bindRowEvent(dtGridReflectionObj.option.onRowDblClick, this, e);
						});
					}
					//绑定行鼠标滑过方法
					if(dtGridReflectionObj.option.onRowMouseOver){
						$('#dt_grid_'+gridId+' .dt-grid-cell').mouseover(function(e){
							dtGridReflectionObj.bindRowEvent(dtGridReflectionObj.option.onRowMouseOver, this, e);
						});
					}
					//绑定行鼠标移动方法
					if(dtGridReflectionObj.option.onRowMouseMove){
						$('#dt_grid_'+gridId+' .dt-grid-cell').mousemove(function(e){
							dtGridReflectionObj.bindRowEvent(dtGridReflectionObj.option.onRowMouseMove, this, e);
						});
					}
					//绑定行鼠标滑出方法
					if(dtGridReflectionObj.option.onRowMouseOut){
						$('#dt_grid_'+gridId+' .dt-grid-cell').mouseout(function(e){
							dtGridReflectionObj.bindRowEvent(dtGridReflectionObj.option.onRowMouseOut, this, e);
						});
					}
					//绑定行鼠标按下方法
					if(dtGridReflectionObj.option.onRowMouseDown){
						$('#dt_grid_'+gridId+' .dt-grid-cell').mousedown(function(e){
							dtGridReflectionObj.bindRowEvent(dtGridReflectionObj.option.onRowMouseDown, this, e);
						});
					}
					//绑定行鼠标释放方法
					if(dtGridReflectionObj.option.onRowMouseUp){
						$('#dt_grid_'+gridId+' .dt-grid-cell').mouseup(function(e){
							dtGridReflectionObj.bindRowEvent(dtGridReflectionObj.option.onRowMouseUp, this, e);
						});
					}
					//绑定表头单击方法
					if(dtGridReflectionObj.option.onHeaderClick){
						$('#dt_grid_'+gridId+' .dt-grid-header').click(function(e){
							dtGridReflectionObj.bindHeaderEvent(dtGridReflectionObj.option.onHeaderClick, this, e);
						});
					}
					/**
					 * 修复：表头双加方法由于表头的单击排序需要重新加载，所以双击事件无法响应，取消该事件
					 */
					/**
					//绑定表头双击方法
					if(dtGridReflectionObj.option.onHeaderDblClick){
						$('#dt_grid_'+gridId+' .dt-grid-header').dblclick(function(e){
							dtGridReflectionObj.bindHeaderEvent(dtGridReflectionObj.option.onHeaderDblClick, this, e);
						});
					}
					 */
					//绑定表头鼠标滑过方法
					if(dtGridReflectionObj.option.onHeaderMouseOver){
						$('#dt_grid_'+gridId+' .dt-grid-header').mouseover(function(e){
							dtGridReflectionObj.bindHeaderEvent(dtGridReflectionObj.option.onHeaderMouseOver, this, e);
						});
					}
					//绑定表头鼠标移动方法
					if(dtGridReflectionObj.option.onHeaderMouseMove){
						$('#dt_grid_'+gridId+' .dt-grid-header').mousemove(function(e){
							dtGridReflectionObj.bindHeaderEvent(dtGridReflectionObj.option.onHeaderMouseMove, this, e);
						});
					}
					//绑定表头鼠标滑出方法
					if(dtGridReflectionObj.option.onHeaderMouseOut){
						$('#dt_grid_'+gridId+' .dt-grid-header').mouseout(function(e){
							dtGridReflectionObj.bindHeaderEvent(dtGridReflectionObj.option.onHeaderMouseOut, this, e);
						});
					}
					//绑定表头鼠标按下方法
					if(dtGridReflectionObj.option.onHeaderMouseDown){
						$('#dt_grid_'+gridId+' .dt-grid-header').mousedown(function(e){
							dtGridReflectionObj.bindHeaderEvent(dtGridReflectionObj.option.onHeaderMouseDown, this, e);
						});
					}
					//绑定表头鼠标释放方法
					if(dtGridReflectionObj.option.onHeaderMouseUp){
						$('#dt_grid_'+gridId+' .dt-grid-header').mouseup(function(e){
							dtGridReflectionObj.bindHeaderEvent(dtGridReflectionObj.option.onHeaderMouseUp, this, e);
						});
					}
					//绑定表格单击方法
					if(dtGridReflectionObj.option.onGridClick){
						$('#dt_grid_'+gridId).click(function(e){
							dtGridReflectionObj.bindGridEvent(dtGridReflectionObj.option.onGridClick, e);
						});
					}
					//绑定表格双击方法
					if(dtGridReflectionObj.option.onGridDblClick){
						$('#dt_grid_'+gridId).dblclick(function(e){
							dtGridReflectionObj.bindGridEvent(dtGridReflectionObj.option.onGridDblClick, e);
						});
					}
					//绑定表格鼠标滑过方法
					if(dtGridReflectionObj.option.onGridMouseOver){
						$('#dt_grid_'+gridId).mouseover(function(e){
							dtGridReflectionObj.bindGridEvent(dtGridReflectionObj.option.onGridMouseOver, e);
						});
					}
					//绑定表格鼠标移动方法
					if(dtGridReflectionObj.option.onGridMouseMove){
						$('#dt_grid_'+gridId).mousemove(function(e){
							dtGridReflectionObj.bindGridEvent(dtGridReflectionObj.option.onGridMouseMove, e);
						});
					}
					//绑定表格鼠标滑出方法
					if(dtGridReflectionObj.option.onGridMouseOut){
						$('#dt_grid_'+gridId).mouseout(function(e){
							dtGridReflectionObj.bindGridEvent(dtGridReflectionObj.option.onGridMouseOut, e);
						});
					}
					//绑定表格鼠标按下方法
					if(dtGridReflectionObj.option.onGridMouseDown){
						$('#dt_grid_'+gridId).mousedown(function(e){
							dtGridReflectionObj.bindGridEvent(dtGridReflectionObj.option.onGridMouseDown, e);
						});
					}
					//绑定表格鼠标释放方法
					if(dtGridReflectionObj.option.onGridMouseUp){
						$('#dt_grid_'+gridId).mouseup(function(e){
							dtGridReflectionObj.bindGridEvent(dtGridReflectionObj.option.onGridMouseUp, e);
						});
					}
					//绑定表格加载完成方法
					if(dtGridReflectionObj.option.onGridComplete){
						dtGridReflectionObj.bindGridEvent(dtGridReflectionObj.option.onGridComplete);
					}
					//绑定显隐方法
					$('#dt_grid_'+gridId+' .extra-column-event').click(function(e){
						var dataNo = $(this).attr('dataNo');
						if($('#dt_grid_'+gridId+'_extra_columns_'+dataNo).hasClass('hidden')){
							$('#dt_grid_'+gridId+'_extra_columns_'+dataNo).removeClass('hidden');
							$(this).html($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].extraColumn.close);
							//绑定扩展行展开方法
							if(dtGridReflectionObj.option.onExtraOpen){
								var row = $('#dt_grid_'+gridId+' tr[dataNo="'+dataNo+'"]');
								dtGridReflectionObj.bindExtraEvent(dtGridReflectionObj.option.onExtraOpen, row, e);
							}
						}else{
							$('#dt_grid_'+gridId+'_extra_columns_'+dataNo).addClass('hidden');
							$(this).html($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].extraColumn.open);
							//绑定扩展行关闭方法
							if(dtGridReflectionObj.option.onExtraClose){
								var row = $('#dt_grid_'+gridId+' tr[dataNo="'+dataNo+'"]');
								dtGridReflectionObj.bindExtraEvent(dtGridReflectionObj.option.onExtraClose, row, e);
							}
						}
					});
					//绑定排序方法
					$('#dt_grid_'+gridId+' .can-sort').click(function(){
						//获取列编号
						var columnId = $(this).attr('columnId');
						//根据当前的排序参数设置显示的排序图标
						if(dtGridReflectionObj.sortParameter==null){
							dtGridReflectionObj.sortParameter = new Object();
						}
						if(!dtGridReflectionObj.sortParameter.columnId||dtGridReflectionObj.sortParameter.columnId!=columnId){
							dtGridReflectionObj.sortParameter.columnId = columnId;
							dtGridReflectionObj.sortParameter.sortType = 1;
						}else{
							var sortType = dtGridReflectionObj.sortParameter.sortType;
							if(sortType==0){
								dtGridReflectionObj.sortParameter.sortType = 1;
							}else if(sortType==1){
								dtGridReflectionObj.sortParameter.sortType = 2;
							}else if(sortType==2){
								dtGridReflectionObj.sortParameter.sortType = 0;
							}
						}
						//重新加载数据
						dtGridReflectionObj.reload();
					});
					//绑定复选方法
					if(dtGridReflectionObj.option.onCheck){
						$('input[id*=dt_grid_'+gridId+'_check_]').click(function(e){
							dtGridReflectionObj.bindCheckEvent(dtGridReflectionObj.option.onCheck, this, e);
						});
					}
					//绑定复选方法（全选反选）
					if(dtGridReflectionObj.option.check){
						$('#dt_grid_'+gridId+'_check').click(function(e){
							$('input[id*=dt_grid_'+gridId+'_check_]').attr('checked', this.checked);
							if(dtGridReflectionObj.option.onCheck){
								$('input[id*=dt_grid_'+gridId+'_check_]').each(function(){
									dtGridReflectionObj.bindCheckEvent(dtGridReflectionObj.option.onCheck, this, e);
								});
							}
						});
					}
				},
				//绑定单元格事件
				bindCellEvent : function(func, cellObject, e){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//备份gridId
					var gridId = dtGridReflectionObj.option.id;
					//获取数据号、列号
					var dataNo = $(cellObject).attr('dataNo');
					var columnNo = $(cellObject).attr('columnNo');
					//获取当前Column对象
					var column = dtGridReflectionObj.option.columns[columnNo];
					//获取当前记录
					var record = dtGridReflectionObj.exhibitDatas[dataNo];
					//获取当前值（处理后的）
					var value;
					if(column.resolution){
						value = column.resolution(record[column.id], record, column, dtGridReflectionObj, dataNo, columnNo);
					}else{
						value = dtGridReflectionObj.formatContent(column, record[column.id]);
					}
					//获取当前单元格jQuery对象
					var cell = $(cellObject);
					//获取当前行jQuery对象
					var row = $('#dt_grid_'+gridId+' tr[dataNo="'+dataNo+'"]');
					//获取扩展行jQuery对象
					var extraCell = $('#dt_grid_'+gridId+' .dt-grid-extra-columns>td[dataNo="'+dataNo+'"]');
					func(value, record, column, dtGridReflectionObj, dataNo, columnNo, cell, row, extraCell, e);
				},
				//绑定行事件
				bindRowEvent : function(func, cellObject, e){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//备份gridId
					var gridId = dtGridReflectionObj.option.id;
					//获取数据号、列号
					var dataNo = $(cellObject).attr('dataNo');
					var columnNo = $(cellObject).attr('columnNo');
					//获取当前Column对象
					var column = dtGridReflectionObj.option.columns[columnNo];
					//获取当前记录
					var record = dtGridReflectionObj.exhibitDatas[dataNo];
					//获取当前值（处理后的）
					var value;
					if(column.resolution){
						value = column.resolution(record[column.id], record, column, dtGridReflectionObj, dataNo, columnNo);
					}else{
						value = dtGridReflectionObj.formatContent(column, record[column.id]);
					}
					//获取当前单元格jQuery对象
					var cell = $(cellObject);
					//获取当前行jQuery对象
					var row = $('#dt_grid_'+gridId+' tr[dataNo="'+dataNo+'"]');
					//获取扩展行jQuery对象
					var extraCell = $('#dt_grid_'+gridId+' .dt-grid-extra-columns>td[dataNo="'+dataNo+'"]');
					func(value, record, column, dtGridReflectionObj, dataNo, columnNo, cell, row, extraCell, e);
				},
				//绑定表头事件
				bindHeaderEvent : function(func, headerObject, e){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//备份gridId
					var gridId = dtGridReflectionObj.option.id;
					//获取列号
					var columnNo = $(headerObject).attr('columnNo');
					//获取当前Column对象
					var column = dtGridReflectionObj.option.columns[columnNo];
					//获取当前表头名称
					var title = column.title;
					//获取当前单元格jQuery对象
					var header = $(headerObject);
					//获取当前行jQuery对象
					var headerRow = $('#dt_grid_'+gridId+' tr.dt-grid-headers');
					func(title, column, dtGridReflectionObj, columnNo, header, headerRow, e);
				},
				//绑定表头事件
				bindGridEvent : function(func, e){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					func(dtGridReflectionObj, e);
				},
				//绑定扩展行事件
				bindExtraEvent : function(func, rowObject, e){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//备份gridId
					var gridId = dtGridReflectionObj.option.id;
					//获取数据号
					var dataNo = $(rowObject).attr('dataNo');
					//获取当前记录
					var record = dtGridReflectionObj.exhibitDatas[dataNo];
					//获取当前行jQuery对象
					var row = $('#dt_grid_'+gridId+' tr[dataNo="'+dataNo+'"]');
					//获取扩展行jQuery对象
					var extraCell = $('#dt_grid_'+gridId+' .dt-grid-extra-columns>td[dataNo="'+dataNo+'"]');
					func(record, dtGridReflectionObj, dataNo, row, extraCell, e);
				},
				//绑定复选事件
				bindCheckEvent : function(func, checkInput, e){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//备份gridId
					var gridId = dtGridReflectionObj.option.id;
					//获取数据号、列号
					var dataNo = $(checkInput).attr('dataNo');
					//获取当前记录
					var record = dtGridReflectionObj.exhibitDatas[dataNo];
					//获取当前行jQuery对象
					var row = $('#dt_grid_'+gridId+' tr[dataNo="'+dataNo+'"]');
					//获取扩展行jQuery对象
					var extraCell = $('#dt_grid_'+gridId+' .dt-grid-extra-columns>td[dataNo="'+dataNo+'"]');
					func(checkInput.checked, record, dtGridReflectionObj, dataNo, row, extraCell, e);
				},
				//构建分页内容
				constructGridPageBar : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//清空工具条
					$('#'+dtGridReflectionObj.option.toolbarContainer+' .dt-grid-pager').html('');
					//获取当前页、每页条数、总页数
					var nowPage = dtGridReflectionObj.pager.nowPage;
					var pageCount = dtGridReflectionObj.pager.pageCount;
					//设置工具条初始内容
					var gridStatus = document.createElement('span');
					if(dtGridReflectionObj.exhibitDatas==null||dtGridReflectionObj.exhibitDatas.length<=0){
						gridStatus.className = 'dt-grid-pager-status text-primary';
						gridStatus.innerHTML = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.nothing;
					}else{
						gridStatus.className = 'dt-grid-pager-status text-primary';
						var info = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.info;
						info = $.fn.DtGrid.tools.replaceAll(info, '{startRecord}', dtGridReflectionObj.pager.startRecord);
						info = $.fn.DtGrid.tools.replaceAll(info, '{nowPage}', dtGridReflectionObj.pager.nowPage);
						info = $.fn.DtGrid.tools.replaceAll(info, '{recordCount}', dtGridReflectionObj.pager.recordCount);
						info = $.fn.DtGrid.tools.replaceAll(info, '{pageCount}', dtGridReflectionObj.pager.pageCount);
						//设置每页数量的DOM对象
						var pageSizeElement = '';
						var pageSize = dtGridReflectionObj.pager.pageSize;
						//数组类型
						if(typeof dtGridReflectionObj.option.pageSizeLimit == 'object'){
							pageSizeElement += '<select id="dt_grid_change_page_size_'+dtGridReflectionObj.option.id+'" name="dt_grid_change_page_size_'+dtGridReflectionObj.option.id+'" class="form-control change-page-size">';
							//整合可用页码
							var isHasNowPageSize = false;
							var pageSizeLimit = new Array();
							for(var i=0; i<dtGridReflectionObj.option.pageSizeLimit.length; i++){
								var loopPageSize = dtGridReflectionObj.option.pageSizeLimit[i];
								pageSizeLimit.push(parseFloat(loopPageSize));
								if(pageSize==loopPageSize){
									isHasNowPageSize = true;
								}
							}
							if(!isHasNowPageSize){
								pageSizeLimit.push(pageSize);
							}
							//对当前页码内容排序
							pageSizeLimit.sort(function(s1, s2){
								return s1>s2;
							});
							//写入代码
							for(var i=0; i<pageSizeLimit.length; i++){
								var loopPageSize = pageSizeLimit[i];
								pageSizeElement += '	<option '+(pageSize==loopPageSize?'selected="selected"':'')+' value="'+loopPageSize+'">'+loopPageSize+'</option>';
							}
							pageSizeElement += '</select>';
							info = $.fn.DtGrid.tools.replaceAll(info, '{pageSize}', pageSizeElement);
						}else if(typeof dtGridReflectionObj.option.pageSizeLimit == 'number'){
							//如果是数值类型则为文本框
							pageSizeElement += '<input id="dt_grid_change_page_size_'+dtGridReflectionObj.option.id+'" name="dt_grid_change_page_size_'+dtGridReflectionObj.option.id+'" type="text" class="form-control change-page-size" value="'+dtGridReflectionObj.pager.pageSize+'" />';
							info = $.fn.DtGrid.tools.replaceAll(info, '{pageSize}', pageSizeElement);
						}else{
							info = $.fn.DtGrid.tools.replaceAll(info, '{pageSize}', pageSize);
						}
						gridStatus.innerHTML = info;
					}
					var operations = document.createElement('ul');
					operations.id = dtGridReflectionObj.option.id+'_dtGridOperations';
					operations.className = 'pagination pagination-sm dt-grid-pager-button';
					$('#'+dtGridReflectionObj.option.toolbarContainer+' .dt-grid-pager').append(operations);
					$('#'+dtGridReflectionObj.option.toolbarContainer+' .dt-grid-pager').append(gridStatus);
					//绑定修改每页显示条数的事件
					$('#dt_grid_change_page_size_'+dtGridReflectionObj.option.id).change(function(){
						var changePageSize = parseFloat(this.value);
						if(isNaN(changePageSize)){
							$.fn.DtGrid.tools.toast($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.errors.notANumber, 'warning', 3000);
							$('#dt_grid_change_page_size_'+dtGridReflectionObj.option.id).val(dtGridReflectionObj.pager.pageSize);
							return;
						}
						if(typeof dtGridReflectionObj.option.pageSizeLimit == 'number'){
							if(changePageSize>dtGridReflectionObj.option.pageSizeLimit){
								$.fn.DtGrid.tools.toast($.fn.DtGrid.tools.replaceAll($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.errors.maxPageSize, '{pageSizeLimit}', dtGridReflectionObj.option.pageSizeLimit), 'warning', 3000);
								$('#dt_grid_change_page_size_'+dtGridReflectionObj.option.id).val(dtGridReflectionObj.pager.pageSize);
								return;
							}
						}
						dtGridReflectionObj.pager.pageSize = changePageSize;
						dtGridReflectionObj.reload(true);
					});
					//处理分页按钮
					if(dtGridReflectionObj.exhibitDatas!=null&&dtGridReflectionObj.exhibitDatas.length>0){
						//第一页按钮
						var goFirstBtn = document.createElement('li');
						goFirstBtn.id = 'dt_grid_'+dtGridReflectionObj.option.id+'_page_first';
						goFirstBtn.title = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.firstPageTitle;
						if(nowPage<=1){
							goFirstBtn.className = 'disabled';
							goFirstBtn.title = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.alreadyFirstPage;
						}
						goFirstBtn.innerHTML = '<a href="javascript:void(0);">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.firstPage+'</a>';
						$('#'+dtGridReflectionObj.option.id+'_dtGridOperations').append(goFirstBtn);
						$('#dt_grid_'+dtGridReflectionObj.option.id+'_page_first').click(function(){
							dtGridReflectionObj.loadByPage('first');
						});
						//上一页按钮
						var goPrevBtn = document.createElement('li');
						goPrevBtn.id = 'dt_grid_'+dtGridReflectionObj.option.id+'_page_prev';
						goPrevBtn.title = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.prevPageTitle;
						if(nowPage<=1){
							goPrevBtn.className = 'disabled';
							goPrevBtn.title = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.alreadyFirstPage;
						}
						goPrevBtn.innerHTML = '<a href="javascript:void(0);">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.prevPage+'</a>';
						$('#'+dtGridReflectionObj.option.id+'_dtGridOperations').append(goPrevBtn);
						$('#dt_grid_'+dtGridReflectionObj.option.id+'_page_prev').click(function(){
							dtGridReflectionObj.loadByPage('prev');
						});
						//页面列表
						if(pageCount<=5){
							for(var i=1; i<=pageCount; i++){
								var goPageBtn = document.createElement('li');
								goPageBtn.id = 'dt_grid_'+dtGridReflectionObj.option.id+'_page_'+i;
								goPageBtn.setAttribute('page', i);
								goPageBtn.title = $.fn.DtGrid.tools.replaceAll($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.nowPageTitle, '{nowPage}', i);
								goPageBtn.className = i==nowPage?'active':'';
								goPageBtn.innerHTML = '<a href="javascript:void(0);">'+$.fn.DtGrid.tools.replaceAll($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.nowPage, '{nowPage}', i)+'</a>';
								$('#'+dtGridReflectionObj.option.id+'_dtGridOperations').append(goPageBtn);
								if(i!=nowPage){
									$('#dt_grid_'+dtGridReflectionObj.option.id+'_page_'+i).click(function(){
										dtGridReflectionObj.goPage($(this).attr('page'));
									});
								}
							}
						}else{
							//获取开始、结束号
							var before = 2;
							var after = 2;
							var start = (nowPage-before)<1?1:(nowPage-before);
							var end = (nowPage+after)>pageCount?pageCount:(nowPage+after);
							if((end-start+1)<(before+after+1)){
								if(start==1){
									end = end+((before+after+1)-(end-start+1));
									end = end>pageCount?pageCount:end;
								}
								if(end==pageCount){
									start = start-((before+after+1)-(end-start+1));
									start = start<1?1:start;
								}
							}
							for(var i=start; i<=end; i++){
								var goPageBtn = document.createElement('li');
								goPageBtn.id = 'dt_grid_'+dtGridReflectionObj.option.id+'_page_'+i;
								goPageBtn.setAttribute('page', i);
								goPageBtn.title = $.fn.DtGrid.tools.replaceAll($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.nowPageTitle, '{nowPage}', i);
								goPageBtn.className = i==nowPage?'active':'';
								goPageBtn.innerHTML = '<a href="javascript:void(0);">'+$.fn.DtGrid.tools.replaceAll($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.nowPage, '{nowPage}', i)+'</a>';
								$('#'+dtGridReflectionObj.option.id+'_dtGridOperations').append(goPageBtn);
								if(i!=nowPage){
									$('#dt_grid_'+dtGridReflectionObj.option.id+'_page_'+i).click(function(){
										dtGridReflectionObj.goPage($(this).attr('page'));
									});
								}
							}
						}
						var showPageText = document.createElement('li');
						showPageText.className = 'active';
						showPageText.innerHTML = '<a href="javascript:void(0);">'+dtGridReflectionObj.pager.nowPage+'</a>';
						//下一页按钮
						var goNextBtn = document.createElement('li');
						goNextBtn.id = 'dt_grid_'+dtGridReflectionObj.option.id+'_page_next';
						goNextBtn.title = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.nextPageTitle;
						if(nowPage>=pageCount){
							goNextBtn.className = 'disabled';
							goNextBtn.title = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.alreadyLastPage;
						}
						goNextBtn.innerHTML = '<a href="javascript:void(0);">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.nextPage+'</a>';
						$('#'+dtGridReflectionObj.option.id+'_dtGridOperations').append(goNextBtn);
						$('#dt_grid_'+dtGridReflectionObj.option.id+'_page_next').click(function(){
							dtGridReflectionObj.loadByPage('next');
						});
						//最后一页按钮
						var goLastBtn = document.createElement('li');
						goLastBtn.id = 'dt_grid_'+dtGridReflectionObj.option.id+'_page_last';
						goLastBtn.title = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.lastPageTitle;
						if(nowPage>=pageCount){
							goLastBtn.className = 'disabled';
							goLastBtn.title = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.alreadyLastPage;
						}
						goLastBtn.innerHTML = '<a href="javascript:void(0);">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].pageInfo.lastPage+'</a>';
						$('#'+dtGridReflectionObj.option.id+'_dtGridOperations').append(goLastBtn);
						$('#dt_grid_'+dtGridReflectionObj.option.id+'_page_last').click(function(){
							dtGridReflectionObj.loadByPage('last');
						});
					}
					//清除浮动
					$('#'+dtGridReflectionObj.option.toolbarContainer+' .dt-grid-pager').append('<div class="clearfix"></div>');
					$('#'+dtGridReflectionObj.option.toolbarContainer).append('<div class="clearfix"></div>');
				},
				//工具条基础内容
				toolbar : function(type){
					//映射表格对象
					var dtGridReflectionObj = this;
					//定义工具条内容
					var toolbars = {
						refresh : '<li title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.refreshTitle+'"><a href="javascript:void(0);" id="refreshGrid_replaceId">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.refresh+'</a></li>',
						faseQuery : '<li title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.faseQueryTitle+'"><a href="javascript:void(0);" id="faseQuery_replaceId">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.faseQuery+'</a></li>',
						advanceQuery : '<li title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.advanceQueryTitle+'"><a href="javascript:void(0);" id="advanceQuery_replaceId">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.advanceQuery+'</a></li>',
						exportExcel : '<li title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.exportExcelTitle+'"><a href="javascript:void(0);" id="exportExcelGrid_replaceId">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.exportExcel+'</a></li>',
						exportCsv : '<li title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.exportCsvTitle+'"><a href="javascript:void(0);" id="exportCsvGrid_replaceId">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.exportCsv+'</a></li>',
						exportPdf : '<li title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.exportPdfTitle+'"><a href="javascript:void(0);" id="exportPdfGrid_replaceId">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.exportPdf+'</a></li>',
						exportTxt : '<li title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.exportTxtTitle+'"><a href="javascript:void(0);" id="exportTxtGrid_replaceId">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.exportTxt+'</a></li>',
						print : '<li title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.printTitle+'"><a href="javascript:void(0);" id="printGrid_replaceId">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].toolbar.print+'</a></li>'
					};
					return toolbars[type];
				},
				//构建工具条
				constructGridToolBar : function(){
					//映射表格对象
					var dtGridReflectionObj = this;
					//清空工具条
					$('#'+dtGridReflectionObj.option.toolbarContainer).html('<span class="pagination pagination-sm dt-grid-tools"></span><span class="dt-grid-pager"></span>');
					//如果为空则不做任何操作
					if(dtGridReflectionObj.option.tools==undefined||dtGridReflectionObj.option.tools==null||dtGridReflectionObj.option.tools==''){
						return;
					}
					//遍历工具条定义列表
					var tools = dtGridReflectionObj.option.tools;
					for(var i=0; i<tools.split('|').length; i++){
						var tool = tools.split('|')[i];
						//处理刷新
						if(tool=='refresh'){
							var content = $.fn.DtGrid.tools.replaceAll(dtGridReflectionObj.toolbar('refresh'), 'replaceId', dtGridReflectionObj.option.id);
							$('#'+dtGridReflectionObj.option.toolbarContainer+' .dt-grid-tools').append(content);
							//绑定方法
							$('#refreshGrid_'+dtGridReflectionObj.option.id).click(function(){
								dtGridReflectionObj.reload(true);
							});
						}
						//处理快速查询
						if(tool=='faseQuery'){
							var content = $.fn.DtGrid.tools.replaceAll(dtGridReflectionObj.toolbar('faseQuery'), 'replaceId', dtGridReflectionObj.option.id);
							$('#'+dtGridReflectionObj.option.toolbarContainer+' .dt-grid-tools').append(content);
							//绑定方法
							$('#faseQuery_'+dtGridReflectionObj.option.id).click(function(){
								dtGridReflectionObj.faseQuery();
							});
						}
						//处理高级查询
						if(tool=='advanceQuery'){
							var content = $.fn.DtGrid.tools.replaceAll(dtGridReflectionObj.toolbar('advanceQuery'), 'replaceId', dtGridReflectionObj.option.id);
							$('#'+dtGridReflectionObj.option.toolbarContainer+' .dt-grid-tools').append(content);
							//绑定方法
							$('#advanceQuery_'+dtGridReflectionObj.option.id).click(function(){
								dtGridReflectionObj.advanceQuery();
							});
						}
						//处理导出
						if(tool.indexOf('export')!=-1){
							tool = $.fn.DtGrid.tools.replaceAll(tool, 'export', '');
							tool = $.fn.DtGrid.tools.replaceAll(tool, '\\[', '');
							tool = $.fn.DtGrid.tools.replaceAll(tool, '\\]', '');
							var content = '';
							for(var j=0; j<tool.split(',').length; j++){
								var exportTool = tool.split(',')[j];
								if(exportTool=='excel'){
									content += $.fn.DtGrid.tools.replaceAll(dtGridReflectionObj.toolbar('exportExcel'), 'replaceId', dtGridReflectionObj.option.id);
								}
								if(exportTool=='csv'){
									content += $.fn.DtGrid.tools.replaceAll(dtGridReflectionObj.toolbar('exportCsv'), 'replaceId', dtGridReflectionObj.option.id);
								}
								if(exportTool=='pdf'){
									content += $.fn.DtGrid.tools.replaceAll(dtGridReflectionObj.toolbar('exportPdf'), 'replaceId', dtGridReflectionObj.option.id);
								}
								if(exportTool=='txt'){
									content += $.fn.DtGrid.tools.replaceAll(dtGridReflectionObj.toolbar('exportTxt'), 'replaceId', dtGridReflectionObj.option.id);
								}
							}
							$('#'+dtGridReflectionObj.option.toolbarContainer+' .dt-grid-tools').append(content);
							//绑定方法
							for(var j=0; j<tool.split(',').length; j++){
								var exportTool = tool.split(',')[j];
								if(exportTool=='excel'){
									$('#exportExcelGrid_'+dtGridReflectionObj.option.id).click(function(){
										dtGridReflectionObj.exportFile('excel');
									});
								}
								if(exportTool=='csv'){
									$('#exportCsvGrid_'+dtGridReflectionObj.option.id).click(function(){
										dtGridReflectionObj.exportFile('csv');
									});
								}
								if(exportTool=='pdf'){
									$('#exportPdfGrid_'+dtGridReflectionObj.option.id).click(function(){
										dtGridReflectionObj.exportFile('pdf');
									});
								}
								if(exportTool=='txt'){
									$('#exportTxtGrid_'+dtGridReflectionObj.option.id).click(function(){
										dtGridReflectionObj.exportFile('txt');
									});
								}
							}
						}
						//处理打印
						if(tool=='print'){
							var content = $.fn.DtGrid.tools.replaceAll(dtGridReflectionObj.toolbar('print'), 'replaceId', dtGridReflectionObj.option.id);
							$('#'+dtGridReflectionObj.option.toolbarContainer+' .dt-grid-tools').append(content);
							//绑定方法
							$('#printGrid_'+dtGridReflectionObj.option.id).click(function(){
								dtGridReflectionObj.print();
							});
						}
					}
				},
				/**
				 * 构件表格内部使用方法
				 */
				//创建滚动条
				processBarThread : null,
				processWidth : null,
				showProcessBar : function(){
					var dtGridReflectionObj = this;
					clearInterval(dtGridReflectionObj.processBarThread);
					$('#dt_grid_process_bar_top_'+dtGridReflectionObj.option.id).remove();
					$('#dt_grid_process_bar_bottom_'+dtGridReflectionObj.option.id).remove();
					dtGridReflectionObj.processWidth = 0;
					dtGridReflectionObj.processBarThread = null;
					var content = '';
					content += '<div class="dt-grid-process-bar-top bg-primary" id="dt_grid_process_bar_top_'+dtGridReflectionObj.option.id+'"></div>';
					content += '<div class="dt-grid-process-bar-bottom bg-primary" id="dt_grid_process_bar_bottom_'+dtGridReflectionObj.option.id+'"></div>';
					$('#'+dtGridReflectionObj.option.gridContainer).append(content);
					dtGridReflectionObj.processWidth += Math.random()*(100-dtGridReflectionObj.processWidth)*0.1;
					$('#dt_grid_process_bar_top_'+dtGridReflectionObj.option.id).animate({width:dtGridReflectionObj.processWidth+'%'}, 200);
					$('#dt_grid_process_bar_bottom_'+dtGridReflectionObj.option.id).animate({width:dtGridReflectionObj.processWidth+'%'}, 200);
					dtGridReflectionObj.processBarThread = setInterval(function(){
						dtGridReflectionObj.processWidth += Math.random()*(100-dtGridReflectionObj.processWidth)*0.1;
						$('#dt_grid_process_bar_top_'+dtGridReflectionObj.option.id).animate({width:dtGridReflectionObj.processWidth+'%'}, 200);
						$('#dt_grid_process_bar_bottom_'+dtGridReflectionObj.option.id).animate({width:dtGridReflectionObj.processWidth+'%'}, 200);
					}, 200);
				},
				hideProcessBar : function(callback){
					var dtGridReflectionObj = this;
					clearInterval(dtGridReflectionObj.processBarThread);
					var dtGridReflectionObj = this;
					$('#dt_grid_process_bar_top_'+dtGridReflectionObj.option.id).animate({width:'100%'}, 100, function(){
						$('#dt_grid_process_bar_top_'+dtGridReflectionObj.option.id).remove();
						callback();
					});
					$('#dt_grid_process_bar_bottom_'+dtGridReflectionObj.option.id).animate({width:'100%'}, 100, function(){
						$('#dt_grid_process_bar_bottom_'+dtGridReflectionObj.option.id).remove();
					});
				},
				//格式化内容
				formatContent:function(column, value){
					try{
						//如果是codeTable
						if(column.codeTable){
							var codeTableValue = column.codeTable[value];
							return codeTableValue?codeTableValue:value;
						}
						//如果是number或date，则需要考虑格式化内容
						if(column.type=='number'&&column.format){
							return $.fn.DtGrid.tools.numberFormat(value, column.format);
						}
						if(column.type=='date'&&column.format){
							if(column.otype){
								if(column.otype=='time_stamp_s'){
									value = new Date(parseFloat(value)*1000);
									return $.fn.DtGrid.tools.dateFormat(value, column.format);
								}else if(column.otype=='time_stamp_ms'){
									value = new Date(parseFloat(value));
									return $.fn.DtGrid.tools.dateFormat(value, column.format);
								}else if(column.otype=='string'){
									if(column.oformat){
										value = $.fn.DtGrid.tools.toDate(value, column.oformat);
										return $.fn.DtGrid.tools.dateFormat(value, column.format);
									}
								}
							}else{
								return $.fn.DtGrid.tools.dateFormat(value, column.format);
							}
						}
					}catch(e){}
					return value;
				},
				//判断列首的下拉按钮列是否显示
				getExtraColumnClass : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					var extraColumnClass = '';
					var temp = [true, true, true, true];
					var columns = dtGridReflectionObj.option.columns;
					for(var i=0; i<columns.length; i++){
						var column = columns[i];
						if(column.hide!=true&&column.hideType!=undefined){
							var hideTypeArr = column.hideType.split('|');
							for(var j=0; j<hideTypeArr.length; j++){
								var type = hideTypeArr[j];
								if(type=='lg') temp[0]='visible-lg';
								if(type=='md') temp[1]='visible-md';
								if(type=='sm') temp[2]='visible-sm';
								if(type=='xs') temp[3]='visible-xs';
							}
						}
					}
					for(var i=0; i<temp.length; i++){
						if(temp[i]!=true) extraColumnClass += temp[i] + ' ';
					}
					if(extraColumnClass=='')
						extraColumnClass = 'hidden ';
					return extraColumnClass;
				},
				//获取显隐的列样式表
				getColumnClassForHide : function(column){
					var showClass = '';
					if(column.hide==true){
						showClass += 'hidden ';
						return showClass;
					}
					if(column.hideType!=undefined){
						var hideTypeArr = column.hideType.split('|');
						for(var i=0; i<hideTypeArr.length; i++){
							var type = hideTypeArr[i];
							if(type=='lg') showClass += 'hidden-lg ';
							if(type=='md') showClass += 'hidden-md ';
							if(type=='sm') showClass += 'hidden-sm ';
							if(type=='xs') showClass += 'hidden-xs ';
						}
					}
					return showClass;
				},
				//获取扩展显隐的列样式表
				getExtraColumnClassForHide : function(column){
					var showClass = '';
					if(column.hide==true){
						showClass += 'hidden ';
						return showClass;
					}
					if(column.hideType!=undefined){
						var hideTypeArr = column.hideType.split('|');
						for(var i=0; i<hideTypeArr.length; i++){
							var type = hideTypeArr[i];
							if(type=='lg') showClass += 'visible-lg ';
							if(type=='md') showClass += 'visible-md ';
							if(type=='sm') showClass += 'visible-sm ';
							if(type=='xs') showClass += 'visible-xs ';
						}
						if(showClass=='')
							showClass = 'hidden ';
					}else{
						showClass = 'hidden ';
					}
					return showClass;
				},
				//跳转页面
				goPage : function(_page){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					var pageSize = dtGridReflectionObj.pager.pageSize;
					var pageCount = dtGridReflectionObj.pager.pageCount;
					var page = parseFloat(_page);
					if(!isNaN(page)){
						if(0<page&&page<=pageCount){
							dtGridReflectionObj.pager.nowPage = page;
							dtGridReflectionObj.pager.startRecord = pageSize*(page-1);
							dtGridReflectionObj.load();
						}
						if(page<=0){
							dtGridReflectionObj.loadByPage('first');
						}
						if(page>pageCount){
							dtGridReflectionObj.loadByPage('last');
						}
					}
				},
				//根据页码重新加载，可配置参数：first、prev、next、last
				loadByPage : function(type){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					type = type?type:'';
					if(type=='first'){
						var nowPage = dtGridReflectionObj.pager.nowPage;
						if(nowPage>1){
							dtGridReflectionObj.pager.startRecord = 0;
							dtGridReflectionObj.pager.nowPage = 1;
							dtGridReflectionObj.load();
						}
					}else if(type=='prev'){
						var nowPage = dtGridReflectionObj.pager.nowPage;
						var pageSize = dtGridReflectionObj.pager.pageSize;
						if(nowPage>1){
							nowPage--;
							dtGridReflectionObj.pager.nowPage = nowPage;
							dtGridReflectionObj.pager.startRecord = pageSize*(nowPage-1);
							dtGridReflectionObj.load();
						}
					}else if(type=='next'){
						var nowPage = dtGridReflectionObj.pager.nowPage;
						var pageSize = dtGridReflectionObj.pager.pageSize;
						var pageCount = dtGridReflectionObj.pager.pageCount;
						if(nowPage<pageCount){
							nowPage++;
							dtGridReflectionObj.pager.nowPage = nowPage;
							dtGridReflectionObj.pager.startRecord = pageSize*(nowPage-1);
							dtGridReflectionObj.load();
						}
					}else if(type=='last'){
						var nowPage = dtGridReflectionObj.pager.nowPage;
						var pageCount = dtGridReflectionObj.pager.pageCount;
						if(nowPage<pageCount){
							var pageSize = dtGridReflectionObj.pager.pageSize;
							var pageCount = dtGridReflectionObj.pager.pageCount;
							dtGridReflectionObj.pager.nowPage = pageCount==0?1:pageCount;
							dtGridReflectionObj.pager.startRecord = pageSize*(pageCount-1);
							if(dtGridReflectionObj.pager.startRecord<0)
								dtGridReflectionObj.pager.startRecord = 0;
							dtGridReflectionObj.load();
						}
					}else{
						dtGridReflectionObj.load();
					}
				},
				/**
				 * 快速查询相关
				 */
				//执行快速查询
				faseQuery : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//如果已经初始化，则调用显示
					if(dtGridReflectionObj.init.fastQueryWindowIsInit){
						$('#faseQuery_'+dtGridReflectionObj.option.id+'Modal').modal('show');
						return;
					}
					var content = '';
					content += $.fn.DtGrid.tools.getWindowStart('faseQuery_'+dtGridReflectionObj.option.id, $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.title);
					content += '<form id="faseQueryForm_'+dtGridReflectionObj.option.id+'">';
					content += '	<div class="modal-body form-horizontal">';
					for(var j=0; j<dtGridReflectionObj.option.columns.length; j++){
						var column = dtGridReflectionObj.option.columns[j];
						if(column.fastQuery==true){
							content += '<div class="form-group">';
							content += '	<label class="col-sm-3 control-label text-right">'+column.title+'：</label>';
							if(column.fastQueryType=='range'){
								content += '<div class="col-sm-4">';
								if(column.type=='date'){
									content += '<div class="input-group">';
									content += '	<input type="text" class="form-control" id="ge_'+column.id+'" name="ge_'+column.id+'" format="'+column.format+'" placeholder="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.selectStart+column.title+'" onclick="WdatePicker({dateFmt:\''+$.fn.DtGrid.tools.replaceAll(column.format, 'h', 'H')+'\'});" />';
									content += '	<div class="input-group-addon"><i class="fa fa-calendar"></i></div>';
									content += '</div>';
								}else{
									content += '<input type="text" class="form-control" id="ge_'+column.id+'" name="ge_'+column.id+'" placeholder="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.inputStart+column.title+'" />';
								}
								content += '</div>';
								content += '<div class="col-sm-4">';
								if(column.type=='date'){
									content += '<div class="input-group">';
									content += '	<input type="text" class="form-control" id="le_'+column.id+'" name="le_'+column.id+'" format="'+column.format+'" placeholder="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.selectEnd+column.title+'" onclick="WdatePicker({dateFmt:\''+$.fn.DtGrid.tools.replaceAll(column.format, 'h', 'H')+'\'});" />';
									content += '	<div class="input-group-addon"><i class="fa fa-calendar"></i></div>';
									content += '</div>';
								}else{
									content += '<input type="text" class="form-control" id="le_'+column.id+'" name="le_'+column.id+'" placeholder="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.inputEnd+column.title+'" />';
								}
								content += '</div>';
							}else if(column.codeTable){
								content += '<div class="col-sm-4">';
								content += '	<select class="form-control" id="'+column.fastQueryType+'_'+column.id+'" name="'+column.fastQueryType+'_'+column.id+'">';
								content += '		<option value="">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.codeTableSelectAll+'</option>';
								for(var key in column.codeTable){
									content += '	<option value="'+key+'">'+column.codeTable[key]+'</option>';
								}
								content += '	</select>';
								content += '</div>';
							}else{
								content += '<div class="col-sm-4">';
								if(column.type=='date'){
									content += '<div class="input-group">';
									var hoderName = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.selectStart;
									if(column.fastQueryType=='lt'||column.fastQueryType=='le'){
										hoderName = $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.selectEnd;
									}
									content += '	<input type="text" class="form-control" id="'+column.fastQueryType+'_'+column.id+'" name="'+column.fastQueryType+'_'+column.id+'" format="'+column.format+'" placeholder="'+hoderName+column.title+'" onclick="WdatePicker({dateFmt:\''+$.fn.DtGrid.tools.replaceAll(column.format, 'h', 'H')+'\'});" />';
									content += '	<div class="input-group-addon"><i class="fa fa-calendar"></i></div>';
									content += '</div>';
								}else{
									content += '<input type="text" class="form-control" id="'+column.fastQueryType+'_'+column.id+'" name="'+column.fastQueryType+'_'+column.id+'" placeholder="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.input+column.title+'" />';
								}
								content += '</div>';
							}
							content += '</div>';
						}
					}
					content += '	</div>';
					content += '</form>';
					var buttons = '';
					buttons += '<button type="button" class="btn btn-warning" id="resetFaseQuery_'+dtGridReflectionObj.option.id+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.buttons.reset+'</button>';
					buttons += '<button type="button" class="btn btn-primary" id="doFaseQuery_'+dtGridReflectionObj.option.id+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].fastQuery.buttons.query+'</button>';
					content += $.fn.DtGrid.tools.getWindowEnd($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].buttons.close, buttons);
					$('body').append(content);
					//绑定方法
					$('#resetFaseQuery_'+dtGridReflectionObj.option.id).click(function(){
						document.forms['faseQueryForm_'+dtGridReflectionObj.option.id].reset();
					});
					$('#doFaseQuery_'+dtGridReflectionObj.option.id).click(function(){
						dtGridReflectionObj.doFaseQuery(document.forms['faseQueryForm_'+dtGridReflectionObj.option.id]);
						$('#faseQuery_'+dtGridReflectionObj.option.id+'Modal').modal('hide');
					});
					//显示快速查询
					$('#faseQuery_'+dtGridReflectionObj.option.id+'Modal').modal('show');
					//标识初始化完成
					dtGridReflectionObj.init.fastQueryWindowIsInit = true;
				},
				//快速查询方法
				doFaseQuery : function(form){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//清空快速查询中的参数
					dtGridReflectionObj.fastQueryParameters = new Object();
					//遍历获取快速查询参数
					var elements = form.elements;
					for(var i=0;i<elements.length;i++){
						var element = elements[i];
						if($.fn.DtGrid.tools.equalsIgnoreCase(element.type, 'text') 
								|| $.fn.DtGrid.tools.equalsIgnoreCase(element.type, 'hidden') 
								|| $.fn.DtGrid.tools.equalsIgnoreCase(element.type, 'textarea') 
								|| $.fn.DtGrid.tools.equalsIgnoreCase(element.type, 'select-one') 
								|| $.fn.DtGrid.tools.equalsIgnoreCase(element.type, 'password')){
							if (element.name != ""){
								dtGridReflectionObj.fastQueryParameters[element.name] = element.value;
								dtGridReflectionObj.fastQueryParameters[element.name+'_format'] = element.getAttribute('format');
							}
						}else if (($.fn.DtGrid.tools.equalsIgnoreCase(element.type, 'checkbox') || $.fn.DtGrid.tools.equalsIgnoreCase(element.type, 'radio')) && element.checked){
							if (element.name != ""){
								dtGridReflectionObj.fastQueryParameters[element.name] = element.value;
								dtGridReflectionObj.fastQueryParameters[element.name+'_format'] = element.getAttribute('format');
							}
						}
					}
					dtGridReflectionObj.load();
				},
				//快速查询数据过滤（仅限非ajax分页加载模式）
				doFaseQueryDatasFilter : function(originalDatas, fastQueryParameters){
					var returnDatas = new Array();
					for(var i=0; i<originalDatas.length; i++){
						var record = originalDatas[i];
						var isShow = true;
						for(var key in fastQueryParameters){
							if(fastQueryParameters[key]&&fastQueryParameters[key]!=null&&fastQueryParameters[key]!=''){
								if($.fn.DtGrid.tools.startsWith(key, 'eq_')){
									var value = record[key.replace('eq_', '')];
									value = value?value:'';
									if(value!=fastQueryParameters[key]){
										isShow = false;
										continue;
									}
								}
								if($.fn.DtGrid.tools.startsWith(key, 'ne_')){
									var value = record[key.replace('ne_', '')];
									value = value?value:'';
									if(value==fastQueryParameters[key]){
										isShow = false;
										continue;
									}
								}
								if($.fn.DtGrid.tools.startsWith(key, 'lk_')){
									var value = record[key.replace('lk_', '')];
									value = value?value:'';
									if(value.indexOf(fastQueryParameters[key])==-1){
										isShow = false;
										continue;
									}
								}
								if($.fn.DtGrid.tools.startsWith(key, 'll_')){
									var value = record[key.replace('ll_', '')];
									value = value?value:'';
									if(!$.fn.DtGrid.tools.startsWith(value, fastQueryParameters[key])){
										isShow = false;
										continue;
									}
								}
								if($.fn.DtGrid.tools.startsWith(key, 'rl_')){
									var value = record[key.replace('rl_', '')];
									value = value?value:'';
									if(!$.fn.DtGrid.tools.endsWith(value, fastQueryParameters[key])){
										isShow = false;
										continue;
									}
								}
								if($.fn.DtGrid.tools.startsWith(key, 'gt_')){
									var value = record[key.replace('gt_', '')];
									value = value?value:'';
									//日期比较
									if(value instanceof Date){
										if((value.getTime()-$.fn.DtGrid.tools.toDate(fastQueryParameters[key], fastQueryParameters[key+'_format']).getTime())<=0){
											isShow = false;
											continue;
										}
									}
									//数值比较
									if(!(value instanceof Date)&&!isNaN(value)){
										if((parseFloat(value) - parseFloat(fastQueryParameters[key]))<=0){
											isShow = false;
											continue;
										}
									}
									//普通比较
									if(!(value instanceof Date)&&isNaN(value)){
										if(value.localeCompare(fastQueryParameters[key])<=0){
											isShow = false;
											continue;
										}
									}
								}
								if($.fn.DtGrid.tools.startsWith(key, 'ge_')){
									var value = record[key.replace('ge_', '')];
									value = value?value:'';
									//日期比较
									if(value instanceof Date){
										if((value.getTime()-$.fn.DtGrid.tools.toDate(fastQueryParameters[key], fastQueryParameters[key+'_format']).getTime())<0){
											isShow = false;
											continue;
										}
									}
									//数值比较
									if(!(value instanceof Date)&&!isNaN(value)){
										if((parseFloat(value) - parseFloat(fastQueryParameters[key]))<0){
											isShow = false;
											continue;
										}
									}
									//普通比较
									if(!(value instanceof Date)&&isNaN(value)){
										if(value.localeCompare(fastQueryParameters[key])<0){
											isShow = false;
											continue;
										}
									}
								}
								if($.fn.DtGrid.tools.startsWith(key, 'lt_')){
									var value = record[key.replace('lt_', '')];
									value = value?value:'';
									//日期比较
									if(value instanceof Date){
										if((value.getTime()-$.fn.DtGrid.tools.toDate(fastQueryParameters[key], fastQueryParameters[key+'_format']).getTime())>=0){
											isShow = false;
											continue;
										}
									}
									//数值比较
									if(!(value instanceof Date)&&!isNaN(value)){
										if((parseFloat(value) - parseFloat(fastQueryParameters[key]))>=0){
											isShow = false;
											continue;
										}
									}
									//普通比较
									if(!(value instanceof Date)&&isNaN(value)){
										if(value.localeCompare(fastQueryParameters[key])>=0){
											isShow = false;
											continue;
										}
									}
								}
								if($.fn.DtGrid.tools.startsWith(key, 'le_')){
									var value = record[key.replace('le_', '')];
									value = value?value:'';
									//日期比较
									if(value instanceof Date){
										if((value.getTime()-$.fn.DtGrid.tools.toDate(fastQueryParameters[key], fastQueryParameters[key+'_format']).getTime())>0){
											isShow = false;
											continue;
										}
									}
									//数值比较
									if(!(value instanceof Date)&&!isNaN(value)){
										if((parseFloat(value) - parseFloat(fastQueryParameters[key]))>0){
											isShow = false;
											continue;
										}
									}
									//普通比较
									if(!(value instanceof Date)&&isNaN(value)){
										if(value.localeCompare(fastQueryParameters[key])>0){
											isShow = false;
											continue;
										}
									}
								}
							}
						}
						if(isShow){
							returnDatas.push(record);
						}
					}
					return returnDatas;
				},
				/**
				 * 高级查询相关
				 */
				//高级查询方法
				advanceQuery : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//如果已经初始化，则调用显示
					if(dtGridReflectionObj.init.advanceQueryWindowIsInit){
						$('#advanceQuery_'+dtGridReflectionObj.option.id+'Modal').modal('show');
						return;
					}
					//加载查询内容
					var content = '';
					content += $.fn.DtGrid.tools.getWindowStart('advanceQuery_'+dtGridReflectionObj.option.id, $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.title);
					content += '<div class="modal-body advance-query">';
					content += '	<ul class="nav nav-tabs" role="tablist">';
//					content += '		<li><a href="#advance_query_plan_'+dtGridReflectionObj.option.id+'" role="tab" data-toggle="tab">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.title+'</a></li>';
					content += '		<li class="active"><a href="#advance_query_condition_'+dtGridReflectionObj.option.id+'" role="tab" data-toggle="tab">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.title+'</a></li>';
					content += '		<li><a href="#advance_query_sort_'+dtGridReflectionObj.option.id+'" role="tab" data-toggle="tab">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.title+'</a></li>';
					content += '	</ul>';
					content += '	<div class="tab-content">';
//					content += '		<div class="tab-pane" id="advance_query_plan_'+dtGridReflectionObj.option.id+'">';
//					content += '			<div class="panel panel-default">';
//					content += '				<input type="hidden" id="advanceQueryId_'+dtGridReflectionObj.option.id+'" name="advanceQueryId_'+dtGridReflectionObj.option.id+'" />';
//					content += '				<div class="form-horizontal" style="padding-top:15px;">';
//					content += '					<div class="form-group">';
//					content += '						<label class="col-sm-3 control-label text-right">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.advanceQueryName+'</label>';
//					content += '						<div class="col-sm-6">';
//					content += '							<input type="text" class="form-control" id="advanceQueryName_'+dtGridReflectionObj.option.id+'" name="advanceQueryName_'+dtGridReflectionObj.option.id+'" placeholder="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.advanceQueryNamePlaceHoder+'">';
//					content += '						</div>';
//					content += '					</div>';
//					content += '					<div class="form-group">';
//					content += '						<label class="col-sm-3 control-label text-right">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.remark+'</label>';
//					content += '						<div class="col-sm-6">';
//					content += '							<textarea id="remark_'+dtGridReflectionObj.option.id+'" name="remark_'+dtGridReflectionObj.option.id+'" class="form-control" placeholder="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.remarkPlaceHoder+'"></textarea>';
//					content += '						</div>';
//					content += '					</div>';
//					content += '					<div class="form-group">';
//					content += '						<div class="col-sm-offset-3 col-sm-12">';
//					content += '							<button id="addAdvanceQuery_'+dtGridReflectionObj.option.id+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.buttons.addAdvanceQueryTitle+'" class="btn btn-xs btn-primary">';
//					content += '								'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.buttons.addAdvanceQuery;
//					content += '							</button>';
//					content += '							<button id="editAdvanceQuery_'+dtGridReflectionObj.option.id+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.buttons.editAdvanceQueryTitle+'" class="btn btn-xs btn-primary">';
//					content += '								'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.buttons.editAdvanceQuery;
//					content += '							</button>';
//					content += '							<button id="copyAdvanceQuery_'+dtGridReflectionObj.option.id+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.buttons.copyAdvanceQueryTitle+'" class="btn btn-xs btn-warning">';
//					content += '								'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.buttons.copyAdvanceQuery;
//					content += '							</button>';
//					content += '							<button id="deleteAdvanceQuery_'+dtGridReflectionObj.option.id+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.buttons.deleteAdvanceQueryTitle+'" class="btn btn-xs btn-danger">';
//					content += '								'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.plan.buttons.deleteAdvanceQuery;
//					content += '							</button>';
//					content += '						</div>';
//					content += '					</div>';
//					content += '				</div>';
//					content += '			</div>';
//					content += '		</div>';
					content += '		<div class="tab-pane active" id="advance_query_condition_'+dtGridReflectionObj.option.id+'">';
					content += '			<div class="panel panel-default">';
					content += '				<div class="form-horizontal text-right advance-query-buttons">';
					content += '					<button id="insertConditionTr_'+dtGridReflectionObj.option.id+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.buttons.insertTitle+'" class="btn btn-xs btn-primary">';
					content += '						'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.buttons.insert;
					content += '					</button>';
					content += '					<button id="clearConditionTr_'+dtGridReflectionObj.option.id+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.buttons.clearTitle+'" class="btn btn-xs btn-danger">';
					content += '						'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.buttons.clear;
					content += '					</button>';
					content += '				</div>';
					content += '				<div class="advance-query-table-container">';
					content += '					<table id="conditionTable_'+dtGridReflectionObj.option.id+'" class="table table-bordered table-striped table-hover table-condition table-center" style="width:860px;">';
					content += '						<tr>';
					content += '							<th style="width:50px;">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.sequence+'</th>';
					content += '							<th style="width:100px;">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.operation+'</th>';
					content += '							<th style="width:80px;">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.leftParentheses+'</th>';
					content += '							<th style="width:140px;">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.field+'</th>';
					content += '							<th style="width:120px;">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.condition+'</th>';
					content += '							<th>'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.value+'</th>';
					content += '							<th style="width:80px;">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.rightParentheses+'</th>';
					content += '							<th style="width:90px;">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.logic+'</th>';
					content += '						</tr>';
					content += '					</table>';
					content += '				</div>';
					content += '				<div class="clearfix"></div>';
					content += '			</div>';
					content += '		</div>';
					content += '		<div class="tab-pane" id="advance_query_sort_'+dtGridReflectionObj.option.id+'">';
					content += '			<div class="panel panel-default">';
					content += '				<div class="form-horizontal text-right advance-query-buttons">';
					content += '					<button id="insertSortTr_'+dtGridReflectionObj.option.id+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.buttons.insertTitle+'" class="btn btn-xs btn-primary">';
					content += '						'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.buttons.insert;
					content += '					</button>';
					content += '					<button id="clearSortTr_'+dtGridReflectionObj.option.id+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.buttons.clearTitle+'" class="btn btn-xs btn-danger">';
					content += '						'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.buttons.clear;
					content += '					</button>';
					content += '				</div>';
					content += '				<div class="advance-query-table-container">';
					content += '					<table id="sortTable_'+dtGridReflectionObj.option.id+'" class="table table-bordered table-striped table-hover table-condition table-center">';
					content += '						<tr>';
					content += '							<th>'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.table.sequence+'</th>';
					content += '							<th>'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.table.operation+'</th>';
					content += '							<th>'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.table.field+'</th>';
					content += '							<th>'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.table.logic+'</th>';
					content += '						</tr>';
					content += '					</table>';
					content += '				</div>';
					content += '				<div class="clearfix"></div>';
					content += '			</div>';
					content += '		</div>';
					content += '	</div>';
					content += '</div>';
					var buttons = '';
					buttons += '<button type="button" class="btn btn-primary" id="doAdvanceQuery_'+dtGridReflectionObj.option.id+'">';
					buttons += '	'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.buttons.query;
					buttons += '</button>';
					content += $.fn.DtGrid.tools.getWindowEnd($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].buttons.close, buttons);
					$('body').append(content);
//					//新增高级查询方案
//					$('#insertConditionTr_'+dtGridReflectionObj.option.id).click(function(){
//					});
//					//编辑高级查询方案
//					$('#editConditionTr_'+dtGridReflectionObj.option.id).click(function(){
//					});
//					//赋值高级查询方案
//					$('#copyConditionTr_'+dtGridReflectionObj.option.id).click(function(){
//					});
//					//删除高级查询方案
//					$('#deleteConditionTr_'+dtGridReflectionObj.option.id).click(function(){
//					});
					//新增一行查询条件
					$('#insertConditionTr_'+dtGridReflectionObj.option.id).click(function(){
						dtGridReflectionObj.insertConditionTr();
					});
					//清空查询条件
					$('#clearConditionTr_'+dtGridReflectionObj.option.id).click(function(){
						dtGridReflectionObj.clearConditionTr();
					});
					//新增一行排序条件
					$('#insertSortTr_'+dtGridReflectionObj.option.id).click(function(){
						dtGridReflectionObj.insertSortTr();
					});
					//清空排序条件
					$('#clearSortTr_'+dtGridReflectionObj.option.id).click(function(){
						dtGridReflectionObj.clearSortTr();
					});
					//绑定方法：执行高级查询
					$('#doAdvanceQuery_'+dtGridReflectionObj.option.id).click(function(){
						dtGridReflectionObj.doAdvanceQuery();
					});
					//显示高级查询
					$('#advanceQuery_'+dtGridReflectionObj.option.id+'Modal').modal('show');
					//标识初始化完成
					dtGridReflectionObj.init.advanceQueryWindowIsInit = true;
				},
				//高级查询执行
				doAdvanceQuery : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//校验
					var pass = true;
					var advanceQueryConditionList = new Array();
					$('#conditionTable_'+dtGridReflectionObj.option.id+' tr[id*=tr_'+dtGridReflectionObj.option.id+'_]').each(function(){
						var id = this.id.split('_')[2];
						var seq = $('#conditionTable_'+dtGridReflectionObj.option.id+' #seqTd_'+dtGridReflectionObj.option.id+'_'+id).html();
						var leftParentheses = $('#conditionTable_'+dtGridReflectionObj.option.id+' #leftParentheses_'+dtGridReflectionObj.option.id+'_'+id).val();
						var field = $('#conditionTable_'+dtGridReflectionObj.option.id+' #field_'+dtGridReflectionObj.option.id+'_'+id).val();
						var format = $('#conditionTable_'+dtGridReflectionObj.option.id+' #format_'+dtGridReflectionObj.option.id+'_'+id).val();
						var condition = $('#conditionTable_'+dtGridReflectionObj.option.id+' #condition_'+dtGridReflectionObj.option.id+'_'+id).val();
						var value = $('#conditionTable_'+dtGridReflectionObj.option.id+' #value_'+dtGridReflectionObj.option.id+'_'+id).val();
						var rightParentheses = $('#conditionTable_'+dtGridReflectionObj.option.id+' #rightParentheses_'+dtGridReflectionObj.option.id+'_'+id).val();
						var logic = $('#conditionTable_'+dtGridReflectionObj.option.id+' #logic_'+dtGridReflectionObj.option.id+'_'+id).val();
						if(field==''){
							$.fn.DtGrid.tools.toast($.fn.DtGrid.tools.replaceAll($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.errors.fieldMustSelect, '{sequence}', seq), 'warning', 3000);
							pass = false;
							return false;
						}
						if(condition==''){
							$.fn.DtGrid.tools.toast($.fn.DtGrid.tools.replaceAll($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.errors.conditionMustSelect, '{sequence}', seq), 'warning', 3000);
							pass = false;
							return false;
						}
						var advanceQueryCondition = new Object();
						advanceQueryCondition.id = id;
						advanceQueryCondition.seq = seq;
						advanceQueryCondition.leftParentheses = leftParentheses;
						advanceQueryCondition.field = field;
						advanceQueryCondition.format = format;
						advanceQueryCondition.condition = condition;
						advanceQueryCondition.value = value;
						advanceQueryCondition.rightParentheses = rightParentheses;
						advanceQueryCondition.logic = logic;
						advanceQueryConditionList.push(advanceQueryCondition);
					});
					var advanceQuerySortList = new Array();
					$('#sortTable_'+dtGridReflectionObj.option.id+' tr[id*=tr_'+dtGridReflectionObj.option.id+'_]').each(function(){
						var id = this.id.split('_')[2];
						var seq = $('#sortTable_'+dtGridReflectionObj.option.id+' #seqTd_'+dtGridReflectionObj.option.id+'_'+id).html();
						var sortField = $('#sortTable_'+dtGridReflectionObj.option.id+' #sortField_'+dtGridReflectionObj.option.id+'_'+id).val();
						var sortLogic = $('#sortTable_'+dtGridReflectionObj.option.id+' #sortLogic_'+dtGridReflectionObj.option.id+'_'+id).val();
						if(sortField==''){
							$.fn.DtGrid.tools.toast($.fn.DtGrid.tools.replaceAll($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.errors.fieldMustSelect, '{sequence}', seq), 'warning', 3000);
							pass = false;
							return false;
						}
						if(sortLogic==''){
							$.fn.DtGrid.tools.toast($.fn.DtGrid.tools.replaceAll($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.errors.logicMustSelect, '{sequence}', seq), 'warning', 3000);
							pass = false;
							return false;
						}
						var advanceQuerySort = new Object();
						advanceQuerySort.field = sortField;
						advanceQuerySort.logic = sortLogic;
						advanceQuerySortList.push(advanceQuerySort);
					});
					if(pass){
						dtGridReflectionObj.advanceQueryParameter = new Object();
						dtGridReflectionObj.advanceQueryParameter.advanceQueryConditions = advanceQueryConditionList;
						dtGridReflectionObj.advanceQueryParameter.advanceQuerySorts = advanceQuerySortList;
						dtGridReflectionObj.load();
						$('#advanceQuery_'+dtGridReflectionObj.option.id+'Modal').modal('hide');
					}
				},
				//高级查询数据过滤（仅限非ajax及全部加载模式）
				doAdvanceQueryDatasFilter : function(originalDatas, advanceQueryParameter){
					var returnDatas = new Array();
					for(var i=0; i<originalDatas.length; i++){
						var record = originalDatas[i];
						if(advanceQueryParameter.advanceQueryConditions&&advanceQueryParameter.advanceQueryConditions.length>0){
							var condition = '';
							for(var j=0; j<advanceQueryParameter.advanceQueryConditions.length; j++){
								var advanceQueryCondition = advanceQueryParameter.advanceQueryConditions[j];
								condition += advanceQueryCondition.leftParentheses;
								if(advanceQueryCondition.condition=='0'){
									condition += 'record["'+advanceQueryCondition.field+'"]=="'+advanceQueryCondition.value+'"';
								}
								if(advanceQueryCondition.condition=='1'){
									condition += 'record["'+advanceQueryCondition.field+'"]!="'+advanceQueryCondition.value+'"';
								}
								if(advanceQueryCondition.condition=='2'){
									condition += 'record["'+advanceQueryCondition.field+'"].indexOf("'+advanceQueryCondition.value+'")!=-1';
								}
								if(advanceQueryCondition.condition=='3'){
									condition += '$.fn.DtGrid.tools.startsWith(record["'+advanceQueryCondition.field+'"], "'+advanceQueryCondition.value+'")';
								}
								if(advanceQueryCondition.condition=='4'){
									condition += '$.fn.DtGrid.tools.endsWith(record["'+advanceQueryCondition.field+'"], "'+advanceQueryCondition.value+'")';
								}
								if(advanceQueryCondition.condition=='5'){
									var value = record[advanceQueryCondition.field];
									//日期比较
									if(value instanceof Date){
										condition += '(record["'+advanceQueryCondition.field+'"].getTime()-$.fn.DtGrid.tools.toDate("'+advanceQueryCondition.value+'", "'+advanceQueryCondition.format+'").getTime())>0';
									}
									//数值比较
									if(!(value instanceof Date)&&!isNaN(value)){
										condition += '(parseFloat(record["'+advanceQueryCondition.field+'"]) - parseFloat("'+advanceQueryCondition.value+'"))>0';
									}
									//普通比较
									if(!(value instanceof Date)&&isNaN(value)){
										condition += 'record["'+advanceQueryCondition.field+'"].localeCompare("'+advanceQueryCondition.value+'")>0';
									}
								}
								if(advanceQueryCondition.condition=='6'){
									var value = record[advanceQueryCondition.field];
									//日期比较
									if(value instanceof Date){
										condition += '(record["'+advanceQueryCondition.field+'"].getTime()-$.fn.DtGrid.tools.toDate("'+advanceQueryCondition.value+'", "'+advanceQueryCondition.format+'").getTime())>=0';
									}
									//数值比较
									if(!(value instanceof Date)&&!isNaN(value)){
										condition += '(parseFloat(record["'+advanceQueryCondition.field+'"]) - parseFloat("'+advanceQueryCondition.value+'"))>=0';
									}
									//普通比较
									if(!(value instanceof Date)&&isNaN(value)){
										condition += 'record["'+advanceQueryCondition.field+'"].localeCompare("'+advanceQueryCondition.value+'")>=0';
									}
								}
								if(advanceQueryCondition.condition=='7'){
									var value = record[advanceQueryCondition.field];
									//日期比较
									if(value instanceof Date){
										condition += '(record["'+advanceQueryCondition.field+'"].getTime()-$.fn.DtGrid.tools.toDate("'+advanceQueryCondition.value+'", "'+advanceQueryCondition.format+'").getTime())<0';
									}
									//数值比较
									if(!(value instanceof Date)&&!isNaN(value)){
										condition += '(parseFloat(record["'+advanceQueryCondition.field+'"]) - parseFloat("'+advanceQueryCondition.value+'"))<0';
									}
									//普通比较
									if(!(value instanceof Date)&&isNaN(value)){
										condition += 'record["'+advanceQueryCondition.field+'"].localeCompare("'+advanceQueryCondition.value+'")<0';
									}
								}
								if(advanceQueryCondition.condition=='8'){
									var value = record[advanceQueryCondition.field];
									//日期比较
									if(value instanceof Date){
										condition += '(record["'+advanceQueryCondition.field+'"].getTime()-$.fn.DtGrid.tools.toDate("'+advanceQueryCondition.value+'", "'+advanceQueryCondition.format+'").getTime())<=0';
									}
									//数值比较
									if(!(value instanceof Date)&&!isNaN(value)){
										condition += '(parseFloat(record["'+advanceQueryCondition.field+'"]) - parseFloat("'+advanceQueryCondition.value+'"))<=0';
									}
									//普通比较
									if(!(value instanceof Date)&&isNaN(value)){
										condition += 'record["'+advanceQueryCondition.field+'"].localeCompare("'+advanceQueryCondition.value+'")<=0';
									}
								}
								if(advanceQueryCondition.condition=='9'){
									condition += '!record["'+advanceQueryCondition.field+'"]';
								}
								if(advanceQueryCondition.condition=='10'){
									condition += 'record["'+advanceQueryCondition.field+'"]';
								}
								condition += advanceQueryCondition.rightParentheses;
								if(advanceQueryCondition.logic=='0'){
									condition += '&&';
								}
								if(advanceQueryCondition.logic=='1'){
									condition += '||';
								}
							}
							try{
								if(eval(condition)){
									returnDatas.push(record);
								}
							}catch(e){
								$.fn.DtGrid.tools.toast($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.errors.conditionError, 'error', 3000);
								return originalDatas;
							}
						}else{
							returnDatas.push(record);
						}
					}
					//处理排序
					if(advanceQueryParameter.advanceQuerySorts&&advanceQueryParameter.advanceQuerySorts.length>0){
						returnDatas = returnDatas.sort(function(record1, record2){
							for(var i=0; i<advanceQueryParameter.advanceQuerySorts.length; i++){
								var advanceQuerySort = advanceQueryParameter.advanceQuerySorts[i];
								var value1 = record1[advanceQuerySort.field];
								var value2 = record2[advanceQuerySort.field];
								if(value1&&value2){
									//如果相同则比较下一个
									if(value1==value2){
										continue;
									}
									//数值比较
									if(!isNaN(value1)&&!isNaN(value2)){
										if(advanceQuerySort.logic=='0'){
											return value1-value2;
										}
										if(advanceQuerySort.logic=='1'){
											return value2-value1;
										}
									}
									//日期比较
									if(value1 instanceof Date&&value2 instanceof Date){
										if(advanceQuerySort.logic=='0'){
											return value1.getTime()-value2.getTime();
										}
										if(advanceQuerySort.logic=='1'){
											return value2.getTime()-value1.getTime();
										}
									}
									//普通比较
									if(advanceQuerySort.logic=='0'){
										return value1.localeCompare(value2);
									}
									if(advanceQuerySort.logic=='1'){
										return value2.localeCompare(value1);
									}
								}
							}
							return 0;
						});
					}
					return returnDatas;
				},
				//查询条件序列号
				sequenceCondition : 0,
				//新增一行查询条件
				insertConditionTr : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//处理内容
					var content = '';
					content += '<tr id="tr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'">';
					content += '	<td id="seqTd_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'"></td>';
					content += '	<td>';
					content += '		<button type="button" id="moveUpConditionTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" class="btn btn-primary btn-xs" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.buttons.upTitle+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.buttons.up+'</button>';
					content += '		<button type="button" id="moveDownConditionTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" class="btn btn-primary btn-xs" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.buttons.downTitle+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.buttons.down+'</button>';
					content += '		<button type="button" id="deleteConditionTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" class="btn btn-danger btn-xs" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.buttons.deleteTitle+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.condition.table.buttons['delete']+'</button>';
					content += '	</td>';
					content += '	<td>';
					content += '		<input type="text" id="leftParentheses_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" name="leftParentheses_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" class="form-control" />';
					content += '	</td>';
					content += '	<td>';
					content += '		<select id="field_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" name="field_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" class="form-control">';
					content += '			<option value=""></option>';
					for(var i=0; i<dtGridReflectionObj.option.columns.length; i++){
						var column = dtGridReflectionObj.option.columns[i];
						if(column.advanceQuery!=false){
							content += '	<option value="'+column.id+'">'+column.title+'</option>';
						}
					}
					content += '		</select>';
					content += '		<input type="hidden" id="format_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" name="format_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" />';
					content += '	</td>';
					content += '	<td>';
					content += '		<select id="condition_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" name="condition_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" class="form-control">';
					content += '			<option value=""></option>';
					content += '			<option value="0">=</option>';
					content += '			<option value="1">!=</option>';
					content += '			<option value="2">like</option>';
					content += '			<option value="3">start with</option>';
					content += '			<option value="4">end with</option>';
					content += '			<option value="5">&gt;</option>';
					content += '			<option value="6">&gt;=</option>';
					content += '			<option value="7">&lt;</option>';
					content += '			<option value="8">&lt;=</option>';
					content += '			<option value="9">is null</option>';
					content += '			<option value="10">is not null</option>';
					content += '		</select>';
					content += '	</td>';
					content += '	<td id="valueTd_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'"></td>';
					content += '	<td>';
					content += '		<input type="text" id="rightParentheses_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" name="rightParentheses_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" class="form-control" />';
					content += '	</td>';
					content += '	<td>';
					content += '		<select id="logic_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" name="logic_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition+'" class="form-control">';
					content += '			<option value=""></option>';
					content += '			<option value="0">and</option>';
					content += '			<option value="1">or</option>';
					content += '		</select>';
					content += '	</td>';
					content += '</tr>';
					$('#conditionTable_'+dtGridReflectionObj.option.id).append(content);
					//绑定方法-上移
					$('#moveUpConditionTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition).click(function(){
						var sequenceCondition = this.id.split('_')[2];
						dtGridReflectionObj.moveUpConditionTr(sequenceCondition);
					});
					//绑定方法-下移
					$('#moveDownConditionTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition).click(function(){
						var sequenceCondition = this.id.split('_')[2];
						dtGridReflectionObj.moveDownConditionTr(sequenceCondition);
					});
					//绑定方法-删除
					$('#deleteConditionTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition).click(function(){
						var sequenceCondition = this.id.split('_')[2];
						dtGridReflectionObj.deleteConditionTr(sequenceCondition);
					});
					//绑定方法-左括号
					$('#leftParentheses_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition).keyup(function(){
						var sequenceCondition = this.id.split('_')[2];
						dtGridReflectionObj.validLeftParentheses(sequenceCondition);
					});
					//绑定方法-字段变更
					$('#field_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition).change(function(){
						var sequenceCondition = this.id.split('_')[2];
						dtGridReflectionObj.fieldChange(sequenceCondition);
					});
					//绑定方法-条件变更
					$('#condition_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition).change(function(){
						var sequenceCondition = this.id.split('_')[2];
						dtGridReflectionObj.conditionChange(sequenceCondition);
					});
					//绑定方法-右括号
					$('#rightParentheses_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceCondition).keyup(function(){
						var sequenceCondition = this.id.split('_')[2];
						dtGridReflectionObj.validRightParentheses(sequenceCondition);
					});
					dtGridReflectionObj.sequenceCondition++;
					dtGridReflectionObj.resetConditionSeq();
				},
				//清除所有行
				clearConditionTr : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					$('#conditionTable_'+dtGridReflectionObj.option.id+' tr[id*=tr_'+dtGridReflectionObj.option.id+'_]').remove();
				},
				//序列号重置
				resetConditionSeq : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					var seq = 1;
					$('#conditionTable_'+dtGridReflectionObj.option.id+' tr[id*=tr_'+dtGridReflectionObj.option.id+'_]').each(function(){
						var id = this.id.split('_')[2];
						$('#conditionTable_'+dtGridReflectionObj.option.id+' #seqTd_'+dtGridReflectionObj.option.id+'_'+id).html(seq);
						seq++;
					});
				},
				//上移
				moveUpConditionTr : function(id){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					$('#conditionTable_'+dtGridReflectionObj.option.id+' #tr_'+dtGridReflectionObj.option.id+'_'+id).prev('#conditionTable_'+dtGridReflectionObj.option.id+' tr[id*=tr_'+dtGridReflectionObj.option.id+'_]').before($('#conditionTable_'+dtGridReflectionObj.option.id+' #tr_'+dtGridReflectionObj.option.id+'_'+id));
					dtGridReflectionObj.resetConditionSeq();
				},
				//下移
				moveDownConditionTr : function(id){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					$('#conditionTable_'+dtGridReflectionObj.option.id+' #tr_'+dtGridReflectionObj.option.id+'_'+id).next('#conditionTable_'+dtGridReflectionObj.option.id+' tr[id*=tr_'+dtGridReflectionObj.option.id+'_]').after($('#conditionTable_'+dtGridReflectionObj.option.id+' #tr_'+dtGridReflectionObj.option.id+'_'+id));
					dtGridReflectionObj.resetConditionSeq();
				},
				//删除当前行
				deleteConditionTr : function(id){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					$('#conditionTable_'+dtGridReflectionObj.option.id+' #tr_'+dtGridReflectionObj.option.id+'_'+id).remove();
					dtGridReflectionObj.resetConditionSeq();
				},
				//左括号的验证函数
				validLeftParentheses : function(id){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//执行验证
					var value = $('#leftParentheses_'+dtGridReflectionObj.option.id+'_'+id).val();
					if(!value.match(/^[\(]*$/g)){
						value = $.fn.DtGrid.tools.replaceAll(value, '（', '(');
						value = value.replace(/[^\(]/g, '');
						$('#leftParentheses_'+dtGridReflectionObj.option.id+'_'+id).val(value);
					}
				},
				//列变更触发
				fieldChange : function(id){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//获取当前的值
					var field = $('#field_'+dtGridReflectionObj.option.id+'_'+id).val();
					//如果为空或条件一栏为9-10则滞空值栏
					var condition = $('#condition_'+dtGridReflectionObj.option.id+'_'+id).val();
					if(field=='' || condition=='9' || condition=='10'){
						$('#valueTd_'+dtGridReflectionObj.option.id+'_'+id).html('');
						return;
					}
					//获取列对象
					var column = new Object();
					for(var i=0; i<dtGridReflectionObj.option.columns.length; i++){
						if(dtGridReflectionObj.option.columns[i].id==field&&dtGridReflectionObj.option.columns[i].advanceQuery!=false){
							column = dtGridReflectionObj.option.columns[i];
							break;
						}
					}
					//处理日期类型
					if(column.type=='date'){
						var content = '';
						content += '<div class="input-group">';
						content += '	<input id="value_'+dtGridReflectionObj.option.id+'_'+id+'" name="value_'+dtGridReflectionObj.option.id+'_'+id+'" class="form-control" onclick="WdatePicker({dateFmt:\''+$.fn.DtGrid.tools.replaceAll(column.format, 'h', 'H')+'\'})" />';
						content += '	<div class="input-group-addon"><i class="fa fa-calendar"></i></div>';
						content += '</div>';
						$('#valueTd_'+dtGridReflectionObj.option.id+'_'+id).html(content);
						$('#format_'+dtGridReflectionObj.option.id+'_'+id).val(column.format);
						return;
					}
					//处理codeTable内容
					if(column.codeTable){
						var content = '';
						content += '<select id="value_'+dtGridReflectionObj.option.id+'_'+id+'" name="value_'+dtGridReflectionObj.option.id+'_'+id+'" class="form-control">';
						content += '	<option value=""></option>';
						for(var key in column.codeTable){
							content += '<option value="'+key+'">'+column.codeTable[key]+'</option>';
						}
						content += '</select>';
						$('#valueTd_'+dtGridReflectionObj.option.id+'_'+id).html(content);
						return;
					}
					//其他则为默认文本
					var content = '<input id="value_'+dtGridReflectionObj.option.id+'_'+id+'" name="value_'+dtGridReflectionObj.option.id+'_'+id+'" class="form-control" />';
					$('#valueTd_'+dtGridReflectionObj.option.id+'_'+id).html(content);
				},
				//条件触发
				conditionChange : function(id){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					var condition = $('#condition_'+dtGridReflectionObj.option.id+'_'+id).val();
					if(condition == '9' || condition == '10'){
						$('#valueTd_'+dtGridReflectionObj.option.id+'_'+id).html('');
					}else{
						var valueHtml = $('#valueTd_'+dtGridReflectionObj.option.id+'_'+id).html();
						if(valueHtml==''){
							dtGridReflectionObj.fieldChange(id);
						}
					}
				},
				//右括号的验证函数
				validRightParentheses : function(id){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					var value = $('#rightParentheses_'+dtGridReflectionObj.option.id+'_'+id).val();
					if(!value.match(/^[\(]*$/g)){
						value = $.fn.DtGrid.tools.replaceAll(value, '）', ')');
						value = value.replace(/[^\)]/g, '');
						$('#rightParentheses_'+dtGridReflectionObj.option.id+'_'+id).val(value);
					}
				},
				//排序条件序列号
				sequenceSort : 0,
				//插入一行排序条件
				insertSortTr : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					//处理内容
					var content = '';
					content += '<tr id="tr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort+'">';
					content += '	<td id="seqTd_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort+'"></td>';
					content += '	<td>';
					content += '		<button type="button" id="moveUpSortTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort+'" class="btn btn-primary btn-xs" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.table.buttons.upTitle+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.table.buttons.up+'</button>';
					content += '		<button type="button" id="moveDownSortTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort+'" class="btn btn-primary btn-xs" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.table.buttons.downTitle+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.table.buttons.down+'</button>';
					content += '		<button type="button" id="deleteSortTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort+'" class="btn btn-danger btn-xs" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.table.buttons.deleteTitle+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.table.buttons['delete']+'</button>';
					content += '	</td>';
					content += '	<td>';
					content += '		<select id="sortField_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort+'" name="sortField_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort+'" class="form-control">';
					content += '			<option value=""></option>';
					for(var i=0; i<dtGridReflectionObj.option.columns.length; i++){
						var column = dtGridReflectionObj.option.columns[i];
						if(column.advanceQuery!=false){
							content += '	<option value="'+column.id+'">'+column.title+'</option>';
						}
					}
					content += '		</select>';
					content += '	</td>';
					content += '	<td>';
					content += '		<select id="sortLogic_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort+'" name="sortLogic_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort+'" class="form-control">';
					content += '			<option value=""></option>';
					content += '			<option value="0">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.logic.asc+'</option>';
					content += '			<option value="1">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].advanceQuery.sort.logic.desc+'</option>';
					content += '		</select>';
					content += '	</td>';
					content += '</tr>';
					$('#sortTable_'+dtGridReflectionObj.option.id).append(content);
					//绑定方法-上移
					$('#moveUpSortTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort).click(function(){
						var sequenceSort = this.id.split('_')[2];
						dtGridReflectionObj.moveUpSortTr(sequenceSort);
					});
					//绑定方法-下移
					$('#moveDownSortTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort).click(function(){
						var sequenceSort = this.id.split('_')[2];
						dtGridReflectionObj.moveDownSortTr(sequenceSort);
					});
					//绑定方法-删除
					$('#deleteSortTr_'+dtGridReflectionObj.option.id+'_'+dtGridReflectionObj.sequenceSort).click(function(){
						var sequenceSort = this.id.split('_')[2];
						dtGridReflectionObj.deleteSortTr(sequenceSort);
					});
					dtGridReflectionObj.sequenceSort++;
					dtGridReflectionObj.resetSortSeq();
				},
				//清除所有排序条件
				clearSortTr : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					$('#sortTable_'+dtGridReflectionObj.option.id+' tr[id*=tr_'+dtGridReflectionObj.option.id+'_]').remove();
				},
				//序列号重置
				resetSortSeq : function(){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					var seq = 1;
					$('#sortTable_'+dtGridReflectionObj.option.id+' tr[id*=tr_'+dtGridReflectionObj.option.id+'_]').each(function(){
						var id = this.id.split('_')[2];
						$('#sortTable_'+dtGridReflectionObj.option.id+' #seqTd_'+dtGridReflectionObj.option.id+'_'+id).html(seq);
						seq++;
					});
				},
				//上移
				moveUpSortTr : function(id){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					$('#sortTable_'+dtGridReflectionObj.option.id+' #tr_'+dtGridReflectionObj.option.id+'_'+id).prev('#sortTable_'+dtGridReflectionObj.option.id+' tr[id*=tr_'+dtGridReflectionObj.option.id+'_]').before($('#sortTable_'+dtGridReflectionObj.option.id+' #tr_'+dtGridReflectionObj.option.id+'_'+id));
					dtGridReflectionObj.resetSortSeq();
				},
				//下移
				moveDownSortTr : function(id){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					$('#sortTable_'+dtGridReflectionObj.option.id+' #tr_'+dtGridReflectionObj.option.id+'_'+id).next('#sortTable_'+dtGridReflectionObj.option.id+' tr[id*=tr_'+dtGridReflectionObj.option.id+'_]').after($('#sortTable_'+dtGridReflectionObj.option.id+' #tr_'+dtGridReflectionObj.option.id+'_'+id));
					dtGridReflectionObj.resetSortSeq();
				},
				//删除当前行
				deleteSortTr : function(id){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					$('#sortTable_'+dtGridReflectionObj.option.id+' #tr_'+dtGridReflectionObj.option.id+'_'+id).remove();
					dtGridReflectionObj.resetSortSeq();
				},
				/**
				 * 打印相关
				 */
				//打印
				print : function(){
					//映射参数
					var dtGridReflectionObj = this;
					//如果已经初始化，则调用显示
					if(dtGridReflectionObj.init.printWindowIsInit){
						$('#dtGridPrint_'+dtGridReflectionObj.option.id+'Modal').modal('show');
						return;
					}
					//放置新的打印选项
					var content = '';
					content += $.fn.DtGrid.tools.getWindowStart('dtGridPrint_'+dtGridReflectionObj.option.id, $.fn.DtGrid.lang[dtGridReflectionObj.option.lang].print.title);
					content += '				<table class="table table-bordered table-print">';
					content += '					<thead>';
					content += '						<tr>';
					content += '							<th><input type="checkbox" id="dt_grid_print_check_'+dtGridReflectionObj.option.id+'" checked="checked" /></th>';
					content += '							<th>'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].print.table.column+'</th>';
					content += '							<th>'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].print.table.operation+'</th>';
					content += '						</tr>';
					content += '					</thead>';
					content += '					<tbody>';
					//遍历列信息
					for(var i=0; i<dtGridReflectionObj.option.columns.length; i++){
						var column = dtGridReflectionObj.option.columns[i];
						if(column.print==false){
							continue;
						}
						//获取记录号
						content += '					<tr id="dt_grid_print_tr_'+dtGridReflectionObj.option.id+'_'+i+'">';
						content += '						<td><input type="checkbox" id="dt_grid_print_check_'+dtGridReflectionObj.option.id+'_'+i+'" checked="checked" value="'+i+'" /></td>';
						content += '						<td>'+column.title+'</td>';
						content += '						<td>';
						content += '							<button type="button" class="btn btn-primary btn-xs" dataId="'+i+'" id="dt_grid_print_up_'+dtGridReflectionObj.option.id+'_'+i+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].print.table.buttons.upTitle+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].print.table.buttons.up+'</button>';
						content += '							<button type="button" class="btn btn-primary btn-xs" dataId="'+i+'" id="dt_grid_print_down_'+dtGridReflectionObj.option.id+'_'+i+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].print.table.buttons.downTitle+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].print.table.buttons.down+'</button>';
						content += '						</td>';
						content += '					</tr>';
					}
					content += '					</tbody>';
					content += '				</table>';
					var buttons = '';
					buttons += '<button type="button" class="btn btn-primary" id="dt_grid_print_execute_'+dtGridReflectionObj.option.id+'">';
					buttons += '	'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang].print.buttons.print;
					buttons += '</button>';
					content += $.fn.DtGrid.tools.getWindowEnd($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].buttons.close, buttons);
					$('body').append(content);
					//绑定复选全选反选方法
					$('#dt_grid_print_check_'+dtGridReflectionObj.option.id).click(function(){
						$('input[id*=dt_grid_print_check_'+dtGridReflectionObj.option.id+'_]').attr('checked', this.checked);
					});
					//绑定上移方法
					$('button[id*=dt_grid_print_up_'+dtGridReflectionObj.option.id+'_]').click(function(){
						var dataId = $(this).attr('dataId');
						var gridId = dtGridReflectionObj.option.id;
						$('#dt_grid_print_tr_'+gridId+'_'+dataId).prev('#dtGridPrint_'+gridId+'Modal tr[id*=dt_grid_print_tr_'+gridId+']').before($('#dt_grid_print_tr_'+gridId+'_'+dataId));
					});
					//绑定下移方法
					$('button[id*=dt_grid_print_down_'+dtGridReflectionObj.option.id+'_]').click(function(){
						var dataId = $(this).attr('dataId');
						var gridId = dtGridReflectionObj.option.id;
						$('#dt_grid_print_tr_'+gridId+'_'+dataId).next('#dtGridPrint_'+gridId+'Modal tr[id*=dt_grid_print_tr_'+gridId+']').after($('#dt_grid_print_tr_'+gridId+'_'+dataId));
					});
					//绑定打印方法
					$('#dt_grid_print_execute_'+dtGridReflectionObj.option.id).click(function(){
						//画表格
						var content = '';
						content += '<table class="dt-grid '+dtGridReflectionObj.option.tableClass+'" id="dt_grid_print_'+dtGridReflectionObj.option.id+'" style="'+dtGridReflectionObj.option.tableStyle+'">';
						if(dtGridReflectionObj.option.showHeader!=false){
							content += '<thead>';
							content += '	<tr>';
							$('input[id*=dt_grid_print_check_'+dtGridReflectionObj.option.id+'_]:checked').each(function(){
								var columnNo = this.value;
								content += '<th class="'+dtGridReflectionObj.option.columns[columnNo].headerClass+'" style="'+dtGridReflectionObj.option.columns[columnNo].headerStyle+'">'+dtGridReflectionObj.option.columns[columnNo].title+'</th>';
							});
							content += '	</tr>';
							content += '</thead>';
						}
						//构建表格
						content += '	<tbody>';
						if(dtGridReflectionObj.exhibitDatas!=null){
							for(var i=0; i<dtGridReflectionObj.exhibitDatas.length; i++){
								content += '	<tr>';
								$('input[id*=dt_grid_print_check_'+dtGridReflectionObj.option.id+'_]:checked').each(function(){
									var columnNo = this.value;
									content += '	<td class="'+dtGridReflectionObj.option.columns[columnNo].columnClass+'" style="'+dtGridReflectionObj.option.columns[columnNo].columnStyle+'">';
									if(dtGridReflectionObj.option.columns[columnNo].resolution){
										content += dtGridReflectionObj.option.columns[columnNo].resolution(dtGridReflectionObj.exhibitDatas[i][dtGridReflectionObj.option.columns[columnNo].id], dtGridReflectionObj.exhibitDatas[i], dtGridReflectionObj.option.columns[columnNo], dtGridReflectionObj, i, columnNo);
									}else{
										content += dtGridReflectionObj.formatContent(dtGridReflectionObj.option.columns[columnNo], dtGridReflectionObj.exhibitDatas[i][dtGridReflectionObj.option.columns[columnNo].id]);
									}
									content += '	</td>';
								});
								content += '	</tr>';
							}
						}
						content += '	</tbody>';
						content += '</table>';
						//隐藏body，放置打印对象
						var scrollTop = $('body').scrollTop();
						$('body').hide();
						$('html').append(content);
						window.print();
						$('#dt_grid_print_'+dtGridReflectionObj.option.id).remove();
						$('body').show();
						$('#dtGridPrint_'+dtGridReflectionObj.option.id+'Modal').modal('hide');
						$('body').scrollTop(scrollTop);
					});
					//显示打印选项
					$('#dtGridPrint_'+dtGridReflectionObj.option.id+'Modal').modal('show');
					//标识初始化完成
					dtGridReflectionObj.init.printWindowIsInit = true;
				},
				/**
				 * 导出相关
				 */
				//导出
				exportFile : function(exportType){
					//映射参数
					var dtGridReflectionObj = this;
					//如果已经初始化，则显示导出选项
					if(dtGridReflectionObj.init.exportWindowIsInit[exportType]){
						//显示导出选项
						$('#dtGridExport_'+exportType+'_'+dtGridReflectionObj.option.id+'Modal').modal('show');
						return;
					}
					//放置新的导出选项
					var content = '';
					content += $.fn.DtGrid.tools.getWindowStart('dtGridExport_'+exportType+'_'+dtGridReflectionObj.option.id, $.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].title);
					content += '				<div class="form-export modal-body form-horizontal form-export">';
					content += '					<div class="form-group">';
					content += '						<label class="col-sm-3 control-label">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].exportType.title+'</label>';
					content += '						<div class="col-sm-9">';
					content += '							<div class="checkbox">';
					content += '								<label><input type="radio" name="dt_grid_export_export_all_data_'+exportType+'_'+dtGridReflectionObj.option.id+'" value="0" checked="checked" /> '+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].exportType.now+'</label>';
					content += '								<label><input type="radio" name="dt_grid_export_export_all_data_'+exportType+'_'+dtGridReflectionObj.option.id+'" value="1" /> '+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].exportType.all+'</label>';
					content += '							</div>';
					content += '						</div>';
					content += '					</div>';
					content += '				</div>';
					content += '				<table class="table table-bordered table-export">';
					content += '					<thead>';
					content += '						<tr>';
					content += '							<th><input type="checkbox" id="dt_grid_export_check_'+exportType+'_'+dtGridReflectionObj.option.id+'" checked="checked" /></th>';
					content += '							<th>'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].table.column+'</th>';
					content += '							<th>'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].table.operation+'</th>';
					content += '						</tr>';
					content += '					</thead>';
					content += '					<tbody>';
					//遍历列信息
					for(var i=0; i<dtGridReflectionObj.option.columns.length; i++){
						var column = dtGridReflectionObj.option.columns[i];
						if(column['export']==false){
							continue;
						}
						content += '					<tr id="dt_grid_export_tr_'+exportType+'_'+dtGridReflectionObj.option.id+'_'+i+'">';
						content += '						<td><input type="checkbox" id="dt_grid_export_check_'+exportType+'_'+dtGridReflectionObj.option.id+'_'+i+'" checked="checked" value="'+i+'" /></td>';
						content += '						<td>'+column.title+'</td>';
						content += '						<td>';
						content += '							<button type="button" class="btn btn-primary btn-xs" dataId="'+i+'" id="dt_grid_export_up_'+exportType+'_'+dtGridReflectionObj.option.id+'_'+i+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].table.buttons.upTitle+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].table.buttons.up+'</button>';
						content += '							<button type="button" class="btn btn-primary btn-xs" dataId="'+i+'" id="dt_grid_export_down_'+exportType+'_'+dtGridReflectionObj.option.id+'_'+i+'" title="'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].table.buttons.downTitle+'">'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].table.buttons.down+'</button>';
						content += '						</td>';
						content += '					</tr>';
					}
					content += '					</tbody>';
					content += '				</table>';
					var buttons = '';
					buttons += '<button type="button" class="btn btn-primary" id="dt_grid_export_execute_'+exportType+'_'+dtGridReflectionObj.option.id+'">';
					buttons += '	'+$.fn.DtGrid.lang[dtGridReflectionObj.option.lang]['export'][exportType].buttons['export'];
					buttons += '</button>';
					content += $.fn.DtGrid.tools.getWindowEnd($.fn.DtGrid.lang[dtGridReflectionObj.option.lang].buttons.close, buttons);
					$('body').append(content);
					//绑定复选方法
					$('#dt_grid_export_check_'+exportType+'_'+dtGridReflectionObj.option.id).click(function(){
						$('input[id*=dt_grid_export_check_'+exportType+'_'+dtGridReflectionObj.option.id+'_]').attr('checked', this.checked);
					});
					//绑定上移方法
					$('button[id*=dt_grid_export_up_'+exportType+'_'+dtGridReflectionObj.option.id+'_]').click(function(){
						var dataId = $(this).attr('dataId');
						var gridId = dtGridReflectionObj.option.id;
						$('#dt_grid_export_tr_'+exportType+'_'+gridId+'_'+dataId).prev('#dtGridExport_'+exportType+'_'+gridId+'Modal tr[id*=dt_grid_export_tr_'+exportType+'_'+gridId+']').before($('#dt_grid_export_tr_'+exportType+'_'+gridId+'_'+dataId));
					});
					//绑定下移方法
					$('button[id*=dt_grid_export_down_'+exportType+'_'+dtGridReflectionObj.option.id+'_]').click(function(){
						var dataId = $(this).attr('dataId');
						var gridId = dtGridReflectionObj.option.id;
						$('#dt_grid_export_tr_'+exportType+'_'+gridId+'_'+dataId).next('#dtGridExport_'+exportType+'_'+gridId+'Modal tr[id*=dt_grid_export_tr_'+exportType+'_'+gridId+']').after($('#dt_grid_export_tr_'+exportType+'_'+gridId+'_'+dataId));
					});
					//绑定导出方法
					$('#dt_grid_export_execute_'+exportType+'_'+dtGridReflectionObj.option.id).click(function(){
						//删除原表单
						$('#dt_grid_export_form_container_'+exportType+'_'+dtGridReflectionObj.option.id).remove();
						//将参数传递后台使用伪表单进行导出
						var exportFormContainer = document.createElement('div');
						exportFormContainer.id = 'dt_grid_export_form_container_'+exportType+'_'+dtGridReflectionObj.option.id;
						exportFormContainer.className = 'hidden';
						var exportIframe = document.createElement('iframe');
						exportIframe.name = 'dt_grid_export_iframe_'+exportType+'_'+dtGridReflectionObj.option.id;
						exportFormContainer.appendChild(exportIframe);
						var exportForm = document.createElement('form');
						exportForm.id = 'dt_grid_export_form_'+exportType+'_'+dtGridReflectionObj.option.id;
						exportForm.method = 'post';
						exportForm.target = 'dt_grid_export_iframe_'+exportType+'_'+dtGridReflectionObj.option.id;
						if(dtGridReflectionObj.option.ajaxLoad==false||dtGridReflectionObj.option.loadAll==true){
							exportForm.action = dtGridReflectionObj.option.exportURL;
						}else{
							exportForm.action = dtGridReflectionObj.option.loadURL;
						}
						var dtGridPager = new Object();
						dtGridPager.pageSize = dtGridReflectionObj.pager.pageSize;
						dtGridPager.startRecord = dtGridReflectionObj.pager.startRecord;
						dtGridPager.nowPage = dtGridReflectionObj.pager.nowPage;
						dtGridPager.recordCount = dtGridReflectionObj.pager.recordCount;
						dtGridPager.pageCount = dtGridReflectionObj.pager.pageCount;
						dtGridPager.isExport = true;
						dtGridPager.exportFileName = dtGridReflectionObj.option.exportFileName;
						dtGridPager.exportType = exportType;
						dtGridPager.exportAllData = $('input[name*=dt_grid_export_export_all_data_'+exportType+'_'+dtGridReflectionObj.option.id+']:checked').val()=='1'?true:false;
						dtGridPager.parameters = new Object();
						dtGridPager.fastQueryParameters = new Object();
						dtGridPager.advanceQueryConditions = new Array();
						dtGridPager.advanceQuerySorts = new Array();
						if(dtGridReflectionObj.parameters){
							dtGridPager.parameters = dtGridReflectionObj.parameters;
						}
						if(dtGridReflectionObj.fastQueryParameters){
							dtGridPager.fastQueryParameters = dtGridReflectionObj.fastQueryParameters;
						}
						if(dtGridReflectionObj.advanceQuery&&dtGridReflectionObj.advanceQuery.advanceQueryConditions){
							dtGridPager.advanceQueryConditions = dtGridReflectionObj.advanceQuery.advanceQueryConditions;
						}
						if(dtGridReflectionObj.advanceQuery&&dtGridReflectionObj.advanceQuery.advanceQuerySorts){
							dtGridPager.advanceQuerySorts = dtGridReflectionObj.advanceQuery.advanceQuerySorts;
						}
						var exportColumns = new Array();
						$('input[id*=dt_grid_export_check_'+exportType+'_'+dtGridReflectionObj.option.id+'_]:checked').each(function(){
							exportColumns.push(dtGridReflectionObj.option.columns[this.value]);
						});
						dtGridPager.exportColumns = exportColumns;
						//获取原生数据
						var originalDatas = new Array();
						var exportDataIsProcessed = false;
						if(!dtGridPager.exportAllData){
							originalDatas = dtGridReflectionObj.exhibitDatas;
							exportDataIsProcessed = true;
						}else{
							if(dtGridReflectionObj.option.ajaxLoad==false||dtGridReflectionObj.option.loadAll==true){
								originalDatas = dtGridReflectionObj.originalDatas;
								exportDataIsProcessed = true;
							}
						}
						dtGridPager.exportDataIsProcessed = exportDataIsProcessed;
						//拼接导出数据
						var exportDatas = new Array();
						for(var i=0; i<originalDatas.length; i++){
							var data = originalDatas[i];
							var exportData = new Object();
							$('input[id*=dt_grid_export_check_'+exportType+'_'+dtGridReflectionObj.option.id+'_]:checked').each(function(){
								var column = dtGridReflectionObj.option.columns[this.value];
								var fieldContent = dtGridReflectionObj.formatContent(column, data[column.id]);
								exportData[column.id] = fieldContent;
							});
							exportDatas.push(exportData);
						}
						dtGridPager.exportDatas = exportDatas;
						var dtGridPagerInput = document.createElement('input');
						dtGridPagerInput.type = 'hidden';
						dtGridPagerInput.id = 'dtGridPager';
						dtGridPagerInput.name = 'dtGridPager';
						dtGridPagerInput.value = JSON.stringify(dtGridPager);
						exportForm.appendChild(dtGridPagerInput);
						exportFormContainer.appendChild(exportForm);
						$('body').append(exportFormContainer);
						$('#dt_grid_export_form_'+exportType+'_'+dtGridReflectionObj.option.id).submit();
						$('#dtGridExport_'+exportType+'_'+dtGridReflectionObj.option.id+'Modal').modal('hide');
					});
					//设置初始化完成
					dtGridReflectionObj.init.exportWindowIsInit[exportType] = true;
					//显示导出选项
					$('#dtGridExport_'+exportType+'_'+dtGridReflectionObj.option.id+'Modal').modal('show');
				},
				/**
				 * 对外开放工具方法
				 */
				//获取选中内容
				getCheckedRecords: function(){
					//设置本体映射
					dtGridReflectionObj = this;
					//获取数据
					var records = new Array();
					$('input[id*=dt_grid_'+dtGridReflectionObj.option.id+'_check_]:checked').each(function(){
						records.push(dtGridReflectionObj.exhibitDatas[this.value]);
					});
					return records;
				},
				//重新加载，true为重新从数据库中获取数据
				reload : function(allReload){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					if(allReload){
						dtGridReflectionObj.load();
					}else{
						//重新加载数据
						dtGridReflectionObj.constructGrid();
						dtGridReflectionObj.constructGridPageBar();
					}
				},
				//重新加载重载方法
				refresh : function(allReload){
					//定义表格对象映像
					var dtGridReflectionObj = this;
					dtGridReflectionObj.reload(allReload);
				}
			};
			return dtGridObject;
		},
		//工具方法
		tools : {
			//生成guid
			guid : function(){
				return 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			    	var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
			        return v.toString(16);
				});
			},
			//创建一个模态窗口（开始）
			getWindowStart : function(id, title){
				var content = '';
				content += '<div class="modal fade" id="'+id+'Modal" tabindex="-1" role="dialog" aria-labelledby="'+id+'ModalLabel" aria-hidden="true">';
				content += '	<div class="modal-dialog">';
				content += '		<div class="modal-content">';
				content += '			<div class="modal-header">';
				content += '				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
				content += '				<h4 class="modal-title" id="'+id+'ModalLabel">'+title+'</h4>';
				content += '			</div>';
				return content;
			},
			//创建一个模态窗口（结束）
			getWindowEnd : function(closeButtonTitle, buttons){
				var content = '';
				content += '			<div class="modal-footer">';
				content += '				<button type="button" class="btn btn-default" data-dismiss="modal">';
				content += '					'+closeButtonTitle;
				content += '				</button>';
				content += '				'+buttons;
				content += '			</div>';
				content += '		</div>';
				content += '	</div>';
				content += '</div>';
				return content;
			},
			/**
			 * 提示框
			 */
			toastZIndex : 1090,
			toastThread : null,
			toastFadeInAnimateTime : 500,
			toastFadeOutAnimateTime : 500,
			openToast : function(content, level, time){
				// default level、time
				level = level?level:'info';
				time = time?time:3000;
				// get the level class
				var levelClass = '';
				if(level=='info') levelClass='text-primary';
				if(level=='warning') levelClass='text-warning';
				if(level=='error') levelClass='text-danger';
				if(level=='success') levelClass='text-success';
				// close other toast div
				clearTimeout($.fn.DtGrid.tools.toastThread);
				$('.toast').remove();
				// constructs the html content
				var toastContent = '<div class="toast '+levelClass+'" style="z-index:'+$.fn.DtGrid.tools.toastZIndex+'">'+content+'</div>';
				// append to the ducoment
				$('body').append(toastContent);
				// set the offset
				var x = $(window).width()/2-$('.toast').width()/2-20;
				//toast(x);
				$('.toast').css("left", x);
				// show the div
				$('.toast').fadeIn($.fn.DtGrid.tools.toastFadeInAnimateTime, function(){
					// callback close
					if(time){
						$.fn.DtGrid.tools.toastThread = setTimeout($.fn.DtGrid.tools.removeToast, time);
					}
				});
			},
			//close an toast
			removeToast : function(){
				$('.toast').fadeOut($.fn.DtGrid.tools.toastFadeOutAnimateTime, function(){
					$('.toast').remove();
				});
			},
			//整理Toast方法
			toast : function(content, level, time){
				$.fn.DtGrid.tools.openToast(content, level, time);
			},
			//将字符串转换成日期时间，有默认格式 
			toDate : function(date, pattern) {
				if (!pattern||pattern == null)
					pattern = 'yyyy-MM-dd hh:mm:ss';
				var compare = {
					'y+' : 'y',
					'M+' : 'M',
					'd+' : 'd',
					'h+' : 'h',
					'm+' : 'm',
					's+' : 's'
				};
				var result = {
					'y' : '',
					'M' : '',
					'd' : '',
					'h' : '00',
					'm' : '00',
					's' : '00'
				};
				var tmp = pattern;
				for ( var k in compare) {
					if (new RegExp('(' + k + ')').test(pattern)) {
						result[compare[k]] = date.substring(tmp.indexOf(RegExp.$1), tmp.indexOf(RegExp.$1) + RegExp.$1.length);
					}
				}
				return new Date(result['y'], result['M'] - 1, result['d'], result['h'], result['m'], result['s']);
			},
			// 格式化时间
			dateFormat : function(value, format){
				if(value==''){
					return '';
				}
				if(value.time){
					value = new Date(value.time);
				}
				var o = {
					"M+" : value.getMonth()+1, //month
					"d+" : value.getDate(),    //day
					"h+" : value.getHours(),   //hour
					"m+" : value.getMinutes(), //minute
					"s+" : value.getSeconds(), //second
					"q+" : Math.floor((value.getMonth()+3)/3), //quarter
					"S" : value.getMilliseconds() //millisecond
				};
				if(/(y+)/.test(format)) format=format.replace(RegExp.$1, (value.getFullYear()+"").substr(4 - RegExp.$1.length));
				for(var k in o)if(new RegExp("("+ k +")").test(format))
					format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
				return format;
			},
			//字符串全局替换
			replaceAll : function(s, s1, s2){
				return s.replace(new RegExp(s1, "gm"), s2);
			},
			//判断字符串开始内容
			startsWith : function(str, prefix){
				if(prefix==undefined)
					return false;
				if(str.indexOf(prefix)==0)
					return true;
				return false;
			},
			//判断字符串结束内容
			endsWith : function(str, suffix){
				if(suffix==undefined)
					return false;
				if(str.lastIndexOf(suffix)==str.length-suffix.length)
					return true;
				return false;
			},
			//字符串不区分大小写的比较
			equalsIgnoreCase : function(str1, str2){
				return (new String(str1.toLowerCase()) == (new String(str2)).toLowerCase());
			},
			//数字格式化函数[#,0格式]
			numberFormat : function(number, pattern){
				var negFlag = "false";
				var str = Number(number).toString();
				if (str.indexOf("-")==0){
					negFlag = "true";
					str = str.replace("-","");
					number = -number;
				}
				var strInt;
				var strFloat;
				var formatInt;
				var formatFloat;
				if($.trim(str)=="")
					return "";
				//判断模式串是否有小数格式
				if(/\./g.test(pattern)){
					formatInt = pattern.split('.')[0];
					formatFloat = pattern.split('.')[1];
				}else{
					formatInt = pattern;
					formatFloat = null;
				}
				if(/\./g.test(str)){
					//如果字符串有小数
					if(formatFloat!=null){
						var tempFloat = Math.round(parseFloat('0.'+str.split('.')[1])*Math.pow(10,formatFloat.length))/Math.pow(10,formatFloat.length);
						strInt = (Math.floor(number)+Math.floor(tempFloat)).toString();
						strFloat = /\./g.test(tempFloat.toString())?tempFloat.toString().split('.')[1]:'0';
					}else{
						strInt = Math.round(number).toString();
						strFloat = '0';
					}
				}else{
					strInt = str;
					strFloat = '0';
				}
				//处理整数数位的格式化
				if(formatInt!=null){
					var outputInt = '';
					var zero = formatInt.match(/0*$/)[0].length;
					var comma = null;
					if(/,/g.test(formatInt)){
						comma = formatInt.match(/,[^,]*/)[0].length-1;
					}
					var newReg = new RegExp('(\\d{'+comma+'})','g');
					if(strInt.length<zero){
						outputInt = new Array(zero+1).join('0')+strInt;
						outputInt = outputInt.substr(outputInt.length-zero,zero);
					}else{
						outputInt = strInt;
					}
					outputInt = outputInt.substr(0,outputInt.length%comma)+outputInt.substring(outputInt.length%comma).replace(newReg,(comma!=null?',':'')+'$1');
					outputInt = outputInt.replace(/^,/,'');
					strInt = outputInt;
				}
				//处理小数位的格式化
				if(formatFloat!=null){
					var outputFloat = '';
					var zero = formatFloat.match(/^0*/)[0].length;
					if(strFloat.length<zero){
						outputFloat = strFloat+new Array(zero+1).join('0');
						var outputFloat1 = outputFloat.substring(0,zero);
						var outputFloat2 = outputFloat.substring(zero,formatFloat.length);
						outputFloat = outputFloat1+outputFloat2.replace(/0*$/,'');
					}else{
						//如果小数是0，而且模式串的小数格式中也不包含0，则只保留整数部分。
						if(strFloat==0&&zero==0)
							outputFloat = '';
						else
						outputFloat = strFloat.substring(0,formatFloat.length);
					}
					strFloat = outputFloat;
				}else{
					if(pattern!='' || (pattern=='' && strFloat=='0'))
						strFloat = '';
				}
				if(negFlag == "true")
					return "-" + strInt+(strFloat==''?'':'.'+strFloat);
				else
					return strInt+(strFloat==''?'':'.'+strFloat);
			}
		}
	};
})(jQuery);

//默认属性
(function($) {
	$.fn.DtGrid.defaultOptions = {
		grid : {
			lang : 'en',
			ajaxLoad : true,
			loadAll : false,
			loadURL : '',
			datas : null,
			check : false,
			exportFileName : 'datas',
			tableStyle : '',
			tableClass : 'table table-bordered table-hover table-responsive',
			showHeader : true,
			gridContainer : 'dtGridContainer',
			toolbarContainer : 'dtGridToolBarContainer',
			tools : 'refresh|faseQuery|advanceQuery|export[excel,csv,pdf,txt]|print',
			exportFileName : 'datas',
			exportURL : '/dtgrid/export',
			pageSize : 20,
			pageSizeLimit : [20, 50, 100]
		},
		column : {
			id : 'field',
			title : 'field', 
			type : 'string', 
			format : null,
			otype : null, 
			oformat : null,
			columnStyle : '',
			columnClass : '',
			headerStyle : '',
			headerClass : '',
			hide : false,
			hideType : '',
			extra : true,
			codeTable : null,
			fastQuery : false,
			fastQueryType : '',
			advanceQuery : true,
			'export' : true,
			print : true,
			resolution : null
		}
	};
})(jQuery);