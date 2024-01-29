$('.saveItemBtn').click(function (){
    // 클릭된 버튼의 id를 기반으로 연관된 div의 id를 생성합니다.
    const divId = '#left_save_box' + this.id.replace('left_save_btn', '');
    saveItemBox(divId);
});

function saveItemBox(divId) {
    const $div = $(divId);
    if ($div.css('display') == 'none') {
        // 클릭된 divId 외의 모든 divId를 선택하고 display 속성을 none으로 설정합니다.
        $('[id^="left_save_box"]').not(divId).css('display', 'none');
        $div.css('display', 'block');
    } else {
        $div.css('display', 'none');
    }
}

let savedItemsLBtn = []; // item 저장 배열

$('.saveHere').click(function () {
    let buttonId = $(this).data('id');    // 버튼별 고유 식별자
    let btnColor = $('#left_save_btn' + buttonId);

    // 이미 저장된 아이템이 있는지 확인
    // if (savedItemsLBtn[buttonId] &&
    //     savedItemsLBtn[buttonId].length > 0 &&
    //     savedItemsLBtn[buttonId][i] !== undefined &&
    //     savedItemsLBtn[buttonId][i] !== 'empty') {
    //     alert('이미 저장된 아이템이 있습니다.');
    //     return;
    // }

    savedItemsLBtn[buttonId] = savedItemsL.slice();
    savedItemsL = [];    // 초기화

    const divId = '#left_save_box' + buttonId;
    let saveItemBoxImgs = $(divId + ' .saveItemBox .saveItemBoxImg img')

    for (let i = 0; i < savedItemsLBtn[buttonId].length; i++) {
        if (savedItemsLBtn[buttonId][i] !== undefined && savedItemsLBtn[buttonId][i] !== 'empty') {
            const imgSrc = "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/" + savedItemsLBtn[buttonId][i].image.full;
            const savedImg = $(saveItemBoxImgs[i]);
            const itemName = savedItemsLBtn[buttonId][i].name;

            // if (imgSrc && savedImg.attr('src')) {
            //     alert('이미 저장된 아이템이 있습니다.');
            //     continue;
            // }

            if (imgSrc) {
                savedImg.attr('src', imgSrc);
                savedImg.css({'display': 'block'});
                savedImg.popover('dispose');
                savedImg.popover({
                    placement: "bottom",
                    trigger: "hover",
                    content: itemName
                });
                btnColor.css({'background-color':'#FF0D4C'});
            } else {
                savedImg.removeAttr('src');
            }

    saveItemBox(divId);
    defaultAll();
    itemStatCalc();
    isSavedItemsDefault();
    itemGoldUpdate();
    console.log('savedItemsLBtn', savedItemsLBtn)
    console.log('savedItemsL', savedItemsL)
        }
    }
});


$('.loadThere').click(function () {
    let buttonId = $(this).data('id');
    let itemsToLoad = savedItemsLBtn[buttonId] || []; // 해당 ID의 저장된 아이템을 불러옵니다. 저장된 아이템이 없다면 빈 배열을 사용합니다.
    let btnColor = $('#left_save_btn' + buttonId);
    const divId = '#left_save_box' + buttonId;

    saveItemBox(divId);
    defaultAll();
    itemStatCalc();

    for (let i = 0; i < itemsToLoad.length; i++) {
        if (itemsToLoad[i] !== undefined && itemsToLoad[i] !== 'empty') {
            var imgSrc = "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/" + itemsToLoad[i].image.full;

            $('#iBox' + i).empty();
            var loadIBox = $('#iBox' + i);
            loadIBox.css({
                'background-image': 'url(' + imgSrc + ')',
                'background-repeat': 'no-repeat',
                'background-position': 'center',
                'background-size': 'contain'
            });

            savedItemsL[i] = itemsToLoad[i];
            // itemsToLoad = [];

            const divId = '#left_save_box' + buttonId;
            let saveItemBoxImgs = $(divId + ' .saveItemBox .saveItemBoxImg img')
            const savedImg = $(saveItemBoxImgs[i]);
            if (imgSrc) {
                savedImg.attr('src','')
                savedImg.attr('onerror',"this.style.display='none'");
                btnColor.css({'background-color':'#3E4C8B'});

            } else {
                savedImg.removeAttr('src');
            }


            console.log('itemsToLoad', itemsToLoad);
            console.log('savedItemsLBtn', savedItemsLBtn)
            console.log("savedItemsL",savedItemsL)

            itemGold[i] = savedItemsL[i].gold.total;
            itemStatCalc();
            isSavedItemsDefault();
            itemGoldUpdate();
        }
    }
    savedItemsLBtn[buttonId] = []; // 불러온 후 해당 saveBox 목록 초기화
});

