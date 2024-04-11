### This is a simple springboot 3 project.

##### hutool 5.8.20 https://hutool.cn/
##### swagger ui: http://localhost:8080/swagger-ui/index.html#/
##### playwright: https://github.com/microsoft/playwright-java
##### playwright config: https://blog.csdn.net/m0_67695717/article/details/131166813
##### mongodb query: https://zhuanlan.zhihu.com/p/70270239

##### Use UTC time zone time for solving client and mongdb timezone different causes time string shows different issue.
```java
import java.time.ZonedDateTime;
import java.time.ZoneId;  
import java.util.Date;

public class MongoDateExample {  
public static void main(String[] args) {  
// 获取当前UTC时间  
ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC"));

        // 将UTC时间转换为Date对象（用于MongoDB存储）  
        Date dateForMongo = Date.from(utcNow.toInstant());  
  
        // ... 在这里执行MongoDB存储操作 ...  
  
        // 从MongoDB读取Date对象并转换回ZonedDateTime（如果需要本地时间）  
        ZonedDateTime localTime = dateForMongo.toInstant().atZone(ZoneId.systemDefault());  
  
        // 打印本地时间  
        System.out.println("Local time: " + localTime);  
    }  
}

```
