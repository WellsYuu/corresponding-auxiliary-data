package com.jiagouedu.core.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 将待执行SQL转换为全小写的拦截器
 *
 * @author dylan
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
        , @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class LowerCaseSqlInterceptor implements Interceptor {
    private static Logger logger = LoggerFactory.getLogger(LowerCaseSqlInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String method = invocation.getMethod().getName();
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        SqlSource sqlSource = wrapperSqlSource(ms, ms.getSqlSource(), invocation.getArgs()[1], method);
        Field sqlSourceField = MappedStatement.class.getDeclaredField("sqlSource");
        ReflectionUtils.makeAccessible(sqlSourceField);
        ReflectionUtils.setField(sqlSourceField, ms, sqlSource);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private SqlSource wrapperSqlSource(MappedStatement ms, SqlSource sqlSource, Object parameter, String method){
        BoundSql originBoundSql = sqlSource.getBoundSql(parameter);
        String sql = originBoundSql.getSql();
//        logger.debug("method type : {}, source sql : {}", method, sql);

//        if (sqlSource instanceof DynamicSqlSource) {//动态sql
//            MetaObject msObject = SystemMetaObject.forObject(ms);
//            SqlNode sqlNode = (SqlNode) msObject.getValue("sqlSource.rootSqlNode");
//            MixedSqlNode mixedSqlNode;
//            if (sqlNode instanceof MixedSqlNode) {
//                mixedSqlNode = (MixedSqlNode) sqlNode;
//            } else {
//                List<SqlNode> contents = new ArrayList<SqlNode>(1);
//                contents.add(sqlNode);
//                mixedSqlNode = new MixedSqlNode(contents);
//            }
//            return new DynamicSqlSource(ms.getConfiguration(), mixedSqlNode);
//        } else if (sqlSource instanceof ProviderSqlSource) {//注解式sql
//            return new ProviderSqlSource(ms.getConfiguration(), (ProviderSqlSource) sqlSource);
//        } else {
//            logger.info("method type : {}, converted sql : {}", method, sql.toLowerCase());
//            return new StaticSqlSource(ms.getConfiguration(), sql.toLowerCase(), originBoundSql.getParameterMappings());
//        }
        SqlSource wrapper = new SqlSourceWrapper(sqlSource);
        sql = wrapper.getBoundSql(parameter).getSql();
        logger.debug("method type : {}, converted sql : {}", method, sql);
        return wrapper;
    }

    public static class SqlSourceWrapper implements SqlSource{
        private SqlSource origin ;
        public SqlSourceWrapper(SqlSource origin){
            this.origin = origin;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            BoundSql boundSql = origin.getBoundSql(parameterObject);
            String sql = boundSql.getSql();
            Field sqlField = null;
            try {
                sqlField = BoundSql.class.getDeclaredField("sql");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            ReflectionUtils.makeAccessible(sqlField);
            ReflectionUtils.setField(sqlField, boundSql, sql.toLowerCase());
            return boundSql;
        }
    }

}
