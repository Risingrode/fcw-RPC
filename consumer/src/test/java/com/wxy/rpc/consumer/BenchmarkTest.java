package com.wxy.rpc.consumer;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.wxy.rpc.client.handler.RpcResponseHandler;
import com.wxy.rpc.client.transport.netty.NettyRpcClient;
import com.wxy.rpc.consumer.config.BenchmarkAnnotationConfig;
import com.wxy.rpc.consumer.controller.HelloController;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * 使用 JMH 进行性能测试
 *
 * @author Wuxy
 * @version 1.0
 * @ClassName BenchmarkTest
 * @since 2023/2/22 16:33
 */
// 使用所有可用的基准测试模式
@BenchmarkMode({Mode.All})
// 在性能测试开始之前，JMH 会先运行几个迭代（`iterations`）来预热 JVM，以避免因为 JIT 编译的影响导致测试结果不准确。
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
// 测量3次,每次测量的持续时间
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
// 并发线程
@Threads(10000)
// 每个测试运行在单独的JVM进程中
@Fork(1)
// 指定类的测试范围
@State(Scope.Benchmark)
// 输出结果的时间单位
@OutputTimeUnit(TimeUnit.SECONDS)
@Slf4j
public class BenchmarkTest {
    private final HelloController helloController;

    static {
        // 初始化时设置 NettyRpcClient 和 RpcResponseHandler 的日志类级别为 OFF，及关闭日志打印
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger clientLogger = loggerContext.getLogger(NettyRpcClient.class);
        clientLogger.setLevel(Level.OFF);
        Logger handlerLogger = loggerContext.getLogger(RpcResponseHandler.class);
        handlerLogger.setLevel(Level.OFF);
    }

    public BenchmarkTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BenchmarkAnnotationConfig.class);
        helloController = context.getBean("helloController", HelloController.class);
    }

    @Benchmark
    public void testSayHello() {
        helloController.hello("hello,fcw");
    }

    public static void main(String[] args) throws RunnerException {
        log.info("测试开始");
        Options opt = new OptionsBuilder()
                .include(BenchmarkTest.class.getSimpleName())
                // 可以通过注解注入
//                .warmupIterations(3)
//                .warmupTime(TimeValue.seconds(10))
                // 报告输出
                .result("result.json")
                // 报告格式
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }
}
