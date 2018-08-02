package org.personal.blackcat.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.junit.Test;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SQLParserTest {

    @Test
    public void sqlParseTest() {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();

        String selectSql = "select * from tbl_user_content;";
        String insertSql = "insert into tbl_user_content(user_id, user_name) values('10002', 'blackcat');";
        String updateSql = "update tbl_user_content set user_remarks = 'talk is cheap, show me the code.' where user_id = '10002';";
        String deleteSql = "delete from tbl_user_content where user_id = '10002';";

        try {
            Statement parse = parserManager.parse(new StringReader(insertSql));

            List<String> tableList = new TablesNamesFinder().getTableList(parse);
            log.info("{}", tableList);
            tableList.forEach(table -> log.info("{}", table));


        } catch (JSQLParserException e) {
            log.info("sql parse error --",e);
        }
    }

    @Test
    public void collectionsTest() {
        String text = "tbl_loan_info, repayment_req, user_auto_repay";
        TreeSet<String> strings = Sets.newTreeSet(Splitter.on(",").splitToList(text));
    }

    @Test
    public void foreachTest() {
        String content = "{\"returnCode\": 200, \"returnMsg\": \"SUCCESS\", \"data\":" +
                "[{\"a\"},{\"b\"},{\"c\"}]}";

    }

    @Test
    public void regexTest() {
        String caseId = "[test-你的]case";
        String regex = "\\[(\\S+)](\\S+)";
        Matcher matcher = Pattern.compile(regex).matcher(caseId);
        while (matcher.find()) {
            log.info("{}", matcher.group(1));
        }

        /*String text = "${column}";
        String regex2 = "\\$\\{(\\w+)}";
        Matcher matcher1 = Pattern.compile(regex2).matcher(text);
        while (matcher1.find()) {
            log.info("{}", matcher1.group(1));
        }*/
    }

    @Test
    public void booleanTest() {
        log.info("{}", Boolean.TRUE.toString().equals("true"));
    }

    @Test
    public void spaceTest() {
        String text = System.getProperty("l");
        log.info("空格: {}",text);
    }

    @Test
    public void stringsTest() {
        log.info("{}", Joiner.on("', '").join(1,2,3));
        log.info("{}", "'0001-01-01 00:00:00', '1234'".replaceAll("0001-01-01 00:00:00", "1971-01-01 00:00:00"));
    }

    @Test
    public void jsonTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        log.info("{}", gson.toJson(timestamp));
    }

}