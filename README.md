# Spring-Cloud-Microservice
> 마이크로 서비스 아키텍처를 기반으로 프로젝트를 진행하면서 분산 환경을 갖출 수 있도록 Spring Clould 가 제공된다.
<br> configuration management, service discovery, circuit breakers, Load balancing

## [Spring Cloud](https://spring.io/projects/spring-cloud)
- [x] Spring Cloud Starter
- [x] [Spring Cloud Netflix](https://docs.spring.io/spring-cloud-netflix/reference/index.html)
- [x] [Spring Cloud Gateway](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/how-it-works.html)

## MSA 표준 구성요소 / feat. [CNCF](https://landscape.cncf.io/)
<img width="639" alt="image" src="https://github.com/user-attachments/assets/3fb62d40-30eb-4078-acdb-4818fe6283a1">

## 아키텍처
![SpringCloudMicroservice_architecture](https://github.com/user-attachments/assets/027944d8-2d5b-4168-a90f-f6aaf0ad88ef)

## 구성
- [x] Spring Cloud Netflix Eureka Server [[discoveryservice]](https://github.com/IMWoo94/Spring-Cloud-Microservice/tree/main/discoveryservice)
  - https://localhost:8761/eureka
- [x] Spring Cloud Netflix Eureka Client [[user-service-eureka-client]](https://github.com/IMWoo94/Spring-Cloud-Microservice/tree/main/user-service-eureka-client)
  - Server random port apply ( server.port : 0 )
  - Eureka Server registry true
  - Eureka Instance Id ( ${spring.cloud.client.hostname}:${spring.application.instance_id:${random.value}})
- [x] Gateway
  - [ deprecated ] Spring-Cloud-Netflix-Zuul ( API Gateway )
    - [Zuul Service](https://github.com/IMWoo94/Spring-Cloud-Microservice/tree/main/zuul-service)
      > #Spring boot 2.4.5 #Java11 #Gradle7.2 #Spring Cloud 2020.0.2 #Zuul2.2.10
      - route
        - path: /first-service/** <br> url: http://localhost:8081
        - path: /second-service/** <br> url: http://localhost:8082
  - [ deprecated ] Spring-Cloud-Netflix-Ribbon ( Client Side Load balancing )
  - [feat. Spring Boot Maintenace](https://spring.io/blog/2018/12/12/spring-cloud-greenwich-rc1-available-now)
    <img width="795" alt="image" src="https://github.com/user-attachments/assets/68aa7cb8-b888-4f0b-95cd-9ca8eb6ffb1b">
  - Spring Cloud Gateway
    - [Spring Cloud Gateway Service](https://github.com/IMWoo94/Spring-Cloud-Microservice/tree/main/apigateway-service)
      > #Spring boot 3.3.4 #Java21 #Gradle8 #Spring Cloud 2023.0.3
    - Spring Cloud Gateway Filter
      - Predicate, Filter 기반으로 작동
      - Gateway Client -> Gateway Handler -> (PRE)Global Filter -> (PRE)CustomFilter -> Proxied Service <br> Gateway Client <- Gateway Handler <- (POST)Global Filter <- (POST)CustomFilter <- Proxied Service
        
        <img width="492" alt="image" src="https://github.com/user-attachments/assets/090583b3-8e02-475c-bacd-f9ad45b261e3">
- [x] Load balancer
  > Spring Cloud Gateway Global Filter
  ```
  ReactiveLoadBalancerClientFilter 에 의해서 Load balancing
  Eureka 에 등록되어 있는 Application 정보를 가져와 URI 와 일치하는 마이크로 서비스에 제공한다.
  Default : RoundRobinLoadBalancer ( 라운드 로빈 방식으로 로드밸런싱 )
  ```
    - [reactive-loadbalancer-client-filter](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/global-filters.html#reactive-loadbalancer-client-filter)
    - [spring-cloud-loadbalancer](https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#spring-cloud-loadbalancer)
- MicroService
  - **Users** [ User APIs ]()
    - Features.
      - [ ] 신규 회원 등록
      - [ ] 회원 로그인
      - [ ] 상세 정보 확인
      - [ ] 회원 정보 수정/삭제
      - [ ] 상품 주문
      - [ ] 주문 내역 확인 
  - [ ] Catalogs
  - [ ] Orders
