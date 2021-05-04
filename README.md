# 볼링 게임 점수판
## 진행 방법
* 볼링 게임 점수판 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.


## 🚀 1단계 - 질문 삭제하기 기능 리팩토링
<details>
    <summary> 1단계 요구사항 </summary>

### 질문 삭제하기 요구사항
- 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
- 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
- 답변이 없는 경우 삭제가 가능하다.
- 질문자와 답변 글의 모든 답변자 같은 경우 삭제가 가능하다.
- 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다.
- 질문자와 답변자가 다른 경우 답변을 삭제할 수없다.
- 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.

### 프로그래밍 요구사항
- qna.service.QnaService의 deleteQuestion()는 앞의 질문 삭제 기능을 구현한 코드이다. 이 메소드는 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드가 섞여 있다.
- 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드 에 대해 단위 테스트를 구현한다.

</details>

<details>
    <summary> 1단계 체크리스트 </summary>

### 질문 삭제 기능을 도메인으로 리팩토링

**도메인 분리**
- [X] 작성정보는 글의 내용과 글쓴이 정보, 삭제여부를 가진다.
- [X] 작성정보는 글쓴이가 동일한지 확인할 수 있다.
- [X] 삭제정보는 글의 id와 타입, 그리고 삭제자의 정보를 가진다.
- [X] 글의 타입은 해당 글이 질문글인지 답변글인지 구분할 수 있다.
  
**비즈니스 로직을 도메인으로 이동**
- [X] 질문글과 답변 목록중에 작성자와 다른 사람이 있는지 확인한다.
- [X] 질문글을 삭제하면 그와 관련된 답변 목록을 삭제할 수 있다.
- [X] 질문글과 답변 삭제시 삭제 기록을 남길 수 있다.

**리팩토링**
- [X] 사용하지 않는 메소드를 제거한다.


</details>

<details>
    <summary> 1단계 피드백 </summary>

### 피드백 [링크](https://github.com/next-step/java-bowling/pull/520)
- 상속 depth는 한 번정도, AbstractEntity에서는 보통 도메인의 공통관심사 정도 구현
- 생성자 내부 validation 로직보다는 static of에서 접근, static validation로 추출하여 각각 활용
- parameter에 list를 넘기기보다는 새 리스트 반환이 사이드 이펙트 방지
- ❗Instanceof는 안티패턴❗ 
  하위 타입에서 메소드 재정의하는 방식으로 사용 (getContentType처럼)
- 메소드명은 이름을 내포하게끔 (isOwner? validOwner?)

</details>

## 🚀 2단계 - 볼링 점수판(그리기)
<details>
    <summary> 2단계 요구사항 </summary>

### 기능 요구사항
- 최종 목표는 볼링 점수를 계산하는 프로그램을 구현한다. 1단계 목표는 점수 계산을 제외한 볼링 게임 점수판을 구현하는 것이다.
- 각 프레임이 스트라이크이면 "X", 스페어이면 "9 | /", 미스이면 "8 | 1", 과 같이 출력하도록 구현한다.
  - 스트라이크(strike) : 프레임의 첫번째 투구에서 모든 핀(10개)을 쓰러트린 상태
  - 스페어(spare) : 프레임의 두번재 투구에서 모든 핀(10개)을 쓰러트린 상태
  - 미스(miss) : 프레임의 두번재 투구에서도 모든 핀이 쓰러지지 않은 상태
  - 거터(gutter) : 핀을 하나도 쓰러트리지 못한 상태. 거터는 "-"로 표시
- 10 프레임은 스트라이크이거나 스페어이면 한 번을 더 투구할 수 있다.

### 프로그램 실행 결과

```
플레이어 이름은(3 english letters)?: PJS
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |      |      |      |      |      |      |      |      |      |      |

1프레임 투구 : 10
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |  X   |      |      |      |      |      |      |      |      |      |

2프레임 투구 : 8
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |  X   |  8   |      |      |      |      |      |      |      |      |

2프레임 투구 : 2
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |  X   |  8|/ |      |      |      |      |      |      |      |      |

3프레임 투구 :  7
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |  X   |  8|/ |  7   |      |      |      |      |      |      |      |

3프레임 투구 :  : 0
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |  X   |  8|/ |  7|- |      |      |      |      |      |      |      |
...

```

</details>

<details>
    <summary> 2단계 체크리스트 </summary>

### 프레임 구성요소
- [X] 핀은 시도한 투구횟수와 남아있는 핀의 개수로 구성된다.
- [X] 투구개수는 쓰러뜨릴 핀의 수로 구성된다. 
- [X] 투구시 투구개수만큼 핀의 개수를 무너뜨릴 수 있다.
- [X] 투구결과는 투구결과타입을 지닌다.
- [X] 투구결과 타입은 타입에 따른 표현 방식을 지닌다.

### 프레임
- [X] 각 프레임은 인덱스와 핀, 투구결과로 구성된다.
- [X] 투구결과를 toString 문자열로 표현할 수 있다.
- [X] 1~9번째 프레임은 일반프레임, 10번째는 마지막 프레임으로 구현한다.
- [X] 일반프레임과 마지막 프레임간의 구분되는 roll 로직을 구성한다.
- [X] 프레임들을 관리하는 프레임목록에서 한 경기씩 실행한다.

### 플레이어
- [X] 세 글자의 이름을 지닌 플레이어를 생성한다.

### 입출력
- [X] 플레이어를 입력받는다.
- [X] 해당번째 프레임의 투구수를 입력받는다.
- [X] 실행 결과를 출력한다.

</details>


<details>
    <summary> 2단계 피드백 </summary>

### [피드백링크](https://github.com/next-step/java-bowling/pull/539)
- 모든 객체가 불변성을 띌 필요는 없음 상황에 적절하게 사용할 것
- ``Pin``의 불변화는 실제 볼링장처럼 서있는지, 넘어져있는지를 표현할 수 있음 (10개의 핀을 관리하는 ``Pins``와 함께)
- ``public`` 메소드를 ``private``보다 상단에 위치시키기
- ``Interface``를 통한 상수 정의는 적절X  
  => 상수를 전달시켜야할 필요가 있다면 ``abstract class``로 전환해보는 걸 고려햐기


</details>

## 🚀 3단계 - 볼링 점수판(점수 계산)
<details>
    <summary> 3단계 요구사항 </summary>

### 기능 요구사항
사용자 1명의 볼링 게임 점수를 관리할 수 있는 프로그램을 구현한다.

### 프로그래밍 요구사항
객체지향 5원칙을 지키면서 프로그래밍한다.

### 프로그램 실행 결과
```
플레이어 이름은(3 english letters)?: PJS
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |      |      |      |      |      |      |      |      |      |      |
|      |      |      |      |      |      |      |      |      |      |      |

1프레임 투구 : 10
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |  X   |      |      |      |      |      |      |      |      |      |
|      |      |      |      |      |      |      |      |      |      |      |

2프레임 투구  : 8
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |  X   |  8   |      |      |      |      |      |      |      |      |
|      |      |      |      |      |      |      |      |      |      |      |

2프레임 투구 : 2
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |  X   |  8|/ |      |      |      |      |      |      |      |      |
|      |  20  |      |      |      |      |      |      |      |      |      |

3프레임 투구 : 8
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |  X   |  8|/ |   8  |      |      |      |      |      |      |      |
|      |  20  |  38  |      |      |      |      |      |      |      |      |

3프레임 투구 : 1
| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |
|  PJS |  X   |  8|/ |  8|1 |      |      |      |      |      |      |      |
|      |  20  |  38  |  47  |      |      |      |      |      |      |      |

...
```

</details>

<details>
    <summary> 3단계 체크리스트 </summary>

### 단일 프레임 단위
- [ ] 점수 객체는 점수값과 추가로 합산된 횟수를 관리한다.
- [ ] 결과타입은 타입에 따라 추가로 점수를 합산할 수 있다.
- [ ] 마지막 프레임에 해당하는 결과목록들의 추가점수는 없다.

### 프레임전체 범위
- [ ] 각 프레임들은 추가점수 합산을 할 수 있고, 점수 결과를 보여줄 수 있다.

### 입출력
- [ ] 점수결과 목록을 출력할 수 있다.

</details>


## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)