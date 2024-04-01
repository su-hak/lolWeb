let apiKey = "RGAPI-00417f8e-82db-4f9b-b4a3-cac4e1622263";

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
            // "entries" 배열 중 처음 100개 객체만 추출하여 순회
            response.entries.slice(0, 100).forEach(function(entry) {
                summonerIds.push(entry.summonerId); // 각 객체의 "summonerId" 값을 배열에 추가
            });
            // console.log("First 100 Summoner IDs:", summonerIds);

            // 소환사 ID를 사용하여 puuid 정보 가져오기
            summonerIds.forEach(function(summonerId, index) {
                // 2초의 딜레이를 주기 위해 setTimeout 함수 사용
                setTimeout(function() {
                    $.ajax({
                        url: 'https://kr.api.riotgames.com/lol/summoner/v4/summoners/' + summonerId,
                        type: 'GET',
                        data: {
                            api_key: apiKey
                        },
                        success: function(summonerInfo) {
                            var puuid = summonerInfo.puuid;
                            console.log("Summoner ID:", summonerId, "PUUID:", puuid);
                            // 여기서 puuid 정보를 사용하여 추가 작업
                            // todo : 배열로 제공되는 데이터는 요청이 원활한데 배열로 받아온 데이터들을 따로 api에 요청하게 되면 요청 과부화 에러. 딜레이 주게되면 시간이 오래 걸림
                        },
                        error: function(xhr, status, error) {
                            console.error('API 요청 중 오류 발생:', status, error);
                        }
                    });
                }, index * 2000); // 각 AJAX 요청 간격을 2초로 설정
            });
        },
        error: function(xhr, status, error) {
            console.error('API 요청 중 오류 발생:', status, error);
        }
    });
});
