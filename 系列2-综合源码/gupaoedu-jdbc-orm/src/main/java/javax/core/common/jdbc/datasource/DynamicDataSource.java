package javax.core.common.jdbc.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/** 
 * 动态数据源 
 *  
 * @author Tom 
 *  
 */  
public class DynamicDataSource extends AbstractRoutingDataSource {  
  
	private DynamicDataSourceEntry dataSourceEntry;  
    
    @Override  
    protected Object determineCurrentLookupKey() {
        return this.dataSourceEntry.get();  
    }  
  
    public void setDataSourceEntry(DynamicDataSourceEntry dataSourceEntry) {  
        this.dataSourceEntry = dataSourceEntry;
    }
    
    public DynamicDataSourceEntry getDataSourceEntry(){
    		return this.dataSourceEntry;
    }
    
}
