package com.liu.eemrsserver;

import com.liu.eemrsserver.config.ApplicationContextProvider;
import com.liu.eemrsserver.utils.GetExceptionMessage;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.ServerSocket;
import java.net.Socket;
@EnableTransactionManagement
@MapperScan(basePackages = "com.liu.eemrsserver.mapper")
@SpringBootApplication
public class EemrsServerApplication implements CommandLineRunner {
    private static Logger logger = Logger.getLogger(EemrsServerApplication.class);

    @Override
    public void run(String... args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8887);
            while (true) {
                logger.info("等待连接");
                Socket socket = serverSocket.accept();//建立与服务器的连接
                String user = socket.getInetAddress().getHostName();
                String ip = socket.getInetAddress().getHostAddress();
                logger.debug("用户" + user + "建立连接");
                logger.info("已为用户[" + user + ":" + ip + "]生成会话密钥");
                ControlThread thread = ApplicationContextProvider.getBean(ControlThread.class);
                thread.setName("用户:"+socket.getInetAddress().getHostName());
                thread.getControlRun().setSocket(socket);
                logger.debug("线程开启");
                thread.start();
            }
        } catch (Exception e) {
            logger.info("\n"+ GetExceptionMessage.getMessage(e));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(EemrsServerApplication.class, args);
    }
}
