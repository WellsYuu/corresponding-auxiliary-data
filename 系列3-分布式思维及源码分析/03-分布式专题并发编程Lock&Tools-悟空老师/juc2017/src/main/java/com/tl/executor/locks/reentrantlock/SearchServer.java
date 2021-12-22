package com.tl.executor.locks.reentrantlock;/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * 以往视频加小乔老师QQ：895900009
 * 悟空老师QQ：245553999
 */

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SearchServer<T> {
    //初始化
    private final CharCloumn<T> [] columns=new CharCloumn[Character.MAX_VALUE];
    private final ReentrantReadWriteLock rwl=new ReentrantReadWriteLock();
    private final Lock r=rwl.readLock();
    private final Lock w=rwl.writeLock();

    //百度一下,百度一下
    public void put(T t,String value){
         w.lock();
         try {
             char[] chars = value.toCharArray();
             for (int i = 0; i < chars.length; i++) {
                 char c = chars[i];//百
                 CharCloumn<T> cloumn = columns[c];
                 if (cloumn == null) {
                     cloumn = new CharCloumn<T>();
                     columns[c] = cloumn;
                 }
                 cloumn.add(t, (byte) i);
             }
         }finally {
             w.unlock();
         }

    }
    //先删除
    //加入
    //更新了
    public void update(T t,String newValue)
    {
        w.lock();
        try{
                remove(t);
                put(t,newValue);
        }finally {
            w.unlock();;
        }


    }

    public boolean remove(T t){
        w.lock();
        try{
            for(CharCloumn<T>  column:columns){
                if(column!=null){
                    if(column.remove(t)){
                            return true;

                    }
                }
            }
            return  false;

        }finally {
            w.unlock();
        }
    }
    public Collection search(String word,int limit){
        r.lock();

        try{
            int n=word.length();
            char chars[]=word.toCharArray();
            Context context=new Context();
            for(int i=0;i<chars.length;i++){
                CharCloumn<T>  column=columns[chars[i]];
                if(column==null){
                    break;
                }
                if(!context.filter(column)){
                    break;
                }
                n--;

            }
            if(n==0){

                return context.limit(limit);

            }
        return Collections.emptySet();
        }finally {
            r.unlock();
        }

    }


    private class Context{
        Map<T,byte[]> result;
        boolean used=false;

        private boolean filter(CharCloumn<T> columns){
            if(this.used==false) {
                this.result = new TreeMap<T, byte[]>(columns.poxIndex);
                this.used = true;
                return true;
            }
            boolean flag=false;
            Map<T,byte[]> newReulst=new TreeMap<T,byte[]> ();
            Set<Map.Entry<T, byte[]>> entrySet = columns.poxIndex.entrySet();
            for(Map.Entry<T, byte[]> entry:entrySet){
                T id=entry.getKey();
                byte[] charPox=entry.getValue();
                if(!result.containsKey(id)){
                    continue;
                }
            byte[] before=result.get(id);
                boolean in=false;
                for(byte pox:before){

                    if(contain(charPox,(byte)(pox+1))){
                            in=true;
                            break;
                    }
                }
                if(in){
                    flag=true;
                    newReulst.put(id,charPox);
                }
            }
            result=newReulst;
            return true;
        }

        public Collection<T> limit(int limit){

            if(result.size()<=limit){
                return result.keySet();
            }
            Collection<T> ids=new TreeSet<T>();
            for (T id:result.keySet()){
                ids.add(id);
                if(ids.size()>=limit){
                    break;
                }


            }
            return  ids;
        }


    }


//CharCloumn[百] poxIndex(map) 存储 key：百度一下 value:'0'
    private class CharCloumn<T>{

        ConcurrentHashMap<T,byte[]> poxIndex=new ConcurrentHashMap<T,byte[]>();

        void add(T t,byte pox){
            byte []arr= poxIndex.get(t);
            if(null==arr){
                arr=new byte[]{pox};
            }else{
                arr=copy(arr,pox);
            }
            poxIndex.put(t,arr);

        }

        boolean remove(T id){
            if(poxIndex.remove(id)!=null)
            {
                return true;
            }
            return  false;
        }




    }


    /***
     * 对老的数据位置进行排序
     * 然后比较当前这个byte位置是否存老的数组
     * @param arr
     * @param value
     * @return
     */
    private static byte[] copy(byte []arr,byte value){
        Arrays.sort(arr);
        if(contain(arr,value)){
            return arr;
        }
        byte[] newArr=new byte[arr.length+1];
        newArr[newArr.length-1]=value;
        System.arraycopy(arr,0,newArr,0,arr.length);
        Arrays.sort(newArr);
        return  newArr;
    }

    private static boolean contain(byte[] arr,byte value){
        int pox= Arrays.binarySearch(arr,value);
        return (pox>=0)?true:false;


    }


}


