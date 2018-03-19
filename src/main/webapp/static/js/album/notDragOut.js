function Drag(id) {
	this.div = document.getElementById(id);
	if (this.div) {
		this.div.style.cursor = "move";
	}
	this.disX = 0;
	this.disY = 0;
	var _this = this;
	this.div.onmousedown = function(evt) {
		evt.preventDefault();
		_this.getDistance(evt);
		document.onmousemove = function(evt) {
			_this.setPosition(evt);
		}
		document.onmouseup = function() {
			_this.clearEvent();
		}
	}
}
Drag.prototype.getDistance = function(evt) {
	var oEvent = evt || event;
	this.disX = oEvent.clientX - this.div.offsetLeft;
	this.disY = oEvent.clientY - this.div.offsetTop;
	this.minLeft = document.documentElement.clientWidth - this.div.offsetWidth;
	this.minTop = document.documentElement.clientHeight - this.div.offsetHeight;
}
Drag.prototype.setPosition = function(evt) {
	var oEvent = evt || event;
	var l = oEvent.clientX - this.disX;// 鼠标往右移动距离
	var t = oEvent.clientY - this.disY;// 鼠标往下移动距离
	if (this.minLeft < 0) {
		if (l > 0) {
			l = 0;
		} else if (l < this.minLeft) {
			l = this.minLeft;
		}
		this.div.style.marginLeft = (-document.documentElement.clientWidth / 2 + l)
				+ "px";
	}
	if (this.minTop < 0) {
		if (t > 0) {
			t = 0;
		} else if (t < this.minTop) {
			t = this.minTop;
		}
		this.div.style.marginTop = (-document.documentElement.clientHeight / 2 + t)
				+ "px";
	}
}
Drag.prototype.clearEvent = function() {
	this.div.onmouseup = null;
	document.onmousemove = null;
}