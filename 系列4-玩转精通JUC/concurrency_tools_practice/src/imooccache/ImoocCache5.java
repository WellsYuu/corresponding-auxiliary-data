package imooccache;

import imooccache.computable.Computable;
import imooccache.computable.ExpensiveFunction;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述：     缩小了synchronized的粒度，提高性能，但是依然并发不安全
 */
public class ImoocCache5<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public ImoocCache5(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws Exception {
        System.out.println("进入缓存机制");
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        ImoocCache5<String, Integer> expensiveComputer = new ImoocCache5<>(
                new ExpensiveFunction());
        Integer result = expensiveComputer.compute("666");
        System.out.println("第一次计算结果：" + result);
        result = expensiveComputer.compute("666");
        System.out.println("第二次计算结果：" + result);
    }
}
