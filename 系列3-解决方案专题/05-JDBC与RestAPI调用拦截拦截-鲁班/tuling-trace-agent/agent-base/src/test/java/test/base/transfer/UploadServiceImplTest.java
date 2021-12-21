package test.base.transfer;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

import test.BasiceTest;

import com.cbt.agent.trace.TraceFrom;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.transfer.UploadService;
import com.cbt.agent.transfer.UploadServiceImpl;

/**
 * Created by tommy on 16/10/21.
 */
public class UploadServiceImplTest extends BasiceTest {
    UploadService service;
    private ArrayList<TraceNode> nodes = null;
    private ArrayList<TraceFrom> infos = new ArrayList<>();
    public UploadServiceImplTest(){
        init();
    }
    private void init() {

        String traceId = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println(traceId);
        UploadServiceImpl serviceImpl = new UploadServiceImpl();
        serviceImpl.setDefaultUpload("http");
        serviceImpl.setHttpUrl("http://120.76.224.136:10000/bug-microscope/trace/upload");
        service = serviceImpl;
        nodes=newMockTraceNodes(traceId);
        infos= new ArrayList<>();
        infos.add(newMockTraceFrom(traceId));
    }

    @Test
    public void uploadDefaultTest() {
        service.uploadByDefault(nodes, infos);
    }

    @Test
    public void uploadByLog(){
        service.uploadToLogfile(nodes,infos);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}