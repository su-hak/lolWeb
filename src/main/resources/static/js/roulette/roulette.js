(function($) {
	// Roulette 객체 정의
	var Roulette = function($roulette, options) {
		// 기본 설정 값
		var defaultSettings = {
			maxPlayCount: null,
			speed: 10,
			stopCommentIndex: null,
			rollCount: 3,
			duration: 3,
			stopCallback: function() {},
			startCallback: function() {},
			slowDownCallback: function() {}
		};

		// 기본 속성 값
		var defaultProperty = {
			playCount: 0,
			$rouletteTarget: null,
			commentCount: null,
			$comments: null,
			originalStopCommentIndex: null,
			distance: 0,
			isRunUp: true,
			isSlowdown: false,
			isStop: false,
			topPosition: 0,
			totalHeight: null,
			runUpDistance: null,
			maxDistance: null,
			slowDownStartDistance: null,
			slowdownTimer: null
		};

		// 설정 값과 기본 속성 값 병합
		var p = Object.assign({}, defaultSettings, options, defaultProperty);

		// 초기화 함수
		var init = function($roulette, options) {
			/*todo: roulette-inner 추가해줘야함 */
			if (!($roulette instanceof jQuery)) {
				console.error('$roulette is not a valid jQuery object');
				return;
			}
			$roulette.css({ 'overflow': 'hidden' });
			p.originalStopCommentIndex = p.stopCommentIndex;

			// 댓글 로드 후 설정
			if (!p.$comments) {
				p.$comments = $roulette.find('.comment-content');
				p.commentCount = p.$comments.length;
			}
		};

		// 시작 함수
		var start = function() {
			p.playCount++;
			if (p.maxPlayCount && p.playCount > p.maxPlayCount) {
				return;
			}
			p.stopCommentIndex = $.isNumeric(p.originalStopCommentIndex) && Number(p.originalStopCommentIndex) >= 0 ?
				Number(p.originalStopCommentIndex) : Math.floor(Math.random() * p.commentCount);
			p.startCallback();
			roll();
			p.slowDownTimer = setTimeout(() => {
				slowDownSetup();
			}, p.duration * 1000);
		};

		// 슬로우다운 설정 함수
		var slowDownSetup = function() {
			if (p.isSlowdown) {
				return;
			}
			p.slowDownCallback();
			p.isSlowdown = true;
			p.slowDownStartDistance = p.distance;
			p.maxDistance = p.distance + (2 * p.totalHeight);
			p.maxDistance += p.totalHeight - p.topPosition % p.totalHeight;
			if (p.stopCommentIndex != null) {
				p.maxDistance += (p.totalHeight - (p.maxDistance % p.totalHeight) + (p.stopCommentIndex * p.totalHeight)) % p.totalHeight;
			}
		};

		// 롤링 함수
		var roll = function() {
			var speed_ = p.speed;

			if (p.isRunUp) {
				if (p.distance <= p.runUpDistance) {
					var rate_ = ~~((p.distance / p.runUpDistance) * p.speed);
					speed_ = rate_ + 1;
				} else {
					p.isRunUp = false;
				}

			} else if (p.isSlowdown) {
				var rate_ = ~~(((p.maxDistance - p.distance) / (p.maxDistance - p.slowDownStartDistance)) * p.speed);
				speed_ = rate_ + 1;
			}

			if (p.maxDistance && p.distance >= p.maxDistance) {
				p.isStop = true;
				reset();
				if (p.$rouletteTarget) { // 유효성 확인
					p.$rouletteTarget.css('transform', 'translate(0px, -' + p.topPosition + 'px)');
					var stopComment = p.$rouletteTarget.find('.comment-content').eq(p.stopCommentIndex);
					var stopCommentText = stopComment.length > 0 ? stopComment.text() : '';
					p.stopCallback(stopCommentText);
				}
				return;
			}
			p.distance += speed_;
			p.topPosition += speed_;
			if (p.topPosition >= p.totalHeight) {
				p.topPosition = p.topPosition - p.totalHeight;
			}
			if (p.$rouletteTarget) { // 유효성 확인
				p.$rouletteTarget.css('transform', 'translate(0px, -' + p.topPosition + 'px)');
			}
			setTimeout(roll, 1);
		};


		// 멈춤 함수
		var stop = function(option) {
			if (!p.isSlowdown) {
				if (option) {
					var stopCommentIndex = Number(option.stopCommentIndex);
					if (0 <= stopCommentIndex && stopCommentIndex <= (p.commentCount - 1)) {
						p.stopCommentIndex = stopCommentIndex;
					}
				}
				slowDownSetup();
			}
		};

		// 옵션 설정 함수
		var setOption = function(options) {
			p = Object.assign(p, options);
			p.speed = Number(p.speed);
			p.duration = Number(p.duration);
			p.duration = p.duration > 1 ? p.duration - 1 : 1;
			defaultProperty.originalStopCommentIndex = options.stopCommentIndex;
		};

		// 초기화 함수 호출
		init($roulette, options);

		// 공개 메소드
		return {
			start: start,
			stop: stop,
			setOption: setOption
		};
	};

	// 플러그인 이름
	var pluginName = 'roulette';

	// jQuery 플러그인 등록
	$.fn[pluginName] = function(method, options) {
		return this.each(function() {
			var self = $(this);
			var roulette = self.data('plugin_' + pluginName);

			if (roulette) {
				if (roulette[method]) {
					roulette[method](options);
				} else {
					console && console.error('Method ' + method + ' does not exist on jQuery.roulette');
				}
			} else {
				roulette = new Roulette(self, method);
				self.data('plugin_' + pluginName, roulette);
			}
		});
	};
})(jQuery);
