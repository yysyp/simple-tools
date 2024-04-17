package ps.demo.common;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.setting.Setting;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataBaseDbTest {

    public static Long insert(String sql) throws SQLException {
        Setting setting = new Setting("c:/myconfigs/config.setting");
        DSFactory dsFactory = DSFactory.create(setting);
        DataSource ds = dsFactory.getDataSource();
        Long id = Db.use(ds).insertForGeneratedKey(Entity.create("user")
                .set("name", "unitTestUser")
                .set("age", 66));

        dsFactory.close();
        return id;
    }

}
