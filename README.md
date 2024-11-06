# java-convenience-store-precourse
## 프로젝트 설명
구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템을 구현하는 과제입니다.

사용자가 입력한 상품별 가격과 수량을 곱하여 계산하고, 최종 결제 금액을 계산하여 영수증을 출력해야 합니다.  
각 상품의 재고를 고려하여 결제 가능 여부를 파악해야 하고, 프로모션이 적용되어 있는 상품들이 있습니다.  
프로모션은 N개 구매 시 1개 무료 증정의 형태로 진행되고, 오늘 날짜가 프로모션 기간 내라면 할인을 적용합니다.  
멤버십 회원은 프로모션이 적용되지 않은 금액의 30%를 할인받습니다. 최대 할인 한도는 8,000원입니다.  
영수증은 고객의 구매 내역과 할인을 요약하여 보기 좋게 출력해야 합니다.

<br>

## 설계 과정
객체지향 설계에 있어서 가장 중요한 것은 협력을 위해 어떤 책임과 역할이 필요한지 이해하는 것이라고 생각합니다.  
따라서 설계의 방향을 잡기 위해 필요한 역할과 책임 그리고 메시지를 정리해 봤습니다.  
이후 정리한 내용을 통해 도출한 대략적인 그림을 참고해서 구현할 기능 목록을 하단에 요약했습니다.


> 이해한 과제 내용을 대략적으로 정리하는 것은 필요한 역할과 책임을 이해하는 데에 도움이 된다고 생각합니다.  
> 이렇게 정리한 내용에서 영감을 받아 구현할 기능 목록을 대략적으로나마 작성할 수 있었습니다.  
> 물론 구현 중 계획했던 내용이 틀어질 수 있지만, 프로젝트를 시작하는 데에 도움이 되었습니다.

<br>

### 필요한 역할 및 기능
- [ ] 상품의 정보(상품명과 가격)을 가지고 있는 역할이 있어야 한다.
- [ ] 상품별 재고 정보를 가지고 있는 역할이 있어야 한다.
- [ ] 재고 수량을 고려하여 결제 가능 여부를 확인할 수 있어야 한다.
- [ ] 상품이 구입될 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감해야 한다.
- [ ] 상품에 프로모션을 적용할 수 있어야 한다. 오늘 날짜가 프로모션 기간인 경우에만 할인을 적용해야 한다.
- [ ] 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감해야 한다.
- [ ] 멤버쉽 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- [ ] 멤버쉽 할인의 최대 한도는 8,000원이다.
- [ ] 영수증 기능이 필요하다.


### 메시지 정리
- [x] 환영 문구를 출력하라 -> (출력 역할) -> OutputView  
- [ ] 사용자에게 상품의 가격과 수량을 입력받아라 -> (입력 역할) -> InputView  
- [ ] 상품 재고를 초기화하라 -> (파일 입출력해서 재고 초기화하는 역할) -> StoreInitializer  
- [ ] 상품 재고를 출력하라 -> OutputView -> (상품 재고를 갖고있는 역할) -> StoreStock  
- [ ] 결제 가능 여부를 파악하라 -> (상품 재고를 갖고있는 역할) -> StoreStock  
- [ ] 프로모션 상품인지 확인하라 -> (상품 역할) -> StoreItem  
- [ ] 프로모션 재고가 충분한지 파악하라 -> (상품 역할) -> StoreItem


<br>


## 구현할 기능 목록
### 1. 출력 역할
- [x] 출력 역할의 객체를 만든다
- [x] 환영 문구를 출력하는 기능
- [ ] 재고 정보를 출력하는 기능
- [ ] 프롬프트 메시지를 출력하는 기능
- [ ] 영수증 내용을 출력하는 기능

### 2. 입력 역할
- [ ] 입력 역할의 객체를 만든다
- [ ] 사용자에게 구매할 상품명과 수량을 입력받는 기능
- [ ] Y/N 형태로 입력받는 기능
- [ ] 입력 값이 잘못되었을 경우 오류 메시지와 함께 재입력 받는 기능

### 3. 파일 입출력 역할
- [ ] 파일 입출력 역할의 객체를 만든다
- [ ] .md 파일을 읽어서 상품 재고를 초기화하는 기능

### 4. 상품 역할
- [ ] 상품의 정보(상품명과 가격 등)을 가지고 있는 객체를 만든다
- [ ] 상품에 프로모션 정보를 포함할 수 있는 기능을 만든다

### 5. 재고 역할
- [ ] 상품의 정보들을 가지고, 재고 역할을 하는 객체를 만든다
- [ ] 재고에서 특정 상품을 찾는 기능
- [ ] 프로모션 상품인지, 비 프로모션 상품인지 구분하는 기능

### 6. 영수증 역할
- [ ] 영수증 역할의 객체를 만든다
- [ ] 구입 내역을 입력받아서 저장하는 기능