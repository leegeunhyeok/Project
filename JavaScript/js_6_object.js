/* 최종 수정일 : 2017-10-12 */
/* 6. 객체 */

var object1 = new Object(); // 새 객체 생성
var object2 = {}; // 새 객체 생성 (리터럴)

object1.name = 'Ob1'; // 객체에 name 이라는 속성 추가 후 Ob1 값 대입
object2.name = 'Ob2'; // 같음

object1.data = '123aaa'; // data 속성 추가
object2.data = 34;


console.log('1 Object');
console.log(object1.name); // . 연산자로 접근할 수 있다.
console.log(object1.data);

console.log('----------');

console.log('2 Object');
console.log(object2['name']); // ['속성 이름'] 으로도 접근할 수 있다.
console.log(object2['data']);

console.log('----------');

console.log(typeof object1); // 둘다 타입은 object 이다.
console.log(typeof object2);
