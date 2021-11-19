package com.gupao.framework.common.pager;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pager<T> {

    /**
     * 是否出错
     */
    private boolean isSuccess;

    /**
     * 每页显示条数
     */
    private int pageSize;

    /**
     * 开始记录数
     */
    private int startRecord;

    /**
     * 当前页数
     */
    private int nowPage;

    /**
     * 记录总数
     */
    private int recordCount;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 参数列表
     */
    private Map<String, Object> parameters;

    /**
     * 快速查询参数列表
     */
    private Map<String, Object> fastQueryParameters;

    /**
     * 高级查询列表
     */
    private List<Condition> advanceQueryConditions;

    /**
     * 高级排序列表
     */
    private List<Sort> advanceQuerySorts;

    /**
     * 显示数据集
     */
    private List<T> exhibitDatas;

    /**
     * 是否导出：1-是，0-否
     */
    private boolean isExport;

    /**
     * 导出类型，支持excel、pdf、txt、cvs
     */
    private String exportType;

    /**
     * 导出文件名
     */
    private String exportFileName;

    /**
     * 导出列
     */
    private List<Column> exportColumns;

    /**
     * 全部数据导出
     */
    private boolean exportAllData;

    /**
     * 导出数据是否已被加工
     */
    private boolean exportDataIsProcessed;

    /**
     * 导出数据
     */
    private List<Map<String, Object>> exportDatas;

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRecord() {
        return startRecord;
    }

    public void setStartRecord(int startRecord) {
        this.startRecord = startRecord;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageCount() {
        return recordCount/getPageSize()+(recordCount%getPageSize()>0?1:0);
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Map<String, Object> getFastQueryParameters() {
        return fastQueryParameters;
    }

    public void setFastQueryParameters(Map<String, Object> fastQueryParameters) {
        this.fastQueryParameters = fastQueryParameters;
    }

    public List<Condition> getAdvanceQueryConditions() {
        return advanceQueryConditions;
    }

    public void setAdvanceQueryConditions(List<Condition> advanceQueryConditions) {
        this.advanceQueryConditions = advanceQueryConditions;
    }

    public List<Sort> getAdvanceQuerySorts() {
        return advanceQuerySorts;
    }

    public void setAdvanceQuerySorts(List<Sort> advanceQuerySorts) {
        this.advanceQuerySorts = advanceQuerySorts;
    }

    public List<T> getExhibitDatas() {
        return exhibitDatas;
    }

    public void setExhibitDatas(List<T> exhibitDatas) {
        this.exhibitDatas = exhibitDatas;
    }

    public boolean getIsExport() {
        return isExport;
    }

    public void setIsExport(boolean isExport) {
        this.isExport = isExport;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getExportFileName() {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
    }

    public List<Column> getExportColumns() {
        return exportColumns;
    }

    public void setExportColumns(List<Column> exportColumns) {
        this.exportColumns = exportColumns;
    }

    public boolean getExportAllData() {
        return exportAllData;
    }

    public void setExportAllData(boolean exportAllData) {
        this.exportAllData = exportAllData;
    }

    public boolean getExportDataIsProcessed() {
        return exportDataIsProcessed;
    }

    public void setExportDataIsProcessed(boolean exportDataIsProcessed) {
        this.exportDataIsProcessed = exportDataIsProcessed;
    }

    public List<Map<String, Object>> getExportDatas() {
        return exportDatas;
    }

    public void setExportDatas(List<Map<String, Object>> exportDatas) {
        this.exportDatas = exportDatas;
    }

    public static Pager copy(JSONObject object,Class cls) throws Exception{
        Map<String, Class> classMap = new HashMap<>();
        classMap.put("parameters", Map.class);
        classMap.put("fastQueryParameters", Map.class);
        classMap.put("advanceQueryConditions", Condition.class);
        classMap.put("advanceQuerySorts", Sort.class);
        classMap.put("exhibitDatas", cls);
        classMap.put("exportColumns", Column.class);
        classMap.put("exportDatas", Map.class);
        Pager pager = (Pager)JSONObject.toBean(object, Pager.class, classMap);
        return pager;
    }
}