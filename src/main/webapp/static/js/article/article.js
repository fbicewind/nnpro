var article = {
	init : function() {
        article.detail.dot();
        article.detail.initSize();
        article.detail.windowResize();
	},
	detail : {
		initSize : function() {
			var width = $('.index-image-outer').width();
			var height = width / 5 * 3 + 'px';
			$('.index-image-outer').height(height);
		},
		windowResize : function() {
			$(window).resize(function() {
                article.detail.initSize();
                article.changeImgSize();
			});
		},
		dot : function(){
			$('.article-li p').dotdotdot();
		}
	},
	imgLoad : function(theImage, img, callback) {
		var timer = setInterval(function() {
			if (theImage.complete) {
				callback(theImage, img);
				clearInterval(timer);
			}
		}, 20);
	},
	changeImgSize : function() {
		$('.index-image-outer>img').each(function() {
			var theImage = new Image();
			theImage.src = $(this).attr("src");
            article.imgLoad(theImage, this, function(theImage, img) {
				var width = theImage.width;
				var height = theImage.height;
				if (height / width > 0.6) {
					$(img).css('height', '100%');
				} else {
					$(img).css('width', '100%');
					var h = $(img).parent().height();
					var th = $(img).height();
					var top = (h - th) / 2 + 'px';
					$(img).css('margin-top', top);
				}
			});
		});
	}
}