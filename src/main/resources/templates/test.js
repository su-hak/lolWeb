function highlightSearchText(option, keyword) {
    var regex = new RegExp(keyword, "gi");

    if(option === "title"){
        $(".view-post-btn").each(function () {
            var text = $(this).text().trim(); // 버튼 텍스트 가져오기
            var highlightedText = text; // 강조된 텍스트를 저장할 변수 초기화
            highlightedText = highlightText(text, keyword);
        });
    } else if(option === "content"){
        $(".view-post-btn").each(function () {
            var text = $(this).text().trim(); // 버튼 텍스트 가져오기
            var highlightedText = text; // 강조된 텍스트를 저장할 변수 초기화
            highlightedText = highlightText(text, keyword);
        });
    }
}