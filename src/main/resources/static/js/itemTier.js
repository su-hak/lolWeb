let apiKey = "RGAPI-04a0f0f8-9682-45e4-8b6a-e5d4d894f5bf";

$(document).ready(function() {
    // 챌린저 리그 정보 가져오기
    $.ajax({
        url: 'https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5',
        type: 'GET',
        data: {
            api_key: apiKey
        },
        success: function(response) {
            var summonerIds = [];
            // "entries" 배열 중 처음 7개 객체만 추출하여 순회
            response.entries.slice(0, 7).forEach(function(entry) {
                summonerIds.push(entry.summonerId); // 각 객체의 "summonerId" 값을 배열에 추가
            });
            console.log("First 7 Summoner IDs:", summonerIds);

            // 레전더리 아이템 사용 횟수를 저장하는 객체
            var legendaryItemCount = {};

            // 소환사 ID를 사용하여 puuid 정보 가져오기
            summonerIds.forEach(function(summonerId, index) {
                $.ajax({
                    url: 'https://kr.api.riotgames.com/lol/summoner/v4/summoners/' + summonerId,
                    type: 'GET',
                    data: {
                        api_key: apiKey
                    },
                    success: function(summonerInfo) {
                        var puuid = summonerInfo.puuid;
                        console.log("Summoner ID:", summonerId, "PUUID:", puuid);
                        // 여기서 puuid 정보를 사용하여 matchId 가져오기
                        $.ajax({
                            url: 'https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/' + puuid + '/ids',
                            type: 'GET',
                            data: {
                                count: 1,       // 1 puuid 당 최근 게임 1경기
                                api_key: apiKey
                            },
                            success: function(puuidInfo) {
                                var matchId = puuidInfo;
                                console.log("matchId : " + matchId);
                                // matchId 를 이용해서 게임 정보 가져오기
                                $.ajax({
                                    url: 'https://asia.api.riotgames.com/lol/match/v5/matches/' + matchId,
                                    type: 'GET',
                                    data: {
                                        api_key: apiKey
                                    },
                                    success: function(matchIdInfo) {
                                        // 각 참가자의 레전더리 아이템 사용 여부 추출
                                        matchIdInfo.info.participants.forEach(function(participantInfo) {
                                            var championId = participantInfo.championId;
                                            var legendaryItemsUsedInfo = participantInfo.challenges.legendaryItemUsed;
                                            if (legendaryItemsUsedInfo) {
                                                // 레전더리 아이템 배열에서 각 아이템에 대한 정보 출력
                                                legendaryItemsUsedInfo.forEach(function(legendaryItemId) {
                                                    // 레전더리 아이템 사용 횟수 추적
                                                    if (legendaryItemCount.hasOwnProperty(legendaryItemId)) {
                                                        legendaryItemCount[legendaryItemId]++;
                                                    } else {
                                                        legendaryItemCount[legendaryItemId] = 1;
                                                    }
                                                    // 각 legendaryItemUsed와 해당하는 championId 값 표시
                                                    console.log("Champion ID:", championId, "Legendary Item ID:", legendaryItemId);
                                                });
                                            }
                                        });
                                    }
                                })
                            }
                        })
                    },
                    error: function(xhr, status, error) {
                        console.error('API 요청 중 오류 발생:', status, error);
                    }
                });
            });

            // 모든 AJAX 요청이 완료된 후 실행되는 코드
            $(document).ajaxStop(function() {
                // 데이터 수집 및 토스트 그리드에 표시하는 함수 호출
                displayLegendaryItemUsage(legendaryItemCount);
            });
        },
        error: function(xhr, status, error) {
            console.error('API 요청 중 오류 발생:', status, error);
        }
    });
});

// 토스트 그리드에 레전더리 아이템 사용 횟수를 표시하는 함수
function displayLegendaryItemUsage(legendaryItemCount) {
    // 토스트 그리드로 데이터 전송 또는 표시하는 로직을 여기에 작성
    console.log("Legendary Item Usage Count:");

    // 데이터를 담을 배열 초기화
    var gridData = [];

    // 데이터 배열에 행 추가
    for (var itemId in legendaryItemCount) {
        if (legendaryItemCount.hasOwnProperty(itemId)) {
            console.log("Item ID:", itemId, "Usage Count:", legendaryItemCount[itemId]);
            // 토스트 그리드에 표시될 데이터 객체 생성
            var rowData = { itemId: itemId, usageCount: legendaryItemCount[itemId] };
            // 데이터 배열에 행 추가
            gridData.push(rowData);
        }
    }

    // 그리드 생성 및 데이터 전달
    const grid = new tui.Grid({
        el: document.getElementById('grid'),
        data: gridData,
        scrollX: false,
        scrollY: false,
        columns: [
            {
                header: 'Item ID',
                name: 'itemId'
            },
            {
                header: 'Usage Count',
                name: 'usageCount'
            }
        ]
    });
}

