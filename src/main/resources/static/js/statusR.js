let statusR = {}; // 오른쪽 관련 함수

$(document).ready(initializestatusR);

function initializestatusR() {
    statusR.updateHpStatsR();
}
// itemsR.fullHpR = 0; // 오른쪽 아이템으로 증가할 hp수치
statusR.updateAttackStatsR = function (){
    var itemAt = itemsR.adValue;
    var attacR = document.getElementById("attackDamageR");
    var nextTd = attacR.nextElementSibling;

    var currentValue = parseInt(nextTd.innerHTML);
    var newValue = 0 + itemAt;
    nextTd.innerHTML = newValue;
}
statusR.updateArmorStatsR = function () {
    var itemAr = itemsR.armor;
    var armorR = document.getElementById("armorR");
    var nextTd = armorR.nextElementSibling;

    var currentValue = parseInt(nextTd.innerHTML);
    var newValue = 30 + itemAr;
    nextTd.innerHTML = newValue;
}
statusR.updateAttackspeedStatsR = function(selectedLevel) {
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
statusR.updateSpellBlockStatsR = function (){
    var itemSp = itemsR.spellBlock;
    var spellBlockR = document.getElementById("spellBlockR");
    var nextTd = spellBlockR.nextElementSibling;

    var currentValue = parseInt(nextTd.innerHTML);
    var newValue = 30 + itemSp;
    nextTd.innerHTML = newValue;
}
statusR.updateHpStatsR = function() {
    var itemHpR = itemsR.fullHp;
    // console.log(dtch[0].stats.hp);
    const totalHp = document.getElementById("right-hp-total");
    // let defaultHp = parseInt(document.getElementById("right-hp-total").innerText);
    let defaultHp = 1000;
    let a = itemHpR + defaultHp;
    totalHp.textContent = a;
    statusR.r_SetRealHp(0);
}
let damage = 0;
statusR.r_SetRealHp = function(getDamage){
    // console.log("hp :::",statValues['hp']);
    var realHp = parseInt(document.getElementById('right-hp-total').innerText);
    damage += getDamage;
    const targetHp = document.getElementById("right-hp-value");
    var damageHp = realHp - damage;// 데미지 입은만큼 빼기
    if (damageHp <= 0) {
        Swal.fire({
            title: "적을 처치 하였슴둥",
            text: "체력을 회복합니다.",
            showCancelButton: true,
            confirmButtonText: "OK",
        }).then((result) => {
            if (result.isConfirmed) {
                damageHp = realHp;
                damage = 0;
                targetHp.textContent = damageHp;
                var maxWidth = 100; // 최대 width 값 (100%)
                var currentWidth = (damageHp / realHp) * maxWidth; // 현재 width 값 계산
                var hpBar = document.getElementById("right-hp-bar"); // hp 바 엘리먼트 가져오기
                hpBar.style.width = currentWidth + "%"; // width 값 업데이트
            }
        });
    } else {
        targetHp.textContent = damageHp;
        var maxWidth = 100; // 최대 width 값 (100%)
        var currentWidth = (damageHp / realHp) * maxWidth; // 현재 width 값 계산
        var hpBar = document.getElementById("right-hp-bar"); // hp 바 엘리먼트 가져오기
        hpBar.style.width = currentWidth + "%"; // width 값 업데이트
    }
}

// 스텟 업테이트
function changeStatusR(){
    statusR.updateAttackStatsR();
    statusR.updateArmorStatsR();
    statusR.updateHpStatsR();
    statusR.updateSpellBlockStatsR();
}
