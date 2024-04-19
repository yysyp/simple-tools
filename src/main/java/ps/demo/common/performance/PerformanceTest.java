package ps.demo.common.performance;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import ps.demo.common.FileTool;
import ps.demo.common.RestTemplateTool;
import ps.demo.common.StringXTool;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PerformanceTest {

    public static void main(String[] args) {
        setLogLevel();

        double[] tps = new double[] {10, 25, 50};//new double[] {0.1d};
        int callsPerStage = 100;

        QpsCallTemplate qpsCallTemplate = new QpsCallTemplate(tps, callsPerStage);
        List<QpsCall<ReqObj>> list = new ArrayList<>();
        for (int i = 0; i < tps.length * callsPerStage; i++) {
            ReqObj reqObj = new ReqObj();
            reqObj.setIndex(i);
            reqObj.setNum(5);
            QpsCall<ReqObj> qpsCall = new QpsCall<ReqObj>(reqObj) {
                @Override
                public ReqObj call() throws Exception {
                    System.out.println(Thread.currentThread().getName()+"-->>Requesting..."+reqObj.getIndex());
                    reqObj.setFileStr(StringXTool.randomAlphabetic(32));
                    String result = null;
                    try {
                        result = RestTemplateTool.getRestTemplate().postForObject("http://localhost:8080/api/hi", JSONUtil.toJsonStr(reqObj), String.class);
                    } catch (Exception ex) {
                        result = ex.getMessage();
                    }
                    reqObj.setResultStr(result);
                    System.out.println("<<--Result:"+reqObj);
                    return reqObj;
                }
            };
            list.add(qpsCall);
        }

        List<ReqObj> reqObjList = qpsCallTemplate.invoke(list);

        for (ReqObj reqObj : reqObjList) {
            reqObj.setFileStr("...");
        }
        System.out.println("--->>Result="+reqObjList.size());
        System.out.println("--->>Done!");

        File file = FileTool.writeToHomeFileInJsonUTF8(reqObjList, "reqObjList");
        FileTool.writeToHomeFileInJsonUTF8(new Date(), file, true);
        Console.log("result file={}", file.getAbsoluteFile());
        qpsCallTemplate.shutdown();

    }

    private static void setLogLevel() {
    /*
    System.setProperty("org.apache.commons.logging.Log",
            "org.apache.commons.logging.impl.NoOpLog");
    org.apache.commons.logging.Log log = LogFactory.getLog("");
    log.info("You do not want to see me");
    System.out.println("Logger using is: "+LogFactory.getLog(""));
    */
        String[] names = { "org.apache", "org.springframework" };
        for (String ln : names) {
            // Try java.util.logging as backend
            java.util.logging.Logger.getLogger(ln).setLevel(java.util.logging.Level.INFO);
            // Try Log4J as backend
            //org.apache.log4j.Logger.getLogger(ln).setLevel(org.apache.log4j.Level.INFO);
            // Try another backend
            //Log4JLoggerFactory.getInstance().getLogger(ln).setLevel(java.util.logging.Level.INFO);
            // Try Logback
            Logger logback = new ch.qos.logback.classic.LoggerContext().getLogger(ln);
            logback.setLevel(Level.INFO);
            //Try Slf4j
            Logger restClientLogger = (Logger) LoggerFactory.getLogger(ln);
            restClientLogger.setLevel(Level.INFO);
        }
    }

}
