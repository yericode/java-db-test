package migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStream;
import java.sql.PreparedStatement;

public class V4__java_migrate_test extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        // 1. DDL: 先新增欄位 (如果還沒建立的話)
        String sql = "ALTER TABLE users ADD COLUMN avatar_data BLOB";
        try (var statement = context.getConnection().prepareStatement(sql)) {
            statement.execute();
        }

        // 2. 取得 Resource 掃描器 (Spring 提供)
        var resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:picture/*");

        // 3. DML: 插入圖片
        long userId = 1;
        sql = "UPDATE users SET avatar_data = ? WHERE id = ?";
        try (PreparedStatement pstmt = context.getConnection().prepareStatement(sql)) {
            for (Resource resource : resources) {
                // 使用 try-with-resources 確保 InputStream 被關閉
                try (InputStream is = resource.getInputStream()) {
                    // Senior Point: 使用 setBinaryStream 而非 byte[]
                    // 這樣資料會以串流方式寫入資料庫，不會把整個檔案吃進 Java 記憶體
                    pstmt.setBinaryStream(1, is, (int) resource.contentLength());
                    pstmt.setLong(2, userId);
                    pstmt.executeUpdate();
                }
                userId ++;
            }
        }
    }
}