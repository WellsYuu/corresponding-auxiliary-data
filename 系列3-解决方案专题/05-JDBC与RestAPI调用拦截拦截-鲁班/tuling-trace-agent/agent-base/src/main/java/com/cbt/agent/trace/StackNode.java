package com.cbt.agent.trace;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StackNode implements java.io.Serializable {
        String id;
        private long classId;
        private String className;
        private String methodName;
        private final  List<Integer> lines = new LinkedList<Integer>();
        boolean done;

        //======已下字段在序列化时将被勿略
        transient StackNode parent;
        transient final List<StackNode> childs = new ArrayList<StackNode>(20);
        transient Throwable error;
        transient StackSession stackSession;

        public StackNode() {

        }

        public StackNode(Long classId, String className, String methodName) {
                super();
                this.classId = classId;
                this.className = className;
                this.methodName = methodName;
        }

        public String getId() {
                return id;
        }

        public long getClassId() {
                return classId;
        }

        public String getClassName() {
                return className;
        }

        public String getMethodName() {
                return methodName;
        }

        public List<Integer> getLines() {
                return lines;
        }

        public boolean isDone() {
                return done;
        }

        public Throwable getError() {
                return error;
        }

        public void setError(Throwable error) {
                this.error = error;
        }

        @Override public boolean equals(Object obj) {
                // 添加执行行号
                if (obj instanceof Integer) {
                        lines.add((Integer) obj);
                        stackSession.setHotStack(this);
                        return false;
                }
                return super.equals(obj);
        }

        @Override public String toString() {

                StringBuilder builder = new StringBuilder();
                if (id != null) {
                        builder.append(id);
                        builder.append(" ");
                }
                if (!done) {
                        builder.append("[ERROR]");
                }
                if (className != null) {
                        builder.append(className);
                        builder.append(".");
                }
                if (methodName != null) {
                        builder.append(methodName);
                        builder.append("()");
                }
                if (lines != null) {
                        builder.append("[");
                        for (Integer i : lines) {
                                builder.append(",");
                                builder.append(i);
                                builder.append("L");
                        }
                        builder.append("]");
                }
                if (error != null) {
                        builder.append(" ");
                        builder.append(error.getClass().getSimpleName());
                        builder.append(":");
                        builder.append(error.getMessage());
                }
                return builder.toString();
        }
}
