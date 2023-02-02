<img src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180315%2F6ef6c3a1aee74819aa6af00b5cf449fc.png&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1668585148&t=d8fe7a9bc79e628d3278aea4c28f81f0" alt="Sentinel Logo" width="60%">

# future-feign-springboot-start - Simple version to call technology

## Environmental requirements
- [jdk-1.8](#jdk)
- [spring-boot-2.5.6](#springboot)

## Usage scenarios

- [At the same maven server address project docking process](#profiles)


## Using the step

The consumer：
- [On the main class springboot start add annotations @EnableFutureFeignClients]()
- [Base feign client Packages - scanning path (with @FutureFeignClient interface class path)]()
- [Configuration access uri, injected with @FutureFeignClient interface to call the Client]()


The producer side：
- [Create external access interface, the interface layer to add annotations @FutureFeignClient]()
- [name ---- Proxy objects bean name（Added to feign after the end of the service proxy objects to the ioc container）]()
- [path ---- Access controller prefix paths]()
- [uri ---- Access uri address, this address can be told that the consumer, implement environment more calls]()
- [Add methods in feign client interface, and mark @FutureFeignMapping]()
- [path ---- Methods the access path]()
- [method ---- Method invocation type]()
