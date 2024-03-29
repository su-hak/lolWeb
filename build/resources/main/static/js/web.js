// // 네비게이션 링크 클릭 시 페이지 로드
// document.querySelectorAll('.topNavMenu').forEach(link => {
//     link.addEventListener('click', function (event) {
//         event.preventDefault(); // 기본 동작 방지
//         const url = this.getAttribute('href'); // 클릭한 링크의 주소
//         fetchPage(url); // 해당 주소로 페이지 로드
//     });
// });
//
// // 페이지 로드 함수
// function fetchPage(url) {
//     fetch(url)
//         .then(response => response.text())
//         .then(html => {
//             document.querySelector('#topNavMenuPage').innerHTML = html; // 내용을 #content에 채움
//         })
//         .catch(error => console.error('Error fetching page:', error));
// }


// document.querySelector('.navMenu').forEach(link => {
//     link.addEventListener('click', function (event) {
//         event.preventDefault(); // 기본 동작 방지
//         const url1 = this.getAttribute('href'); // 클릭한 링크의 주소
//         fetchPage(url1); // 해당 주소로 페이지 로드
//     });
// });
//
// // 페이지 로드 함수
// function fetchPage(url1) {
//     fetch(url1)
//         .then(response => response.text())
//         .then(html => {
//             document.querySelector('#lolMain').innerHTML = html; // 내용을 #content에 채움
//         })
//         .catch(error => console.error('Error fetching page:', error));
// }
