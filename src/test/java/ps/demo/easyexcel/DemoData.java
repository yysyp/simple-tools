package ps.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoData {

    @ExcelIgnore
    private Integer id;

    @ExcelProperty(value = "StudentNo", index = 0)
    private Integer sno;

    @ExcelProperty(value = "StudentName",index = 1)
    private String sname;

    @ExcelProperty(value = "StudentAge",index = 2)
    private Integer age;

}
