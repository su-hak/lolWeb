$(function(){

	var displayComments = function (){
		// 댓글을 표시할 div를 가져옵니다.
		var rouletteDiv = document.getElementsByClassName('roulette')[0];
		// 댓글 내용을 가져올 요소들을 선택합니다.
		var commentContents = document.querySelectorAll('.commentContent');
		// 댓글 내용을 담을 변수를 초기화합니다.
		var commentsHTML = '';
		// 모든 댓글 내용을 순회하면서 HTML에 추가합니다.
		commentContents.forEach(function(comment) {
			// 댓글 내용을 가져와서 해당 div에 삽입하되, 클래스를 rouletteComment로 추가합니다.
			commentsHTML += '<div class="rouletteComment">' + comment.innerHTML + '</div>';
		});
		// 댓글 내용을 rouletteDiv에 삽입합니다.
		rouletteDiv.innerHTML = commentsHTML;
	}
	displayComments();

	$('.roulette').find('.rouletteComment').hover(function(){
		console.log("rouletteComment height : " + $(this).height());
	});

	var p = {
		startCallback : function() {
			console.log('start');
			$('#speed, #duration').slider('disable');
			$('.start').attr('disabled', 'true');
			$('.stop').removeAttr('disabled');
		},
		slowDownCallback : function() {
			console.log('slowdown');
			$('.stop').attr('disabled', 'true');
		},
		stopCallback : function($stopElm) {
			console.log('stop');
			$('#speed, #duration').slider('enable');
			$('.start').removeAttr('disabled');
			$('.stop').attr('disabled', 'true');
		}

	}
	var rouletter = $('div.roulette');
	rouletter.roulette(p);
	$('.stop').click(function(){
		var stopImageNumber = $('.stopImageNumber').val();
		if(stopImageNumber == "") {
			stopImageNumber = null;
		}
		rouletter.roulette('stop');
	});
	$('.stop').attr('disabled', 'true');
	$('.start').click(function(){
		rouletter.roulette('start');
	});

	var updateParamater = function(){
		p['speed'] = Number($('.speed_param').eq(0).text());
		p['duration'] = Number($('.duration_param').eq(0).text());
		rouletter.roulette('option', p);
	}
	var updateSpeed = function(speed){
		$('.speed_param').text(speed);
	}
	$('#speed').slider({
		min: 1,
		max: 30,
		value : 10,
		slide: function( event, ui ) {
			updateSpeed(ui.value);
			updateParamater();
		}
	});
	updateSpeed($('#speed').slider('value'));

	var updateDuration = function(duration){
		$('.duration_param').text(duration);
	}
	$('#duration').slider({
		min: 2,
		max: 10,
		value : 3,
		slide: function( event, ui ) {
			updateDuration(ui.value);
			updateParamater();
		}
	});
	updateDuration($('#duration').slider('value'));


	$('#stopImageNumber').spinner('value', -1);

});

