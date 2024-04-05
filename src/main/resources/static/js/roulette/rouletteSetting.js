$(function(){
	var rouletter = $('div.roulette');
	var comments = [];

	// 댓글 내용을 배열에 추가
	$('.commentContent').each(function() {
		comments.push($(this).text());
	});

	// 룰렛 설정
	var p = {
		startCallback: function() {
			// 시작할 때
			console.log('start');
			console.log(comments);
			$('#speed, #duration').slider('disable');
			$('#stopImageNumber').spinner('disable');
			$('.start').attr('disabled', true);
			$('.stop').removeAttr('disabled');
		},
		slowDownCallback: function() {
			// 감속할 때
			console.log('slowdown');
			$('.stop').attr('disabled', true);
		},
		stopCallback: function($stopElm) {
			// 멈출 때
			console.log('stop');
			$('#speed, #duration').slider('enable');
			$('#stopImageNumber').spinner('enable');
			$('.start').removeAttr('disabled');
			$('.stop').attr('disabled', true);
		},
		// 룰렛 항목 설정
		stopImageNumber: null, // 초기값은 null로 설정
		rollCount: 3,
		duration: 3,
		speed: 10
	};

	rouletter.roulette(p);

	// START 버튼 클릭 시 룰렛 시작
	$('.start').click(function() {
		updateComments(); // 댓글 내용 업데이트
		rouletter.roulette('start');
	});

	// STOP 버튼 클릭 시 룰렛 멈춤
	$('.stop').click(function() {
		var stopIndex = Math.floor(Math.random() * comments.length); // 랜덤으로 댓글 선택
		p.stopImageNumber = stopIndex; // 선택된 댓글의 인덱스를 멈출 이미지 번호로 설정
		rouletter.roulette('stop');
	});

	// 댓글 내용을 업데이트하는 함수
	var updateComments = function() {
		comments = [];
		$('.commentContent').each(function() {
			comments.push($(this).text());
		});
		updateRoulette(); // 룰렛 내용 업데이트
	};

	// 룰렛 옵션을 업데이트하는 함수
	var updateRoulette = function() {
		$('.roulette').empty(); // 기존에 있던 내용을 모두 지우고 새로운 댓글을 추가
		$.each(comments, function(index, comment) {
			var commentDiv = $('<div>').addClass('comment-content').text(comment);
			$('.roulette').append(commentDiv);
		});
	};

	// 슬라이더와 스피너 초기화
	$('#speed').slider({
		min: 1,
		max: 30,
		value: 10,
		slide: function(event, ui) {
			p.speed = ui.value;
			updateRouletteOptions();
		}
	});

	$('#duration').slider({
		min: 2,
		max: 10,
		value: 3,
		slide: function(event, ui) {
			p.duration = ui.value;
			updateRouletteOptions();
		}
	});

	$('#stopImageNumber').spinner({
		min: -1,
		max: comments.length - 1,
		spin: function(event, ui) {
			p.stopImageNumber = ui.value;
			updateRouletteOptions();
		}
	});
});
