package ps.demo.easyexcel;

import cn.hutool.core.lang.Console;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import ps.demo.common.FileTool;


import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Gson gson = new Gson();

        //Generate random pojo data.
        List<DemoData> demoDataList =
        IntStream.range(1, 11).mapToObj(i -> DemoData.builder()
                        .id(i).sno(RandomUtils.nextInt(100, 200))
                        .sname(RandomStringUtils.randomAlphabetic(5))
                        .age(RandomUtils.nextInt(18, 35)).build())
                .collect(Collectors.toList());

        //Save to Excel
        File file = FileTool.touchFileInHomeWithDateDirAndTsPrefixByName("demo-data.xlsx");
        EasyExcel.write(file, DemoData.class).sheet("DemoData").needHead(true).doWrite(demoDataList);


        //Read the Excel file to List<DemoData>
        List<DemoData> readList = new ArrayList<>();
        EasyExcel.read(file, DemoData.class, new ReadListener<DemoData>() {
            @Override
            public void invoke(DemoData o, AnalysisContext analysisContext) {
                if (lineNull(o)) {
                    return;
                }
                readList.add(o);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                //done!
            }
        }).charset(StandardCharsets.UTF_8).excelType(ExcelTypeEnum.XLSX).sheet(0).headRowNumber(1).doRead();
        System.out.println("Read the Excel file to List<DemoData>------------------------------------");
        Console.log("===data={}", gson.toJson(readList));


        //Read the Excel file to List<LinkedHashMap<String, Object>>
        List<LinkedHashMap<String, Object>> readListInListMap = new ArrayList<>();
        EasyExcel.read(file, new AnalysisEventListener<Map<String, Object>>() {
            private Map<Integer, String> headMap;

            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                this.headMap = headMap;
            }

            @Override
            public void invoke(Map<String, Object> rowData, AnalysisContext analysisContext) {
                LinkedHashMap<String, Object> dataMap = new LinkedHashMap<>();
                for (int i = 0, n = rowData.size(); i < n; i++) {
                    //String key = RegexTool.removeSymbols(""+headMap.get(i))+"_"+getExcelColumnName(i+1);
                    String key = getExcelColumnName(i + 1);
                    Object value = rowData.get(i);
//                    int keyPrefix = 0;
//                    String key1 = key;
//                    while (dataMap.containsKey(key1)) {
//                        keyPrefix++;
//                        key1 = key+keyPrefix;
//                    }
                    dataMap.put(key, value);
                }
                if (mapNull(dataMap)) {
                    return;
                }
                readListInListMap.add(dataMap);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).charset(StandardCharsets.UTF_8).excelType(ExcelTypeEnum.XLSX).sheet(0).headRowNumber(1).doRead();

        System.out.println("Read the Excel file to List<LinkedHashMap<String, Object>>------------------------------------");

        Console.log("===data={}", gson.toJson(readListInListMap));


    }

    public static String getExcelColumnName(int col) {
        StringBuilder columnName = new StringBuilder();
        while (col > 0) {
            int rem = col % 26;
            if (rem == 0) {
                columnName.append("Z");
                col = (col / 26) - 1;
            } else {
                columnName.append((char)((rem - 1) + 'A'));
                col = col / 26;
            }
        }
        return columnName.reverse().toString();
    }

    public static <T> boolean lineNull(T line) {
        if (line instanceof String) {
            return StringUtils.isEmpty((String)line);
        }
        try {
            Set<Field> fieldSet = Arrays.stream(line.getClass().getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(ExcelProperty.class)).collect(Collectors.toSet());
            for (Field field : fieldSet) {
                field.setAccessible(true);
                if(field.get(line) != null) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean mapNull(Map<String, Object> line) {
        if (line == null || line.size() == 0) {
            return true;
        }
        for (Map.Entry<String, Object> entry : line.entrySet()) {
            if (entry.getValue() != null) {
                return false;
            }
        }
        return true;
    }

}
