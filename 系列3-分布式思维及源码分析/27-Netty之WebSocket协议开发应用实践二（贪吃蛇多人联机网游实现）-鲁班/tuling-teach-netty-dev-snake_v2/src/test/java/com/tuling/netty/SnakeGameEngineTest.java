package com.tuling.netty;

import com.alibaba.fastjson.JSON;
import com.tuling.netty.snake_game.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 2018/1/9.
 */
public class SnakeGameEngineTest {

    @Test
    public void startTest() throws InterruptedException {
        SnakeGameEngine engine = new SnakeGameEngine(500, 500, 1000);
        engine.start();
        engine.newSnake("test1","test1");
        engine.newSnake("test2","test2");
        engine.newSnake("test3","test3");
        for (int i = 0; i < 10; i++) {
            Thread.currentThread().sleep(1100L);
            System.out.println("当前版本----" + engine.getCurrentVersion());
            /*System.out.println(pointsToStrng(engine.getCurentAddPoints(), "增加"));
            System.out.println(pointsToStrng(engine.getCurentRemovePoints(), "移除"));*/
            System.out.println(pointsToStrng(engine.getAllPoint(), "所有位点"));
            System.out.println();
        }
    }

    @Test
    public void controlTest() throws InterruptedException, IOException {
        SnakeGameEngine engine = new SnakeGameEngine(500, 500, 5000);
        engine.start();
        SnakeEntity snake = engine.newSnake("test1","test1");
        engine.setListener(new SnakeGameEngine.SnakeGameListener() {
            @Override
            public void versionChange(VersionData changeData, VersionData currentData) {
                // 输出变更版本
                System.out.println("定量版本："+JSON.toJSONString(changeData));
            }

            @Override
            public void statusChange(GameStatistics statistics) {

            }

            @Override
            public void noticeEvent(GameEvent[] events) {

            }


        });
        while (true) {
            byte[] bytes = new byte[1024];
            int size = System.in.read(bytes);
            String cmd = new String(bytes, 0, size).trim();
            engine.controlSnake(snake.getAccountId(), Integer.parseInt(cmd));
            System.out.println("全量版本："+JSON.toJSONString(engine.getCurrentMapData(false)));
            System.out.println("角色信息："+snake.toString());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new SnakeGameEngineTest().controlTest();
    }


    private String pointsToStrng(ArrayList<Integer[]> points, String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix + " ");
        for (Integer[] point : points) {
            sb.append(String.format("y:%s,x:%s", point[0], point[1]));
            sb.append(" ");
        }
        return sb.toString();
    }
}
