@Async 注解：
@Async 注解用于声明一个方法是异步的。当在方法上加上这个注解时，Spring 将会在一个新的线程中执行该方法，而不会阻塞原始线程。这对于需要进行一些异步操作的场景非常有用，比如在后台执行一些耗时的任务而不影响前台响应。
@Async 通常会使用一个新的线程，而新线程无法继承原始线程的事务上下文。

@Transactional 注解：
@Transactional 注解用于声明一个方法应该被封装在一个事务中。在方法执行期间，如果发生异常，事务将被回滚，否则，事务将被提交。这确保了一组相关操作要么全部成功，要么全部失败。

一般来说，在 @Async 方法中调用 @Transactional 可以保证数据完整性，因为 Spring 会 正确传播相同的上下文。
而从 @Transactional 调用 @Async 时，可能会导致数据完整性问题。


通常：@Async注解的方法上，再标注@Transactional注解，事务依旧是生效的， 但最好还是分开写比较清晰和保险。
AsyncExecutionInterceptor拦截器总是能够确保在其他拦截器之前执行，避免事务拦截器先执行，而异步拦截器后执行，导致错误发生。
事务拦截器执行过程中，会将事务相关资源存放到threadlocal中，因此事务执行过程必须确保在一个线程内完成

主线程和子线程的在事务上是相互隔离的，子线程的异常不会影响主线程的事务混滚与否（让若主线程不主动throw出异常，子线程即使抛出了异常也不会影响主线程的）。
不同线程之间的事务完全隔离，异步线程内仍是可以调用异步~

------------------------------------------------------------------------------------------------------------------------
在异步任务中实现事务管理需要确保事务的开始和提交发生在异步方法的正确位置。可以使用 TransactionTemplate 来显式控制事务的边界。
示例：
```java
@Service
public class MyAsyncService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Async
    public void asyncMethodWithTransaction() {
        transactionTemplate.execute(status -> {
            try {
                // 异步执行的代码
                // ...
                return null; // 事务提交
            } catch (Exception e) {
                status.setRollbackOnly(); // 事务回滚
                throw e;
            }
        });
    }
}
```
在上述例子中，通过 TransactionTemplate 显式控制了事务的开始和提交，确保在异步任务中正确管理事务。
