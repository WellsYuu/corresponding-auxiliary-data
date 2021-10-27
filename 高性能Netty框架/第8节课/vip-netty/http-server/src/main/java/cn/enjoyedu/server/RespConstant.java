package cn.enjoyedu.server;

import java.util.Random;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class RespConstant {
    private static final String[] NEWS = {
            "她那时候还太年轻，不知道所有命运赠送的礼物，早已在暗中标好了" +
                    "价格。——斯蒂芬·茨威格《断头皇后》",
            "这是一个最好的时代，也是一个最坏的时代；" +
            "这是一个智慧的年代，这是一个愚蠢的年代；\n" +
            "这是一个信任的时期，这是一个怀疑的时期；" +
            "这是一个光明的季节，这是一个黑暗的季节；\n" +
            "这是希望之春，这是失望之冬；" +
            "人们面前应有尽有，人们面前一无所有；\n" +
            "人们正踏上天堂之路，人们正走向地狱之门。 —— 狄更斯《双城记》",
            };

    private static final Random R = new Random();

    public static String getNews(){
        return NEWS[R.nextInt(NEWS.length)];
    }

}
