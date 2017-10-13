/* 최종 수정일 : 2017-10-12 */
/* 3. 조건문과 조건식 */

// 조건은 항상 참(true) 또는 거짓(false)이다.
console.log(true);
console.log(false);
console.log('----------');

console.log(10 > 5); // 10이 5보다 큰가?
console.log(3 > 3); // 3이 3보다 큰가?
console.log(102 <= 102) // 102가 102보다 작거나 같은가?
console.log(7 == 7) // 7과 7이 같은가?
console.log(2 != 5) // 2와 5가 같지 않은가?
console.log('----------');

console.log(1 == '1'); //문자열인 '1'을 자바스크립트에서 숫자로 자동 형변환
console.log(1 === '1'); // 자동 형 변환 
console.log(3 !== '5'); // != 도 동일함
console.log('----------');

console.log('Hello!' == "Hello!"); // 문자열도 비교할 수 있음
console.log('----------');



/* 조건문 if */

// 괄호에 조건을 작성한다.
if(3 > 5){
    console.log('3이 5보다 큽니다.');
} else if (3 == 4){
    console.log('아니면 3과 4는 같습니다.');
} else {
    console.log('참인 조건이 없습니다.');
}

//--------------------------//
var a = 5;
var b = 10;
if(a > b){
    console.log('a가 b보다 큽니다.');
} else {
    console.log('a가 b보다 크지 않습니다.');
}
console.log('----------');

//--------------------------//
// switch문
var n = 2;
switch(n){
    case 1:
        console.log(1);
        break; // 꼭 break를 써준다
        
    case 2: // 여기가 실행됨
        console.log(2);
        // break를 안써주면 case 2실행 후 바로 아래인 case 3도 실행됨
    
    case 3:
        console.log(3);
        break;
        
    default:
        break;
}