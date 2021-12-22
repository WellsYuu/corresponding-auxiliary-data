package test.collects.jdbc;

import com.cbt.agent.core.AgentItemSource;
import com.cbt.agent.core.MethodInfo;
import org.junit.Test;
import test.collects.BasiceTest;
import test.collects.CollectItemBuildFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Created by tommy on 16/10/21.
 */
public class JdbcCollectTest extends BasiceTest {
    private static final String jdbc_url = "jdbc:mysql://sql.w108.vhostgo.com:3306/cbttest?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull";

    @Test
    public void driverTest() throws Exception {
        String className = "com.mysql.jdbc.NonRegisteringDriver";
        String handClassName = "com.cbt.agent.collects.jdbc.JdbcDriverHandle";
        String method="connect";
        MethodInfo info=new MethodInfo(className,method);
        info.setReturnType("java.sql.Connection");
        info.setParamTypes(new String[]{String.class.getName(),Properties.class.getName()});
        List<AgentItemSource> items = new CollectItemBuildFactory().createJdbcItems();
        for (AgentItemSource item : items) {
            item.setSrcTemplate(getSrc(item.getSrcTemplate()));
        }
        Class<?> cla=loader.buildAgentClass(className,getClass().getClassLoader(),items).toClass();

        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbc_url, "cbttest",
                    "vwuch4cp");
            ps = conn
                    .prepareStatement("SELECT  manifest_id as 清单id,lib_id as 资源id ,create_time as 创建时间 from agent_boot_manifest\n" +
                            "WHERE  manifest_id=? and lib_id=?");
            ps.setInt(1, 1); // push_channel
            ps.setInt(2,1);//
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            resultSet.close();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        Thread.sleep(2000);
    }
}
