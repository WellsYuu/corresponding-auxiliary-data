package test.collects.Redis;/**
 * Created by Administrator on 2018/6/13.
 */

import com.cbt.agent.common.util.JaxbUtil;
import com.cbt.agent.core.AgentItemSource;
import com.cbt.agent.core.AgentPlugin;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import test.collects.BasiceTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tommy
 *         Created by Tommy on 2018/6/13
 **/
public class RedisClassTest extends BasiceTest {
    private AgentPlugin agentPlugin;

    @Before
    public void init() throws IOException {
        String out_file = System.getProperty("user.dir") + "/src/main/resources/agentConfig/agent.xml";
        byte[] bytes = Files.readAllBytes(new File(out_file).toPath());
        agentPlugin = JaxbUtil.converyToJavaBean(
                new String(bytes, "UTF-8"), AgentPlugin.class);
    }

    @Test
    public void setCommandTest() throws Exception {
        AgentItemSource item = agentPlugin.getAgentItems().get(4);
        item.setSrcTemplate(agentPlugin.getTemplates()[0].getValue());
        List<AgentItemSource> items = new ArrayList<>();
        items.add(item);
        Class<?> cla = loader.buildAgentClass(item.getTargetClassName(), getClass().getClassLoader(), items).toClass();

        JedisPool jedispool = new JedisPool("192.168.0.15", 6379);
        Jedis jedis = jedispool.getResource();
        jedis.set("test", "hello luban");
        System.out.println(jedis.get("test"));
        jedis.del("test");
        System.in.read();
    }
}
