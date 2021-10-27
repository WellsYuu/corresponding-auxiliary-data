package cn.enjoyedu.sse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
@Controller
public class NobleMetalController {

    private static Logger logger = LoggerFactory.getLogger(NobleMetalController.class);

    @RequestMapping("/nobleMetal")
    public String stock(){
        return "nobleMetal";
    }

    @RequestMapping(value="needPrice")
    @ResponseBody
    public void push(HttpServletResponse response){
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");
        Random r = new Random();
        int sendCount = 0;/*服务器数据发送完*/
        try {
            PrintWriter pw = response.getWriter();
            while(true){
                if(pw.checkError()){
                    System.out.println("客户端断开连接");
                    return;
                }
                Thread.sleep(1000);
                //字符串拼接
                StringBuilder sb = new StringBuilder("");
                sb//.append("retry:2000\n")
                        .append("data:")
                        .append((r.nextInt(1000)+50)+",")
                        .append((r.nextInt(800)+100)+",")
                        .append((r.nextInt(2000)+150)+",")
                        .append((r.nextInt(1500)+100)+",")
                        .append("\n\n");

                pw.write(sb.toString());
                pw.flush();
                sendCount++;
                if(sendCount>=100){
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*不完美的用法*/
//    @RequestMapping(value="/needPrice",produces = "text/event-stream;charset=UTF-8")
//    @ResponseBody
//    public String push(){
//        Random r = new Random();
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        StringBuilder stringBuilder = new StringBuilder("");
//        stringBuilder.append("retry:1\n")
//                .append("data:")
//                .append((r.nextInt(100)+50)+",")
//                .append((r.nextInt(80)+45)+",")
//                .append((r.nextInt(60)+40)+",")
//                .append((r.nextInt(40)+35))
//                .append("\n\n");
//
//        logger.info("当前股票信息："+stringBuilder.toString());
//        return stringBuilder.toString();
//    }

}
