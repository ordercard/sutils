package com.spring.sutils.db;

/**
 * @Auther :huiqiang
 * @Description :如果您需要在启动后运行某些特定代码SpringApplication，则可以实现ApplicationRunner或CommandLineRunner接口。两个接口以相同的方式工作并提供单个run方法，该方法在SpringApplication.run(…​)完成之前调用 。

所述CommandLineRunner接口提供访问的应用程序的参数作为一个简单的字符串数组，而ApplicationRunner使用了ApplicationArguments前面所讨论的接口。以下示例显示了CommandLineRunner一个run方法：

如果定义了必须以特定顺序调用的多个CommandLineRunner或ApplicationRunnerbean，则可以另外实现 org.springframework.core.Ordered接口或使用 org.springframework.core.annotation.Order注释。
 * @Date: Create in 下午4:06 2018/7/20 2018
 * @Modify:
 */

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class complate implements CommandLineRunner {

@Override
public  void run(String ... args){
        //做点什么......
                }
}
