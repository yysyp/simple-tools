package ps.demo.common;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;

public class SettingTest {

    public static void main(String[] args) {
//        Setting setting = new Setting(FileUtil.touch("c:/myconfigs/db.setting"), CharsetUtil.CHARSET_UTF_8, true);
//        setting.setByGroup("url", "mysql", "mysql:xxxx");
//        setting.store();

        Setting setting = new Setting("c:/myconfigs/config.setting", CharsetUtil.CHARSET_UTF_8, true);
        String url = setting.getStr("url", "mysql", "");
        Console.log("url={}", url);

    }

}
