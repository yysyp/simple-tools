package ps.demo.pojo;

import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class DemoStruct {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Basic {

        private String name;
        private Date time;
        private ZonedDateTime bizDate;
        private Integer ver;


    }

    private Basic basic;

    @Builder.Default
    private String user = "xiaoming";

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TheData {

        private int x;
        private int y;

    }

    private TheData theData;


}
