
```java

//开输入->开输出->操作->关输出->关输入
//开内层->开外层->操作->关外层->关内层

    try (
        OutputStream outputStream = new FileOutputStream(createdFile);
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        OutputStreamWriter osw = new OutputStreamWriter(gzipOutputStream);
        BufferedWriter bw = new BufferedWriter(osw)
        ) {
        
        //...
        //这些变量在执行try块之前初始化并在执行try块后以与它们初始化的相反顺序自动关闭。
            
    }


public static void main(String[] args) throws Exception {
    //In流，从内到外开
    FileReader fr = new FileReader("in.file");
    BufferedReader br = new BufferedReader(fr);    
    
    //Out流，从内到外打开
    FileOutputStream fos = new FileOutputStream("d:\\a.txt");
    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
    BufferedWriter bw = new BufferedWriter(osw);
        
        bw.write("java IO close test");

	//Out流，从外到内顺序关闭ok， 顺序反过来在旧版java中可能报IO已经关闭错误
	bw.close();
	osw.close();
	fos.close();
    
    //In流，从外到内关闭
    br.close();
    fr.close();
}

```
-------------------------------------------------------

我正在写一段代码：

OutputStream outputStream = new FileOutputStream(createdFile);

GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);

BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(gzipOutputStream));

我是否需要关闭以下每个流或作者？

gzipOutputStream.close();

bw.close();

outputStream.close();

或者只是关闭最后一个流好吗？

bw.close();

#1 热门回答(136 赞)

假设所有流都被创建好了，是的，只需关闭bw就可以了解这些流实现;但这是一个很大的假设 (即所有流创建都没有问题)。

我使用try-with-resources(tutorial)，以便构造抛出异常的后续流的任何问题不会使先前的流挂起，因此你不必依赖具有调用的流实现来关闭底层流：

try (

OutputStream outputStream = new FileOutputStream(createdFile);

GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);

OutputStreamWriter osw = new OutputStreamWriter(gzipOutputStream);

BufferedWriter bw = new BufferedWriter(osw)

) {undefined

// ...

}

请注意，你不再手动调用close。

重要说明：要让资源尝试关闭它们，你必须在打开它们时将流分配给变量，不能使用嵌套。如果使用嵌套，则在构造其中一个后续流(例如，GZIPOutputStream)期间的异常将使由嵌套调用构造的任何流保持打开状态。


try-with-resources语句使用变量(称为资源)进行参数化，这些变量在执行try块之前初始化并在执行try块后以与它们初始化的相反顺序自动关闭。

注意"变量"这个词(我的重点)。

例如，不要这样做：

// DON'T DO THIS

try (BufferedWriter bw = new BufferedWriter(

new OutputStreamWriter(

new GZIPOutputStream(

new FileOutputStream(createdFile))))) {undefined

// ...

}

...因为来自GZIPOutputStream(OutputStream)构造函数的异常(表示它可能会抛出IOException，并将标头写入基础流)将离开FileOutputStream开启。由于某些资源具有可能抛出的构造函数而其他资源没有，因此将它们单独列出是一个好习惯。

