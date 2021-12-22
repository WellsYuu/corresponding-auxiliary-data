/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.tl.sql解析;


import io.shardingjdbc.core.constant.DatabaseType;
import io.shardingjdbc.core.constant.ShardingOperator;
import io.shardingjdbc.core.parsing.SQLJudgeEngine;
import io.shardingjdbc.core.parsing.SQLParsingEngine;
import io.shardingjdbc.core.parsing.lexer.Lexer;
import io.shardingjdbc.core.parsing.lexer.LexerEngine;
import io.shardingjdbc.core.parsing.lexer.LexerEngineFactory;
import io.shardingjdbc.core.parsing.lexer.analyzer.*;
import io.shardingjdbc.core.parsing.lexer.token.Token;
import io.shardingjdbc.core.parsing.parser.context.condition.Column;
import io.shardingjdbc.core.parsing.parser.context.condition.Condition;
import io.shardingjdbc.core.parsing.parser.exception.SQLParsingUnsupportedException;
import io.shardingjdbc.core.parsing.parser.sql.SQLStatement;
import io.shardingjdbc.core.parsing.parser.sql.dml.insert.InsertStatement;
import io.shardingjdbc.core.parsing.parser.sql.dql.DQLStatement;
import io.shardingjdbc.core.parsing.parser.token.SQLToken;
import io.shardingjdbc.core.rule.ShardingRule;
import io.shardingjdbc.core.rule.TableRule;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class InsertStatementParserTest extends AbstractStatementParserTest {
    
    @Test
    public void assertParseWithoutParameter() throws SQLException {
        ShardingRule shardingRule = createShardingRule();//创建分片规则
        SQLParsingEngine statementParser = new SQLParsingEngine(DatabaseType.MySQL, "INSERT INTO `TABLE_XXX` (`field1`, `field2`) VALUES (10, 1)", shardingRule);//解析sql引擎
        InsertStatement insertStatement = (InsertStatement) statementParser.parse();//解析sql开始
        System.out.println("sql解析tables："+insertStatement.getTables());
        List<SQLToken> list=insertStatement.getSqlTokens();
        for(SQLToken sqlToken:list){
            System.out.println(sqlToken);
        }
        System.out.println("toString:"+insertStatement.toString());
    }

    @Test
    public void assertParseWithoutParameter2() throws SQLException {
        ShardingRule shardingRule = createShardingRule();
        SQLParsingEngine statementParser = new SQLParsingEngine(DatabaseType.MySQL, "SELECT * FROM  TTT WHERE ID=1", shardingRule);
        DQLStatement insertStatement = (DQLStatement) statementParser.parse();
        System.out.println("sql解析tables："+insertStatement.getTables());
        List<SQLToken> list=insertStatement.getSqlTokens();
        for(SQLToken sqlToken:list){
            System.out.println(sqlToken);
        }
        System.out.println("toString:"+insertStatement.toString());
    }

    public static void main(String[] args) {

        LexerEngine lexerEngine = LexerEngineFactory.newInstance(DatabaseType.MySQL,  "INSERT INTO `TABLE_XXX` (`field1`, `field2`) VALUES (10, 1)");
        lexerEngine.nextToken();
        System.out.println(lexerEngine.getCurrentToken().getType()+","+lexerEngine.getCurrentToken().getEndPosition());

    }




}
