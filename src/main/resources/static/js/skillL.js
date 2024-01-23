// 스킬 정보 업데이트 --------------------
// 받은 이미지로 spell 정보 받아오기 // 스킬 이미지 및 설명
function setChampSpellsL(id) {
    console.log("setChampSpells 진입성공");
    detailedChampL(id, function (dtch) {
        for (var i = 0; i < 4; i++) {
            var skillButtonId = "skill" + (i + 1); // 스킬버튼 id
            var skillInputId = "left-skill" + (i + 1) + "-num"; // 스킬 레벨 표시 id 변수선언
            var skillLevelInput = document.getElementById(skillInputId);
            var skillImageSrc = "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/spell/" + dtch[0].spells[i].id + ".png";   // 각 스킬 이미지
            var skillDescription = dtch[0].spells[i].description; // 스킬 설명 정보 추가

            console.log ("챔피언 스킬정보 불러오기 성공 : " + skillButtonId + skillInputId + skillLevelInput + skillImageSrc + skillDescription);

            // 스킬 이미지 및 설명 설정
            if (skillImageSrc) {
                document.getElementById(skillButtonId).style.backgroundImage = "url('" + skillImageSrc + "')";
            } else {
                // 챔피언 이미지가 없는 경우 해당 input의 값을 0으로 설정
                skillLevelInput.value = 0;
            }

            // 스킬 정보 불러올 때 스킬 레벨을 0로 설정
            skillLevelInput.value = 0;

            // 각 스킬 버튼에 대한 Popover 제거
            $("#" + skillButtonId).popover('dispose');

            // 각 스킬 버튼에 대한 Popover 설정
            $("#" + skillButtonId).popover({
                placement: "bottom",
                trigger: "hover",
                content: skillDescription
            });
        }
    });
}

// 스킬 레벨 업 다운 버튼  --------------------
document.addEventListener("DOMContentLoaded", function () {
    for (var i = 1; i <= 4; i++) {
        setupSkillControlsL(i);
    }
});

function setupSkillControlsL(skillIndex) {
    var skillInputId = "left-skill" + skillIndex + "-num";
    var skillLevelInput = document.getElementById(skillInputId);

    // 레벨 다운 버튼 이벤트 처리
    document.getElementById("left-skill" + skillIndex + "-numDown").addEventListener("click", function () {
        if (skillLevelInput.value > 0) {
            skillLevelInput.value = parseInt(skillLevelInput.value) - 1;
            calculateTotalSkillLevelL();
        }
    });

    document.getElementById("left-skill" + skillIndex + "-numUp").addEventListener("click", function () {
        if (skillLevelInput.value < parseInt(skillLevelInput.getAttribute("max"))) {
            var totalLevel = calculateTotalSkillLevelL();
            var level = getSelectedLevel();
            if (skillIndex === 4) {
                if (level >= 6 && skillLevelInput.value == 0) {
                    skillLevelInput.value = parseInt(skillLevelInput.value) + 1;
                } else if (level >= 11 && skillLevelInput.value == 1) {
                    skillLevelInput.value = parseInt(skillLevelInput.value) + 1;
                } else if (level >= 16 && skillLevelInput.value == 2) {
                    skillLevelInput.value = parseInt(skillLevelInput.value) + 1;
                } else {
                    Swal.fire("궁 찍을수 있는 레벨이 아니지렁~");
                }
            } else {
                if (totalLevel > level) {
                    Swal.fire("스킬의 레벨합이 선택한 레벨보다 높습니다. \n 챔피언 레벨을 더 올려주세요.");
                } else {
                    skillLevelInput.value = parseInt(skillLevelInput.value) + 1;
                }
            }
        }
    });
}
function calculateTotalSkillLevelL() {
    var totalSkillLevel = 1;
    for (var i = 1; i <= 4; i++) {
        var skillInputId = "left-skill" + i + "-num";
        var skillLevelInput = document.getElementById(skillInputId);
        totalSkillLevel += parseInt(skillLevelInput.value);
    }
    console.log("4개 스킬 레벨의 합은", totalSkillLevel);
    return totalSkillLevel;
}

function calculateDamage(championName, skillIndex, level, values, valuesR, ) {
    const logPan = document.getElementById('left-log_pan');
    const Magic_Penetration = values[7].match(/\((.*?)\)/)[1];
    let championIndex = 0;
    console.log("재재재재재재재ㅐ재재재재재재" + championIndex);
    console.log("ㅂㅈㅇㅈㅂㅈㅂㅇㅈㅂㅇㅈ");

    console.log("선택한 챔프 :::",championName);
    let damageText = "";
    if(championName === "Lux"){
        championIndex = 74;
        if (skillIndex === 0) {
            const damage = Math.round((Number(championsArray[championIndex].abilities.Q[0].effects[0].leveling[0].modifiers[0].values[level - 1]) + (Number(championsArray[championIndex].abilities.Q[0].effects[0].leveling[0].modifiers[1].values[0]) * 0.01) * Number(values[1])) * 100 / (100 + Number(valuesR[3]) - (Number(valuesR[3]) * (Number(parseInt(values[7])) * 0.01) + Number(Magic_Penetration))));
            const skillMp = championsArray[74].abilities.Q[0].cost.modifiers[0].values[level - 1];
            statusR.r_SetRealHp(damage);
            status.setRealMpL(skillMp);
            damageText = `(Q) <span style='color: #eeff00;'>${damage}</span>의 데미지를 입혔습니다.<br>`;
        } else if (skillIndex === 1) {
            const skillMp = championsArray[74].abilities.W[0].cost.modifiers[0].values[level - 1];
            damageText = "(W) 0의 데미지를 입혔습니다.<br>";
            statusR.r_SetRealHp(damage);
            status.setRealMpL(skillMp);
        } else if (skillIndex === 2) {
            const damage = Math.round((Number(championsArray[championIndex].abilities.E[0].effects[2].leveling[0].modifiers[0].values[level - 1]) + (Number(championsArray[championIndex].abilities.E[0].effects[2].leveling[0].modifiers[1].values[0]) * 0.01) * Number(values[1])) * 100 / (100 + Number(valuesR[3]) - (Number(valuesR[3]) * (Number(parseInt(values[7])) * 0.01) + Number(Magic_Penetration))));
            const skillMp = championsArray[74].abilities.E[0].cost.modifiers[0].values[level - 1];
            statusR.r_SetRealHp(damage);
            status.setRealMpL(skillMp);
            damageText = `(E) <span style='color: #eeff00;'>${damage}</span>의 데미지를 입혔습니다.<br>`;
        } else if (skillIndex === 3) {
            const damage = Math.round((Number(championsArray[championIndex].abilities.R[0].effects[0].leveling[0].modifiers[0].values[level - 1]) + (Number(championsArray[championIndex].abilities.R[0].effects[0].leveling[0].modifiers[1].values[0] * 0.01)) * Number(values[1])) * 100 / (100 + Number(valuesR[3]) - (Number(valuesR[3]) * (Number(parseInt(values[7])) * 0.01) + Number(Magic_Penetration))));
            const skillMp = championsArray[74].abilities.R[0].cost.modifiers[0].values[level - 1];
            statusR.r_SetRealHp(damage);
            status.setRealMpL(skillMp);
            damageText = `(R) <span style='color: #eeff00;'>${damage}</span>의 데미지를 입혔습니다.<br>`;
        }
    }


    // 현재 로그에 데미지 텍스트 추가
    logPan.innerHTML += damageText;
}

function getValues() {
    const values = [];
    const statValueElements = document.getElementsByClassName('stat_value');
    const leftRscValue = document.getElementById('left-rsc-value').innerHTML;
    const leftHpValue = document.getElementById('left-hp-value').innerHTML;

    for (let i = 0; i < statValueElements.length; i++) {
        const value = statValueElements[i].innerHTML;
        values.push(value);
    }

    values.push(leftRscValue);
    values.push(leftHpValue);

    return values;
}
function getValuesR() {
    const valuesR = [];
    const statValueElements = document.getElementsByClassName('stat_value_R');
    const rightRscValue = document.getElementById('right-rsc-value').innerHTML;
    const rightHpValue = document.getElementById('right-hp-value').innerHTML;

    for (let i = 0; i < statValueElements.length; i++) {
        const valueR = statValueElements[i].innerHTML;
        valuesR.push(valueR);
    }

    valuesR.push(rightRscValue);
    valuesR.push(rightHpValue);

    return valuesR;
}

// left_BA_button 클릭 이벤트 처리
const leftBAButton = document.getElementById('left_BA_button');
const skillButtons = [
    document.getElementById('skill1'),
    document.getElementById('skill2'),
    document.getElementById('skill3'),
    document.getElementById('skill4')
];

for (let i = 0; i < skillButtons.length; i++) {
    const button = skillButtons[i];

    button.addEventListener('click', function() {
        const values = getValues();
        const valuesR = getValuesR();
        const imgElement = document.querySelector('.portrait');
        const src = imgElement.getAttribute('src');
        const championName = src.split('/').pop().split('.')[0];

        const levelInput = document.getElementById(`left-skill${i + 1}-num`);
        const level = parseInt(levelInput.value);

        console.log(championName, values, valuesR); // 배열 출력 또는 원하는 작업 수행

        if (level >= 1 && level <= 5) {
            calculateDamage(championName, i, level, values, valuesR);
        } else {
            console.error('Invalid level input');
        }
    });
}



//평타버튼
leftBAButton.addEventListener('click', function() {
    const values = getValues(); // 선택한 챔 능력치
    const valuesR = getValuesR(); // 허수아비 능력치
    const selectedLevel = getSelectedLevel(); // 레벨 불러오기

    var imgElement = document.querySelector('.portrait');
    var src = imgElement.getAttribute('src');
    var championName = src.split('/').pop().split('.')[0];

    console.log(championName, values, valuesR); // 배열 출력 또는 원하는 작업 수행
    const logPan = document.getElementById('left-log_pan');

    let damage;
    if (values[11] === "100") { // 치명타 구현
        damage = Math.round(Number(values[0] * 1.75) * 100 / (100 + Number(valuesR[2]) - ((Number(valuesR[2]) * Number(values[6]) * 0.01 + ((0.6 * Number(values[8]) + (0.4 * (selectedLevel / 18) * Number(values[8]))))))));
        statusR.r_SetRealHp(damage);
    } else {
        damage = Math.round(Number(values[0]) * 100 / (100 + Number(valuesR[2]) - ((Number(valuesR[2]) * Number(values[6]) * 0.01 + ((0.6 * Number(values[8]) + (0.4 * (selectedLevel / 18) * Number(values[8]))))))));
        statusR.r_SetRealHp(damage);
    }

    console.log(Number(valuesR[15]) - Number(damage));
    logPan.innerHTML += "(평타) <span style='color: #eeff00;'>" + damage + "</span>" + "의 데미지를 입혔습니다. <br>";
});



// 챔피언 변경시 데미지로그 초기화
const championButton = document.getElementById('champion-btn');
const logPanElement = document.getElementById('left-log_pan');
championButton.addEventListener('click', function () {
    // 여기서 log_pan의 내용을 초기화
    logPanElement.innerHTML = '';
});

// 데이미 로그 하단 갱신
function scrollToBottom() {
    var chatContainer = document.getElementById("left-log_pan");
    chatContainer.scrollTop = chatContainer.scrollHeight;
}
setInterval(scrollToBottom, 1000); // 1초마다 스크롤을 맨 아래로 이동





// 파일경로
const filePath = "js/champions.json";

// Ajax를 사용하여 파일을 받아오는 함수

// function getFile(url, callback) {
//     const xhr = new XMLHttpRequest();
//     xhr.onreadystatechange = function () {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             if (xhr.status === 200) {
//                 callback(null, xhr.responseText);
//             } else {
//                 callback(new Error('파일을 받아오는 중 오류 발생'), null);
//             }
//         }
//     };
//
//     xhr.open('GET', url, true);
//     xhr.send();
// }
// 오류가 발생하면 swal.fire창이 나오고 ok버튼을 눌러 새로고침으로 새로 시작
function getFile(url, callback) {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                callback(null, xhr.responseText);
            } else {
                callback(new Error('파일을 받아오는 중 오류 발생'), null);
                Swal.fire({
                    title: '오류',
                    text: '파일을 받아오는 중 오류가 발생했습니다.',
                    icon: 'error',
                    confirmButtonText: '확인'
                }).then(function() {
                    location.reload(); // 페이지 새로고침
                });
            }
        }
    };

    xhr.open('GET', url, true);
    xhr.send();
}

let championsArray = []; // 전역 변수로 선언

// 파일을 받아와서 배열에 저장하는 함수
function saveFileToArray(filePath, callback) {
    getFile(filePath, function (error, fileContent) {
        if (error) {
            callback(error, null);
        } else {
            try {
                const jsonData = JSON.parse(fileContent);
                championsArray = Object.values(jsonData); // 전역 변수에 저장
                callback(null, championsArray);
            } catch (jsonError) {
                callback(jsonError, null);
            }
        }
    });
}

// 파일을 받아와서 배열에 저장하는 예제 호출
// saveFileToArray(filePath, function (error, jsonData) {
//     if (error) {
//         console.error('파일 받아오기 중 오류:', error);
//     } else {
//         championsArray = Object.values(jsonData); // jsonData의 값들을 배열로 변환하여 저장
//         console.log(championsArray);
//         console.log(championsArray[49].abilities.Q[0].effects[1].leveling[0].modifiers[0].values[0]);
//         console.log(championsArray[74].abilities.Q[0].cost.modifiers[0].values[0]);
//         console.log(championsArray[74].abilities.W[0].cost.modifiers[0].values[0]);
//         console.log(championsArray[74].abilities.E[0].cost.modifiers[0].values[0]);
//         console.log(championsArray[74].abilities.R[0].cost.modifiers[0].values[0]);
//     }
// });
// 오류가 발생하면 swal.fire창이 나오고 ok버튼을 눌러야 새로고침으로 다시 시도할수 있게 변경
saveFileToArray(filePath, function(error, jsonData) {
    if (error) {
        console.error('파일 받아오기 중 오류:', error);
        Swal.fire({
            title: '오류',
            text: '파일 받아오기 중 오류가 발생했습니다.',
            icon: 'error',
            confirmButtonText: '확인'
        }).then(function() {
            location.reload(); // 페이지 새로고침
        });
    } else {
        championsArray = Object.values(jsonData); // jsonData의 값들을 배열로 변환하여 저장
        console.log(championsArray);
        console.log(championsArray[49].abilities.Q[0].effects[1].leveling[0].modifiers[0].values[0]);
        console.log(championsArray[74].abilities.Q[0].cost.modifiers[0].values[0]);
        console.log(championsArray[74].abilities.W[0].cost.modifiers[0].values[0]);
        console.log(championsArray[74].abilities.E[0].cost.modifiers[0].values[0]);
        console.log(championsArray[74].abilities.R[0].cost.modifiers[0].values[0]);
    }
});
