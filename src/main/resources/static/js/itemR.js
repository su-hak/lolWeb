$(document).ready(initializeitemR);

function initializeitemR() {

    // 검색창에 입력이 있을 때마다 검색 수행
    $("#right-item-search").on("input", searchItemR);
}


let itemsR = {};
var saveditemsR = new Array(6);
var itemGoldR = new Array(6);
itemGoldR.fill(0);
let allitemsR = {};
let filteritemsR = {};
let callIdxR = 0; //선택한 아이템 칸 idx
let itemPriceR= 0; // 아이템 값을 담아줄 변수

// 스탯 값을 담을 변수 선언
itemsR.adValue= 0;
itemsR.apValue= 0;
itemsR.armor= 0;
itemsR.spellBlock= 0;
itemsR.attackSpeed= 0;
itemsR.moveSpeed= 0;
itemsR.newArPen= 0;
itemsR.adPen= 0;
itemsR.spPen= 0;
itemsR.spPen2= 0;
itemsR.crit= 0;
itemsR.newOmniVamp= 0;
itemsR.cooltime= 0;
itemsR.hpRegen= 0;
itemsR.mpRegen= 0;
itemsR.fullHp= 0;
itemsR.fullMp= 0;

function searchItemR() {
    var searchText = $("#right-item-search").val().toLowerCase();
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


// API 가져오기
$.ajax({
    type: "get",
    url: "https://ddragon.leagueoflegends.com/cdn/13.24.1/data/ko_KR/item.json",
    success: function (data) {
        allitemsR = Object.values(data.data); //아이템 데이터 배열 추출

        /* ===========아이템 가나다 순 정렬 start ==========*/
        allitemsR.sort(function(a,b){
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
        filteritemsR = allitemsR.filter(function(allitemsR){
            return !allitemsR.requiredChampion // 챔피언전용템제외
                // && itemsR.description.includes('rarityMythic') // 신화급 아이템만 출력
                && allitemsR.inStore!==false // 스토어: false인 item 제외
                && allitemsR.maps["11"]===true // 소환사의 협곡 맵("11")만 출력
                && !allitemsR.tags.includes("Jungle")
                && !allitemsR.tags.includes("Consumable")
                && !allitemsR.description.includes('퀘스트')
                && !allitemsR.description.includes('장신구')
                && allitemsR.description.indexOf('<stats></stats>') === -1;
        });


        console.log(filteritemsR);
        // 아이템 필터링 End


        // 아이템 설명창 띄우기
        function showDescriptionR(data, index) {
            // 팝오버 내용 설정
            var itemName = data.name;
            var description = data.description;

            description = description.replace(/(<(?!br\s*\/?)[^>]+)>/ig, ""); // HTML 태그 제거
            description = description.replace(/\r?\n|\r/g, ""); // 필요 없는 문자 제거
            // console.log("description",description)

            // 문장 뒤에 <br> 추가
            description = description.replace(/\.(?!\s*<br>)/g, ".<br>");

            // 팝오버 생성 및 표시
            $('#item-imgR-' + index).popover({
                title: itemName,
                content: description,
                trigger: 'manual', // 수동으로 트리거
                html: true,
                placement: 'bottom',
                // container: 'body'
            }).popover('show');
        }
        // 아이템 설명창 띄우기 E


        // filteritemsR에 대한 코드 추가
        filteritemsR.forEach((data, index) => {
            var itemBox = $("<div>").addClass("item_box_list");
            var itemImg = $("<img>", {
                id: 'item-imgR-' + index,
                src: "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/" + data.image.full,
                alt: data.name + " 이미지",
                class: "item-img",
                value: index
            });
            var itemName = $("<p>").addClass("item-name").text(data.name);

            // 마우스 오버 이벤트에 팝오버 표시 함수 연결
            itemImg.mouseover(function () {
                showDescriptionR(data, index, '#item-listR');
            });

            // 마우스 나가기 이벤트에 팝오버 숨기기
            itemImg.mouseout(function () {
                $('#item-imgR-' + index).popover('hide');
            });

            itemBox.append(itemImg);
            itemBox.append($("<br>"));
            itemBox.append(itemName);
            $("#item-listR").append(itemBox);
        });


    },
    error : function (){
        console.log("API 데이터 가져오는 중 오류 발생");
    }
});
function checkSaveditemsNullR() {
    for (var i = 0; i < saveditemsR.length; i++) {
        if (saveditemsR[i] != null) {
            return false; // null이 아닌 값이 하나라도 존재하면 false 반환
        }
    }
    return true; // 모든 값이 null이면 true 반환
}

// 템 제거 시 스텟 초기화
function isSaveditemsDefaultR() {
    var isSaveditemsNullR = checkSaveditemsNullR();


    if(isSaveditemsNullR == true){
        itemsR.adValue= 0;
        itemsR.apValue= 0;
        itemsR.armor= 0;
        itemsR.spellBlock= 0;
        itemsR.attackSpeed= 0;
        itemsR.moveSpeedInt= 0;
        itemsR.moveSpeedPer = 0;
        itemsR.newArPen= 0;
        itemsR.adPen= 0;
        itemsR.spPen= 0;
        itemsR.spPen2= 0;
        itemsR.crit= 0;
        itemsR.newOmniVamp= 0;
        itemsR.cooltime= 0;
        itemsR.hpRegen= 0;
        itemsR.mpRegen= 0;
        itemsR.fullHp= 0;
        itemsR.fullMp= 0;
        changeStatusR();
    }else{
        changeStatusR();
    }
}

function itemGoldUpdateR() {
    var totalGoldR = 0;
    for(var i=0; i<itemGoldR.length; i++){
        totalGoldR += itemGoldR[i];
        $("#right-cost-value").text(": "+ totalGoldR + " 원"); //아이템 가격을 HTML에 적용
        console.log("for문 goldR :: ", itemGoldR[i]);
    }
}

// 아이템 선택
$("#item-listR").click(function (e) {


    if (e.target.id === 'emptyBtnR') {
        console.log("삭제 버튼 클릭하였습니다.");

        delete saveditemsR[callIdxR];
        itemGoldR[callIdxR-6] = 0;

        itemstatCalcR();
        isSaveditemsDefaultR();
        console.log("아이템 잔여 확인 :: ",saveditemsR);
        $("#iBox" + callIdxR).css("background-image", "none");
        $("#iBox" + callIdxR).html('<iconify-icon icon="ic:baseline-plus" style="color: #ff00e1;" width="50" height="50"></iconify-icon>');
        console.log("저장된 스탯::: ", itemsR);
        itemGoldUpdateR();
        itemFilterControlR();



    } else if (e.target.classList.contains('item-img')) {
        console.log("아이템 클릭하였습니다.", e.target.getAttribute("value"));
        // callIdx = $(e.target).closest('.iBox').index();
        console.log("saveditemsR :: ",saveditemsR);
        var itemDataR = filteritemsR[e.target.getAttribute("value")];
        var imgSrcR = "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/" + filteritemsR[e.target.getAttribute("value")].image.full;

        $('#iBox' + callIdxR).empty();
        $('#iBox' + callIdxR).css({
            'background-image': 'url(' + imgSrcR + ')',
            'background-repeat': 'no-repeat',
            'background-position': 'center',
            'background-size': 'contain'
        });
        saveditemsR[callIdxR] = itemDataR;
        console.log(saveditemsR[callIdxR].gold.total);
        itemGoldR[callIdxR-6] = saveditemsR[callIdxR].gold.total; // 아이템의 total값을 누산
        const searchInputR = document.getElementById('right-item-search');
        searchInputR.value = '';
        searchItemR();
        itemstatCalcR(); // 아이템 스텟 값 함수 호출
        changeStatusR();
        // console.log("saveditemsR", saveditemsR);
        itemGoldUpdateR();
        itemFilterControlR();
        console.log("itemsR ::::", itemsR);
    }

});



// 십자 이미지와 그 밖의 버튼 모두 하나의 버튼에 동작 하게 설정
$("#plusItemR").click(function (e){
    // 오른쪽 챔피언 구현되면 주석 풀기
    // if (!test.choose) {
    //     Swal.fire("챔프 선택부터 혀라");
    //     return; // test.choose가 false인 경우 함수 실행 중단
    // }
    console.log("plusItemR 클릭 !", e.type);
    if(e.target.dataset.idx != undefined){ // callIdx 안 십자 바깥 영역 클릭 시
        callIdxR = e.target.dataset.idx; // 해당 idx 값을 callIdx에 저장
        itemFilterControlR();

    }else if(e.target.tagName == 'ICONIFY-ICON' && e.target.parentElement.dataset.idx != undefined){ // 십자 이미지 클릭 시
        callIdxR = e.target.parentElement.dataset.idx; // 해당 idx 값을 callIdx에 저장
        itemFilterControlR();

    }else if($(this).find('li img').length > 0 ) {
        // callIdx = $(e.target).closest('.iBox').index();
        itemFilterControlR();
        // 아이템을 가지고 있어도 템 목록 창 열릴 수 있게 설정
    }else if(e.target.id === 'right-item-filter-options') {
        // left-item-search를 클릭한 경우 아무 동작도 수행하지 않도록 합니다.
        return;
    }
    console.log(e.target.tagName , e.target.classList[0]);
    console.log(callIdxR,"callIdxR")

});


// 아이템 목록 창 출력
function itemFilterControlR() {
    if($("#right-item-filter-options").css("display") == "block"){
        $("#right-item-filter-options").css("display", "none");
    }else {
        $("#right-item-filter-options").css("display", "block");
    }

}



// 스탯 계산 함수
function itemstatCalcR() {
    itemsR.adValue= 0;
    itemsR.apValue= 0;
    itemsR.armor= 0;
    itemsR.spellBlock= 0;
    itemsR.attackSpeed= 0;
    itemsR.moveSpeedInt= 0;
    itemsR.moveSpeedPer = 0;
    itemsR.newArPen= 0;
    itemsR.adPen= 0;
    itemsR.spPen= 0;
    itemsR.spPen2= 0;
    itemsR.crit= 0;
    itemsR.newOmniVamp= 0;
    itemsR.cooltime= 0;
    itemsR.hpRegen= 0;
    itemsR.mpRegen= 0;
    itemsR.fullHp= 0;
    itemsR.fullMp= 0;

    saveditemsR.forEach(function (data){

        var description = data.description;
        var stats = description.match(/<stats>(.*?)<\/stats>/);
        console.log("stats",stats)

        var statValues = [];
        if (stats) {
            statValues = stats[1].split('<br>');
        }

        statValues.forEach(function (stat) {
            //TODO : 스탯값 있는지 확인 로직 추가

            var statName = stat.match(/^\s*(.*?)\s*<attention>/)[1];
            var statValue = stat.match(/<attention>(.*?)<\/attention>/)[1];


            if (statName && statValue) {
                // var level = testR.getSelectedLevel();
                switch (statName) {
                    case "공격력":
                        itemsR.adValue += parseInt(statValue);
                        statusR.updateAttackStatsR();
                        // console.log("itemsR",itemsR)
                        break;
                    case "주문력":
                        itemsR.apValue += parseInt(statValue);
                        // testR.updateAbilitypowerStats(level);
                        break;
                    case "방어력":
                        itemsR.armor += parseInt(statValue);
                        statusR.updateArmorStatsR();
                        // testR.updateArmorStats(level);
                        break;
                    case "마법 저항력":
                        itemsR.spellBlock += parseInt(statValue);
                        statusR.updateSpellBlockStatsR();
                        // testR.updateSpellBlockStats(level);
                        break;
                    case "공격 속도":
                        itemsR.attackSpeed += parseInt(statValue);
                        // testR.updateAttackspeedStats(level);
                        break;
                    case "이동 속도":
                        // itemsR.moveSpeed += parseInt(statValue);
                        // testR.updateMovespeedStats();
                        // break;
                        if (statValue.includes('%')){
                            itemsR.moveSpeedPer += parseInt(statValue);
                            // testR.updateMovespeedStats();
                            break;
                        }else {
                            itemsR.moveSpeedInt += parseInt(statValue);
                            // testR.updateMovespeedStats();
                            break;
                        }
                    case "방어구 관통력":
                        itemsR.newArPen += parseInt(statValue);
                        // testR.updateArPenStats();
                        break;
                    case "물리 관통력":
                        itemsR.adPen += parseInt(statValue);
                        // testR.updateAdPenStats();
                        break;
                    case "마법 관통력":
                        // if (statValue.includes('%')){
                        //     itemsR.spPen += parseInt(statValue);
                        //     break;
                        // }else {
                        //     itemsR.spPen2 += parseInt(statValue);
                        //     break;
                        // }
                        if (statValue.includes('%')){
                            itemsR.spPen += parseInt(statValue);
                            // $("#spPenL").next().text( itemsR.spPen +'%' +"("+ itemsR.spPen2+")");
                            // testR.updateSpPenStats();
                            break;
                        }else {
                            itemsR.spPen2 += parseInt(statValue);
                            // $("#spPenL").next().text(itemsR.spPen + '%' +"("+ itemsR.spPen2+")");
                            // testR.updateSpPenStats();
                            break;
                        }
                    case "치명타 확률":
                        itemsR.crit += parseInt(statValue);
                        break;
                    case "모든 피해 흡혈":
                        itemsR.newOmniVamp += parseInt(statValue);
                        // testR.updateNewOmniVampStats();
                        // $("#vampL").next().text(itemsR.newOmniVamp + "%");
                        break;
                    case "스킬 가속":
                        itemsR.cooltime += parseInt(statValue);
                        // testR.updateCooltimeStats();
                        break;
                    case "기본 체력 재생":
                        itemsR.hpRegen += parseInt(statValue);
                        // testR.updateHpregenStats(level);
                        break;
                    case "기본 마나 재생":
                        itemsR.mpRegen += parseInt(statValue);
                        // testR.updateMpregenStats(level);
                        break;
                    case "체력":
                        itemsR.fullHp += parseInt(statValue);
                        statusR.updateHpStatsR();
                        // testR.updateHpStats(level);
                        break;
                    case "마나":
                        itemsR.fullMp += parseInt(statValue);
                        // testR.updateMpStats(level);
                        break;
                }
            }
        });
    })

}