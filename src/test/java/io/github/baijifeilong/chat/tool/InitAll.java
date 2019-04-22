package io.github.baijifeilong.chat.tool;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/17 下午8:43
 * <p>
 * 初始化本机测试环境:
 * 1. 数据库迁移
 * 2. 生成JOOQ模型
 * 3. 数据库播种
 */
public class InitAll {
    public static void main(String[] args) {
        MyDatabaseMigration.main(args);
        MyJooqGenerator.main(args);
        MySeeder.main(args);
    }
}
