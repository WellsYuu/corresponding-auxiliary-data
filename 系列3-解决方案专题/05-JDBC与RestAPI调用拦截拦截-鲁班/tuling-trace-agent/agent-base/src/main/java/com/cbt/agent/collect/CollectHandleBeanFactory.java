package com.cbt.agent.collect;


import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;

/**
 * bean 类初始化
 * 
 *
 */
public class CollectHandleBeanFactory {
    static Logger logger = LoggerFactory.getLogger(CollectHandleBeanFactory.class);

    /**
     * 存放需要初始化class
     */
    @SuppressWarnings("unchecked")
    volatile static Class<CollectHandle>[] CLASSDATE = new Class[100];
    /**
     * 存放对象
     */
    volatile static CollectHandle[] OBJECTS = new CollectHandle[100];

    /**
     * 查找是否创建对象
     * 
     * @param clazz
     * @return
     */
    static CollectHandle find(Class<?> clazz) {
        CollectHandle obj = null;
        for (int i = 0; i < CLASSDATE.length; i++) {
            Class<CollectHandle> c = CLASSDATE[i];
            if (c == null)
                break;
            if (clazz.equals(c)) {
                obj = OBJECTS[i];
                break;
            }
        }
        return obj;
    }

    /**
     * 创建对象
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    static CollectHandle createBean(Class<?> clazz) {
        int index = 0;
        for (int i = 0; i < CLASSDATE.length; i++) {
            Class<CollectHandle> c = CLASSDATE[i];
            if (c == null) {
                index = i;
                break;
            }
        }
        CLASSDATE[index] = (Class<CollectHandle>) clazz;
        CollectHandleProxy obj = new CollectHandleProxy(newInstance(clazz));
        OBJECTS[index] = obj;
        return obj;
    }

    /**
     * 创建对象
     * 
     * @param clazz
     * @return
     */
    static CollectHandle newInstance(Class<?> clazz) {
        try {
            return (CollectHandle) clazz.newInstance();
        } catch (Exception e) {
            System.err.print("new handle instance failed!" + e.getStackTrace());
        }
        return new CollectHandle() {
            @Override
            public Event invokerBefore(Event event, InParams in) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void invokerAfter(Event event, OutResult out) {
                // TODO Auto-generated method stub
            }

            @Override
            public EventType getEventType() {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

    /**
     * 获取代理对象
     * 
     * @param clazz
     * @return
     */
    public static <T> CollectHandle getBean(Class<T> clazz) {
        CollectHandle obj = find(clazz);
        if (obj == null) {
            synchronized (CollectHandleBeanFactory.class) {
                obj = find(clazz);
                if (obj == null) {
                    obj = createBean(clazz);
                }
            }
        }
        return obj;
    }

    public static <T> CollectHandle getBean(String clazz) {
        try {
            return getBean(Class.forName(clazz));
        } catch (ClassNotFoundException e) {
            logger.error(e);
            throw new RuntimeException("采集器加载失败", e);
        }
    }

}
