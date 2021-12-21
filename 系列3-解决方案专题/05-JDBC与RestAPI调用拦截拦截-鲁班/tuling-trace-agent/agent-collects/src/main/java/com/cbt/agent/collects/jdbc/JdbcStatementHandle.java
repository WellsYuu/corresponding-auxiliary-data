package com.cbt.agent.collects.jdbc;

import com.cbt.agent.collect.*;

public class JdbcStatementHandle implements CollectHandle {

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
        return EventType.JDBC;
    }


}
