// 헤더 게임 로고 옆 드롭다운 메뉴
function toggleDropdown(element) {
    // 클릭한 요소의 다음 형제 요소를 찾습니다 (드롭다운 내용)
    var dropdownContent = element.nextElementSibling;

    // 현재 드롭다운의 상태를 토글합니다.
    if (dropdownContent.style.display === "block") {
        dropdownContent.style.display = "none";
    } else {
        dropdownContent.style.display = "block";
    }
}
