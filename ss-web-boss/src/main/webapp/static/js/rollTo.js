define(function(require, exports, module){
	$.fn.extend({
		rollTo: function(options) {
			var o = {
				oFinish: "body",	//要滚动到的元素
				sSpeed: "0",		//滚动速度
				bMonitor: false, 	//是否楼层监听
				sClass: "current",	//楼层监听时需要添加的样式
				iBias:0,			//偏差调节
				fnAdditional: ""	//追加方法
			}
			o = $.extend(o,options);
			var oThis = $(this),
				targetOffset = parseInt($(o.oFinish).offset().top+o.iBias);
			oThis.click(function() {
				$("html,body").stop(true, true).animate({
					scrollTop: targetOffset
				}, o.sSpeed);
				/*$("html,body").scrollTop(targetOffset);*/
				o.sSpeed == 0 && $("body").stop(true, true);
				o.fnAdditional && o.fnAdditional();
			});
			if (o.bMonitor) {
				$(window).bind("scroll load", function(event) {
					if ($(this).scrollTop() >= targetOffset) {
						oThis.addClass(o.sClass).siblings().removeClass(o.sClass);
					}
				});
			}
			return $(this);
		}
	});
});