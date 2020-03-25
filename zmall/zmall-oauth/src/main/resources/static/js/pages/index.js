// window.onload = function() {
// 	cartListController.setup();
// }

/*商品分类*/
$(function() {
	$('.all-sort-list2 > .item').hover(function() {
		//父类分类列表容器的高度

		$(this).addClass('hover');
		$(this).children('.item-list').css('display', 'block');
	}, function() {
		$(this).removeClass('hover');
		$(this).children('.item-list').css('display', 'none');
	});

	$('.item > .item-list > .close').click(function() {
		$(this).parent().parent().removeClass('hover');
		$(this).parent().hide();
	});
});