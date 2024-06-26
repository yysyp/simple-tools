package ps.demo.common;

import cn.hutool.db.ds.DSFactory;
import cn.hutool.setting.Setting;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import java.util.List;
import java.util.Map;

class ResultSetToolTest {
/*
"""
[mysql]
url=jdbc:mysql://localhost:3306/world?allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=UTF-8
username=root
password=root
driver=com.mysql.cj.jdbc.Driver
showSql=true
formatSql=false
showParams=true
sqlLevel=debug
"""
*/

    //@Test
    void convertToGsonObjList() {
        Setting setting = new Setting("c:/myconfigs/config.setting"); //config-sample.setting
        try (DSFactory dsFactory = DSFactory.create(setting);) {
            //DataSource ds = dsFactory.getDataSource();
            DataSource ds = dsFactory.getDataSource("mysql");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

            List<Object> result = jdbcTemplate.query("select * from city limit 10", ResultSetTool::convertToGsonObjList);


            StringXTool.printOut(result);

            Map row1 = JsonTool2.getField(result, "1", Map.class);
            System.out.println(row1);
            String name = JsonTool2.getField(row1, "Name", String.class);
            System.out.println(name);

            JsonTool2.setField(row1, "Name", "xxx");
            name = JsonTool2.getField(row1, "Name", String.class);
            System.out.println(name);

            JsonTool2.delField(row1, "Name");
            name = JsonTool2.getField(row1, "Name", String.class);
            System.out.println(name);

            JsonTool2.delField(result, "2");
            StringXTool.printOut(result);


        }
    }

    //@Test
    void convertToMapList() {
        Setting setting = new Setting("c:/myconfigs/config.setting"); //config-sample.setting
        try (DSFactory dsFactory = DSFactory.create(setting);) {
            //DataSource ds = dsFactory.getDataSource();
            DataSource ds = dsFactory.getDataSource("mysql");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

            List<Map<String, Object>> result = jdbcTemplate.query("select * from city limit 10", ResultSetTool::convertToMapList);


            StringXTool.printOut(result);

        }

    }
}