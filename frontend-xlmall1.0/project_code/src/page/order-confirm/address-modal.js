var _xl = require("util/xl.js");
var orderAddressService = require("service/order-address-service.js");
var templateAddressModal = require("./address-modal.string");
var citys = require("util/citys/citys.js");
var page = {
    data: {

    },
    show: function(option) {
        // option的绑定
        this.option = option;
        this.option.data = option.data || {};
        this.$modal = $('.modal');
        // 渲染页面
        this.loadModal(option);
        // 绑定事件
        this.bindEvent();
    },
    hide: function() {
        this.$modal.hide();
    },
    loadModal: function(option) {
        var modalHtml = _xl.renderHtml(templateAddressModal, option);
        this.$modal.html(modalHtml).show();
        this.loadProvinces();
    },
    bindEvent: function() {
        var _this = this;
        // 点击关闭关闭添加新地址弹窗
        // $(document).on("click", ".close", function() {
        //     _this.$modal.hide();
        // });
        $(".close").off("click").click(function() {
            _this.$modal.hide();

        });
        // 提交地址信息
        // $(document).off("click", ".receiver-address-submit").on("click", ".receiver-address-submit", function() {
        //     console.log("点击");
        //     if (_this.option.isUpdate) {
        //         // 更新地址
        //         _this.updateAddress();
        //     } else {
        //         // 新增地址
        //         console.log("点击增加地址");
        //         _this.addAddress();

        //     }
        // });
        $(".receiver-address-submit").click(function() {
            if (_this.option.isUpdate) {
                // 更新地址
                _this.updateAddress();
            } else {
                // 新增地址
                console.log("点击增加地址");
                _this.addAddress();

            }
        });
        // 在第一次点击的时候，因为事件绑定是异步的，所以此时才进行事件绑定，并执行，第二次点击的时候，会再进行一次事件绑定，但是
        // 此时我们的元素已经绑定了一个click事件了，可以同时绑定多个click时间，因此等于现在绑定了多个事件，也就会执行多次
        // 当城市改变时,加载城市信息
        // 如果我们只使用click而不是进行事件委托监听，因为click事件只能有一个，所以也是ok的
        $(document).on("change", "#receiver-select-province", function() {
            var provinceName = ($(this).val());
            _this.loadCitys(provinceName);
        });
    },
    // 获取省份信息
    loadProvinces: function() {
        var html = this.loadSelect(citys.getProvinces());
        // 加载省份
        $("#receiver-select-province").html(html);
        if (this.option.isUpdate && this.option.data.receiverProvince) {
            $("#receiver-select-province").val(this.option.data.receiverProvince);
            this.loadCitys(this.option.data.receiverProvince);
        }
    },
    // 加载城市信息
    loadCitys: function(provinceName) {
        var html = this.loadSelect(citys.getCities(provinceName));
        $("#receiver-select-city").html(html);
        if (this.option.isUpdate && this.option.data.receiverCity) {
            $("#receiver-select-city").val(this.option.data.receiverCity);

        }
    },
    // 加载select
    loadSelect: function(optionArray) {
        var html = "<option>请选择</option>";
        for (var i = 0, length = optionArray.length; i < length; i++) {
            html += "<option>" + optionArray[i] + "</option>";
        };
        return html;
    },
    // 新增收货地址
    addAddress: function() {
        var _this = this;
        var info = _this.getAddressInfo();
        var validated = this.validateForm(info);
        if (validated.isOk) {
            // 进行请求
            console.log("验证通过");
            orderAddressService.addAddress(info, function(res) {
                //    执行成功的回调函数
                console.log("添加地址");
                _this.option.onSuccess();
                _this.hide();
            }, function(errMsg) {
                _xl.errorHint(errMsg);
            });
        } else {
            alert(validated.msg);
        }
    },
    // 更新收货地址
    updateAddress: function() {
        var _this = this;
        var info = _this.getAddressInfo();
        var validated = this.validateForm(info);
        if (validated.isOk) {
            // 进行请求
            orderAddressService.updateAddress(info, function(res) {
                //    执行成功的回调函数
                _this.option.onSuccess();
                _this.hide();
            }, function(errMsg) {
                _xl.errorHint(errMsg);
            });
        } else {
            alert(validated.msg);
        }
    },
    getAddressInfo: function() {
        this.data.newAddressInfo = {
            receiverName: $("#receiver-name").val(),
            receiverProvince: $("#receiver-select-province").val(),
            receiverCity: $("#receiver-select-city").val(),
            receiverAddress: $("#receiver-address").val(),
            receiverPhone: $("#receiver-phone").val(),
            receiverZip: $("#receiver-zip").val(),
        };
        if (this.option.isUpdate) {
            this.data.newAddressInfo.id = $("#modal-id").data("id");
        };
        return this.data.newAddressInfo;
    },
    // 验证新建地址表单
    validateForm: function(addressInfo) {
        // 对数据进行验证
        var status = {
            isOk: true,
            msg: ""
        };
        // 验证姓名
        if (_xl.validate(addressInfo.receiverName, "notEmpty")) {
            status.isOk = true;

        } else {
            status.isOk = false;
            status.msg = "请填写收件人姓名";
            return status;
        };
        // 验证地址
        if (_xl.validate(addressInfo.receiverAddress, "notEmpty")) {
            status.isOk = true;
        } else {
            status.isOk = false;
            status.msg = "请填写收件人地址";
            return status;
        };
        // 验证省份和城市
        if (addressInfo.receiverProvince !== "请选择" && addressInfo.receiverCity !== "请选择") {
            status.isOk = true;
        } else {
            status.isOk = false;
            status.msg = "请选择收货人省份和城市";
            return status;
        }
        // 验证号码
        if (_xl.validate(addressInfo.receiverPhone, "notEmpty")) {

            if (_xl.validate(addressInfo.receiverPhone, "phone")) {
                status.isOk = true;
            } else {
                status.isOk = false;
                status.msg = "请填写正确的手机号格式";
                return status;
            }
        } else {
            status.isOk = false;
            status.msg = "请填写收件人手机号码";
            return status;
        };
        return status;
    },

};
module.exports = page;