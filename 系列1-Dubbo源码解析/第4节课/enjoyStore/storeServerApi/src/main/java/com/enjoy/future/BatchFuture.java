package com.enjoy.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 花呗审核贷款人资质时，需要汇总借款人在第三方银行是否有欠款
 * 需要向每个银行接口，发起一次查询
 * 银行名单会不断增加
 * 希望总查询时间可控----不会在后续名单较多时，延时太长
 * a-b-c-d
 */
public class BatchFuture {
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static List<String> banks = new ArrayList();
    //银行名单
    static {
        banks.add("建设银行");
        banks.add("工商银行");
        banks.add("招商银行");
        banks.add("农业银行");
        banks.add("北京银行");
        banks.add("浦发银行");
        banks.add("光大银行");
        banks.add("兴业银行");
        banks.add("民生银行");
        banks.add("建设银行");
        banks.add("工商银行");
        banks.add("招商银行");
        banks.add("农业银行");
        banks.add("北京银行");
        banks.add("浦发银行");
        banks.add("光大银行");
        banks.add("兴业银行");
        banks.add("民生银行");
    }

    public static void main(String[] args) {
        String person = "张三";

        long start = System.currentTimeMillis();

        try {
            long money = doFutuerBatch(person,banks);
            System.out.println();
            System.out.println(person+"总共欠外债："+money + "元");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("本次查询，耗时："+(System.currentTimeMillis() - start) + "毫秒");
    }

    private static long doFutuerBatch(String person,List<String> taskBatchs) throws ExecutionException, InterruptedException {
        List<Future<Integer>> futures = new ArrayList<>();
        for (final String task : taskBatchs) {
            //现实中，不同的银行有不同的Task实体对象-----在此暂用相同的对象
            Task bankTask = new Task(person,task);

            Future<Integer> future = executorService.submit(bankTask);
            futures.add(future);
        }

        long money = 0;
        for (Future<Integer> future : futures) {
            // 判断任务是否已全部完成。
            Integer ret = future.get();
            money += ret;
        }

        executorService.shutdown();
        return money;
    }



}
