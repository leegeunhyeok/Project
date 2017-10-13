/* 최종 수정일 : 2017-10-12 */
/* 4. 반복문 */

// 4-1. while문
var a = 5;
while(a > 0){ // a가 0보다 크면 반복
    console.log(a);
    a--; // a를 1씩 감소
}
console.log('----------');

// 4-2. do-while문
var b = 0;
do { //일단 한번 실행 (조건검사는 한번 실행 하고)
    console.log(b);
} while(b > 0); // b가 0보다 크면 반복
console.log('----------');

// 4-3. for문
for(var c=0; c<3; c++){ // c가 0부터 3까지 1씩 증가하면서 (3번 반복. 0, 1, 2)
    console.log(c);
}