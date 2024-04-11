package ps.demo.common;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse implements Serializable {

    @Builder.Default
    protected String code = CodeEnum.SUCCESS.getCode();
    @Builder.Default
    protected String message = CodeEnum.SUCCESS.getDetailedMessage();
    protected String detail;
    protected String trace;
    protected String correlationId;
    protected String instance;
    @Builder.Default
    protected String timestamp = ProjConstant.getNowDateStr();;
    protected String path;

    public static BaseResponse success() {
        return new BaseResponse();
    }

    public static BaseResponse of(CodeEnum codeEnum) {
        return BaseResponse.builder().code(codeEnum.getCode())
                .message(codeEnum.getDetailedMessage()).build();
    }

}
