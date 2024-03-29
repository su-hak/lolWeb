// 작은 화면 메뉴버튼 누를때 메뉴 띄우기
document.addEventListener('DOMContentLoaded', function () {
    const triggerButton = document.getElementById('fullscreen-trigger');
    const closeButton = document.getElementById('fullscreen-closed');
    const overlay = document.getElementById('fullscreen-overlay');

    triggerButton.addEventListener('click', function () {
        overlay.style.display = 'block';
        triggerButton.style.display = 'none';
    });

    closeButton.addEventListener('click', function () {
        overlay.style.display = 'none';
        triggerButton.style.display = 'block';
    });
});

//
// // 공지사항 모달창 띄우기
// document.addEventListener('DOMContentLoaded', function() {
//     // 모달 관련 선언
//     var modal = document.getElementById('fullscreenModal');
//     var openModalButton = document.getElementById('openModalButton');
//     var mContent = document.getElementById('modal_contents');
//     var innerContent = document.getElementById('modal_content');
//     var mRightLine = document.getElementById('modal-line-right');
//     var mLeftLine = document.getElementById('modal-line-left');
//
//     // 버튼 클릭 시 공기사항 모달창 보이게 하기
//     openModalButton.onclick = function() {
//         modal.style.display = 'block';
//     };
//
//     // 닫기버튼 클릭시 모달창 지우기 // 닫기 버튼 안만들어서 적용안함
//     // function closeModal() {
//     //   modal.style.display = 'none';
//     // }
//
//     // 모달창 클릭시 모당찰 지우기
//     window.onclick = function(event) {
//         if (event.target == mContent || event.target == innerContent || event.target == mRightLine || event.target == mLeftLine ) {
//             modal.style.display = 'none';
//         }
//     };
// });


// 작은 화면일때 아이템 리스트 위치 변경
window.addEventListener('resize', function () {
    var plusItem = document.getElementById('plusItem');

    // 현재 윈도우의 너비 가져오기
    var windowWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;

    // 너비가 768px 이하인 경우 dropright 클래스 제거
    if (windowWidth <= 768) {
        plusItem.classList.remove('dropright');
        plusItemR.classList.remove('dropleft');
        plusItem.classList.add('dropcenter');
        plusItemR.classList.add('dropcenter');
    } else {
        // 너비가 768px 초과인 경우 dropright 클래스 추가 (선택적)
        plusItem.classList.remove('dropcenter');
        plusItemR.classList.remove('dropcenter');
        plusItem.classList.add('dropright');
        plusItemR.classList.add('dropleft');
    }
});

// 초기 로딩 시 한 번 실행
window.dispatchEvent(new Event('resize'));

// 작은 화면일때 아이템 리스트 위치 변경 End


 // 특정 영역에 페이지 로딩
// function loadMainContent(page) {
//     var xhr = new XMLHttpRequest();
//     xhr.onreadystatechange = function () {
//     if (xhr.readyState === 4 && xhr.status === 200) {
//         document.getElementById("main").innerHTML = xhr.responseText;
//
//         }
//     };
//     xhr.open("GET", "/" + page, true);
//     xhr.send();
// };


// 네비바에 마우스 오버시 서브메뉴 표출
document.addEventListener("DOMContentLoaded", function () {
    var navElement = document.querySelector('.nav');
    var subNavElement = document.getElementById('sub_nav');

    navElement.addEventListener("mouseenter", function () {
        subNavElement.style.maxHeight = "180px"; // 원하는 최대 높이로 설정하세요.
    });

    navElement.addEventListener("mouseleave", function () {
        subNavElement.style.maxHeight = "0";
    });

    subNavElement.addEventListener("mouseenter", function () {
        subNavElement.style.maxHeight = "180px"; // 원하는 최대 높이로 설정하세요.
    });

    subNavElement.addEventListener("mouseleave", function () {
        subNavElement.style.maxHeight = "0";
    });
});


// 검색 아이콘 마우스 오버 시 전체 검색창 표시
document.addEventListener("DOMContentLoaded", function () {
    var searchElement = document.getElementById('searching');
    var searchFormElement = document.getElementById('searchForm');

    searchElement.addEventListener("mouseenter", function () {
        searchFormElement.style.maxHeight = "180px"; // 원하는 최대 높이로 설정하세요.
    });

    searchElement.addEventListener("mouseleave", function () {
        searchFormElement.style.maxHeight = "0";
    });

    searchFormElement.addEventListener("mouseenter", function () {
        searchFormElement.style.maxHeight = "180px"; // 원하는 최대 높이로 설정하세요.
    });

    searchFormElement.addEventListener("mouseleave", function () {
        searchFormElement.style.maxHeight = "0";
    });
});