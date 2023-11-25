package com.wxy.rpc.server.transport.http;

import com.wxy.rpc.core.exception.RpcException;
import com.wxy.rpc.server.transport.RpcServer;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 基于 HTTP 通信协议实现的 Rpc Server 类
 *
 * @author Wuxy
 * @version 1.0
 * @ClassName HttpRpcServer
 * @Date 2023/1/7 11:11
 */
public class HttpRpcServer implements RpcServer {

    @Override
    public void start(Integer port) {
        try {
            Tomcat tomcat = new Tomcat();

            Server server = tomcat.getServer();

            Service service = server.findService("Tomcat");

            Connector connector = new Connector();
            connector.setPort(port);

            // 获取本地主机的ip地址
            String hostname = InetAddress.getLocalHost().getHostAddress();

            StandardEngine engine = new StandardEngine();
            engine.setDefaultHost(hostname);

            StandardHost host = new StandardHost();
            host.setName(hostname);

            // 空字符串表示根路径
            String contextPath = "";
            Context context = new StandardContext();
            context.setPath(contextPath);
            // 添加生命周期监听器 验证路径是否正确 一般在初始化阶段运行
            context.addLifecycleListener(new Tomcat.FixContextListener());

            host.addChild(context);
            engine.addChild(host);

            service.setContainer(engine);
            service.addConnector(connector);

            // 注册一个servlet服务器，将其映射到根路径
            tomcat.addServlet(contextPath, "dispatcher", new DispatcherServlet());
            // 这意味着所有到达服务器的请求都将被该 Servlet 处理
            context.addServletMappingDecoded("/*", "dispatcher");

            tomcat.start();
            // 这行代码启动了 Tomcat 的主事件循环。await() 方法使 Tomcat 保持运行状态，
            // 等待来自客户端的请求。在这里，它阻塞当前线程，使得 Tomcat 保持运行状态，直到显式停止。
            tomcat.getServer().await();
        } catch (LifecycleException | UnknownHostException e) {
            throw new RpcException("Tomcat server failed to start.", e);
        }
    }

}
