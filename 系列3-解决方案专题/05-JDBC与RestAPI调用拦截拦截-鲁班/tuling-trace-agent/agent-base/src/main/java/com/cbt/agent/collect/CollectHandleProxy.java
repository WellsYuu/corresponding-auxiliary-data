package com.cbt.agent.collect;

import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceSession;

/**
 * 监控点服务代码
 * 

 */
public class CollectHandleProxy implements CollectHandle {
    private static final Logger logger = LoggerFactory.getLogger(CollectHandleProxy.class);
    private final CollectHandle handle;
    public CollectHandleProxy(CollectHandle handle) {
        this.handle = handle;
    }

    @Override
    public Event invokerBefore(Event event, InParams in) {
        try {
            return handInvokerBefore(event, in);
        } catch (Throwable e) {
            logger.error("调用链执行before方法异常", e);
        }
        return null;
    }

    /**
     * 
     * 对监控方法的末尾进行处理 <br/>
     * 
     * @param event为对应invokerBefore 进行创建。
     * @param out
     * @author zengguangwei@cbt.com
     * @date: 2016年6月15日 下午11:19:56
     * @version 1.0
     * 
     * @return
     */
    @Override
    public void invokerAfter(Event event, OutResult out) {
        try {
            handInvokerAfter(event, out);
        } catch (Throwable e) {
            logger.error("调用链执行after方法异常", e);
        }
    }

    private EventCreate handInvokerBefore(Event event, InParams in) {
        if (!TraceContext.isDefaultOpen()) {
            return null;
        } else if (!TraceContext.getDefault().isActive()) {
            return null;
        }
        // 是否为新的Event
        EventCreate eventCreate = null;
        if (event == null && TraceContext.getDefault().getCurrentSession() != null) {
            event = TraceContext.getDefault().getCurrentSession().getCurrentEvent(handle.getEventType());
        }
        Event newEvent = handle.invokerBefore(event, in);
        if (TraceContext.getDefault().getCurrentSession() != null && newEvent != null && !newEvent.init) {
            // 存储event至当前会话并开始向下传递
            addEvent(TraceContext.getDefault().getCurrentSession(), newEvent);
            eventCreate = new EventCreate(newEvent, new MethodInfo(in.className, in.methodName, null));
        }
        // 只有新发起Event 才会直接通过参数传递至对应 invoker 的After处理方法。并进行销毁.
        return eventCreate;

    }

    private void handInvokerAfter(Event event, OutResult out) {
        if (!TraceContext.isDefaultOpen()) {
            return;
        } else if (!TraceContext.getDefault().isActive()) {
            return;
        }
        TraceSession session = TraceContext.getDefault().getCurrentSession();
        // 当前事件优通过入参传递的Event 获取
        Event currentEvent = null;
        if (event instanceof EventCreate) {
            currentEvent = ((EventCreate) event).getEvent();
        } else if (event != null && event.getClass().equals(Event.class)) {
            currentEvent = event;
        } else if (session != null) {
            currentEvent = session.getCurrentEvent(handle.getEventType());
        }

        try {
            // 通过session 传递event
            handle.invokerAfter(currentEvent, out);
        } finally {
            boolean isClear = false;
            // 会话关闭不需要销毁事件
            if (TraceContext.getDefault().getCurrentSession() == null) {
                isClear = false;
            }
            // 满足方法的执行前创建，执行后销毁条件。
            else if (event instanceof EventCreate && ((EventCreate) event).getEvent().getDestoryMethod() == null) {
                if (((EventCreate) event).getCreateMethod().equals(new MethodInfo(out.className, out.methodName, null))) {
                    isClear = true;
                }
            }
            // 满足通过指定方法来销毁的条件
            else if (currentEvent != null && currentEvent.getDestoryMethod() != null) {
                if (currentEvent.getDestoryMethod().equals(new MethodInfo(out.className, out.methodName, null))) {
                    isClear = true;
                }
            }

            if (isClear) {
                currentEvent.close();
                TraceContext.getDefault().getCurrentSession().clearEvent(currentEvent);
            }
        }
    }

    private void addEvent(TraceSession currentSession, Event event) {
        currentSession.addEvent(event);
        event.init = true;
    }

    @Override
    public EventType getEventType() {
        return handle.getEventType();
    }


    /**
     * 事件创建信息.表示指定事件由哪个方法进行创建.该对象用于创建方法内部进行传递
     * 
     * Created by tommy on 16/7/9.
     */
    private static class EventCreate extends Event {
        private final Event event;
        private final MethodInfo createMethod;

        public EventCreate(Event event, MethodInfo createMethod) {
            super(event.type, event.active);
            this.event = event;
            this.createMethod = createMethod;
        }


        public Event getEvent() {
            return event;
        }

        public MethodInfo getCreateMethod() {
            return createMethod;
        }
    }


}
