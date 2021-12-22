package com.tuling.netty.snake_game;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 蛇蛇游戏引擎
 * Created by Tommy on 2018/1/9.
 */
public class SnakeGameEngine {
    static final Logger logger = LoggerFactory.getLogger(SnakeGameEngine.class);
    public Map<String, SnakeEntity> snakes = new HashMap<>();
    private final int mapWidth;
    private final int mapHeight;

    // 存储了地图上所有的节点
    private final Mark mapsMarks[];
    // 刷新间隔(毫秒)
    private final int refreshTime;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
    private ScheduledExecutorService stateService = Executors.newScheduledThreadPool(1);

    private ScheduledFuture<?> mapFuture;
    private SnakeGameListener listener;
    private Long currentVersion = 0L;
    private volatile LinkedList<VersionData> historyVersionData = new LinkedList();
    private volatile VersionData currentMapData = null;
    private static final int historyVersionMax = 20;
    private ArrayList<Food> foods = new ArrayList<>();
    private int footMaxSize = 10;
    private ScheduledFuture<?> stateFuture;
    // 事件对列
    private LinkedList<GameEvent> eventQueue = new LinkedList();

    public SnakeGameEngine() {
        mapWidth = 400;
        mapHeight = 300;
        refreshTime = 200;
        mapsMarks = new Mark[mapWidth * mapHeight];
    }

    public SnakeGameEngine(int mapWidth, int mapHeight, int refreshTime) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.refreshTime = refreshTime;
        mapsMarks = new Mark[mapWidth * mapHeight];
    }


    // 启动
    public void start() {
        logger.info("游戏引擎启动...");
        // 固定间隔执行任务
        mapFuture = executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                gameTimeStep();
            }
        }, refreshTime, refreshTime, TimeUnit.MILLISECONDS);

        // 状态信息更新
        stateFuture = stateService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                // 触发状态变更事件
                fireStateChange();
                fireNoticeEvent();
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    private void fireNoticeEvent() {
        if (listener == null)
            return;
        if (eventQueue.isEmpty())
            return;
        GameEvent[] events = eventQueue.toArray(new GameEvent[eventQueue.size()]);
        listener.noticeEvent(events);
        // TODO 可能为引发线程同步的问题
        for (GameEvent event : events) {
            eventQueue.remove(event);
        }
    }

    private void fireStateChange() {
        if (listener != null) {
            GameStatistics statistics = new GameStatistics();
            statistics.setLastVersion(currentVersion);
            // TODO 在线人数需除去离线角色
            statistics.setOnlineCount(snakes.size());
            statistics.setRankingList(getRankingList());
            listener.statusChange(statistics);
        }
    }

    //animate
    public void gameTimeStep() {
        try {
            build();
        } catch (Throwable e) {
            logger.error("地图构建异常", e);
        } finally {
            afterBuild();
        }
    }

    private void build() {
        /**
         * 基于状态执行算法
         */
        for (SnakeEntity snake : snakes.values()) {
            switch (snake.getState()) {
                case inactive:
                    logger.info("激活新角色:{}当前版本:{}", snake.toString(), currentVersion + 1);
                    snake.active();
                    break;
                case alive:
                    snake.moveStep();
                    break;
                case grow:// TODO 需重构成 先进食后消化
                    snake.addToHead();
                    snake.alive(); //  增涨后恢复为普通状态
//                    snake.moveStep();
                    break;
                case dying:
                    snake.die();
                    // 生成死亡事件并通知客户端
                    GameEvent event = new GameEvent(GameEvent.EventType.die, "角色死亡");
                    event.setAccountId(snake.getAccountId());
                    eventQueue.addFirst(event);
                    break;
                case die:   //角色已经死亡
                    break;
                case offline:   //角色已经离线
                    break;
            }
        }

        // 当前版本 新增的节点
        ArrayList<Integer[]> changeNodes = new ArrayList<>();


        /**
         * 执行触发的游戏规则
         */
        for (SnakeEntity snake : snakes.values()) {
            if(snake.isOffline())
                continue;
            //断定蛇头是否撞击边界
            if (!snake.isDie() && !isMapRange(snake.getHead())) {
                snake.dying();
            }
            for (Integer[] node : snake.getAddNodes()) {
                if (getMark(node).snakeNodes > 1) { // 是否撞击蛇身
                    // 死亡规则触发
                    snake.dying();
                    // 击杀规则触发
                    killSnake(snake, node);
                } else if (getMark(node).footNode > 0) {// 吃掉食物
                    digestionFood(snake, node);
                }
            }
            changeNodes.addAll(snake.getAddNodes());
            changeNodes.addAll(snake.getRemoveNodes());
        }


        // 投放规定量食物
        while (foods.size() < footMaxSize) {
            Food food = grantFood();
            changeNodes.add(food.point);
        }


        // 如果变更不为空，则创建新的版本号
        if (!changeNodes.isEmpty()) {
            /**
             * 编码新版本数据
             */
            long newVersion = currentVersion + 1;


            /**
             * 版本归档存储
             */
            while (historyVersionData.size() >= historyVersionMax) {
                historyVersionData.removeLast();
            }
            VersionData changeData = encodeVersion(newVersion, changeNodes);
            historyVersionData.addFirst(changeData);

            /**
             * 变更版本号
             */
            currentVersion = newVersion;
            // 刷新整体地图
            getCurrentMapData(true);
            /**
             * 通知版本变更
             */
            if (listener != null) {
                try {
                    listener.versionChange(changeData, null);
                } catch (Exception e) {
                    logger.error("版本变更通知失败", e);
                }
            }

        }
    }

    /**
     * @param die       被击杀角色数
     * @param killPoint 击杀点位
     */
    private void killSnake(SnakeEntity die, Integer[] killPoint) {
        // 找出击杀点位下所有角色
        List<SnakeEntity> list = getSnakeByNode(killPoint);
        list.remove(die); // 移除角色自身
        SnakeEntity killer = null;
        for (SnakeEntity snakeEntity : list) {
            if (Arrays.equals(killPoint, snakeEntity.getHead()))
                continue;
            killer = snakeEntity;
            break;
        }

        if (killer != null) {
            killer.addKillIntegral();// 增加角色击杀积分
            logger.info("{}击杀{}", killer.getGameName(), die.getGameName());
        }
    }

    private List<SnakeEntity> getSnakeByNode(Integer[] node) {
        List<SnakeEntity> result = new ArrayList<>();
        for (SnakeEntity snakeEntity : snakes.values()) {
            for (Integer[] integers : snakeEntity.getBodys()) {
                if (Arrays.equals(integers, node)) {
                    result.add(snakeEntity);
                    break;
                }
            }
        }
        return result;
    }

    private void afterBuild() {
        for (SnakeEntity snake : snakes.values()) {
            snake.flush();
        }
    }

    public Food grantFood() {
        // 随机生成的投放点
        int releasePoint = -1;

        Random random = new Random();
        int start = random.nextInt(mapHeight * mapHeight - 5) + 4;
        int nextCount = random.nextInt(50);

        // i 查找开始位置
        // n 从开始位置起的第 n个空位,如果所有空位没有n个，则往后减
        // m 遍历的最大值,防止无限循环
        for (int i = start, n = 0, m = 0; i < mapsMarks.length
                && m < mapsMarks.length; i++, m++) {
            if (mapsMarks[i] == null || mapsMarks[i].isEmpty()) {
                n++;
                releasePoint = i;
                if (n >= nextCount) {
                    break;
                }
            }
            // 重新从0开始遍历
            if (i == mapsMarks.length - 1) {
                i = 0;
            }
        }
        if (releasePoint > -1) {
            Integer[] point = new Integer[]{releasePoint / mapWidth, releasePoint % mapWidth};
            Food food = new Food(point, 1);
            foods.add(food);
            getMark(point).footNode = 1;
            return food;
        } else {
            throw new RuntimeException("投食失败。无法找到空位投食");
        }
    }

    // 吃掉食物
    private Food digestionFood(SnakeEntity snake, Integer[] point) {
        Food food = null;
        for (Food f : foods) {
            if (Arrays.equals(f.point, point)) {
                food = f;
                break;
            }
        }
        if (food == null) {
            throw new RuntimeException(
                    String.format("消化食物异常，坐标上不存在指定食物x:%s,y:%s", point[1], point[00]));
        }

        foods.remove(food); // 从食物列表中移除
        getMark(point).footNode = 0;// 清除地图中食物标记状态
        snake.grow();// 指定角色为增长状态
        logger.info("吃掉食物 位置信息：x={},y={},角色信息:{}", point[1], point[0], snake.toString());
        return food;
    }


    // 停止运行中的地图
    public void stop() {
        if (mapFuture != null && !mapFuture.isCancelled()) {
            mapFuture.cancel(false);
        }
        if (stateFuture != null && !stateFuture.isCancelled()) {
            stateFuture.cancel(false);
        }
    }
    //TODO BUG 出生点位 可能已经被占用
    public SnakeEntity newSnake(String accountId, String accountName) {
        int max = Math.min(mapWidth, mapHeight) - 10;
        int min = 10;
        Random random = new Random();
        // 随机生成 出生点位
        int startPoint = random.nextInt(max - min + 1) + min;
        SnakeEntity node = new SnakeEntity(this, accountId, startPoint,
                3, SnakeEntity.Direction.right);
        String gameName=accountName;
        // 防重名机制,补充accountId为后缀
        for (SnakeEntity snakeEntity : snakes.values()) {
            if(accountName.equals(snakeEntity.getGameName())){
                gameName=gameName+"_"+accountId;
                break;
            }
        }
        node.setGameName(gameName);

        snakes.put(node.getAccountId(), node);
        this.logger.info("新增Snake ID:{} 出生点位:{} 初始节点:{}", accountId, startPoint, 3);
        return node;
    }

    public void controlSnake(String accountId, int controlCode) {
        if (!snakes.containsKey(accountId)) {
//            logger.warn("找不到指定帐户");
            return;
        }

        SnakeEntity snake = snakes.get(accountId);
        if(snake.isDie()){
            return;
        }
        switch (controlCode) {
            case 37:
                snake.setDirection(SnakeEntity.Direction.left);
                break;
            case 38:
                snake.setDirection(SnakeEntity.Direction.up);
                break;
            case 39:
                snake.setDirection(SnakeEntity.Direction.right);
                break;
            case 40:
                snake.setDirection(SnakeEntity.Direction.down);
                break;
        }
        logger.debug("指令控制 ID:{},指令:{}", accountId, controlCode);
    }

    public Mark getMark(Integer[] point) {
        int index = point[0] * mapWidth + point[1];
        if (index < 0 || index >= mapsMarks.length) {
            throw new IllegalArgumentException(String
                    .format("超出地图边界x=%s,y=%s", point[1], point[0]));
        }
        return getMark(index);
    }

    /**
     * 是否在地图边界内？
     *
     * @param point
     * @return
     */
    public boolean isMapRange(Integer[] point) {
        return point[0] >= 0 && point[0] < mapHeight && point[1] >= 0 && point[1] < mapWidth;
    }

    private Mark getMark(int index) {
        if (mapsMarks[index] == null) {
            mapsMarks[index] = new Mark();
        }
        return mapsMarks[index];
    }

    // 获取当前所有位点
    public ArrayList<Integer[]> getAllPoint() {
        ArrayList<Integer[]> allPoints = new ArrayList<>(2000);
        Integer x, y;
        for (int i = 0; i < mapsMarks.length; i++) {
            if (mapsMarks[i] != null && !mapsMarks[i].isEmpty()) {
                x = i % mapWidth;
                y = i / mapWidth;
                allPoints.add(new Integer[]{y, x});
            }
        }
        return allPoints;
    }

    // 构建当前地图所有的像素
    private VersionData encodeCurrentMapData() {
        StringBuilder body = new StringBuilder();
        StringBuilder food = new StringBuilder();
        Mark mark;
        int x, y;
        for (int i = 0; i < mapsMarks.length; i++) {
            mark = mapsMarks[i];
            x = i % mapWidth;
            y = i / mapWidth;
            if (mark != null && mark.snakeNodes > 0) {
                body.append("," + x + "," + y);
            } else if (mark != null && mark.footNode > 0) {
                food.append("," + x + "," + y);
            }
        }
        List<String> cmds = new ArrayList();
        List<String> cmdDatas = new ArrayList();

        // 去掉前缀 中的逗号
        if (body.length() > 0) {
            body.deleteCharAt(0);
            cmds.add("Green");
            cmdDatas.add(body.toString());
        }
        if (food.length() > 0) {
            food.deleteCharAt(0);
            cmds.add("Yellow");
            cmdDatas.add(food.toString());
        }
        VersionData vd = new VersionData(currentVersion, System.currentTimeMillis());
        vd.setCmds(cmds.toArray(new String[cmds.size()]));
        vd.setCmdDatas(cmdDatas.toArray(new String[cmdDatas.size()]));
        vd.setFull(true);
        return vd;
    }

    // 构建当前版本地图像素的变更
    private VersionData encodeVersion(long version, ArrayList<Integer[]> changePoints) {
        StringBuilder body = new StringBuilder();
        StringBuilder food = new StringBuilder();
        StringBuilder remove = new StringBuilder();
        Mark mark;
        for (Integer[] p : changePoints) {
            mark = getMark(p);
            if (mark == null || mark.isEmpty()) {
                remove.append("," + p[1] + "," + p[0]);
            } else if (mark.snakeNodes > 0) {
                body.append("," + p[1] + "," + p[0]);
            } else if (mark.footNode > 0) {
                food.append("," + p[1] + "," + p[0]);
            }
        }
        List<String> cmds = new ArrayList();
        List<String> cmdDatas = new ArrayList();
        // 去掉前缀 中的逗号
        if (body.length() > 0) {
            body.deleteCharAt(0);
            cmds.add("Green");
            cmdDatas.add(body.toString());
        }
        if (food.length() > 0) {
            food.deleteCharAt(0);
            cmds.add("Yellow");
            cmdDatas.add(food.toString());
        }
        if (remove.length() > 0) {
            remove.deleteCharAt(0);
            cmds.add("Black");
            cmdDatas.add(remove.toString());
        }

        VersionData vd = new VersionData(version, System.currentTimeMillis());
        vd.setCmds(cmds.toArray(new String[cmds.size()]));
        vd.setCmdDatas(cmdDatas.toArray(new String[cmdDatas.size()]));
        vd.setFull(false);
        return vd;
    }


    public VersionData getCurrentMapData(boolean check) {
        if (currentMapData == null) {
            currentMapData = encodeCurrentMapData();
        } else if (check && currentMapData.getVersion() < currentVersion) {
            currentMapData = encodeCurrentMapData();
        }
        return currentMapData;
    }

    public List<VersionData> getVersion(Long[] versionId) {
        List<VersionData> list = new ArrayList<>();
        VersionData[] historys = historyVersionData.toArray(new VersionData[list.size()]);
        for (VersionData historyVersion : historys) {
            for (long v : versionId) {
                if (historyVersion.getVersion() == v) {
                    list.add(historyVersion);
                }
            }
        }
        return list;
    }

    public DrawingCommand getDrawingCommand(String accountId) {
        DrawingCommand cmd = null;
        if (!snakes.containsKey(accountId)) {
            return cmd;
        }
        SnakeEntity snake = snakes.get(accountId);
        Integer[] head = snake.getHead();
        if (head != null)
            cmd = new DrawingCommand("Lime", head[1] + "," + head[0]);
        return cmd;
    }


    /**
     * 获取积分榜 前10位
     *
     * @return
     */
    public List<IntegralInfo> getRankingList() {
        List<IntegralInfo> result = new ArrayList<>(10);
        List<SnakeEntity> list = new ArrayList<>(snakes.size());
        list.addAll(snakes.values());
        Collections.sort(list, new Comparator<SnakeEntity>() {
            @Override
            public int compare(SnakeEntity o1, SnakeEntity o2) {
                return o1.getKillIntegral() - o2.getKillIntegral();
            }
        });
        IntegralInfo info;
        for (int i = 0; i < list.size() && i < 10; i++) {
            info = new IntegralInfo();
            info.setLastVersion(currentVersion);
            info.setGameName(list.get(i).getGameName());
            info.setAccountId(list.get(i).getAccountId());
            info.setDieIntegral(list.get(i).getDieIntegral());
            info.setKillIntegral(list.get(i).getKillIntegral());
            result.add(info);
        }
        return result;
    }

    public SnakeEntity getSnakeByAccountId(String accountId) {
        if (!snakes.containsKey(accountId)) {
//            logger.warn("找不到指定帐户");
            return null;
        }
        return snakes.get(accountId);
    }

    public IntegralInfo getIntegralInfoByAccountId(String accountId) {
        if (!snakes.containsKey(accountId)) {
//            logger.warn("找不到指定帐户");
            return null;
        }
        SnakeEntity snake = snakes.get(accountId);
        IntegralInfo info = new IntegralInfo();
        info.setLastVersion(currentVersion);
        info.setGameName(snake.getGameName());
        info.setAccountId(snake.getAccountId());
        info.setDieIntegral(snake.getDieIntegral());
        info.setKillIntegral(snake.getKillIntegral());
        return info;
    }


    public void setListener(SnakeGameListener listener) {
        this.listener = listener;
    }

    public Long getCurrentVersion() {
        return currentVersion;
    }
    //TODO BUG 复活点位 可能已经被占用
    public void doResurgence(String accountId) {
        if(!snakes.containsKey(accountId)){
            this.logger.warn("角色复活失败，找不到指定帐户 ID:{}", accountId);
            return ;
        }
        if (!snakes.get(accountId).isDie()) {
            this.logger.warn("角色复活失败，必须为死亡状态 ID:{}", accountId);
            return;
        }
        int max = Math.min(mapWidth, mapHeight) - 10;
        int min = 10;
        Random random = new Random();
        // 随机生成 出生点位
        int startPoint = random.nextInt(max - min + 1) + min;
        snakes.get(accountId).resurgence(startPoint,3);
        this.logger.info("角色复活 ID:{} 出生点位:{} 初始节点:{}", accountId, startPoint, 3);
    }

    // 地图标记位
    static class Mark {
        public int snakeNodes = 0;
        public int footNode = 0;//1,2,3,4,5

        private boolean isEmpty() {
            return snakeNodes <= 0 && footNode <= 0;
        }
    }


    private static class Food {
        // 当前位位置
        private Integer[] point;
        private int type;

        public Food(Integer[] point, int type) {
            this.point = point;
            this.type = type;
        }
    }

    public static interface SnakeGameListener {
        /**
         * 地图版本变更
         *
         * @param changeData
         * @param currentData
         */
        public void versionChange(VersionData changeData, VersionData currentData);

        /**
         * 积分变更
         *
         * @param statistics 积分排行榜
         */
        public void statusChange(GameStatistics statistics);

        /**
         * 事件通知
         *
         * @param events 积分排行榜
         */
        public void noticeEvent(GameEvent[] events);
    }
}
