# Spring-Cloud-Microservice
> 마이크로 서비스 아키텍처를 기반으로 프로젝트를 진행하면서 분산 환경을 갖출 수 있도록 Spring Clould 가 제공된다.
<br> configuration management, service discovery, circuit breakers, Load balancing

## [Spring Cloud](https://spring.io/projects/spring-cloud)
- [x] Spring Cloud Starter
- [x] [Spring Cloud Netflix](https://docs.spring.io/spring-cloud-netflix/reference/index.html)
- [x] [Spring Cloud Gateway](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/how-it-works.html)
- [x] Spring Cloud Config
- [x] Spring Cloud Bus

## MSA 표준 구성요소 / feat. [CNCF](https://landscape.cncf.io/)
<img width="639" alt="image" src="https://github.com/user-attachments/assets/3fb62d40-30eb-4078-acdb-4818fe6283a1">

## 아키텍처
![SpringCloudMicroservice_architecture](https://github.com/user-attachments/assets/30772f78-9f68-4eab-ae0c-cb30960ed77e)


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
- [x] Spring Cloud Config
  - **Application 의 설정 파일 ( application.properties / application.yml ) 을 동적으로 서버 호출을 통해서 읽어 온다.**
  - 분산 시스템에서 서버 클라이언트 구성에 필요한 설정 정보를 외부 시스템에서 관리
  - 하나의 중앙화 된 저장소에서 구성요소 관리 가능
  - 각 서비스를 다시 빌드하지 않고 바로 적용 가능
  ```
  Application 을 구동하는 과정에서 application.properties / application.yml 설정 파일을 통해서 Server port, Eureka URL 등을 구동 시에 가져와서 선언하게 됩니다.
  각 프로젝트별로 설정 파일이 존재하고, 이 설정 파일은 프로젝트 내에 필요한 내용만 작성이 됩니다.
  Users 의 application.yml / Catalogs 의 application.yml / Spring Cloud Gateway 의 application.yml 처럼
  사실 큰 프로젝트던, MSA 프로젝트이건 application.yml 은 한번 설정해두면 크게 바뀌는 일이 없지만, 바뀌게 되면 application 을 재 빌드하고 배포를 해야 합니다.
  ```
  - application 을 새롭게 빌드하고 배포하는게 많은 프로젝트가 아니고 CI/CD 가 무중단으로 되어 있다면 서비스에는 큰 필요성을 느끼지 못할 수 있습니다.
  - 그럼 Spring Cloud Config 를 왜 사용하냐?
      - MSA 같은 경우 실제로 많은 application 이 분리되어 있고, 동일한 application 이 10 ~ 20 개 이상의 여러 인스턴스를 생성하게 됩니다.
      - application 정보를 수정하면, 10 ~ 20개의 인스턴스를 전부 CI/CD 를 해야합니다. 생각보다 한개의 인스턴스인 경우에 비해 작업 할게 늘어납니다.
      - Spring Cloud Config 를 사용하면, **application 설정 정보를 Spring Cloud Config 를 통해서 CI/CD 없이 실행 중인 Application 에 반영할 수 있습니다.**
      - 그리고 application 마다 중복되는 사항들이 있을 겁니다. 이를 공통 application 으로 중복을 제거하고 편리하게 쓸 수 있습니다.
  - 단, Spring Cloud Config 를 사용해도 Actuator 의 refresh 작업을 일일이 작동 시켜 주어야 반영이 됩니다.
  - 이를 쉽게 적용할 수 있도록 해주는 것이 Spring Cloud Bus 가 있습니다.    
  - 개인적인 생각으로 동적으로 설정 파일을 변경 할 수 있지만, 이 과정에서도 refresh 라는 작업이 필요하게 되고 datasource 와 같은 작업은 동적 변경에 있어서 더 어려움이 있을 수 있으니 꼭 필요한가에 대한 생각이 듭니다.
- [ ] Spring Cloud Bus
  - 분산 시스템의 노드( MSA ) 를 경량 메시지 브로커와 연결
  - 상태 및 구성에 대한 변경 사항을 연결된 노드에게 전달 ( broadcast )
  - 즉, 설정이 바뀌면 노드에게 "설정이 변경 되었다" 를 방송으로 전파!!
- MicroService [[APIs]](https://github.com/IMWoo94/Spring-Cloud-Microservice/wiki#users-apis)
> #Spring boot 3.3.4 #Java21 #Gradle8 #Spring Cloud Eureka Client #Spring Security6.x #H2 #Spring Data JPA #ModelMapper
  - **Users** [User Service](https://github.com/IMWoo94/Spring-Cloud-Microservice/tree/main/user-service-eureka-client)
  - **Catalogs** [Catalog Service](https://github.com/IMWoo94/Spring-Cloud-Microservice/tree/main/catalog-service-eureka-client)
  - **Orders** [Order Service](https://github.com/IMWoo94/Spring-Cloud-Microservice/tree/main/order-service-eureka-client)

### Login 흐름
![SpringCloudMicroservice_architecture drawio](https://github.com/user-attachments/assets/bb181318-69e8-49c8-9d53-139ac30917cb)

