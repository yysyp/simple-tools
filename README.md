### This is a simple springboot 3 project.

##### hutool 5.8.20 https://hutool.cn/
##### swagger ui: http://localhost:18888/swagger-ui/index.html#/
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
EXCEL VBS:
```vbs
' Encode special characters of a string
' this is useful when you want to put a string in the URL
' inspired by http://stackoverflow.com/questions/218181/how-can-i-url-encode-a-string-in-excel-vba
Public Function URLEncode( StringVal )
  Dim i, CharCode, Char, Space
  Dim StringLen

  StringLen = Len(StringVal)
  ReDim result(StringLen)

  Space = "+"
  'Space = "%20"

  For i = 1 To StringLen
    Char = Mid(StringVal, i, 1)
    CharCode = AscW(Char)
    If 97 <= CharCode And CharCode <= 122 _
    Or 64 <= CharCode And CharCode <= 90 _
    Or 48 <= CharCode And CharCode <= 57 _
    Or 45 = CharCode _
    Or 46 = CharCode _
    Or 95 = CharCode _
    Or 126 = CharCode Then
      result(i) = Char
    ElseIf 32 = CharCode Then
      result(i) = Space
    Else
      result(i) = "&#" & CharCode & ";"
    End If
  Next
  URLEncode = Join(result, "")
End Function

or: System.Uri.EscapeDataString(input)
```
Excel Button -> right click, assign Macro --> Edit. Toolbar Project Explorer

W3C http traceparent Headers:
base16 version (2)
base16 trace-id (32)
base16 parent-id (16)
base16 (trace-flags) (2)
traceparent=00-11111111111111111111111111111111-1234567890123456-00


===

Spring Flux是Spring Framework 5.0中引入的新的反应式Web框架，它基于Reactive Streams的非阻塞响应式编程模型。Spring Flux主要解决的是异步和非阻塞的操作问题，使得在处理大量并发请求时，能够更有效地利用系统资源，提高系统的吞吐量和响应速度。
在Spring Flux中，Mono和Flux是两个核心的类型。Mono代表一个包含零个或一个元素的异步序列，类似于Java 8中的Optional，但具有额外的异步能力。而Flux则代表一个包含零个或多个元素的异步序列，类似于Java 8中的Stream，但具有非阻塞和异步的特性。Flux可以用于处理多个值的流，例如从消息队列中获取一系列消息或从文件中读取一行行的数据。
总的来说，Spring Flux通过引入响应式编程模型，解决了传统同步阻塞编程在处理大量并发请求时可能出现的性能瓶颈问题，使得系统能够更加高效地处理这些请求，提高了系统的整体性能和用户体验。
Spring Flux 与传统的 Spring MVC Controller 在设计和目标上存在显著的联系和区别。
联系：
两者都是Spring框架的一部分，旨在提供用于构建Web应用程序的组件和工具。
在某些方面，它们都关注于处理HTTP请求和响应，以及将数据从模型层传递到视图层。
区别：
编程模型：
Spring MVC Controller 基于传统的请求-响应模型，每个HTTP请求都由一个特定的Controller方法处理，该方法返回一个视图或数据，然后该视图或数据被发送到客户端。
Spring Flux 则是基于反应式编程模型，它使用非阻塞的、基于事件的编程风格来处理数据流。Flux允许你定义数据流，并在数据可用时异步地处理它，而不是等待整个请求完成后再进行处理。
并发性：
Spring MVC Controller 在处理高并发请求时可能会受到线程和阻塞I/O操作的限制。虽然可以使用异步请求处理来减轻这种限制，但它仍然基于传统的同步模型。
Spring Flux 使用非阻塞的I/O操作和反应式数据流，可以更有效地处理高并发请求。由于它不需要为每个请求分配一个线程，因此可以大大减少线程的数量，降低资源消耗。
数据处理：
在Spring MVC Controller中，数据通常被封装在Model对象中，并在Controller方法中进行处理。然后，这些数据被传递到视图层进行渲染。
在Spring Flux中，数据以流的形式进行处理。你可以使用Flux或Mono对象来定义数据流，并使用操作符（如map、flatMap、filter等）来转换或处理数据。这些操作符是声明式的，并且可以在数据到达时异步地执行。
视图层：
Spring MVC 通常与JSP、Thymeleaf等模板引擎一起使用，以生成HTML页面作为视图。
Spring Flux 更倾向于与反应式视图库（如React、Vue等）一起使用，这些库可以在客户端以异步方式处理数据流并生成动态内容。
适用场景：
Spring MVC 更适合构建传统的、基于页面的Web应用程序，其中每个页面都有一个明确定义的视图和模型。
Spring Flux 更适合构建实时Web应用程序、Web API和微服务，其中需要处理大量的并发请求和数据流。
总之，Spring Flux 和传统的 Spring MVC Controller 在编程模型、并发性、数据处理、视图层和适用场景等方面都存在显著的差异。选择哪种模型取决于你的具体需求和项目类型。

---

当使用Spring Flux时，你通常会编写基于反应式编程的Web控制器，这些控制器处理HTTP请求并返回Flux或Mono对象。以下是一个简单的Spring Flux示例，该示例展示了一个返回Flux<String>的控制器方法，该方法模拟了一个异步数据流：
首先，确保你的项目中包含了Spring WebFlux的依赖。如果你使用Maven，可以在pom.xml中添加以下依赖：
xml
<dependency>  
<groupId>org.springframework.boot</groupId>  
<artifactId>spring-boot-starter-webflux</artifactId>  
</dependency>
然后，你可以编写一个控制器类，如下所示：

java
import org.springframework.http.MediaType;  
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.ResponseBody;  
import reactor.core.publisher.Flux;

@Controller  
public class FluxController {

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)  
    @ResponseBody  
    public Flux<String> streamData() {  
        // 模拟一个异步数据流，这里简单地每秒发出一个字符串  
        return Flux.interval(Duration.ofSeconds(1)) // 每秒发出一个Long类型的信号  
                .map(tick -> "Data " + tick); // 将Long类型的信号转换为String  
    }  
}
在这个例子中，streamData方法返回一个Flux<String>对象。这个Flux对象使用Flux.interval方法创建，该方法每秒发出一个递增的Long值（表示时间间隔的序号）。然后，我们使用map操作符将每个Long值转换为一个字符串。
注意，@GetMapping注解的produces属性设置为MediaType.TEXT_EVENT_STREAM_VALUE，这告诉客户端（通常是浏览器）该响应是一个服务器发送事件（Server-Sent Events, SSE）流。这样，浏览器就可以实时地接收并处理服务器发送的数据。
当你运行这个Spring Boot应用程序并访问/stream端点时，你应该会在浏览器的开发者工具中看到一个不断更新的数据流，每秒钟显示一个新的字符串。

在Spring Flux中，Mono和Flux的使用取决于你想要处理的数据类型和场景。
Mono通常用于处理可能只产生一个元素（或者没有元素）的异步场景。Mono表示的是未来可能产生的单个值（或者一个空值）。例如，从数据库获取单个用户记录、执行一个返回单一结果的操作等场景就适合使用Mono。当Mono包含一个值时，订阅者将接收到这个值；如果Mono为空或包含错误，则会相应地触发空值推送或错误通知。
Flux则用于处理包含零个或多个元素的异步序列的场景。Flux表示的是0到N个元素的数据序列，它可以发布多个值，也可以为空，或者发出完成信号。Flux类似于Java 8中的Stream，但是具有非阻塞和异步的特性。当你需要处理多个值的流时，例如从消息队列中获取一系列消息或从文件中读取一行行的数据，Flux就是更好的选择。
总结来说，选择使用Mono还是Flux主要取决于你的应用场景和数据类型。如果你在处理可能只产生一个元素的异步操作，那么Mono是更合适的选择；而如果你需要处理包含多个元素的异步流，那么Flux则是更好的选择。