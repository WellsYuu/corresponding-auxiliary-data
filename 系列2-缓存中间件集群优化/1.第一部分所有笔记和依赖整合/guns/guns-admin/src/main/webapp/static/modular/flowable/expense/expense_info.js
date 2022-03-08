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

/**
 * 初始化报销管理详情对话框
 */
var ExpenseInfoDlg = {
    expenseInfoData : {}
};

/**
 * 清除数据
 */
ExpenseInfoDlg.clearData = function() {
    this.expenseInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExpenseInfoDlg.set = function(key, val) {
    this.expenseInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExpenseInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ExpenseInfoDlg.close = function() {
    parent.layer.close(window.parent.Expense.layerIndex);
}

/**
 * 收集数据
 */
ExpenseInfoDlg.collectData = function() {
    this
    .set('id')
    .set('money')
    .set('desc')
    ;
}

/**
 * 提交添加
 */
ExpenseInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/expense/add", function(data){
        Feng.success("添加成功!");
        window.parent.Expense.table.refresh();
        ExpenseInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.expenseInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ExpenseInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/expense/update", function(data){
        Feng.success("修改成功!");
        window.parent.Expense.table.refresh();
        ExpenseInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.expenseInfoData);
    ajax.start();
}

$(function() {

});
