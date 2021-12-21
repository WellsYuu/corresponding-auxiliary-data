package com.cbt.agent.transfer;

import com.cbt.agent.trace.TraceFrom;
import com.cbt.agent.trace.TraceNode;

import java.util.Collection;

/**
 * 上传器接口
 * 
 * @since 0.1.0
 */
public interface UploadService {
    public void uploadByDefault(Collection<TraceNode> nodes, Collection<TraceFrom> infos);

    public void uploadByHttp(Collection<TraceNode> nodes, Collection<TraceFrom> infos, String url);

    public void uploadByDubbo(Collection<TraceNode> nodes, Collection<TraceFrom> infos);
    
    public void uploadToLogfile(Collection<TraceNode> nodes, Collection<TraceFrom> infos);

    void uploadToRedis(Collection<TraceNode> nodes, Collection<TraceFrom> infos);

    void uploadToMysql(Collection<TraceNode> nodes, Collection<TraceFrom> infos);

}
