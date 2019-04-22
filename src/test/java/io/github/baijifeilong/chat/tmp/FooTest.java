package io.github.baijifeilong.chat.tmp;

import io.github.baijifeilong.chat.generated.jooq.chat.tables.records.UserRecord;
import io.github.baijifeilong.chat.tool.MyEnvironment;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;

import static io.github.baijifeilong.chat.generated.jooq.chat.Tables.*;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/17 上午9:28
 */
public class FooTest implements MyEnvironment {

    public static void main(String[] args) {
        DSLContext dslContext = new DefaultDSLContext(
                new HikariDataSource() {{
                    setJdbcUrl(JDBC_URL);
                    setUsername(JDBC_USERNAME);
                    setPassword(JDBC_PASSWORD);

                }}, SQLDialect.MYSQL_8_0
        );

        Result<UserRecord> into = dslContext
                .select(USER.fields())
                .from(GROUP)
                .join(GROUP_USER_RELATION)
                .on(GROUP.GROUP_ID.equal(GROUP_USER_RELATION.GROUP_ID))
                .join(USER)
                .on(GROUP_USER_RELATION.USER_ID.equal(USER.USER_ID.cast(Integer.class)))
                .orderBy(GROUP.GROUP_ID)
                .fetch()
                .into(USER);
        System.out.println(into);
    }
}
