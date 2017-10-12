/* 최종 수정일 : 2017-10-20 */
/* 5. 연산자 */

var name = 'lgh';

// and 연산자 (&&)
console.log(true && false); // 조건 두개가 항상 참일 때 true
console.log(null && name); // 앞에 있는 값이 거짓이면 앞의 값을 반환

// or 연산자 (||)
console.log(true || false); // 조건 중 하나라도 참일 때 true
console.log(null || name); // 앞에 있는 값이 거짓이면 뒤의 값을 반환

// 삼항 연산자 ? :
console.log(3 > 5 ? '1번':'2번'); // 조건이 참이면 1번 아니면 2번 반환