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
