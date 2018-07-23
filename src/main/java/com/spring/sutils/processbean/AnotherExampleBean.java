package com.spring.sutils.processbean;

import org.springframework.beans.factory.InitializingBean;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午2:50 2018/7/23 2018
 * @Modify:
 */


/**
 * 。生命周期回调
 要与容器的bean生命周期管理进行交互，可以实现Spring InitializingBean和DisposableBean接口。容器调用 afterPropertiesSet()前者，destroy()后者允许bean在初始化和销毁​​bean时执行某些操作。

 JSR-250 @PostConstruct和@PreDestroy注释通常被认为是在现代Spring应用程序中接收生命周期回调的最佳实践。使用这些注释意味着您的bean不会耦合到Spring特定的接口。有关详细信息，请参阅@PostConstruct和@PreDestroy。

 如果您不想使用JSR-250注释但仍希望删除耦合，请考虑使用init-method和destroy-method对象定义元数据。

 在内部，Spring Framework使用BeanPostProcessor实现来处理它可以找到的任何回调接口并调用适当的方法。如果您需要自定义功能或其他生命周期行为Spring不提供开箱即用的功能，您可以BeanPostProcessor自己实现。有关更多信息，请参阅 容器扩展点。

 除了初始化和销毁​​回调之外，Spring管理的对象还可以实现Lifecycle接口，以便这些对象可以参与由容器自身生命周期驱动的启动和关闭过程。

 本节描述了生命周期回调接口。

 初始化回调
 该org.springframework.beans.factory.InitializingBean接口允许bean在容器设置了bean的所有必需属性之后执行初始化工作。的InitializingBean接口规定了一个方法：

 void afterPropertiesSet() throws Exception;
 建议您不要使用该InitializingBean接口，因为它会不必要地将代码耦合到Spring。或者，使用@PostConstruct注释或指定POJO初始化方法。对于基于XML的配置元数据，可以使用该init-method属性指定具有void无参数签名的方法的名称。使用Java配置，您可以使用initMethod属性@Bean，请参阅接收生命周期回调。例如，以下内容：


 */
public class AnotherExampleBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        // do some initialization work
    }
}