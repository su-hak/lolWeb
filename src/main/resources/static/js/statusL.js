// 페이지 로드 시 초기화 함수 호출
$(document).ready(initializestatusL);
let status = {};
// let status = {};
// 초기화 함수
function initializestatusL() {
    getChampionList();
    // rArea.updateHpStatsR();

    // 검색창에 입력이 있을 때마다 검색 수행
    $("#champion-search").on("input", searchChampion);
    // $("#left-item-search").on("input", searchItem);
    // $("#right-item-search").on("input", searchItemR);
}


function getChampionList() {
    $.ajax({
        type: "get",
        url: "https://ddragon.leagueoflegends.com/cdn/13.24.1/data/ko_KR/champion.json",
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

// 챔피언 목록을 표시하는 함수
function displayChampionList(champions) {
    // console.log(test.choose);
    const championList = $("#champion-list");
    championList.empty(); // 기존 목록 초기화

    champions.forEach(function (champion, index) {
        var championBox = $("<div>", {
            class: "champion-box",
            click: function () {
                selectChampion(champion);
            }
        });

        const championImg = $("<img>", {
            src: "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/" + champion.id + ".png",
            alt: champion.name + " 이미지",
            class: "champion-img"
        });

        const championName = $("<p>", {
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
    status.choose = true;
    console.log(status.choose);
    // detailedChampL(champion.id);
    // 선택한 챔피언에 대한 동작을 추가하세요.
    console.log("선택한 챔피언 ID:", champion.id);
    // 이미지 업데이트
    updateChampionButtonImage(champion.id);
    // 스킬 정보 업데이트

    itemStatCalc();
    setChampSpellsL(champion.id);
    setChampStatsL(champion.id);
    // console.log(savedItems);

    const selectLevel = getSelectedLevel();
    console.log('선택한 값:', selectLevel);
    // deleteItem();
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

// 챔피언 선택시 초상화 설정
function updateChampionButtonImage(championId) {
    const championBtnImg = $("#left-champ-portrait");
    championBtnImg.attr("src", "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/" + championId + ".png");
}

// 현재 레벨을 부르는 함수
function getSelectedLevel() {
    const selectElement = document.getElementById('champ_lv');
    const selectedLevel = selectElement.value;
    return selectedLevel;
}

// 선택한 챔피언의 디테일한 스텟을 긁어오는 함수
function detailedChampL(id, callback){
    let detail;
    $.ajax({
        type: "get",
        url: "https://ddragon.leagueoflegends.com/cdn/13.24.1/data/ko_KR/champion/"+id+".json",
        success: function (data) {
            var dtch = Object.values(data.data); // 챔피언 데이터 배열 추출
            console.log("dtch :" + dtch);
            callback(dtch); // 결과를 콜백 함수로 전달합니다.
            console.log("callback(dtch) :" + dtch);
        }
    });
}

// 스텟설정 함수들
// 선택한 챔피언의 스탯 긁어오기
function setChampStatsL(id) {
    console.log("setChampStats 진입성공");

    detailedChampL(id, function(dtch) {
        const baseAttackDamage = dtch[0].stats.attackdamage; // 기본 공격력 값 저장
        statValuesL = {
            'attackDamageL': baseAttackDamage, // 공격력
            'abilityPowerL': 0, // 주문력
            'armorL': dtch[0].stats.armor, // 방어력
            'spellBlockL': dtch[0].stats.spellblock, // 마법 저항력
            'attackSpeedL': dtch[0].stats.attackspeed, // 공격 속도
            'moveSpeedL': dtch[0].stats.movespeed, // 이동 속도
            'mpRegenL': Math.round(dtch[0].stats.mpregen), // 마나 재생
            'hpRegenL': Math.round(dtch[0].stats.hpregen), // 체력제생
            'critL': dtch[0].stats.crit, // 치명타 확률
            'hpL' : dtch[0].stats.hp, // 체력
            'mpL' : dtch[0].stats.mp, // 마나
            'arPenL' : 0, // 방어구 관통력
            'spPenL' : 0, // 마법 관통력 (%)
            'adPenL' : 0, // 물리 관통력
            'vampL' : 0, // 모든 피해 흡혈
            'coolTimeL' : 0 // 스킬 가속

        };
        status.updateAttackStatsL = function(selectedLevel)  {
            console.log("초기화 updateAs 호출");
            var itemAt = itemsL.adValue;
            if (selectedLevel > 1) {
                let coefficient = dtch[0].stats.attackdamageperlevel;
                let a = [];
                // let itemAt = adValue;
                a[0] = baseAttackDamage;
                let level = 1;
                for (let i = 1; i < selectedLevel; i++) {
                    a[i] = (a[i-1]+(coefficient* (0.65+(0.035*(i+1)))));
                }
                // statValues['attackdamage'] = Math.round(a[selectedLevel - 1]);
                const value = new Decimal(a[selectedLevel - 1]);
                const roundedValue = value.toDecimalPlaces(2);
                const realRoundedValue = roundedValue.toDecimalPlaces(1);
                statValuesL['attackDamageL'] = Math.round(realRoundedValue) + itemAt;
                // statValues['attackdamage'] = realRoundedValue;
                console.log(a, itemAt);
            } else if (selectedLevel < 2) {
                statValuesL['attackDamageL'] = baseAttackDamage + itemAt;
            }
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
            }

        }
        status.updateArmorStatsL = function (selectedLevel) {
            var itemAr = itemsL.armor;
            if (selectedLevel > 1) {
                let coefficient = dtch[0].stats.armorperlevel;
                let a = [];
                // let itemAr = 0;
                a[0] = dtch[0].stats.armor;
                let level = 1;
                for (let i = 1; i < selectedLevel; i++) {
                    a[i] = (a[i-1]+(coefficient* (0.65+(0.035*(i+1)))));
                }
                const value = new Decimal(a[selectedLevel - 1]);
                const roundedValue = value.toDecimalPlaces(2);
                const realRoundedValue = roundedValue.toDecimalPlaces(1);
                statValuesL['armorL'] = Math.round(realRoundedValue) + itemAr;
                // statValues['attackdamage'] = realRoundedValue;
                console.log(a);
            } else if (selectedLevel < 2) {
                statValuesL['armorL'] = dtch[0].stats.armor + itemAr;
            }
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }
        status.updateSpellBlockStatsL = function(selectedLevel) {
            var itemSb = itemsL.spellBlock;
            if (selectedLevel > 1) {
                let coefficient = dtch[0].stats.spellblockperlevel;
                let a = [];
                // let itemSb = 0;
                a[0] = dtch[0].stats.spellblock;
                let level = 1;
                for (let i = 1; i < selectedLevel; i++) {
                    a[i] = (a[i-1]+(coefficient* (0.65+(0.035*(i+1)))));
                }
                const value = new Decimal(a[selectedLevel - 1]);
                const roundedValue = value.toDecimalPlaces(2);
                const realRoundedValue = roundedValue.toDecimalPlaces(1);
                statValuesL['spellBlockL'] = Math.round(realRoundedValue) + itemSb;
                // statValues['attackdamage'] = realRoundedValue;
                console.log(a);
            } else if (selectedLevel < 2) {
                statValuesL['spellBlockL'] = dtch[0].stats.spellblock + itemSb;
            }
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }
        status.updateHpregenStatsL = function(selectedLevel) {
            var itemHr = itemsL.hpRegen;
            var itemHrNum = itemHr * 0.01;
            if (selectedLevel > 1) {
                let coefficient = dtch[0].stats.hpregenperlevel;
                let a = [];
                // let itemHr = 0;
                a[0] = dtch[0].stats.hpregen;
                let level = 1;
                for (let i = 1; i < selectedLevel; i++) {
                    a[i] = (a[i-1]+(coefficient* (0.65+(0.035*(i+1)))));
                }
                const value = new Decimal(a[selectedLevel - 1]);
                const roundedValue = value.toDecimalPlaces(2);
                const realRoundedValue = roundedValue.toDecimalPlaces(1);
                statValuesL['hpRegenL'] = Math.round(realRoundedValue) + Math.round(realRoundedValue*itemHrNum);
                // statValues['attackdamage'] = realRoundedValue;
                console.log(a);
            } else if (selectedLevel < 2) {
                statValuesL['hpRegenL'] = Math.round(dtch[0].stats.hpregen) + Math.round(dtch[0].stats.hpregen*itemHrNum);
            }
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }
        status.updateMpregenStatsL = function(selectedLevel) {
            var itemMr = itemsL.mpRegen;
            var itemMrNum = itemMr * 0.01;
            if (selectedLevel > 1) {
                let coefficient = dtch[0].stats.mpregenperlevel;
                let a = [];
                // let itemMr = 0;
                a[0] = dtch[0].stats.mpregen;
                let level = 1;
                for (let i = 1; i < selectedLevel; i++) {
                    a[i] = (a[i-1]+(coefficient* (0.65+(0.035*(i+1)))));
                }
                const value = new Decimal(a[selectedLevel - 1]);
                const roundedValue = value.toDecimalPlaces(2);
                const realRoundedValue = roundedValue.toDecimalPlaces(1);
                statValuesL['mpRegenL'] = Math.round(realRoundedValue) + Math.round(realRoundedValue*itemMrNum);
                console.log(a);
            } else if (selectedLevel < 2) {
                statValuesL['mpRegenL'] = Math.round(dtch[0].stats.mpregen) + Math.round(dtch[0].stats.mpregen*itemMrNum);
            }
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }

        status.updateAttackspeedStatsL = function(selectedLevel) {
            var itemAs = itemsL.attackSpeed;
            let a = [];
            var itemAsNum = 0;
            if(itemAs == 0){
                itemAsNum = 0;
            }else{
                itemAsNum = itemAs * 0.01;
            }
            console.log("item 공속 ::::",itemAsNum);
            if (selectedLevel > 1) {
                let coefficient = dtch[0].stats.attackspeedperlevel;

                a[0] = dtch[0].stats.attackspeed;
                let level = 1;
                // if(asstatValue != null){
                for (let i = 1; i < selectedLevel; i++) {
                    a[i] = ((itemAsNum+(+(coefficient*0.01)*(selectedLevel-1))*(0.7025+(0.0175*(selectedLevel-1))))*a[0]) + a[0];
                }

                const value = new Decimal(a[selectedLevel - 1]);
                const roundedValue = roundToThreeDecimalPlaces(value);
                // const realRoundedValue = roundedValue.toDecimalPlaces(1);
                statValuesL['attackSpeedL'] = roundedValue;
                // statValues['attackdamage'] = realRoundedValue;
                console.log(a);
            } else if (selectedLevel < 2) {
                statValuesL['attackSpeedL'] = roundToThreeDecimalPlaces(dtch[0].stats.attackspeed + (dtch[0].stats.attackspeed*itemAsNum));
            }
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }

        status.updateHpStatsL = function(selectedLevel) {
            var itemHp = itemsL.fullHp;
            console.log(selectedLevel, dtch[0].stats.hp);
            const totalHp = document.getElementById("left-hp-total");
            if (selectedLevel > 1) {
                console.log("statValues  ::",statValuesL);
                let coefficient = dtch[0].stats.hpperlevel;
                let a = [];
                let itemHp = 0;
                a[0] = dtch[0].stats.hp;
                let level = 1;
                for (let i = 1; i < selectedLevel; i++) {
                    a[i] = (a[i - 1] + Math.round((coefficient * (0.65 + (0.035 * (i + 1))))));
                }
                const value = new Decimal(a[selectedLevel - 1]);
                const roundedValue = value.toDecimalPlaces(2);
                const realRoundedValue = roundedValue.toDecimalPlaces(1);
                statValuesL['hpL'] = Math.round(realRoundedValue) + itemHp;
                console.log(a);
            } else if (selectedLevel < 2) {
                statValuesL['hpL'] = Math.round(dtch[0].stats.hp) + itemHp;
            }
            for (const id in statValuesL) {
                const element = 'hpL';
                if(element == id){
                    let value = statValuesL[id];
                    totalHp.textContent = value;
                }
            }
            setRealHpL();
        }

        status.updateMpStatsL = function(selectedLevel) {
            const totalMp = document.getElementById("left-rsc-total");
            if(dtch[0].stats.mp != 0){
                var itemMp = itemsL.fullMp;
                console.log(dtch[0].stats.mp);
                if (selectedLevel > 1) {
                    console.log("statValues  ::",statValuesL);
                    let coefficient = dtch[0].stats.mpperlevel;
                    let a = [];
                    let itemMp = 0;
                    a[0] = dtch[0].stats.mp;
                    let level = 1;
                    for (let i = 1; i < selectedLevel; i++) {
                        a[i] = (a[i - 1] + Math.round((coefficient * (0.65 + (0.035 * (i + 1))))));
                    }
                    const value = new Decimal(a[selectedLevel - 1]);
                    const roundedValue = value.toDecimalPlaces(2);
                    const realRoundedValue = roundedValue.toDecimalPlaces(1);
                    statValuesL['mpL'] = Math.round(realRoundedValue) + itemMp;
                    console.log(a);
                } else if (selectedLevel < 2) {
                    statValuesL['mpL'] = Math.round(dtch[0].stats.mp) + itemMp;
                }
                for (const id in statValuesL) {
                    const element = 'mpL';
                    if(element == id){
                        let value = statValuesL[id];
                        totalMp.textContent = value;
                    }
                }
            }else{
                totalMp.textContent = 0;
            }
            status.setRealMpL(0);

        }

        // function updateCritStatsL(){
        //     var critTdElement = document.getElementById('critL');
        //     var statValueElement = document.getElementById('critL_value');
        //
        //     var criticalCheckbox = document.getElementById('critical');
        //
        //     criticalCheckbox.addEventListener('change', function() {
        //         if (criticalCheckbox.checked) {
        //             statValueElement.textContent = '100';
        //         } else {
        //             statValueElement.textContent = '0';
        //         }
        //     });
        //
        // }
        status.updateCritStatsL = function(){
            var critTdElement = document.getElementById('critL');
            var statValueElement = document.getElementById('critL_value');

            var criticalCheckbox = document.getElementById('critical');

            criticalCheckbox.addEventListener('change', function() {
                if (criticalCheckbox.checked) {
                    statValueElement.textContent = '100';
                } else {
                    statValueElement.textContent = '0';
                }
            });

        }

        status.updateMovespeedStatsL = function(){
            // let itemMs = 0;
            let a = 0;
            // var itemMs = items.moveSpeed;
            console.log(dtch[0].stats.movespeed + itemsL.moveSpeedInt);
            console.log((dtch[0].stats.movespeed + itemsL.moveSpeedInt) * (itemsL.moveSpeedPer * 0.01));
            a = (dtch[0].stats.movespeed + itemsL.moveSpeedInt) + ((dtch[0].stats.movespeed + itemsL.moveSpeedInt) * (itemsL.moveSpeedPer * 0.01));
            statValuesL['moveSpeedL'] = Math.round(a);
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }
        status.updateAbilitypowerStatsL = function(){
            // let itemAp = 0;
            let a = 0;
            var itemAp = itemsL.apValue;
            a = a+itemAp;
            statValuesL['abilityPowerL'] = a;
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }
        status.updateArPenStatsL = function(){ // 방관
            // let itemAp = 0;
            let a = 0;
            var itemArpen = itemsL.newArPen;
            a = a+itemArpen;
            statValuesL['arPenL'] = a;
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }
        status.updateAdPenStatsL = function(){ // 물관
            // let itemAp = 0;
            let a = 0;
            var itemAdpen = itemsL.adPen;
            a = a+itemAdpen;
            statValuesL['adPenL'] = a;
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }
        status.updateNewOmniVampStatsL = function(){ // 피흡
            // let itemAp = 0;
            let a = 0;
            // var result = test.sendItemStats();
            var itemNewOmniVamp = itemsL.newOmniVamp;
            a = a+itemNewOmniVamp;
            statValuesL['vampL'] = a;
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }
        status.updateCooltimeStatsL = function(){ // 쿨탐
            // let itemAp = 0;
            let a = 0;
            // var result = test.sendItemStats();
            var itemcooltime = itemsL.cooltime;
            a = a+itemcooltime;
            statValuesL['coolTimeL'] = a;
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }
        status.updateSpPenStatsL = function() {
            let a = 0;
            var itemSpPen = itemsL.spPen;
            var itemSpPen2 = itemsL.spPen2;
            a = a + itemSpPen;
            statValuesL['spPenL'] = a + '% (' + itemsL.spPen2 + ')';
            for (const id in statValuesL) {
                const element = document.getElementById(id);
                let value = statValuesL[id];
                if (element) {
                    element.nextElementSibling.textContent = value;
                }
            }
        }


        function setRealHpL(){
            // console.log("hp :::",statValues['hp']);
            var realHp = statValuesL['hpL'];
            var damage = 0;
            const targetHp = document.getElementById("left-hp-value");
            var damageHp = statValuesL['hpL'] - damage;// 데미지 입은만큼 빼기
            targetHp.textContent = damageHp;
            var maxWidth = 100; // 최대 width 값 (100%)
            var currentWidth = (damageHp / realHp) * maxWidth; // 현재 width 값 계산
            var hpBar = document.getElementById("left-hp-bar"); // hp 바 엘리먼트 가져오기
            hpBar.style.width = currentWidth + "%"; // width 값 업데이트
        }
        let cost = 0;
        status.setRealMpL = function(getCost){
            // console.log("mp :::",statValues['mp']);
            var realMp = statValuesL['mpL'];
            cost += getCost;
            const targetMp = document.getElementById("left-rsc-value");
            var costMp = statValuesL['mpL'] - cost;// 데미지 입은만큼 빼기
            if (costMp <= 0 && statValuesL['mpL'] != 0) {
                Swal.fire({
                    text: "마나를 다 써버리셨군요... \n 리필 해드릴게요!!",
                    showCancelButton: true,
                    confirmButtonText: "OK",
                }).then((result) => {
                    if (result.isConfirmed) {
                        costMp = realMp;
                        cost = 0;
                        targetMp.textContent = costMp;
                        var maxWidth = 100; // 최대 width 값 (100%)
                        var currentWidth = (costMp / realMp) * maxWidth; // 현재 width 값 계산
                        var mpBar = document.getElementById("left-rsc-bar"); // mp 바 엘리먼트 가져오기
                        mpBar.style.width = currentWidth + "%"; // width 값 업데이트
                    }
                });
            } else {
                targetMp.textContent = costMp;
                var maxWidth = 100; // 최대 width 값 (100%)
                var currentWidth = (costMp / realMp) * maxWidth; // 현재 width 값 계산
                var mpBar = document.getElementById("left-rsc-bar"); // mp 바 엘리먼트 가져오기
                mpBar.style.width = currentWidth + "%"; // width 값 업데이트
            }
        }

        changeStatusL();
    });
}


function changeStatusL(){
    const selectedLevel = getSelectedLevel();
    status.updateAttackStatsL(selectedLevel);
    status.updateArmorStatsL(selectedLevel);
    status.updateSpellBlockStatsL(selectedLevel);
    status.updateAttackspeedStatsL(selectedLevel);
    status.updateHpregenStatsL(selectedLevel);
    status.updateMpregenStatsL(selectedLevel);
    status.updateHpStatsL(selectedLevel);
    status.updateMpStatsL(selectedLevel);
    status.updateCritStatsL();
    status.updateMovespeedStatsL();
    status.updateAbilitypowerStatsL();
    status.updateArPenStatsL();
    status.updateAdPenStatsL();
    status.updateNewOmniVampStatsL();
    status.updateCooltimeStatsL();
    status.updateSpPenStatsL();
}

// 초기 레벨 설정


// 레벨 변경 시 업데이트
document.getElementById('champ_lv').onchange = function() {
    changeStatusL();
};

// 치명타 온오프
var checkbox = document.getElementById('right_critical');

checkbox.addEventListener('change', function() {
    var critValue = checkCritbox(); // checkCritbox() 함수 호출하여 반환된 값을 가져옵니다.
    updateCritStats(critValue); // updateCritStats() 함수 호출하여 critValue를 전달합니다.
});

// 반올림 함수
function roundToThreeDecimalPlaces(number) {
    return Math.round(number * 1000) / 1000;

}
