/* 최종 수정일 : 2017-10-13 */
/* 8. 함수 */

// function 키워드로 함수를 선언한다.
function add(a, b){
    return a + b;
}

console.log(add(2, 6)); // 위에서 생성한 add 함수를 호출
console.log('----------');

// 함수도 객체이다. 변수에 대입 가능
var f = function(str){ 
    console.log(str);
}

console.log(f('Hello'));
console.log('----------');

// 아래와 같이 바로 실행할 수 있다.
var sum = function(a, b){
    console.log(a + b);
}(7, 3);
console.log('----------');

// 콜백함수
var callbackTest = function(str, callback){ // 함수의 매개변수로 함수를 전달할 수 있다
    callback(str); //전달받은 콜백함수를 호출
}

console.log(callbackTest('Hello callback!', f)); // 위에서 만든 f 함수를 전달
console.log('----------');

// 객체를 생성하는 함수
function Person(name, age){ 
    return { 
        name: name,
        age: age
    }
}

var p = new Person('Lee', 18);
console.log(p.name);
console.log(p.age);
console.log('----------');


