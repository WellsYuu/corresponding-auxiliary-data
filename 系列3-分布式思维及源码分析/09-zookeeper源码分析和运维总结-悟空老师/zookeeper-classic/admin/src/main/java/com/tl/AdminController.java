package com.tl;/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    ZookeeperRegister zookeeperRegister=new ZookeeperRegister();

    @GetMapping("/list") //问题在这 /admin/list2可以访问
    @RequestMapping("/list2")
    public String list(HttpServletRequest request){
        ZooKeeper zooKeeper=zookeeperRegister.getConnection(new Watcher() {
            public void process(WatchedEvent event) {

            }
        });

        try {
            List<String> data= zooKeeper.getChildren(ZookeeperRegister.ROOT,true);
            List<TlState>  list=new ArrayList<TlState>();
            for(String server:data){
               byte[] bytes= zooKeeper.getData(zookeeperRegister.ROOT+"/"+server,false,null);
                TlState tlState=JSON.parseObject(new String(bytes),TlState.class);
                list.add(tlState);

            }
            request.setAttribute("servetList",list);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         return "admin";

    }





    @RequestMapping("/mointer/{command}")
    public String list(HttpServletRequest request,@PathVariable String command) throws IOException {

         String zk1=  hander("192.168.0.11",command);
        String zk2=  hander("192.168.0.12",command);
        String zk3=  hander("192.168.0.13",command);
        request.setAttribute("zk1",zk1);
        request.setAttribute("zk2",zk2);
        request.setAttribute("zk3",zk3);
        return "mointer";

    }

    private String hander(String host,String command) throws IOException {
        Socket socket=new Socket(host,2181);
        OutputStream outputStream=socket.getOutputStream();
        outputStream.write(command.getBytes());
        outputStream.flush();
         return IOUtils.toString(socket.getInputStream());
    }

}
