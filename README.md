# Description
- auth : 77kkyu
- development environment : java
- technology : TDD
- [코드출처 블로그](https://mangkyu.tistory.com/182)

> **_TDD를 사용하면서 느낀 점_**
>
> 처음에는 테스트 코드를 작성을 꼭 해야 하나? 
> 
> 개발 비용이 많이 들어가는 거 아닌가?
> 
> 실제 코드에서 테스트하면 되는 거 아닌가?라는 생각들을 했습니다
> 
> 그래서 TDD 방법론으로 코드를 작성해 봤습니다
> 
> 단위 테스트를 하면서 코드를 작성하고 마지막에 리팩토링을 해서 
> 
> 실제 기능을 구현하니 오류 없이 원하는 기능이 나왔습니다(모든 오류를 박멸하진 않습니다)
> 
> 개발 비용은 처음에는 높지만 테스트 코드를 다 작성했기 때문에 
> 
> 나중에 다른 버그가 나와도 미리 작성해둔 테스트 코드가 용이하게 쓰일 것 같습니다!


---

# Test Driven Development

``` text
테스트 주도 개발은 개발 방법론 중에 하나이다
기능을 먼저 만들기보다는 테스트 케이스를 먼저 작성하고 
기능에 필요한 요소들을 하나씩 만들어가는 것이다
```

---

### TDD 개발 프로세스

![](https://github.com/77kkyu/tdd-springboot/blob/main/assets/img.png?raw=true)

- {<span style="color: red">Red</span>} : 실패하는 테스트 코드 작성
- {<span style="color: #008000">Greedn</span>} : 테스트 코드를 성공시키기 위한 실제 코드 작성
- {<span style="color: #0000FF">Blue</span>} : 중복 코드 제거 및 리팩토링

``` text
테스트 코드를 작성할 때까지 실제 코드를 작성하지 않고
실패하는 코드를 먼저 통과할 정도의 실제 코드를 작성해야 한다
그러면 정확한 요구 사항에 집중할 수 있다..! 
```