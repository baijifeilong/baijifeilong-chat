package io.github.baijifeilong.chat.tool;

import com.zaxxer.hikari.HikariDataSource;
import io.github.baijifeilong.sql.ScriptRunner;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by BaiJiFeiLong@gmail.com at 2018/12/25 下午3:36
 * <p>
 * 数据库迁移 依次执行sql目录下的DDL脚本、播种脚本与透视脚本
 */
public class MyDatabaseMigration implements MyEnvironment {

    @SneakyThrows
    public static void main(String[] args) {
        String directoryName = ClassLoader.getSystemResource("sql").getFile();
        List<Path> ddlFiles = Files.walk(Paths.get(directoryName))
                .filter(Files::isRegularFile)
                .filter($ -> $.getFileName().toString().startsWith("ddl."))
                .sorted()
                .collect(Collectors.toList());
        List<Path> seederFiles = Files.walk(Paths.get(directoryName))
                .filter(Files::isRegularFile)
                .filter($ -> $.getFileName().toString().startsWith("seeder."))
                .sorted()
                .collect(Collectors.toList());
        List<Path> inspectorFiles = Files.walk(Paths.get(directoryName))
                .filter(Files::isRegularFile)
                .filter($ -> $.getFileName().toString().startsWith("inspector."))
                .sorted()
                .collect(Collectors.toList());

        System.out.println("DDLs:");
        ddlFiles.forEach(System.out::println);

        System.out.println("Seeders:");
        seederFiles.forEach(System.out::println);

        System.out.println("Inspectors:");
        inspectorFiles.forEach(System.out::println);

        DataSource dataSource = new HikariDataSource() {{
            setJdbcUrl(JDBC_URL);
            setUsername(JDBC_USERNAME);
            setPassword(JDBC_PASSWORD);
        }};
        ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection(), false, true);


        for (Path path : ddlFiles) {
            scriptRunner.runScript(new FileReader(path.toFile()));
        }

        for (Path path : seederFiles) {
            scriptRunner.runScript(new FileReader(path.toFile()));
        }

        DSLContext dslContext = DSL.using(dataSource, SQLDialect.MYSQL_8_0);

        Result<Record> tables = dslContext.fetch("SELECT TABLE_NAME, CREATE_TIME, ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'chat'");
        System.out.println(tables);

        for (Path path : inspectorFiles) {
            String text = new Scanner(path).useDelimiter("\\A").next();
            List<String> sqls = Stream.of(text.split(";")).filter($ -> !$.replaceAll(";", "").trim().isEmpty()).collect(Collectors.toList());
            for (String sql : sqls) {
                System.out.printf("Executing SQL [%s]:\n", sql.replaceAll("\n", " "));
                Result<Record> rows = dslContext.fetch(sql);
                System.out.println(rows);
            }
        }
    }
}
