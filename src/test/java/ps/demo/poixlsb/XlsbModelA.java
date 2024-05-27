package ps.demo.poixlsb;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ps.demo.annotation.XlsbRowObjAttribute;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class XlsbModelA {

    @XlsbRowObjAttribute(columnName = "A", format = "MM/dd/yyyy")
    private String startDate;

    @XlsbRowObjAttribute(columnName = "B", format = "")
    private String name;

}
