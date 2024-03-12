$(document).ready(initializeitemL);

function initializeitemL() {

    // 검색창에 입력이 있을 때마다 검색 수행
    $("#left-item-search").on("input", searchItemL);
    // $("#right-item-search").on("input", searchItemR);
}

// let savedItems = []; // 아이템 저장 배열
var savedItemsL = new Array(6);
var itemGold = new Array(6);
itemGold.fill(0);
let allItemsL = {};
let filterItemsL = {};
let callIdx = 0; //선택한 아이템 칸 idx
let itemPriceL= 0; // 아이템 값을 담아줄 변수

console.log("savedItemsL",savedItemsL)


// 스탯 값을 담을 변수 선언
let itemsL = {};

itemsL.adValue= 0;
itemsL.apValue= 0;
itemsL.armor= 0;
itemsL.spellBlock= 0;
itemsL.attackSpeed= 0;
itemsL.moveSpeedInt= 0;
itemsL.moveSpeedPer = 0;
itemsL.newArPen= 0;
itemsL.adPen= 0;
itemsL.spPen= 0;
itemsL.spPen2= 0;
itemsL.crit= 0;
itemsL.newOmniVamp= 0;
itemsL.cooltime= 0;
itemsL.hpRegen= 0;
itemsL.mpRegen= 0;
itemsL.fullHp= 0;
itemsL.fullMp= 0;


// API 가져오기
$.ajax({
    type: "get",
    url: "https://ddragon.leagueoflegends.com/cdn/13.24.1/data/ko_KR/item.json",
    success: function (data) {
        allItemsL = Object.values(data.data); //아이템 데이터 배열 추출

        /* ===========아이템 가나다 순 정렬 start ==========*/
        allItemsL.sort(function(a,b){
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
        filterItemsL = allItemsL.filter(function(allItemsL){
            return !allItemsL.requiredChampion // 챔피언전용템제외
                // && items.description.includes('rarityMythic') // 신화급 아이템만 출력
                && allItemsL.inStore!==false // 스토어: false인 item 제외
                && allItemsL.maps["11"]===true // 소환사의 협곡 맵("11")만 출력
                && !allItemsL.tags.includes("Jungle")
                && !allItemsL.tags.includes("Consumable")
                && !allItemsL.description.includes('퀘스트')
                && !allItemsL.description.includes('장신구')
                && allItemsL.description.indexOf('<stats></stats>') === -1; // <stats></stats> 값이 null인 경우 출력하지 않음
        });


        console.log(filterItemsL);
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


        filterItemsL.forEach((data, index) => {
            var itemBox = $("<div>").addClass("item_box_list");
            var itemImg = $("<img>", {
                id: 'item-img-' + index,
                src: "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/" + data.image.full,
                alt: data.name + " 이미지",
                class: "item-img",
                value: index
            });
            var itemName = $("<p>").addClass("itemTier-name").text(data.name);

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

function checkSavedItemsNull() {
    for (var i = 0; i < savedItemsL.length; i++) {
        if (savedItemsL[i] != null) {
            return false; // null이 아닌 값이 하나라도 존재하면 false 반환
        }
    }
    return true; // 모든 값이 null이면 true 반환
}

// 템 제거 시 스텟 초기화
function isSavedItemsDefault() {
    var isSavedItemsNull = checkSavedItemsNull();


    if(isSavedItemsNull == true){
        itemsL.adValue = 0;
        itemsL.apValue= 0;
        itemsL.armor= 0;
        itemsL.spellBlock= 0;
        itemsL.attackSpeed= 0;
        itemsL.moveSpeedInt= 0;
        itemsL.moveSpeedPer = 0;
        itemsL.newArPen= 0;
        itemsL.adPen= 0;
        itemsL.spPen= 0;
        itemsL.spPen2= 0;
        itemsL.crit= 0;
        itemsL.newOmniVamp= 0;
        itemsL.cooltime= 0;
        itemsL.hpRegen= 0;
        itemsL.mpRegen= 0;
        itemsL.fullHp= 0;
        itemsL.fullMp= 0;
        changeStatusL();
    }else {
        changeStatusL();
    }
}

function itemGoldUpdate() {
    var totalGold = 0;
    for(var i=0; i<itemGold.length; i++){
        totalGold += itemGold[i];
        $("#left-cost-value").text(": "+ totalGold + " 원"); //아이템 가격을 HTML에 적용
        console.log("for문 gold :: ", itemGold[i]);
    }
}

// 아이템 선택
$("#item-list").click(function (e) {

    console.log(itemGold);
    if (e.target.id === 'emptyBtn') {
        console.log("삭제 버튼 클릭하였습니다.");

        delete savedItemsL[callIdx];
        itemGold[callIdx] = 0;

        itemStatCalc();
        isSavedItemsDefault();
        console.log("아이템 잔여 확인 :: ",savedItemsL);
        $("#iBox" + callIdx).css("background-image", "none");
        $("#iBox" + callIdx).html('<iconify-icon icon="ic:baseline-plus" style="color: #ff00e1;" width="50" height="50"></iconify-icon>');
        console.log("저장된 스탯::: ", itemsL);
        itemGoldUpdate();
        itemFilterControl();


    } else if (e.target.classList.contains('item-img')) {
        console.log(333,)
        console.log("아이템 클릭하였습니다.", e.target.getAttribute("value"));
        // callIdx = $(e.target).closest('.iBox').index();
        console.log("savedItems :: ",savedItemsL);
        var itemData = filterItemsL[e.target.getAttribute("value")];
        var imgSrc = "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/" + filterItemsL[e.target.getAttribute("value")].image.full;

        $('#iBox' + callIdx).empty();
        $('#iBox' + callIdx).css({
            'background-image': 'url(' + imgSrc + ')',
            'background-repeat': 'no-repeat',
            'background-position': 'center',
            'background-size': 'contain'
        });
        savedItemsL[callIdx] = itemData;
        itemGold[callIdx] = savedItemsL[callIdx].gold.total; // 아이템의 total값을 누산
        console.log("items ::::", itemsL);
        const searchInput = document.getElementById('left-item-search');
        searchInput.value = '';
        searchItemL();
        itemStatCalc(); // 아이템 스텟 값 함수 호출
        changeStatusL();
        // console.log("savedItems", savedItems);
        itemGoldUpdate();
        itemFilterControl();
    }

});

// 아이템 스탯 업데이트
// function deleteItem(){
//     var level = getSelectedLevel();
//     updateAttackStatsL(level);
//     updateArmorStatsL(level);
//     updateSpellBlockStatsL(level);
//     updateAttackspeedStatsL(level);
//     updateHpregenStats(level);
//     test.updateMpregenStats(level);
//     test.updateHpStats(level);
//     test.updateMpStats(level);
//     // updateCritStats();
//     test.updateMovespeedStats();
//     test.updateAbilitypowerStats();
//     test.updateArPenStats();
//     test.updateAdPenStats();
//     test.updateNewOmniVampStats();
//     test.updateCooltimeStats();
//     test.updateSpPenStats();
// }


// 십자 이미지와 그 밖의 버튼 모두 하나의 버튼에 동작 하게 설정
$("#plusItem").click(function (e){
    if (!status.choose) {
        Swal.fire("챔프 선택부터 혀라");
        return; // test.choose가 false인 경우 함수 실행 중단
    }
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



// 스탯 계산 함수
function itemStatCalc() {
    console.log("itemStatCalc 진입");
    itemsL.adValue= 0;
    itemsL.apValue= 0;
    itemsL.armor= 0;
    itemsL.spellBlock= 0;
    itemsL.attackSpeed= 0;
    itemsL.moveSpeedInt= 0;
    itemsL.moveSpeedPer = 0;
    itemsL.newArPen= 0;
    itemsL.adPen= 0;
    itemsL.spPen= 0;
    itemsL.spPen2= 0;
    itemsL.crit= 0;
    itemsL.newOmniVamp= 0;
    itemsL.cooltime= 0;
    itemsL.hpRegen= 0;
    itemsL.mpRegen= 0;
    itemsL.fullHp= 0;
    itemsL.fullMp= 0;

    savedItemsL.forEach(function (data){

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
                var level = getSelectedLevel();
                switch (statName) {
                    case "공격력":
                        itemsL.adValue += parseInt(statValue);
                        status.updateAttackStatsL(level);
                        break;
                    case "주문력":
                        itemsL.apValue += parseInt(statValue);
                        status.updateAbilitypowerStatsL(level);
                        break;
                    case "방어력":
                        itemsL.armor += parseInt(statValue);
                        status.updateArmorStatsL(level);
                        break;
                    case "마법 저항력":
                        itemsL.spellBlock += parseInt(statValue);
                        status.updateSpellBlockStatsL(level);
                        break;
                    case "공격 속도":
                        itemsL.attackSpeed += parseInt(statValue);
                        status.updateAttackspeedStatsL(level);
                        break;
                    case "이동 속도":
                        // items.moveSpeed += parseInt(statValue);
                        // test.updateMovespeedStats();
                        // break;
                        if (statValue.includes('%')){
                            itemsL.moveSpeedPer += parseInt(statValue);
                            status.updateMovespeedStatsL();
                            break;
                        }else {
                            itemsL.moveSpeedInt += parseInt(statValue);
                            status.updateMovespeedStatsL();
                            break;
                        }
                    case "방어구 관통력":
                        itemsL.newArPen += parseInt(statValue);
                        status.updateArPenStatsL();
                        break;
                    case "물리 관통력":
                        itemsL.adPen += parseInt(statValue);
                        status.updateAdPenStatsL();
                        break;
                    case "마법 관통력":
                        // if (statValue.includes('%')){
                        //     items.spPen += parseInt(statValue);
                        //     break;
                        // }else {
                        //     items.spPen2 += parseInt(statValue);
                        //     break;
                        // }
                        if (statValue.includes('%')){
                            itemsL.spPen += parseInt(statValue);
                            // $("#spPenL").next().text( items.spPen +'%' +"("+ items.spPen2+")");
                            status.updateSpPenStatsL();
                            break;
                        }else {
                            itemsL.spPen2 += parseInt(statValue);
                            // $("#spPenL").next().text(items.spPen + '%' +"("+ items.spPen2+")");
                            status.updateSpPenStatsL();
                            break;
                        }
                    case "치명타 확률":
                        itemsL.crit += parseInt(statValue);
                        break;
                    case "모든 피해 흡혈":
                        itemsL.newOmniVamp += parseInt(statValue);
                        status.updateNewOmniVampStatsL();
                        // $("#vampL").next().text(items.newOmniVamp + "%");
                        break;
                    case "스킬 가속":
                        itemsL.cooltime += parseInt(statValue);
                        status.updateCooltimeStatsL();
                        break;
                    case "기본 체력 재생":
                        itemsL.hpRegen += parseInt(statValue);
                        status.updateHpregenStatsL(level);
                        break;
                    case "기본 마나 재생":
                        itemsL.mpRegen += parseInt(statValue);
                        status.updateMpregenStatsL(level);
                        break;
                    case "체력":
                        itemsL.fullHp += parseInt(statValue);
                        status.updateHpStatsL(level);
                        break;
                    case "마나":
                        itemsL.fullMp += parseInt(statValue);
                        status.updateMpStatsL(level);
                        break;
                }
            }
        });
    })
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

// 아이템 스텟 반환용
// function initializeItems() {
//     return {
//         adValue: 0,
//         apValue: 0,
//         armor: 0,
//         spellBlock: 0,
//         attackSpeed: 0,
//         moveSpeed: 0,
//         newArPen: 0,
//         adPen: 0,
//         spPen: 0,
//         spPen2: 0,
//         crit: 0,
//         newOmniVamp: 0,
//         cooltime: 0,
//         hpRegen: 0,
//         mpRegen: 0,
//         fullHp: 0,
//         fullMp: 0
//     };
// }

// itemsL 객체를 반환하는 함수
// function initializeItems() {
//     return itemsL;
// }

function searchItemL() {
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


$('#defaultAll').click(function (){
    console.log("초기화 버튼 클릭");
    defaultAll();
    itemStatCalc();
    isSavedItemsDefault();
    itemGoldUpdate();

})

function defaultAll() {
    const resetLevel = document.getElementById('champ_lv');
    resetLevel.value = 1;

    savedItemsL = Array(6); // 아이템 초기화
    itemGold = Array(6); // 아이템 골드 초기화 (nan으로 표시)
    itemGold.fill(0); // 골드를 0 으로 초기화

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