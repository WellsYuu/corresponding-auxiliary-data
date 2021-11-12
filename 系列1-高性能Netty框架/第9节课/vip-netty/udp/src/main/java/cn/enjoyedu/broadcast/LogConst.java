package cn.enjoyedu.broadcast;

import java.util.Random;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：日志信息，用String数组代替
 */
public class LogConst {
    public final static int MONITOR_SIDE_PORT = 9998;
    private static final String[] LOG_INFOS = {
            "20180912:mark-machine:Send sms to 10001",
            "20180912:lison-machine:Send email to james@enjoyedu",
            "20180912:james-machine:Happen Exception",
            "20180912:peter-machine:人生不能象做菜,把所有的料都准备好了才下锅",
            "20180912:deer-machine:牵着你的手,就象左手牵右手没感觉,但砍下去也会痛!",
            "20180912:king-machine:我听别人说这世界上有一种鸟是没有脚的," +
                    "它只能一直飞呀飞呀,飞累了就在风里面睡觉,这种鸟一辈子只能下地一次," +
                    "那一次就是它死亡的时候.",
            "20180912:mark-machine:多年以后我有个绰号叫西毒,任何人都可以变得狠毒," +
                    "只要你尝试过什么叫妒嫉.我不介意其他人怎么看我," +
                    "我只不过不想别人比我更开心.我以为有一些人永远不会妒嫉," +
                    "因为他太骄傲 . 在我出道的时候,我认识了一个人," +
                    "因为他喜欢在东边出没,所以很多年以后,他有个绰号叫东邪.",
            "20180912:lison-machine:做人如果没有梦想,那和咸鱼有什么区别",
            "20180912:james-machine:恐惧让你沦为囚犯,希望让你重获自由," +
                    "坚强的人只能救赎自己,伟大的人才能拯救别人." +
                    "记着,希望是件好东西,而且从没有一样好东西会消逝." +
                    "忙活,或者等死.",
            "20180912:peter-machine:世界上最远的距离不是生和死," +
                    "而是我站在你的面前却不能说:我爱你",
            "20180912:deer-machine:成功的含义不在于得到什么," +
                    "而是在于你从那个奋斗的起点走了多远.",
            "20180912:king-machine:一个人杀了一个人,他是杀人犯.是坏人," +
                    "当一个人杀了成千上万人后,他是英雄,是大好人",
            "20180912:mark-machine:世界在我掌握中，我却掌握不住对你的感情",
            "20180912:lison-machine:我害怕前面的路，但是一想到你，就有能力向前走了。",
            "20180912:james-machine:早就劝你别吸烟，可是烟雾中的你是那么的美，" +
                    "叫我怎么劝得下口。",
            "20180912:peter-machine:如果你只做自己能力范围之内的事情，就永远无法进步。" +
                    "昨天已成为历史，明天是未知的，而今天是上天赐予我们的礼物，" +
                    "这就是为什么我们把它叫做现在！",
            "20180912:deer-machine:年轻的时候有贼心没贼胆，等到了老了吧，" +
                    "贼心贼胆都有了，可贼又没了。",
            "20180912:king-machine:别看现在闹得欢，小心将来拉清单。"};

    private final static Random r = new Random();
    public static String getLogInfo(){
        return LOG_INFOS[r.nextInt(LOG_INFOS.length-1)];
    }
}
