package ps.demo.poixlsb;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.springframework.data.jpa.domain.AbstractAuditable_;
import ps.demo.annotation.XlsbRowObjAttribute;
import ps.demo.common.FileTool;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Data
public class MyXSSFSheetContentHandler<T> implements XSSFSheetXMLHandler.SheetContentsHandler {

    private List<T> list = new ArrayList<>();

    private Class<T> clazz;

    private T data = null;

    private Map<String, Field> fieldColumnMap = new HashedMap();

    private int headerRow;

    public MyXSSFSheetContentHandler(Class<T> clazz, int headerRow) {
        this.clazz = clazz;
        this.headerRow = headerRow;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(XlsbRowObjAttribute.class)) {
                fieldColumnMap.put(field.getAnnotation(XlsbRowObjAttribute.class).columnName(), field);
            }
        }
    }

    @Override
    public void startRow(int i) {
        if (i >= headerRow) {
            try {
                data = clazz.getDeclaredConstructor().newInstance();
                Field field = fieldColumnMap.get("ROW_NUM");
                if (field == null) {
                    return;
                }
                field.setAccessible(true);
                field.set(data, i + 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void endRow(int i) {
        if (data != null) {
            list.add(data);
        }
    }

    @Override
    public void cell(String cellName, String cellValue, XSSFComment xssfComment) {
        if (data == null || StringUtils.isBlank(cellValue)) {
            return;
        }

        Pattern pattern = Pattern.compile("[A-Z]+");
        Matcher matcher = pattern.matcher(cellName);
        String column = null;
        if (matcher.find()) {
            column =  matcher.group();
        }
        Field field = fieldColumnMap.get(column);
        if (field == null) {
            return;
        }

        field.setAccessible(true);
        try {
            field.set(data, convertToType(field, cellValue));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Object convertToType(Field field, String value) throws ParseException {
        if (value == null) {
            return null;
        }
        Class<?> fieldType = field.getType();
        if (!String.class.isAssignableFrom(fieldType)
        && StringUtils.isBlank(value)) {
            return null;
        }
        if (String.class.isAssignableFrom(fieldType)) {
            return value;
        } else if (Date.class.isAssignableFrom(fieldType)) {
            String fmt = field.getAnnotation(XlsbRowObjAttribute.class).format();
            return new SimpleDateFormat(fmt).parse(value);
        } else if (BigDecimal.class.isAssignableFrom(fieldType)) {
            value = value.replaceAll(",|", "");
            if (value.endsWith("%")) {
                value = value.replace("%", "");
                return new BigDecimal(value).divide(new BigDecimal(100)).setScale(6);
            }
            return new BigDecimal(value);
        } else if (double.class.isAssignableFrom(fieldType) || Double.class.isAssignableFrom(fieldType)) {
            value = value.replaceAll(",|", "");
            return Double.parseDouble(value);
        } else if (float.class.isAssignableFrom(fieldType) || Float.class.isAssignableFrom(fieldType)) {
            value = value.replaceAll(",|", "");
            return Float.parseFloat(value);
        } else if (long.class.isAssignableFrom(fieldType) || Long.class.isAssignableFrom(fieldType)) {
            value = value.replaceAll(",|", "");
            return Long.parseLong(value);
        } else if (int.class.isAssignableFrom(fieldType) || Integer.class.isAssignableFrom(fieldType)) {
            value = value.replaceAll(",|", "");
            return Integer.parseInt(value);
        } else if (boolean.class.isAssignableFrom(fieldType) || Boolean.class.isAssignableFrom(fieldType)) {
            return Boolean.parseBoolean(value);
        } else if (short.class.isAssignableFrom(fieldType) || Short.class.isAssignableFrom(fieldType)) {
            value = value.replaceAll(",|", "");
            return Short.parseShort(value);
        } else if (byte.class.isAssignableFrom(fieldType) || Byte.class.isAssignableFrom(fieldType)) {
            return Byte.parseByte(value);
        } else {
            return null;
        }

    }

}

