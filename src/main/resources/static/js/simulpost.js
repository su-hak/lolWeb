let test = {};
//
window.onload = function() {
    setBackgroundImage();
};

function setBackgroundImage() {

    var element = document.getElementById("championHas");
    var championClass = element.className;
    if(championClass !== "null"){
        var championBtnImg = $("#left-champ-portrait");
        championBtnImg.attr("src", "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/" + championClass + ".png");
        championBtnImg.next("a").removeClass().addClass(championClass);
    }


    var ibox0Element = document.getElementById("iBox0");
    var item1 = ibox0Element.getAttribute("value");
    var element = document.getElementById("item_box0_id");
    var elementClass0 = element.className;
    if (elementClass0 !== "null") {

        $('#iBox0').empty();
        $('#iBox0').css({
            'background-image': 'url(' + item1 + ')',
            'background-repeat': 'no-repeat',
            'background-position': 'center',
            'background-size': 'contain'
        });

    }
    var ibox1Element = document.getElementById("iBox1");
    var item2 = ibox1Element.getAttribute("value");
    var element = document.getElementById("item_box1_id");
    var elementClass1 = element.className;
    if (elementClass1 !== "null") {

        $('#iBox1').empty();
        $('#iBox1').css({
            'background-image': 'url(' + item2 + ')',
            'background-repeat': 'no-repeat',
            'background-position': 'center',
            'background-size': 'contain'
        });

    }
    var ibox2Element = document.getElementById("iBox2");
    var item3 = ibox2Element.getAttribute("value");
    var element = document.getElementById("item_box2_id");
    var elementClass2 = element.className;
    if (elementClass2 !== "null") {

        $('#iBox2').empty();
        $('#iBox2').css({
            'background-image': 'url(' + item3 + ')',
            'background-repeat': 'no-repeat',
            'background-position': 'center',
            'background-size': 'contain'
        });

    }
    var ibox3Element = document.getElementById("iBox3");
    var item4 = ibox3Element.getAttribute("value");
    var element = document.getElementById("item_box3_id");
    var elementClass3 = element.className;
    if (elementClass3 !== "null") {

        $('#iBox3').empty();
        $('#iBox3').css({
            'background-image': 'url(' + item4 + ')',
            'background-repeat': 'no-repeat',
            'background-position': 'center',
            'background-size': 'contain'
        });

    }
    var ibox4Element = document.getElementById("iBox4");
    var item5 = ibox4Element.getAttribute("value");
    var element = document.getElementById("item_box4_id");
    var elementClass4 = element.className;
    if (elementClass4 !== "null") {

        $('#iBox4').empty();
        $('#iBox4').css({
            'background-image': 'url(' + item5 + ')',
            'background-repeat': 'no-repeat',
            'background-position': 'center',
            'background-size': 'contain'
        });

    }
    var ibox5Element = document.getElementById("iBox5");
    var item6 = ibox5Element.getAttribute("value");
    var element = document.getElementById("item_box5_id");
    var elementClass5 = element.className;
    if (elementClass5 !== "null") {

        $('#iBox5').empty();
        $('#iBox5').css({
            'background-image': 'url(' + item6 + ')',
            'background-repeat': 'no-repeat',
            'background-position': 'center',
            'background-size': 'contain'
        });

    }
}
function selectObject(colElement) {
    var clickedDiv = currentTarget;
    var imgElement = clickedDiv.querySelector("img");
    var imgId = imgElement.id;
    console.log("클릭한 div의 img id:", imgId);
    // 필요한 작업을 수행합니다.
}

// 이미지를 검색하는 함수
function searchChampById(imgId) {
    var champion = champ.find(function(champion) {
        return champion.id === imgId;
    });
    if (champion) {
        console.log(champion.id);
        setChampStats(champion.id);
        // 필요한 작업을 수행합니다.
    } else {
        console.log("해당 imgId를 가진 챔피언을 찾을 수 없습니다.");
    }
}


// 광규햄 js
// 챔피언 정보를 받아오는 함수
function getChampionList() {
    $.ajax({
        type: "get",
        url: "https://ddragon.leagueoflegends.com/cdn/14.6.1/data/ko_KR/champion.json",
        success: function (data) {
            var champions = Object.values(data.data);
            // 챔피언 이름을 기준으로 정렬
            champions.sort(function(a, b) {
                return a.name.localeCompare(b.name);
            });
            displayChampionList(champions);
        }
    });
}


// 챔피언의 스킬정보와 디테일한 스텟정보를 받아오는 함수
function detailedChamp(id, callback){
    let detail;
    $.ajax({
        type: "get",
        url: "https://ddragon.leagueoflegends.com/cdn/14.6.1/data/ko_KR/champion/"+id+".json",
        success: function (data) {
            var dtch = Object.values(data.data); // 챔피언 데이터 배열 추출
            console.log("dtch :" + dtch);
            callback(dtch); // 결과를 콜백 함수로 전달합니다.
            console.log("callback(dtch) :" + dtch);
        }
    });
}

// 챔피언 목록을 표시하는 함수
function displayChampionList(champions) {
    console.log(test.choose);
    var championList = $("#champion-list");
    championList.empty(); // 기존 목록 초기화

    champions.forEach(function (champion, index) {
        var championBox = $("<div>", {
            class: "champion-box",
            click: function () {
                selectChampion(champion);
            }
        });

        var championImg = $("<img>", {
            src: "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/" + champion.id + ".png",
            alt: champion.name + " 이미지",
            class: "champion-img"
        });

        var championName = $("<p>", {
            class: "champion-name",
            text: champion.name
        });

        championBox.append(championImg);
        championBox.append($("<br>"));
        championBox.append(championName);
        championList.append(championBox);
    });
}


// 챔피언 선택 시 동작하는 함수
function selectChampion(champion) {
    test.choose = true;
    console.log(test.choose);
    // detailedChamp();
    // 선택한 챔피언에 대한 동작을 추가하세요.
    console.log("선택한 챔피언 ID:", champion.id);
    // 이미지 업데이트
    updateChampionButtonImage(champion.id);
    // 스킬 정보 업데이트

}


// 검색창에 입력된 텍스트로 챔피언을 검색하는 함수
function searchChampion() {
    var searchText = $("#champion-search").val().toLowerCase();
    var championBoxes = $(".champion-box");

    championBoxes.each(function () {
        var championBox = $(this);
        var championName = championBox.find("p").text().toLowerCase();

        if (championName.includes(searchText)) {
            championBox.show();
        } else {
            championBox.hide();
        }
    });
}
function searchItem() {
    var searchText = $("#left-item-search").val().toLowerCase();
    var itemBoxes = $(".item_box_list");

    itemBoxes.each(function () {
        var itemBox = $(this);
        var itemName = itemBox.find("p").text().toLowerCase();

        if (itemName.includes(searchText)) {
            itemBox.show();
        } else {
            itemBox.hide();
        }
    });
}



// 챔피언 버튼 이미지를 업데이트하는 함수
function updateChampionButtonImage(championId) {
    var championBtnImg = $("#left-champ-portrait");
    championBtnImg.attr("src", "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/" + championId + ".png");
    championBtnImg.next("a").removeClass().addClass(championId);
}



// 초기화 함수
function initialize() {
    getChampionList();

    // 검색창에 입력이 있을 때마다 검색 수행
    $("#champion-search").on("input", searchChampion);
    $("#left-item-search").on("input", searchItem);
}

// 페이지 로드 시 초기화 함수 호출
$(document).ready(initialize);


// API 가져오기
$.ajax({
    type: "get",
    url: "https://ddragon.leagueoflegends.com/cdn/14.6.1/data/ko_KR/item.json",
    success: function (data) {
        allItems = Object.values(data.data); //아이템 데이터 배열 추출

        /* ===========아이템 가나다 순 정렬 start ==========*/
        allItems.sort(function(a,b){
            var nameA=a.name.toUpperCase();
            var nameB=b.name.toUpperCase();

            if(nameA<nameB){
                return -1;
            }
            if(nameA>nameB){
                return 1;
            }
            return 0;
        });
        /* ===========아이템 가나다 순 정렬 start ==========*/

        // 아이템 필터링 start
        filterItems = allItems.filter(function(allItems){
            return !allItems.requiredChampion // 챔피언전용템제외
                // && items.description.includes('rarityMythic') // 신화급 아이템만 출력
                && allItems.inStore!==false // 스토어: false인 item 제외
                && allItems.maps["11"]===true // 소환사의 협곡 맵("11")만 출력
                && !allItems.tags.includes("Jungle")
                && !allItems.tags.includes("Consumable")
                && !allItems.description.includes('퀘스트')
                && !allItems.description.includes('장신구')
                && allItems.description.indexOf('<stats></stats>') === -1; // <stats></stats> 값이 null인 경우 출력하지 않음
        });


        console.log(filterItems);
        // 아이템 필터링 End

        // 아이템 설명창 띄우기
        function showDescription(data, index) {
            // 팝오버 내용 설정
            var itemName = data.name;
            var description = data.description;

            description = description.replace(/(<(?!br\s*\/?)[^>]+)>/ig, ""); // HTML 태그 제거
            description = description.replace(/\r?\n|\r/g, ""); // 필요 없는 문자 제거
            // console.log("description",description)

            // 문장 뒤에 <br> 추가
            description = description.replace(/\.(?!\s*<br>)/g, ".<br>");

            // 팝오버 생성 및 표시
            $('#item-img-' + index).popover({
                title: itemName,
                content: description,
                trigger: 'manual', // 수동으로 트리거
                html: true,
                placement: 'bottom',
                // container: 'body'
            }).popover('show');
        }
        // 아이템 설명창 띄우기 E


        filterItems.forEach((data, index) => {
            var itemBox = $("<div>").addClass("item_box_list");
            var itemImg = $("<img>", {
                id: 'item-img-' + index,
                src: "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/" + data.image.full,
                alt: data.name + " 이미지",
                class: "item-img",
                value: index
            });
            var itemName = $("<p>").addClass("item-name").text(data.name);

            // 마우스 오버 이벤트에 아이템 설명창 팝오버 표시 함수 연결
            itemImg.mouseover(function () {
                showDescription(data, index);
            });

            // 마우스 나가기 이벤트에  아이템 설명창 팝오버 숨기기
            itemImg.mouseout(function () {
                $('#item-img-' + index).popover('hide');
            });

            itemBox.append(itemImg);
            itemBox.append($("<br>"));
            itemBox.append(itemName);
            $("#item-list").append(itemBox);
        });


    },
    error : function (){
        console.log("API 데이터 가져오는 중 오류 발생");
    }
});


// 아이템 선택
$("#item-list").click(function (e) {

    if (e.target.id === 'emptyBtn') {
        console.log("삭제 버튼 클릭하였습니다.");


        $("#iBox" + callIdx).css("background-image", "none");
        $("#iBox" + callIdx).html('<iconify-icon icon="ic:baseline-plus" style="color: #ff00e1;" width="50" height="50"></iconify-icon>');
        itemFilterControl();


    } else if (e.target.classList.contains('item-img')) {
        console.log(333,)
        console.log("아이템 클릭하였습니다.", e.target.getAttribute("value"));
        // callIdx = $(e.target).closest('.iBox').index();
        var itemData = filterItems[e.target.getAttribute("value")];
        var imgSrc = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/" + filterItems[e.target.getAttribute("value")].image.full;

        $('#iBox' + callIdx).empty();
        $('#iBox' + callIdx).css({
            'background-image': 'url(' + imgSrc + ')',
            'background-repeat': 'no-repeat',
            'background-position': 'center',
            'background-size': 'contain'
        });
        $('#iBox' + callIdx).next("a").removeClass().addClass(itemData.image.full);
        const searchInput = document.getElementById('left-item-search');
        searchInput.value = '';
        searchItem();
        itemFilterControl();
    }

});


// 십자 이미지와 그 밖의 버튼 모두 하나의 버튼에 동작 하게 설정
$("#plusItem").click(function (e){
    // if (!test.choose) {
    //     Swal.fire("챔프 선택부터 혀라");
    //     return; // test.choose가 false인 경우 함수 실행 중단
    // }
    console.log("plusItem 클릭 !", e.type);
    if(e.target.dataset.idx != undefined){ // callIdx 안 십자 바깥 영역 클릭 시
        callIdx = e.target.dataset.idx; // 해당 idx 값을 callIdx에 저장
        itemFilterControl();

    }else if(e.target.tagName == 'ICONIFY-ICON' && e.target.parentElement.dataset.idx != undefined){ // 십자 이미지 클릭 시
        callIdx = e.target.parentElement.dataset.idx; // 해당 idx 값을 callIdx에 저장
        itemFilterControl();

    }else if($(this).find('li img').length > 0 ) {
        // callIdx = $(e.target).closest('.iBox').index();
        itemFilterControl();
        // 아이템을 가지고 있어도 템 목록 창 열릴 수 있게 설정
    }else if(e.target.id === 'left-item-filter-options') {
        // left-item-search를 클릭한 경우 아무 동작도 수행하지 않도록 합니다.
        return;
    }
    console.log(e.target.tagName , e.target.classList[0]);
    console.log(callIdx,"callIdx")

});


// 아이템 목록 창 출력
function itemFilterControl() {
    if($("#left-item-filter-options").css("display") == "block"){
        $("#left-item-filter-options").css("display", "none");
    }else {
        $("#left-item-filter-options").css("display", "block");
    }
}





// 아이템 생성 컨테이너 바깥 영역 클릭 시 닫기
$(document).mouseup(function(e){
    var containerL=$("#left-item-filter-options");
    var containerR=$("#right-item-filter-options");

// newBox와 item_pan를 제외한 부분을 클릭 했을 경우 newBox닫기
    if(!containerL.is(e.target)
        && containerL.has(e.target).length===0
        && !$("#plusItem").is(e.target)
        && $("#plusItem").has(e.target).length===0){
        $("#left-item-filter-options").css("display", "none");
    }
    if(!containerR.is(e.target)
        && containerR.has(e.target).length===0
        && !$("#plusItemR").is(e.target)
        && $("#plusItemR").has(e.target).length===0){
        $("#right-item-filter-options").css("display", "none");
    }
});

// 왼쪽 아이템 끝



function defaultAll() {
    const resetLevel = document.getElementById('champ_lv');
    resetLevel.value = 1;


    // 저장 된 스킬 레벨
    for (var i = 0; i < 4; i++) {
        var skillInputId = "left-skill" + (i + 1) + "-num"; // 스킬 레벨 표시 id 변수선언
        var skillLevelInput = document.getElementById(skillInputId);
        skillLevelInput.value = 0;
    }

    // for문 돌면서 모든 iBox 선택
    for (let callIdx = 0; callIdx < 6; callIdx++) {
        $("#iBox" + callIdx).css("background-image", "none");
        $("#iBox" + callIdx).html('<iconify-icon icon="ic:baseline-plus" style="color: #ff00e1;" width="50" height="50"></iconify-icon>');
    }
}
