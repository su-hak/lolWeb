/* TODO 메뉴 드랍 버튼 및 아이콘 생성*/
$('.f1r1').click(function () {
    const notice = $('.f1r1');
    if (notice.css('display') == 'none') {
        notice.css('display', 'block');
    } else {
        notice.css('display', 'none')
    }
});

$('.search-icon').click(function () {
    const searchIcon = $('#search-body');
    if (searchIcon.css('display') == 'none') {
        searchIcon.css('display', 'block');
    } else {
        searchIcon.css('display', 'none');
    }
});
/*

$('.container').click(function () {
    const searchIcon = $('#search-body');
    if (searchIcon.css('display') == 'block') {
        searchIcon.css('display', 'none');
    }
});*/
