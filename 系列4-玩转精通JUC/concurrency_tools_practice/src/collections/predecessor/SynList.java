package collections.predecessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：     演示Collections.synchronizedList(new ArrayList<E>())
 */
public class SynList {
    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
        list.add(5);
        System.out.println(list.get(0));

        Map<Object, Object> objectObjectMap = Collections.synchronizedMap(new HashMap<>());
    }
}
