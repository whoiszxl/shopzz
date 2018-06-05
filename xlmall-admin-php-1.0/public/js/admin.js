$.ajaxSetup({
    headers: {
        'X-CSRF-TOKEN' : $('meta[name="csrf-token"]').attr('content')
    }
});

$(".article-audit").click(function(event){
	target = $(event.target);
	var article_id = target.attr("article-id");
	var status = target.attr("article-action-status");

	$.ajax({

		url		: "/admin/article/status/" + article_id,
		method	: "POST",
		data 	: {"status": status},
		dataType: "json",
		success : function(data){
			if(data.error != 0){
				alert(data.msg);
				return;
			}
			target.parent().parent().remove();
		}
	});
});


$(".resource-delete").click(function(event){

	if(confirm("确认删除？") == false){
		return;
	}

	target = $(event.target);
	event.parentDefault();
	var url = $(target).attr("delete-url");

	$.ajax({

		url		: url,
		method	: "POST",
		data 	: {"_method": 'DELETE'},
		dataType: "json",
		success : function(data){
			if(data.error != 0){
				alert(data.msg);
				return;
			}
			window.location.reload();
		}
	});
});