package ps.demo.common;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.HashMap;


@Slf4j
class NoSpringTest {

    @BeforeEach
    void setUp() {
      log.info("This is before each setUp");
    }

    @AfterEach
    void tearDown() {
        log.info("This is after each tearDown");
    }

    @Test
    void toValidFileName() throws JSONException {
        log.info("This is to valid file name");


        //MongoDatabase db = MongoFactory.getDS("master", "slave").getDb("test");
        String result1= HttpUtil.get("https://www.baidu.com");
//        String result2= HttpUtil.get("https://www.baidu.com", CharsetUtil.CHARSET_UTF_8);
        log.info("baidu.com result1={}", result1);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("city", "北京");
        String result3 = HttpUtil.get("https://www.baidu.com", paramMap);
        log.info("baidu.com result3={}", result3);


        String actualResult = """
                {"b": 2, "a": 1}
                """;
        JSONAssert.assertEquals("""
                {"a": 1, "b": 2}
                """, actualResult, false);



//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("city", "北京");
//        String result= HttpUtil.post("https://www.baidu.com", paramMap);

//        HashMap<String, Object> paramMap = new HashMap<>();
//        //文件上传只需将参数中的键指定（默认file），值设为文件对象即可，对于使用者来说，文件上传与普通表单提交并无区别
//        paramMap.put("file", FileUtil.file("D:\\face.jpg"));
//        String result= HttpUtil.post("https://www.baidu.com", paramMap);

//        String fileUrl = "http://mirrors.sohu.com/centos/8.4.2105/isos/x86_64/CentOS-8.4.2105-x86_64-dvd1.iso";
//        //将文件下载后保存在c盘，返回结果为下载文件大小
//        long size = HttpUtil.downloadFile(fileUrl, FileUtil.file("c:/"));
//        System.out.println("Download size: " + size);


    }

    @Test
    void getUserHomeDir() {
        log.info("this is get user home dir");
    }


}