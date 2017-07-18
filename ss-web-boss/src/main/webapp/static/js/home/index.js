define(function(require,exports,modlue){
	require('jquery');
	
	var param="";
	ajaxUrl(param);//初始化调用
	//排序

	$(".search_button").click(function(){
		var title = $(".product_search").val();
		param = "&title=" + title;
		ajaxUrl(param);
	});
	
	//通用显示函数
	function ajaxUrl(param){
		$.ajax({
		type:'get',
		url:'/book/indexBookList.html?' + 'randomTime=' + (new Date()).getTime() + param,
		dataType:'json',
		success:function(json){
			//总记录数
			require.async('/static/js/handlebars-v1.3.0.js',function(){
					require.async('/static/js/transFormatJson.js',function(){
						var tpl = require('../../tpl/bookList.tpl');
						var template = Handlebars.compile(tpl);
						var html = template(json);
						$('.book_list').html(html);
					});
				});
			
				//分页插件
				if(json.data.totalPage > 0)
				{
					require.async(['/static/css/pager.css','/static/js/pager.js'],function(){
						kkpager.generPageHtml({
								pno : json.data.page,//当前页码
								total : json.data.totalPage,//总页码
								totalRecords : json.data.count,//总数据条数
								isShowFirstPageBtn	: false,
								isShowLastPageBtn	: false,
								isShowTotalPage 	: false, 
								isShowTotalRecords 	: false, 
								isGoPage 			: false,
								lang:{
									prePageText				: '<',
									nextPageText			: '>'
								},
								mode:'click',//click模式匹配getHref 和 click
								click:function(n,total,totalRecords){
						        	$.ajax({
						        		type:"get",
						        		url:"/book/indexBookList.html?"+"randomTime=" + (new Date()).getTime() + "&page.page=" + n + param,
						        		dataType:"json",//这个必不可少，如果缺少，导致传回来的不是json格式
						        		success:function(json){
						        			require.async(['/static/js/handlebars-v1.3.0.js','/static/js/transFormatJson.js'],function(){
												var tpl = require('/static/tpl/bookList.tpl');
												var template = Handlebars.compile(tpl);
												var html    = template(json);
												$('.book_list').html(html);
											});
						        		}
						        	});
									this.selectPage(n); //处理完后可以手动条用selectPage进行页码选中切换
								}
						});
					});
				}else{
					$("#kkpager").html('暂无数据');
				}
			}
		})	
	}
	
});