/* 최종 수정일 : 2017-10-13 */
/* 7. 배열 */

// 배열도 객체이다
var arr1 = new Array(); // new Array로 생성할 수 있다
arr1[0] = 1; // 인덱스는 0부터 시작한다
arr1[1] = 2;
arr1[2] = 3;
arr1[3] = 4;
arr1[4] = 5;

// 배열객체.length 는 해당 배열의 길이를 의미한다.
console.log('Array length: ' + arr1.length);
for(var i=0; i<arr1.length; i++){
    console.log(arr1[i]); // 0 ~ 배열 길이까지
}

var arr2 = ['a', 'b', 123]; // 리터럴 배열 생성
console.log(arr2[2]); // 인덱스와 기타 접근법은 동일하다.