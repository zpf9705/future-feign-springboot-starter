# future-feign-springboot-starter
简易版本地调用技术


<img src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180315%2F6ef6c3a1aee74819aa6af00b5cf449fc.png&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1668585148&t=d8fe7a9bc79e628d3278aea4c28f81f0" alt="Sentinel Logo" width="60%">

## 环境要求
- [jdk-1.8](#jdk)
- [spring-boot-2.5.6](#springboot)

## 使用场景

- [处于同一maven私服地址项目对接流程](#profiles)


## 使用步骤

消费者端：
- [在springboot启动主类上添加注解@EnableFutureFeignClients]()
- [basePackages - 扫描feign客户端路径（带有@FutureFeignClien的接口类所在路径)]()
- [配置访问uri,注入带有@FutureFeignClient的feign客户端接口进行调用]()

生产者端：
- [创建对外访问接口,在接口层面添加注解@FutureFeignClient]()
- [name ---- 代理对象bean名称（之后要为feign客服端添加代理对象至ioc容器）]()
- [path ---- 访问控制器前缀路径]()
- [uri ---- 访问uri地址,可将此地址告知消费者端,实现多环境调用]()
- [在feign客户端接口里面添加方法,并标注@FutureFeignMapping]()
- [value ---- 方法访问路径]()
- [method ---- 方法调用类型]()
