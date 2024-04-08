(function($) {
	var Roulette = function(options) {
		var defaultSettings = {
			maxPlayCount: null,
			speed: 10,
			stopImageNumber: -1,
			rollCount: 3,
			duration: 3,
			stopCallback: function() {},
			startCallback: function() {},
			slowDownCallback: function() {}
		};

		var defaultProperty = {
			playCount: 0,
			$rouletteTarget: null,
			commentCount: null,
			$comments: null,
			originalStopImageNumber: -1,
			totalHeight: null,
			topPosition: 0,
			maxDistance: null,
			slowDownStartDistance: null,
			isRunUp: true,
			isSlowdown: false,
			isStop: false,
			distance: 0,
			runUpDistance: null,
			slowdownTimer: null,
			isIE: navigator.userAgent.toLowerCase().indexOf('msie') > -1
		};

		var p = $.extend({}, defaultSettings, options, defaultProperty);

		var reset = function() {
			p.maxDistance = defaultProperty.maxDistance;
			p.slowDownStartDistance = defaultProperty.slowDownStartDistance;
			p.distance = defaultProperty.distance;
			p.isRunUp = defaultProperty.isRunUp;
			p.isSlowdown = defaultProperty.isSlowdown;
			p.isStop = defaultProperty.isStop;
			p.topPosition = defaultProperty.topPosition;
			clearTimeout(p.slowDownTimer);
		};

		var slowDownSetup = function() {
			if (p.isSlowdown) {
				return;
			}
			p.slowDownCallback();
			p.isSlowdown = true;
			p.slowDownStartDistance = p.distance;
			/* 슬로우다운 최대 거리 = 속도 */
			p.maxDistance = p.distance + (20 * p.totalHeight);
			p.maxDistance += p.commentHeight - p.topPosition % p.commentHeight;
			if (p.stopImageNumber != null) {
				p.maxDistance += (p.totalHeight - (p.maxDistance % p.totalHeight) + (p.stopImageNumber * p.commentHeight)) % p.totalHeight;
			}
		};

		var roll = function() {
			var speed_ = p.speed;

			if (p.isRunUp) {
				if (p.distance <= p.runUpDistance) {
					var rate_ = ~~((p.distance / p.runUpDistance) * p.speed);
					speed_ = rate_ + 1;
					console.log("p.isRunUp 일때", rate_, speed_)
				} else {
					p.isRunUp = false;
				}
			} else if (p.isSlowdown) {
				var rate_ = ~~(((p.maxDistance - p.distance) / (p.maxDistance - p.slowDownStartDistance)) * (p.speed));
				speed_ = rate_ + 0.2;
				console.log("p.isSlowdown 일때", rate_, speed_)
			}

			if (p.maxDistance && p.distance >= p.maxDistance) {
				p.isStop = true;
				reset();
				p.stopCallback();
				return;
			}

			p.distance += speed_;
			p.topPosition += speed_;

			if (p.topPosition >= p.totalHeight) {
				p.topPosition = p.topPosition - p.totalHeight;
			}

			if (p.isIE) {
				p.$rouletteTarget.css('top', '-' + p.topPosition + 'px');
			} else {
				p.$rouletteTarget.css('transform', 'translate(0px, -' + p.topPosition + 'px)');
			}

			setTimeout(roll, 1);
		};

		var init = function($roulette) {
			$(document).ready(function() {
				var rouletteDiv = document.getElementsByClassName('roulette')[0];
				var commentContents = document.querySelectorAll('.commentContent');
				var commentsHTML = '';

				commentContents.forEach(function(comment) {
					commentsHTML += '<div class="rouletteComment" style="height: 40px">' + comment.innerHTML + '</div>';
				});

				rouletteDiv.innerHTML = commentsHTML;

				$roulette.css({ 'overflow': 'hidden' });
				defaultProperty.originalStopImageNumber = p.stopImageNumber;

				if (!p.$comments) {
					p.$comments = $roulette.find('.rouletteComment').remove();
					p.commentCount = p.$comments.length;

					p.commentHeight = 40; // Default height
					$roulette.css({ 'height': (p.commentHeight + 'px') });

					p.totalHeight = p.commentCount * p.commentHeight;
					p.runUpDistance = 2 * p.commentHeight;
				}

				$roulette.find('div').remove();
				p.$comments.css({ 'display': 'block' });

				p.$rouletteTarget = $('<div>').css({
					'position': 'relative',
					'top': '0'
				}).attr('class', "roulette-inner");

				$roulette.append(p.$rouletteTarget);
				p.$rouletteTarget.append(p.$comments);
				p.$rouletteTarget.append(p.$comments.eq(0).clone());
				$roulette.show();
			});
		};

		var start = function() {
			p.playCount++;
			if (p.maxPlayCount && p.playCount > p.maxPlayCount) {
				return;
			}

			p.stopImageNumber = $.isNumeric(defaultProperty.originalStopImageNumber) && Number(defaultProperty.originalStopImageNumber) >= 0 ?
				Number(defaultProperty.originalStopImageNumber) : Math.floor(Math.random() * p.commentCount);

			p.startCallback();
			roll();

			p.slowDownTimer = setTimeout(function() {
				slowDownSetup();
			}, p.duration * 1000);
		};

		var stop = function(option) {
			if (!p.isSlowdown) {
				if (option) {
					var stopImageNumber = Number(option.stopImageNumber);
					if (0 <= stopImageNumber && stopImageNumber <= (p.commentCount - 1)) {
						p.stopImageNumber = option.stopImageNumber;
					}
				}
				slowDownSetup();
			}
		};

		var option = function(options) {
			p = $.extend(p, options);
			p.speed = Number(p.speed);
			p.duration = Number(p.duration);
			p.duration = p.duration > 1 ? p.duration - 1 : 1;
			defaultProperty.originalStopImageNumber = options.stopImageNumber;
		};

		return {
			start: start,
			stop: stop,
			init: init,
			option: option
		};
	};

	var pluginName = 'roulette';
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
				roulette = new Roulette(method);
				roulette.init(self, method);
				$(this).data('plugin_' + pluginName, roulette);
			}
		});
	};
})(jQuery);
