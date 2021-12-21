package com.cbt.agent.trace;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StackSession {
    static final int MAX_SZIE = 3000;
    private StackNode rootNode;
    private StackNode hotNode;
    private int size = 0;
    private int errorSize = 0;

    public StackSession(StackNode rootNode) {
        size = 1;
        rootNode.id = "0";
        this.rootNode = rootNode;
        hotNode = rootNode;
    }

    public void close() {
        rootNode.done = true;
    }

    /**
     * 添加节点
     * @param node
     * @return
     */
    public StackNode addNode(StackNode node) {
        if (size >= MAX_SZIE) {
            size++;
            return null;
        }
        hotNode.childs.add(node);
        node.stackSession=this;
        node.parent = hotNode;
        node.id = hotNode.id + "." + hotNode.childs.size();
        size++;
        hotNode = node;
        return node;
    }

    public void doneNode(StackNode node) {
        node.done = true;
        node.error = null;
        hotNode = node.parent;
    }

    public StackNode getHotStack() {
        return hotNode;
    }

    protected StackNode setHotStack(StackNode hot) {
        return hotNode = hot;
    }

    public void printStack(PrintStream out) {
        print(rootNode, out);
    }

    // 递归打印堆栈节点
    private void print(StackNode node, PrintStream out) {
        out.println(node.toString());
        for (StackNode n : node.childs) {
            print(n, out);
        }
    }
    public List<StackNode> getAllNodes(){
        List<StackNode> result=new ArrayList<>(size);
        result.add(rootNode);
        putNodes(rootNode,result);
        return result;
    }
    private void putNodes(StackNode parent, List<StackNode> list) {
        for (StackNode node : parent.childs) {
            list.add(node);
            putNodes(node,list);
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Throwable) {
            // TODO 暂时无法实现
            /*
             * StackNode node = nodes[hotIndex]; while (!node.done && node !=
             * nodes[0]) { node.error = (Throwable) obj; node =
             * nodes[node.parentIndex]; }
             */
            errorSize++;
            // throwables[errorSize++]= (Throwable) obj;
            return false;
        }

        /**
         *通过一个4个长度的数组对象来添加堆栈节点。
         *通过数组下标【0】 来返回一个新的堆栈节点对象
         *@param [1]=long classId, [2]=String className, [3]=String methodName
         *@return [0]= Object stackNode
         */
        else if (obj instanceof Object[]) {
            Object params[] = (Object[]) obj;
            params[0] = addNode(new StackNode((Long) params[1], (String) params[2], (String) params[3]));
            params[0] = params[0] == null ? new Object() : params[0];
            return false;
        }
        /*
         * 结束节点
         */
        else if (obj instanceof StackNode) {
            doneNode((StackNode) obj);
            return false;
        }
        return super.equals(obj);
    }
}
