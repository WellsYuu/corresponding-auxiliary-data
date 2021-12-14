package com.tl.servlet;




import com.tl.annotiion.Controller;
import com.tl.annotiion.Qualifier;
import com.tl.annotiion.RequestMapping;
import com.tl.annotiion.Service;
import com.tl.controller.TlController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    Context context=new Context();
    public void init(){
        try {
            context.init();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 拿到完整路径
        String uri = req.getRequestURI();
        String projectName = req.getContextPath();
        // bastURL+methodUrl
        String path = uri.replace(projectName, "");
        // 方法对象
        Method method = context.getHandlerMaps().get(path);
        System.out.println("path:"+path);
        PrintWriter outPrintWriter = resp.getWriter();
        if (method == null) {
            // 给客户端一个友好的提示
            outPrintWriter.write("<HTML><BODY><H1><FONT COLOR=RED>not found 404 </FONT></H1> </BODY></HTML>");
            outPrintWriter.flush();
            outPrintWriter.close();
            return;
        }
        String className = uri.split("/")[1];
        TlController tlController = (TlController) context.getInstanceMaps().get("/"+className);
        try {
            Object o=method.invoke(tlController, new Object[] { req, resp });
            outPrintWriter.write((String)o);
            outPrintWriter.flush();
            outPrintWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    class Context{

        //扫包
        private List<String> oneList=new ArrayList<String>();
        //实例化 Controller注解 Service的注解
        private Map<String,Object> twoMaps=new HashMap<String, Object>();
        //url跟方法的一个映射关系 Controller的requestMapping的url 对应的method
        //insert->insert方法
        private Map<String,Method> threeMaps=new HashMap<String,Method>();

        public Map<String,Method> getHandlerMaps(){
            return threeMaps   ;
        }

        public  Map<String,Object>  getInstanceMaps()
        {
            return twoMaps;
        }

        /***
         * 這個有點像面向過程的一個變編程是吧？
         * @throws Exception
         */
        public  void init()throws  Exception{
            scanBase("com.tl");
            foundBeansInstance();
            springLoc();
            handerMap();
        }
        /**
         * 扫包
         * @param basePackageName
         */
        private void scanBase(String basePackageName){
            URL url = this.getClass().getClassLoader().getResource("/" + basePackageName.replaceAll("\\.", "/"));
            // 拿到这个资源路径下面的文件或者文件夹
            String pathFile = url.getFile();
            File file = new File(pathFile);
            String[] files = file.list();
            for (String path : files) {
                //这个要写全文件名称
                File eachFile = new File(pathFile+"/"+path);
                // 如果是文件夹 那么我们在要调用这个本方法进入拿真正的文件
                if (eachFile.isDirectory()) {
                    // 将基础包下面的包当成二次基包传入进去
                    scanBase(basePackageName + "." + eachFile.getName());
                } else if (eachFile.isFile()) {
                    oneList.add(basePackageName + "." + eachFile.getName());
                    System.out.println(" Spring mvc容器扫面到的类有：" + basePackageName + "." + eachFile.getName());

                }
            }

        }

        /**
         * 实例化
         */
        private void foundBeansInstance()throws  Exception{
            if (oneList.size() == 0) {
                return;
            }
            for (String className : oneList) {
                Class ccName = Class.forName(className.replace(".class", ""));
                String value=null;
                if (ccName.isAnnotationPresent(Controller.class)) {
                    value=( (Controller) ccName.getAnnotation(Controller.class)).value();

                }else if (ccName.isAnnotationPresent(Service.class)) {
                    value=( (Service) ccName.getAnnotation(Service.class)).value();
                }
                if(null!=value) {
                    twoMaps.put(value, ccName.newInstance());
                }
            }

        }
        /**
         * loc操作
         */
        private void springLoc()throws  Exception{
            if (twoMaps.size() == 0) {
                return;
            }
            for (Map.Entry<String, Object> entry : twoMaps.entrySet()) {
                // 拿到控制层或者服务成Bean实例
                Field[] fields = entry.getValue().getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Qualifier.class)) {
                        // Field需要注入实例
                        String qualifierParam = ((Qualifier) field
                                .getAnnotation(Qualifier.class)).value();
                        field.setAccessible(true);
                        field.set(entry.getValue(), twoMaps.get(qualifierParam));
                    } else {
                        continue;
                    }
                }
            }
        }

        /**
         * 映射关系
         */
        private void handerMap(){
            if (twoMaps.size() == 0) {
                return;
            }
            // 构建方法请求连接 针对 Controller 类注解的参数/方法注解的参数
            for (Map.Entry<String, Object> entry : twoMaps.entrySet()) {
                Class t=entry.getValue().getClass();
                if (t.isAnnotationPresent(Controller.class)) {
                    Controller controller = (Controller) t.getAnnotation(Controller.class);
                    // 类注解的参数
                    String classURL = controller.value();
                    // 目的是拿方法上面注解的参数
                    Method[] methods = t.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            // 方法上面注解的参数
                            String methodUrl = ((RequestMapping) (method.getAnnotation(RequestMapping.class))).value();
                            threeMaps.put(classURL+ methodUrl, method);
                        } else {
                            continue;
                        }
                    }
                }
            }

        }




    }
}
