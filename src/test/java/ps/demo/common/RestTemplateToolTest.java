package ps.demo.common;

import cn.hutool.core.lang.Console;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestTemplateToolTest {

    @Test
    void deleteResource() {
        String url = "http://localhost:18888/api/mongo/hidel";
        RestTemplateTool.getRestTemplate().delete(url);
    }

    @Test
    void putBodyObjectForStr() {
        String url = "http://localhost:18888/api/mongo/hiput";
        String reqBody = "{'a': '1', 'b': 2}";
        String body = RestTemplateTool.getInstance().putBodyObjectForStr(url, reqBody).getBody();
        Console.log("body = {}", body);
    }

    @Test
    void patchBodyObjectForStr() {
        String url = "http://localhost:18888/api/mongo/hipatch";
        String reqBody = "{'a': '1', 'b': 2}";
        String body = RestTemplateTool.getInstance().patchBodyObjectForStr(url, reqBody).getBody();
        Console.log("body = {}", body);
    }

    @Test
    void postSubmitFormMultiValueMapForStr() {
    }

    @Test
    void postSubmitFormMultiValueMapForT() {
    }

    @Test
    void postJsonStrForStr() {
    }

    @Test
    void postJsonStrForT() {
    }

    @Test
    void testPostJsonStrForT() {
    }

    @Test
    void getWithUriVariableObjectsForStr() {
        String url = "https://localhost:18888/api/mongo/higet?par={par}";
        //String reqBody = "{'a': '1', 'b': 2}";
        String body = RestTemplateTool.getInstance().getWithUriVariableObjectsForStr(url, "parvalue1").getBody();
        Console.log("body = {}", body);

    }

    @Test
    void getWithUriVariableObjectsForT() {
    }

    @Test
    void testGetWithUriVariableObjectsForT() {
    }


}