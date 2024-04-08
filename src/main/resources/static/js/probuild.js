document.addEventListener('DOMContentLoaded', function () {
    var ddEle = document.querySelectorAll('.match-info');
    var entries = document.querySelectorAll('.match');
    var userRankInfo = document.querySelectorAll('.user-rank-info');
    var winRate = document.querySelectorAll('.winrate');

    //
    ddEle.forEach(function (ddEle) {
        var kills = parseFloat(ddEle.querySelector('#kills').textContent);
        var deaths = parseFloat(ddEle.querySelector('#deaths').textContent);
        var assists = parseFloat(ddEle.querySelector('#assists').textContent);
        var kdaResult = ddEle.querySelector('.kda-result');
        var kda = calculateKda(kills, deaths, assists);
        kdaResult.textContent = kda;

        if (kda.includes('Perfect Kill')) {
            kdaResult.style.color = 'orange';
        } else if (kda >= 5) {
            kdaResult.style.color = 'red';
        } else if (kda >= 4) {
            kdaResult.style.color = 'blue';
        } else if (kda >= 3) {
            kdaResult.style.color = 'green';
        }
    });

    // 승률
    winRate.forEach(function (winrate) {
        var win = parseFloat(winrate.querySelector('#win').textContent);
        var lose = parseFloat(winrate.querySelector('#lose').textContent);
        var winrateResult = winrate.querySelector('.winrate_result');
        var winRate = calculateWinrate(win, lose);
        console.log(winRate);

        if (winRate >= 70) {
            winrateResult.style.color = 'orange';
        } else if (winRate >= 60) {
            winrateResult.style.color = 'blue';
        } else if (winRate >= 50) {
            winrateResult.style.color = 'green';
        }

        winrateResult.textContent = winRate.toFixed(1) + "%";
    });

    //
    entries.forEach(function (entry) {
        var summonerName = entry.querySelector('#nickName span').textContent;

        // 해당 entry에 대한 모든 participants 가져오기
        var participants = entry.querySelectorAll('.match-user-info');

        participants.forEach(function (participant) {
            var participantSummonerName = participant.querySelector('#summonerName dd').textContent;
            var championName = participant.querySelector('#championName dd').textContent;

            // participant.summonerName = entry.summonerName인 경우
            if (participantSummonerName === summonerName) {
                var whatUseChamp = entry.querySelector('.what-use-champ');

                // 'p' 요소 다음 위치에 추가
                var championImgSrc = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/" + championName + ".png";
                var championImg = document.createElement('img');
                championImg.src = championImgSrc;
                championImg.alt = championName + " 이미지";
                championImg.id = "participant_champion";

                var pElement = whatUseChamp.querySelector('#nickName');
                pElement.insertAdjacentElement('beforebegin', championImg);
                // whatUseChamp.insertBefore(championImg, pElement.nextSibling);
            }
        });
    });

    // 랭크 티어
    userRankInfo.forEach(function (entry) {
        var tierText = entry.querySelector('#RankTier span').textContent;

        // 이미지 경로 설정
        var imgSrc = "./Img/Rank_Tier/" + "Rank=" + tierText + ".png";

        // 이미지 엘리먼트 생성
        var img = new Image();
        img.src = imgSrc;
        img.alt = tierText + " 이미지";
        img.className = "tier-img";

        var rankTier = entry.querySelector('#RankTier');
        rankTier.innerHTML = '';
        rankTier.appendChild(img);
    });


    // 모든 div 요소 선택
    var divs = document.querySelectorAll('div');

    // 동적으로 생성 된 안쓰는 'div'들 삭제
    divs.forEach(function (div) {
        // div의 내용을 trim() 함수를 사용하여 앞뒤 공백을 제거한 후 확인
        if (div.textContent.trim() === '') {
            // 내용이 공백인 경우 해당 div 요소를 삭제
            div.parentNode.removeChild(div);
        }
    });


    getChampionImg();
    getMostChampImg();
    getItemImg();
    wardItem();
    totalTimestamp();
    controlWard();
});

// KDA를 계산하는 함수
function calculateKda(kills, deaths, assists) {
    if (deaths !== 0) {
        var kdaValue = (kills + assists) / deaths;
        return kdaValue.toFixed(2);
    } else {
        return "Perfect Kill";
    }
}

function calculateWinrate(win, lose) {
    if (win + lose !== 0) {
        var winRate = (win / (win + lose)) * 100;
        return winRate;
    } else {
        return 0;
    }
}

// 아이템 빌드 마지막 화살표 제거
$(document).ready(function () {
    $('.build-all-item').each(function () {
        $(this).find('.probuild-itemBuild-arrow:last').css('display', 'none');
    });
});

// 검색
function search() {
    // 입력 상자에서 검색어 가져오기
    var searchText = document.getElementById('searchBox').value.toLowerCase()/*.replace(/\s/g, '')*/;

    // 모든 match 요소들을 가져와서 반복합니다.
    var matches = document.getElementsByClassName('match');
    for (let i = 0; i < matches.length; i++) {
        var match = matches[i];
        // match의 모든 match-box 요소들을 가져와서 반복합니다.
        var matchBoxes = match.getElementsByClassName('match-user-info');
        var matchFound = false; // 해당 match에 검색어와 일치하는 요소가 있는지 여부를 나타내는 플래그

        for (let j = 0; j < matchBoxes.length; j++) {
            var matchBox = matchBoxes[j];
            // 챔피언, 소환사 변수선언
            var summonerName = matchBox.querySelector('#summonerName');
            var championName = matchBox.querySelector('#championName');
            // 변수의 텍스트 내용
            var summonerNameText = summonerName.innerText.toLowerCase()/*.replace(/\s/g, '')*/;
            var championNameText = championName.innerText.toLowerCase()/*.replace(/\s/g, '')*/;

            // 검색어와 일치하는 항목이 있으면 해당 match를 표시하고 플래그를 true로 설정합니다.
            if (summonerNameText.indexOf(searchText) !== -1 || championNameText.indexOf(searchText) !== -1) {
                matchFound = true;
                // 검색한 단어 배경색 추가
                var ddEle = matchBox.getElementsByTagName('dd');
                for (let k = 0; k < ddEle.length; k++) {
                    var ddEles = ddEle[k];
                    var ddText = ddEles.innerText.toLowerCase();
                    var index = ddText.indexOf(searchText);
                    if (index !== -1) {
                        var highlightedText = '<span style="background-color: #0058f3;">' + ddText.substring(index, index + searchText.length) + '</span>';
                        var newText = ddText.substring(0, index) + highlightedText + ddText.substring(index + searchText.length);
                        ddEles.innerHTML = newText;
                    }
                }
                break; // 일치하는 요소를 찾았으므로 더 이상 반복할 필요가 없습니다.
            }
        }

        // 해당 match에 검색어와 일치하는 요소가 없으면 숨깁니다.
        if (!matchFound) {
            match.style.display = 'none';
        } else {
            match.style.display = 'block';
        }
    }

}


/*
// page 넘버링
function loadPage(page) {
    window.location.href = '/probuild?page=' + page;
}
*/

// page 나누기
document.addEventListener("DOMContentLoaded", function () {
    var currentPage = getUrlParameter("page") || 1;
    currentPage = parseInt(currentPage);
    var totalPages = 10; // 총 페이지 수
    var maxVisibleBtn = 5; // 최대로 보이는 페이지 버튼 수

    updatePageButtons(currentPage, totalPages);

    function updatePageButtons(currentPage, totalPages) {
        var pageBtn = document.getElementById("pageBtn");
        pageBtn.innerHTML = ""; // 기존 페이지 버튼 초기화

        // 시작 페이지 번호 계산
        var startPage = Math.max(1, currentPage - Math.floor(maxVisibleBtn / 2));

        // 끝 페이지 번호 계산
        var endPage = Math.min(totalPages, startPage + maxVisibleBtn - 1);

        // 이전 페이지 버튼 추가
        if (currentPage > 1) {
            var prevButton = document.createElement("button");
            prevButton.innerHTML = "이전";
            prevButton.onclick = function () {
                loadPage(currentPage - 1);
            };
            pageBtn.appendChild(prevButton);
        }

        // 페이지 버튼 추가
        for (var i = startPage; i <= endPage; i++) {
            var button = document.createElement("button");
            button.innerHTML = i;
            if (i === currentPage) {
                button.disabled = true; // 현재 페이지 버튼은 비활성화
            } else {
                button.onclick = function () {
                    loadPage(parseInt(this.innerHTML));
                };
            }
            pageBtn.appendChild(button);
        }

        // 다음 페이지 버튼 추가
        if (currentPage < totalPages) {
            var nextButton = document.createElement("button");
            nextButton.innerHTML = "다음";
            nextButton.onclick = function () {
                loadPage(currentPage + 1);
            };
            pageBtn.appendChild(nextButton);
        }
    }

    function loadPage(page) {
        window.location.href = '/probuild?page=' + page;
    }

    // URL에서 쿼리 매개변수 가져오기
    function getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        var results = regex.exec(location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    }

});

function getChampionImg() {
    $('.championName').each(function (index, element) {
        // 현재 반복문의 텍스트
        var championName = $(element).text().trim();

        // 챔피언 이름 api에 넣어서 이미지 호출
        var championImgSrc = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/" + championName + ".png";

        // 이미지 엘리먼트를 생성하고 속성을 설정
        var championImg = $("<img>", {
            src: championImgSrc,
            alt: championName + " 이미지",
            class: "championName-img"
        });
        // 해당 요소에 이미지 추가
        $(element).empty().append(championImg);
    });
}

function getMostChampImg() {
    $('.most-champion-img span').each(function (index, element) {
        var championId = $(element).text().trim().split(',');
        /*console.log('championId', championId);*/

        var allChampion = "https://ddragon.leagueoflegends.com/cdn/14.6.1/data/en_US/champion.json"

        $.getJSON(allChampion, function (data) {
            /*console.log('data', data);*/

            championId.forEach(function (championIds) {
                /*console.log('championIds', championIds);*/

                var championKey = Object.values(data.data).find(champion => champion.key === championIds);
                /*console.log('championKey', championKey);*/

                if (championKey) {
                    var img = document.createElement("img");
                    img.src = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/" + championKey.image.full;
                    img.alt = championKey.name;
                    $(element).empty().append(img);
                    /*console.log('img', img);*/
                }
            });
        });
    });
}

// 빌드
function getItemImg() {
    $('.match-time-line').each(function () {
        // 빌드 아이템 이미지
        var itemIdSpan = $(this).find('.build-getItemId');
        var itemId = itemIdSpan.text().trim();

        /*console.log("itemId",itemId);*/

        var itemImg = $("<img>", {
            src: "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/" + itemId + ".png",
            alt: itemId.name,
        });
        /*console.log(itemImg);*/


        itemIdSpan.empty().append(itemImg);

        // 타임스탬프 분/초 단위로 바꾸기
        // timestamp / 1000 = 초 단위
        // 예를 들어 60000(timestamp) / 1000 = 60초 = 1분
        var timeStampSpan = $(this).find('.build-getTimeStamp');
        var timeStamp = timeStampSpan.text().trim();
        /*console.log(timeStamp)*/


        var timeStampCalcSec = timeStamp / 1000;
        timeStampCalcSec.toFixed(0);

        if (timeStampCalcSec >= 300) {
            var timeStampCalcMin = (timeStampCalcSec / 60).toFixed(0) + "분";
        }else if (timeStampCalcSec >= 60) {
             timeStampCalcMin = (timeStampCalcSec / 60).toFixed(0) + "분 " + (timeStampCalcSec % 60).toFixed(0) + "초";
        }   else if (timeStampCalcSec < 60) {
            timeStampCalcMin = "1분 미만";
        }
        /*console.log(timeStampCalcSec);
        console.log(timeStampCalcMin)*/
        timeStampSpan.empty().append(timeStampCalcMin)
    });
}

function wardItem() {
    $('.build-ward-item').each(function () {
        var wardItemBox = $(this).find('.ward-item-box');
        if (wardItemBox.length === 0) {
            $(this).find('.info-title:first').after("<span>와드를 구매하지 않았습니다.</span>");
        }
    });
}

// 총 게임시간
function totalTimestamp() {
    $('.total-timestamp').each(function () {
        var totalTimestamp = $(this);
        var lastFrame = totalTimestamp.find('.total-match-timestamp').last();
        var lastTimestamp = lastFrame.text().trim();

        var lastTimestampCalc = lastTimestamp / 1000;
        lastTimestampCalc.toFixed(0);

        var lastTimestampCalcMin = (lastTimestampCalc / 60)
            .toFixed(0) + "분 " + (lastTimestampCalc % 60)
            .toFixed(0) + "초";

        totalTimestamp.empty().append(lastTimestampCalcMin)
    });
}

// 제어와드 이미지 추가
function controlWard() {
    var controlWard = $('.controlWard');

    var imageUrl = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/2055.png";
    var url = $("<img>", {
        src: imageUrl
    });
    controlWard.append(url);
}
