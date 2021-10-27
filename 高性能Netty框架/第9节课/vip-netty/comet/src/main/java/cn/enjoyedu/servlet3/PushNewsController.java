package cn.enjoyedu.servlet3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
@Controller
@RequestMapping(produces="text/html;charset=UTF-8")
/*记得要在WebInitializer中增加servlet.setAsyncSupported(true);*/
public class PushNewsController {

    private ExecutorService executorService
            = Executors.newFixedThreadPool(1);

    @RequestMapping("/pushnews")
    public String news(){
        return "pushNews";
    }

    @RequestMapping(value="/realTimeNews")
    @ResponseBody
    /*在WebInitializer中要加上servlet.setAsyncSupported(true);*/
    public DeferredResult<String> realtimeNews(HttpServletRequest request){
        final DeferredResult<String> dr = new DeferredResult<String>();

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int index = new Random().nextInt(Const.NEWS.length);
                dr.setResult(Const.NEWS[index]);
            }
        });
        return dr;
    }

}
