/* 최종 수정일 : 2017-10-12 */
/* 1. 기초 자료형 */

// 자바스크립트는 타입을 지정하는 키워드가 없다. (int, char, float..)
console.log(3); 
console.log(3.14);
console.log('Hello');
console.log('A');
console.log(true);
console.log(NaN); // Not a Number
console.log(10 + NaN); // NaN과 연산하면 결과는 항상 NaN 
console.log(typeof object); // undefined
console.log('----------');

// typeof 로 해당 데이터 타입을 알 수 있다.
// 3과 3.0은 같으며 정수, 실수를 구분하지 않는다. (숫자는 항상 number)
console.log(typeof 3); 
console.log(typeof 3.14);
console.log('----------');

// 문자(char), 문자열(String)의 구분이 없다.
console.log(typeof 'Hello'); // String
console.log(typeof 'A'); // String
console.log('----------');

console.log(typeof function(){}); // 함수도 자료형에 포함(객체)
console.log(typeof new Object()); // 객체
console.log(typeof {}); // 리터럴 객체