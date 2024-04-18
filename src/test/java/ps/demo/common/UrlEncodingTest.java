package ps.demo.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlEncodingTest {

    public static void main(String [] args) throws UnsupportedEncodingException {
        String str = "a+b c";
        String encode = URLEncoder.encode(str, "UTF-8");
        String decode = URLDecoder.decode(str, "UTF-8");
        System.out.println("===>>encode="+encode);
        System.out.println("===>>decode="+decode);

    }

}
