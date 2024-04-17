package ps.demo.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.RandomUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.thymeleaf.util.StringUtils;
import ps.demo.pojo.DemoStruct;

import java.io.File;
import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtilJSONUtilTest {

    public static void main(String[] args) {
        List<DemoStruct> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DemoStruct demoStruct = DemoStruct.builder().user(StringUtils.randomAlphanumeric(5)+i)
                    .basic(DemoStruct.Basic.builder().name(StringUtils.randomAlphanumeric(3))
                            .time(new Date())
                            .bizDate(ZonedDateTime.of(2024, 4, 15, 0, 0, 0, 0, ZoneId.of("UTC")).toInstant())
                            .ver(RandomUtils.nextInt(1, 11)).build())
                    .theData(DemoStruct.TheData.builder().x(RandomUtils.nextInt(100, 1000))
                            .y(RandomUtils.nextInt(100, 1000)).build())
                    .build();
            list.add(demoStruct);
        }

        String jsonStr = JSONUtil.toJsonPrettyStr(list);
        File outfile = FileUtil.writeString(jsonStr, FileUtil.touch("./jsonlist.json"), Charset.forName("UTF-8"));
        Console.log("==>>outfile={}", outfile);

        JSONArray jsonArray = JSONUtil.parseArray(FileUtil.readUtf8String("list.json"));
        //Console.log("jsonArray={}", jsonArray);

        for(int i = 0; i < jsonArray.size(); i++) {
            String str = jsonArray.get(i).toString();
            int x = Integer.parseInt(JSONUtil.getByPath(jsonArray,"$["+i+"].theData.x")+"");
            int y = Integer.parseInt(JSONUtil.getByPath(jsonArray,"$["+i+"].theData.y")+"");
            Document document = Document.parse(str);
            document.put("sum", x+y);
            JSONObject sumobj = new JSONObject();
            sumobj.set("xysum", x+y);
            ((JSONObject)jsonArray.get(i)).set("sum", sumobj);
            Console.log("document={}", document);
        }

        Console.log("jsonArray after={}", jsonArray);

    }
}
