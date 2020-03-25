$(function(){
	// select 元素
		var $selectorProvince = $("#province");
		var $selectorCity = $("#city");
		var $selectorDistrict = $("#district");

		// 地区的默认值，通过select的default-data获取
		var defaultProvince = $selectorProvince.attr('data-default');
		var defaultCity = $selectorCity.attr('data-default');
		var defaultDistrict= $selectorDistrict.attr('data-default');

		if(!defaultProvince) defaultProvince = currentProvince;
		if(!defaultCity) defaultCity = currentCity;

		// 初始化
		initSelector($selectorProvince,provinces);
		initSelector($selectorCity,getCities(defaultProvince));
		initSelector($selectorDistrict,getDistricts(defaultProvince,defaultCity));

		// 选择省份
		$selectorProvince.change(function(){
			currentProvince = $(this).val();
			initSelector($selectorCity,getCities(currentProvince));
			$selectorCity.trigger('change');
		})

		// 选择城市
		$selectorCity.change(function(){
			currentCity = $(this).val();
			initSelector($selectorDistrict,getDistricts(currentProvince,currentCity));
		})

		// 初始化选择框 其中 data 表示包含所有选择项的数组
		function initSelector(selectObj,data){
			// 空的数据直接隐藏select元素
			if(data == ""){
				selectObj.hide();
				selectObj.html("");
			}else{
				selectObj.show();
			}

			var str = "";
			var selected = selectObj.attr('data-default');
			for (var i = 0; i < data.length; i++) {
				var _data = data[i];
				if(_data === selected){
					str += '<option selected="selected" value="'+_data+'">'+_data+'</option>';
				}else{
					str += '<option value="'+_data+'">'+_data+'</option>';
				}
			}
			selectObj.html(str);
		}
})
