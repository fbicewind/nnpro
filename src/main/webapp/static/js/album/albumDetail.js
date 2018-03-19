var albumDetail = {
	param : {
		percent : 0,
		width : 0,
		height : 0
	},
	init : function() {
		albumDetail.detail.initSize();
		albumDetail.detail.windowResize();
	},
	detail : {
		initSize : function() {
			var width = $('.album-big-div').width();
			var imgWidth = $('.album-img-div').width() + 2 + 'px';
			var height = width / 5 * 4 + 'px';
			$('.album-big-div').height(height);
		},
		windowResize : function() {
			$(window).resize(function() {
				albumDetail.detail.initSize();
				albumDetail.changeImgSize();
				if(showNow != '' && $('.album-photo-div').is(':visible')){
					albumDetail.detail.showFullImage(showNow);
				}
			});
		},
		showFullImage : function(i){
			albumDetail.param.percent = 0;
			showNow = i;
			var src = $('.photo-' + i).attr('data-src');
			src = '/static/upload/album/l/' + src;
			$('#fullImage').attr('src', src)
			$('.album-photo-div').show();
			$('.album-cover').show();
			var windowWidth = $(window).width();
			var windowHeight = $(window).height();
			var theImage = new Image();
			theImage.src = src;
			albumDetail.imgLoad(theImage, this, function(theImage, img){
				var imgWidth = theImage.width;
				var imgHeight = theImage.height;
				//$('#fullImage').css({'left':'50%','margin-left': ('-' + imgWidth/2 + 'px'),'top':'50%','margin-top':('-' + (imgHeight/2+10) + 'px')});
				if(imgWidth <= windowWidth && imgHeight <= windowHeight){
					albumDetail.param.width = imgWidth;
					albumDetail.param.height = imgHeight;
					$('#fullImage').css({'width':(imgWidth + 'px'),'height':(imgHeight + 'px'),'left':'50%','margin-left': ('-' + imgWidth/2 + 'px'),'top':'50%','margin-top':('-' + (imgHeight/2) + 'px')});
				}else if(imgWidth <= windowWidth && imgHeight > windowHeight){
					albumDetail.param.width = imgWidth*windowHeight/imgHeight;
					albumDetail.param.height = windowHeight;
					$('#fullImage').css({'width':(imgWidth*windowHeight/imgHeight + 'px'),'top':'0','margin-top':'0','height':(windowHeight + 'px'),'left':'50%','margin-left':('-' + (imgWidth*windowHeight/(imgHeight*2)) + 'px')});
				}else if(imgWidth > windowWidth && imgHeight <= windowHeight){
					albumDetail.param.width = windowWidth;
					albumDetail.param.height = imgHeight*windowHeight/imgWidth;
					$('#fullImage').css({'height':(imgHeight*windowHeight/imgWidth + 'px'),'left':'0','margin-left':'0','width':(windowWidth + 'px'),'top':'50%','margin-top':('-' + (windowWidth*imgHeight/(imgWidth*2)) + 'px')});
				}else if(imgWidth > windowWidth && imgHeight > windowHeight){
					if(imgWidth/imgHeight >= windowWidth/windowHeight){
						albumDetail.param.width = windowWidth;
						albumDetail.param.height = imgHeight*windowHeight/imgWidth;
						$('#fullImage').css({'height':(imgHeight*windowHeight/imgWidth + 'px'),'left':'0','margin-left':'0','width':(windowWidth + 'px'),'top':'50%','margin-top':('-' + (windowWidth*imgHeight/(imgWidth*2)) + 'px')});
					}else{
						albumDetail.param.width = imgWidth*windowHeight/imgHeight;
						albumDetail.param.height = windowHeight;
						$('#fullImage').css({'width':(imgWidth*windowHeight/imgHeight + 'px'),'top':'0','margin-top':'0','height':(windowHeight + 'px'),'left':'50%','margin-left':('-' + (imgWidth*windowHeight/(imgHeight*2)) + 'px')});
					}
				}
				new Drag("fullImage");
			});
		},
		hideFullImage : function(){
			$('.album-photo-div,.album-cover').hide();
		},
		showLeftImage : function(){
			if(showNow == 0){
				return;
			}
			albumDetail.detail.showFullImage(parseInt(showNow) - 1);
		},
		showRightImage : function(){
			if(showNow == endSize){
				return;
			}
			albumDetail.detail.showFullImage(parseInt(showNow) + 1);
		},
		showOriginPic : function(){
			albumDetail.param.percent = 0;
			var theImage = new Image();
			theImage.src = $('#fullImage').attr('src');
			albumDetail.imgLoad(theImage, this, function(theImage, img){
				var imgWidth = theImage.width;
				var imgHeight = theImage.height;
				albumDetail.param.width = imgWidth;
				albumDetail.param.height = imgHeight;
				$('#fullImage').css({'width':(imgWidth + 'px'),'height':(imgHeight + 'px'),'left':'50%','margin-left': ('-' + imgWidth/2 + 'px'),'top':'50%','margin-top':('-' + imgHeight/2 + 'px')});
				new Drag("fullImage");
			});
		},
		zoom : function(type){
			var windowWidth = $(window).width();
			var windowHeight = $(window).height();
			var percent = 1;
			if(type == 1){
				percent = parseInt(percent) + (parseInt(albumDetail.param.percent) + 1)/10;
				albumDetail.param.percent = parseInt(albumDetail.param.percent) + 1;
			}else if(albumDetail.param.percent > -7){
				percent = parseInt(percent) + (parseInt(albumDetail.param.percent) - 1)/10;
				albumDetail.param.percent = parseInt(albumDetail.param.percent) - 1;
			}else{
				return;
			}
			var width = albumDetail.param.width * percent;
			var height = albumDetail.param.height * percent;
			$('#fullImage').css({'width':(width + 'px'),'height':(height + 'px'),'left':'50%','margin-left': ('-' + width/2 + 'px'),'top':'50%','margin-top':('-' + height/2 + 'px')});
			new Drag("fullImage");
		}
	},
	imgLoad : function(theImage, img, callback) {
		var timer = setInterval(function(){
			if(theImage.complete){
				callback(theImage, img);
				clearInterval(timer);
			}
		}, 20);
	},
	changeImgSize : function(){
		$('.album-big-div img').each(function(){
			var theImage = new Image();
			theImage.src = $(this).attr("src");
			albumDetail.imgLoad(theImage, this, function(theImage, img){
				var width = theImage.width;
				var height = theImage.height;
				if(height/width < 0.8){
					$(img).css('height', '100%');
				}else{
					$(img).css('width', '100%');
				}
			});
		});
	}
}