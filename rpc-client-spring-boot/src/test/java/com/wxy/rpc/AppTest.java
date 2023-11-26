package com.wxy.rpc;

import com.wxy.rpc.client.transport.netty.RequestMetadata;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    public RequestMetadata requestMetadata;

    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }


    public void testApp() {
        assertTrue(true);
    }

    public static void main(String[] args) {
        RequestMetadata r=RequestMetadata.builder()
                        .port(8080)
                        .serverAddr("127.0.0.1")
                        .timeout(1000)
                        .rpcMessage(null)
                        .build();
        System.out.println(r);
    }


}
