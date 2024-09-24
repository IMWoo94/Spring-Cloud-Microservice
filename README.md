# Spring-Cloud-Microservice
> 마이크로 서비스 아키텍처를 기반으로 프로젝트를 진행하면서 분산 환경을 갖출 수 있도록 Spring Clould 가 제공된다.
<br> configuration management, service discovery, circuit breakers, Load balancing

## [Spring Cloud](https://spring.io/projects/spring-cloud)
- [x] Spring Cloud Starter
- [x] [Spring Cloud Netflix](https://docs.spring.io/spring-cloud-netflix/reference/index.html)


## MSA 표준 구성요소 / feat. [CNCF](https://landscape.cncf.io/)
<img width="639" alt="image" src="https://github.com/user-attachments/assets/3fb62d40-30eb-4078-acdb-4818fe6283a1">

## 구성
- [x] Spring Cloud Netflix Eureka Server [[discoveryservice]](https://github.com/IMWoo94/Spring-Cloud-Microservice/tree/main/discoveryservice)
  - https://localhost:8761/eureka
- [x] Spring Cloud Netflix Eureka Client [[user-service-eureka-client]](https://github.com/IMWoo94/Spring-Cloud-Microservice/tree/main/user-service-eureka-client)
  - Server random port apply ( server.port : 0 )
  - Eureka Server registry true
  - Eureka Instance Id ( ${spring.cloud.client.hostname}:${spring.application.instance_id:${random.value}})
