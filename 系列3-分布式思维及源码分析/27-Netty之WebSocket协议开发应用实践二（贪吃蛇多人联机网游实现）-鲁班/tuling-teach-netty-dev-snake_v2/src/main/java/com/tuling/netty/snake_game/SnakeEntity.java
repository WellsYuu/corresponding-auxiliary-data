package com.tuling.netty.snake_game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static com.tuling.netty.snake_game.SnakeEntity.Direction.*;

/**
 * Created by Tommy on 2018/1/8.
 */
public class SnakeEntity {
    static final Logger logger = LoggerFactory.getLogger(SnakeEntity.class);

    private  int startPoint;
    private  int initBodyCount;
    private final SnakeGameEngine engine;
    private String accountId;
    private String gameName;
    private volatile Direction direction; // 方向
    private volatile Direction lastChangeDirection=null; // 最后一次变更的方向
    private ArrayList<Integer[]> bodys = new ArrayList<>();//y,x
    private ArrayList<Integer[]> addNodes = new ArrayList<>(); // 当前地图上添加的临时节点
    private ArrayList<Integer[]> removeNodes = new ArrayList<>();//当前地图上移除的临时节点
    private int dieIntegral; //积分
    private int killIntegral=0;// 击杀角色数
    private final int bodyNodeSize=3;//  节点大小

    public State state;

    public enum State {
        /**
         * 待激活
         */
        inactive,
        /**
         * 正常存活
         */
        alive,
        /**
         * 生长
         */
        grow,
        /**
         * 待死亡
         */
        dying,
        /**
         * 死亡
         */
        die,
        /*
        *已离线
        * */
        offline;
    }

    public enum Direction {
        left(37), up(38), right(39), down(40);
        int code;
        Direction(int code){
            this.code=code;
        }

        public static Direction toDirection(int code) {
            switch (code) {
                case 37:
                    return Direction.left;
                case 38:
                    return Direction.up;
                case 39:
                    return Direction.right;
                case 40:
                    return Direction.down;
                default:
                    throw new RuntimeException("error Code:" + code);
            }
        }
    }

    public SnakeEntity(SnakeGameEngine engine, String accountId, int startPoint, int initBodyCount, Direction defaultDirection) {
        this.engine = engine;
        this.accountId = accountId;
        this.direction = defaultDirection;
        state = State.inactive;
        this.startPoint = startPoint;
        this.initBodyCount = initBodyCount;

    }
    /**
     * 复活
     * @param startPoint 复活点位
     * @param initBodySzie 复活初始大小
     */
    public void resurgence(int startPoint,int initBodySzie) {
        if (!isDie()) {
            throw new RuntimeException("未达到复活条件,角色必须为死亡状态");
        }
        state = State.inactive;
        this.startPoint = startPoint;
        this.initBodyCount = initBodySzie;
    }
    public void setDirection(Direction direction) {
        for (Direction oldDirection : new Direction[]{this.direction, lastChangeDirection}) {
            // 无效指令验证 .
            if (oldDirection == Direction.up && direction == Direction.down)
                return;
            if (oldDirection == Direction.down && direction == Direction.up)
                return;
            if (oldDirection == Direction.left && direction == Direction.right)
                return;
            if (oldDirection == Direction.right && direction == Direction.left)
                return;
        }
        logger.debug("改变snake 移动方向 ID:{},当前方向:{}", accountId, direction.toString());
        this.direction = direction;
    }

    // 上前前动一步
    public void moveStep() {
        addLineToHead();
        removeLineToTail();
    }


    public void removeLineToTail() {
        for (int i = 0; i < bodyNodeSize && !bodys.isEmpty(); i++) {
            Integer[] node = bodys.remove(bodys.size() - 1);
            if (engine.isMapRange(node)) {
                removeNodes.add(node);
                engine.getMark(node).snakeNodes--;
            }
        }
    }

    //

    /**
     * 在当前方向上添加一行节点
     * ,如果当前{#bodys}当前节点为空则,则基于{#startPoint} 进行追加
     */
    public void addLineToHead(){
        Integer[] refer = null;
        Direction direction = this.direction;
        if (bodys.isEmpty()) {
            refer = new Integer[]{startPoint, startPoint - 1};
        } else {

            // 转向条件条件验证
            if (lastChangeDirection != direction) {
                boolean turnCondition=false;
                if (lastChangeDirection == left || lastChangeDirection == right) { // 左右
                    turnCondition = bodys.get(0)[0] == bodys.get(bodyNodeSize * bodyNodeSize - bodyNodeSize)[0];
                } else if(lastChangeDirection ==down || lastChangeDirection == up){ // 上下
                    turnCondition = bodys.get(0)[1] == bodys.get(bodyNodeSize * bodyNodeSize - bodyNodeSize)[1];
                }
                // 转向条件不足,强制按原方向直行
                if (!turnCondition) {
                    direction=lastChangeDirection;
                }
            }
            //   计算当前行走的方向
            int turn = lastChangeDirection == null ? 0 :
                    direction.code - lastChangeDirection.code;
            if (turn == 0) { //直行
                refer = bodys.get(bodyNodeSize - 1);
            } else if (turn == -1 || turn == 3) {// 正在向左转
//                refer = bodys.get(bodyNodeSize * bodyNodeSize - bodyNodeSize);
                refer = bodys.get(0);
            } else if (turn == 1 || turn == -3) { // 正在向右转
                refer = bodys.get(bodyNodeSize * bodyNodeSize - 1);
            }
        }


        Integer[] first =addNode(refer,direction);

        if (bodyNodeSize > 1) {
            for (int i = 0; i < bodyNodeSize - 1; i++) {
                switch (direction) {
                    case left:
                        first = addNode(first, down);
                        break;
                    case up:
                        first = addNode(first, left);
                        break;
                    case right:
                        first = addNode(first, up);
                        break;
                    case down:
                        first = addNode(first, right);
                        break;
                    default:break;
                }
            }
        }

        lastChangeDirection=direction;
    }

    private Integer[] addNode(Integer[] refer, Direction direction) {
        Integer[] newNode;
        switch (direction) {
            case up:
                newNode = new Integer[]{refer[0] - 1, refer[1]};
                break;
            case down:
                newNode = new Integer[]{refer[0] + 1, refer[1]};
                break;
            case left:
                newNode = new Integer[]{refer[0], refer[1] - 1};
                break;
            case right:
                newNode = new Integer[]{refer[0], refer[1] + 1};
                break;
            default:
                throw new RuntimeException("direction must not null");
        }


        bodys.add(0, newNode);
        if (engine.isMapRange(newNode)) {
            engine.getMark(newNode).snakeNodes++;
            addNodes.add(newNode);
        }
        logger.debug("添加节点 x:{} y:{}", newNode[1], newNode[0]);
        return newNode;
    }

    private void add(int index, Integer[] point) {

    }

    public String getAccountId() {
        return accountId;
    }

    public Direction getDirection() {
        return direction;
    }

    public ArrayList<Integer[]> getBodys() {
        return bodys;
    }

    public ArrayList<Integer[]> getAddNodes() {
        return addNodes;
    }

    public ArrayList<Integer[]> getRemoveNodes() {
        return removeNodes;
    }


    public State getState() {
        return state;
    }

    // 增加击杀积分
    public int addKillIntegral(){
       return ++killIntegral;
    }

    // 生长一节
    public void grow() {
        this.state = State.grow;
        logger.info(" id:{} name:{}", accountId, gameName);
    }

    public void alive() {
        this.state = State.alive;
    }

    public void die() {
        int bodySize = bodys.size();
        for (int i = 0; i < bodySize; i++) {
            removeLineToTail();
        }
        this.state = State.die;
        dieIntegral++; // 增加死亡积分
        logger.info("清除死亡角色 id:{} name:{}", accountId, gameName);
    }

    public void dying() {
        this.state = State.dying;
        logger.info("角色死亡 id:{} name:{}", accountId, gameName);
    }

    public void offline(){
        this.state=State.offline;
        logger.info("角色掉线 id:{} name:{}", accountId, gameName);
    }

    public boolean isOffline() {
        return this.state == State.offline;
    }

    public boolean isDie() {
        return this.state == State.die;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void flush() {
        addNodes.clear();
        removeNodes.clear();
    }

    public void active() {
        // 添加蛇身
        for (int i = 0; i < initBodyCount *bodyNodeSize; i++) {
            addLineToHead();
        }
        this.state = State.alive;
    }
    // 获取头部位点
    protected Integer[] getHead() {
        if (bodys.isEmpty()) {
            return null;
        }
        return bodys.get(0);
    }

    public int getDieIntegral() {
        return dieIntegral;
    }

    public int getKillIntegral() {
        return killIntegral;
    }

    @Override
    public String toString() {
        return "accountId='" + accountId + '\'' +
                ", gameName='" + gameName + '\'' +
                ", state=" + state +
                ",direction=" + direction;
    }
}
