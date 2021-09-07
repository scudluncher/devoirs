
환경구성정보

1. 사용 JDK : zulu open jdk for M1 mac 11.0.12+7  // https://www.azul.com/downloads/?version=java-11-lts&os=macos&architecture=arm-64-bit&package=jdk

2. 사용한 프레임워크 및 라이브러리
2.0 Spring Boot 2.5.4
2.1 Spring Shell (터미널 구성을 위하여 사용)
2.2 Spring-Boot-Starter-JPA, ORM 사용
2.3 H2 in-memory database 



프로젝트 테이블 및 코드 개략적인 설명

A.테이블 구조  (schema.sql 참조)
	1.ORDER_TABLE (오더 내역 테이블, H2 DB 에서 ORDER 가 예약어로 잡혀있어서 ORDER_TABLE 로 진행)

	COLUMN NAME,  DATA TYPE,  COLUMN PROPERTY, COLUMN CONTENTS
	ID            INT         PK               비식별자 PK
	TOTAL_AMOUNT  INT                          주문의 총 금액
	PAID_AMOUNT   INT                          주문금액 + 택배금액
	PARCEL_FEE    INT                          택배비 금액


	2.PRODUCT (상품 정보 테이블)
	COLUMN NAME,  DATA TYPE,  COLUMN PROPERTY, COLUMN CONTENTS
	ID            INT         PK               비식별자 PK
	PRODUCT_DESC  VARCHAR2                     상품정보
	PRICE         INT                          가격정보
	STOCK_QTY     INT                          재고수량정보


	비식별자 PK 의 경우 HIBERNATE_SEQUEUNCE 에 위임합니다.

B. ORDER -> PRODUCT 재고 감소의 경우
	재고 관련 서비스인 StockService 를 만들기보다, Product 객체의 Order 에 대한 의존성을 낮추기 위해서
	Order 가 생성되어 JPA 를 통해 저장 될 시, Spring 에서 지원하는 Event 를 발행하여 재고 감소가 이루어집니다.
C. OrderCommands 가 기존 웹 기반 Spring 에서 Controller 의 역할을 합니다.
D. OrderService - OrderServiceImpl : 주문을 준비하고, 실제 주문을 만드는 역할을 합니다
E. OrderValidator : 진행하고 있는 주문에 대해 유효한 주문인지 확인합니다.
F. ProductListingService - ProductServiceImpl : 상품 리스트를 보여주는 역할을 합니다
G. PickAndSendWithOrderPaidEventHandler : 주문이 진행될 시, 해당 주문에 대한 event 를 받아 재고 처리를 담당합니다.
H. 멀티스레드 재고 차감의 경우, JPA Lock 을 이용하여 구현하였기에 부득이하게 단위테스트로 진행하지 못하고 SpringBootTest 를 통해 진행하였습니다.
I. SoldOutException, WrongProductException : RuntimeException 을 상속받아 각각 재고가 부족한 경우, 잘못된 상품이 입력된 경우를 처리합니다.





실행방법

터미널 상에서 프로젝트로 이동 후 아래 명령어를 입력하면 프로그램이 구동합니다.
./gradlew bootRun
