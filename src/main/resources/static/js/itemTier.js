let apiKey = "RGAPI-31c883a0-8404-44bb-9aaa-4c32d825df69";

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
                                                legendaryItemsUsedInfo.forEach(function(legendaryitemId) {
                                                    // Riot Games의 데이터 Dragon API를 사용하여 아이템 이름 가져오기
                                                    getItemName(legendaryitemId, function(itemName) {
                                                        console.log("championId:", championId, "legendaryItem ID:", legendaryitemId, "itemName:", itemName);
                                                    });

                                                    // 레전더리 아이템 사용 횟수 추적
                                                    if (legendaryItemCount.hasOwnProperty(legendaryitemId)) {
                                                        legendaryItemCount[legendaryitemId]++;
                                                    } else {
                                                        legendaryItemCount[legendaryitemId] = 1;
                                                    }
                                                });
                                            } else {
                                                console.log("championId:", championId, "이 참가자가 사용한" +
                                                    " 전설적인 아이템이 없습니다..");
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
                // 레전더리 아이템 사용 횟수를 표시
                console.log("Legendary Item Usage Count:");
                for (var itemId in legendaryItemCount) {
                    if (legendaryItemCount.hasOwnProperty(itemId)) {
                        console.log("Item ID:", itemId, "Usage Count:", legendaryItemCount[itemId]);
                    }
                }
            });

        },
        error: function(xhr, status, error) {
            console.error('API 요청 중 오류 발생:', status, error);
        }
    });
});

// Riot Games의 데이터 Dragon API를 사용하여 아이템 이름을 가져오는 함수
function getItemName(itemId, callback) {
    // Riot Games의 데이터 Dragon API에서 아이템 이름 가져오기
    $.ajax({
        url: 'https://ddragon.leagueoflegends.com/cdn/14.6.1/data/ko_KR/item.json',
        type: 'GET',
        success: function(itemData) {
            // 아이템 ID에 해당하는 아이템 이름 찾기
            var itemName = itemData.data[itemId].name;
            // 콜백 함수 호출하여 아이템 이름 전달
            callback(itemName);
        },
        error: function(xhr, status, error) {
            console.error('Failed to fetch item data:', error);
        }
    });
}
