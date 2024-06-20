package ps.demo.service;

import cn.hutool.core.lang.Console;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ps.demo.common.RestTemplateTool;

import java.io.File;
import java.net.URLEncoder;

public class FuncDev1Test extends FuncBaseTest {

    protected String str = "dev1";

    @Override
    protected String getStr() {
        return str;
    }


    @Test
    public void uploadFile() {
        File file = new File("C:\\Users\\yysyp\\Desktop\\tmp\\1.jpg");
        String key = "aaabbbccc";
        String url = "http://localhost:8084/api/upload/file?key={key}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> formMap = new LinkedMultiValueMap<>();
        formMap.add("file", new FileSystemResource(file));

        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };

        String body = RestTemplateTool.getInstance().postSubmitFormMultiValueMapWithUriVariableObjectsForT(
                url, headers, formMap, responseType, key
        ).getBody();
        Console.log("body = {}", body);
    }

}
