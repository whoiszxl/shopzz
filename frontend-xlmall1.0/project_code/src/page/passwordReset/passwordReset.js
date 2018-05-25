require("./passwordReset.css");
require("../common/nav-simple/nav-simple.css");

var _xl = require("util/xl.js");
var userService = require("service/user-service.js");

// 错误处理
var error = {
    show: function(msg) {
        $(".error-item").show();
        $(".error-msg").text(msg);
    },
    hide: function() {
        $(".error-item").hide();
    }
};
var step = 0; // 步骤
var passwordReset = {
    data: {
        username: '',
        question: '',
        answer: '',
        password: '',
        passwordReset: ''
    },
    init: function() {
        this.onLode();
        this.bindEvent();
        $(".user-con").find("input").text('');
    },
    onLode: function() {
        // 加载第一步
        step = 0;
        this.loadUsername();
    },
    loadUsername: function() {
        $(".step-con").hide();
        $(".step-username").show();
    },
    loadQuestion: function() {
        $(".step-con").hide();
        $(".step-question").show();
    },
    loadReset: function() {
        $(".step-con").hide();
        $(".step-Reset").show();
    },
    bindEvent: function() {
        var _this = this;
        // 点击事件
        $(".submit").click(function() {
            _this.submit(_this);
        });
        // 回车事件
        $(".step-con").keyup(function(e) {
            if (e.keyCode === 13) {
                _this.submit(_this);
            }
        });
    },
    submit: function(t) {
        // 正向提交
        var _this = t;
        // 输入用户名后提交
        if (step === 0) {
            // 如果没有问题进入下一步
            var username = _this.data.username = $("#username").val();
            if (username) {

                // 加载用户问题
                userService.getQuestion(_this.data, function(res) {
                    error.hide();
                    $(".question").text(res.data);
                    _this.data.question = res.data;
                    _this.loadQuestion();
                    step++;

                }, function() {
                    error.show();
                });
            } else {
                error.show("请输入用户名")
            }

        };
        // 输入问题答案后提交
        if (step === 1) {
            var answer = _this.data.answer = $("#anwser").val();
            if (answer) {
                // 提交问题进行检验
                userService.checkAnwser(_this.data, function(res) {
                    _this.data.forgetToken = res.data;
                    _this.loadReset();
                    step++;
                }, function(errorMsg) {
                    error.show("回答错误")
                });
            }
        };
        // 修改密码
        if (step === 2) {
            var password = _this.data.passwordNew = $("#password").val();
            var passwordConfirm = $("#passwordComfirm").val();
            // 进行检验
            if (password && password.length >= 6) {
                if (password === passwordConfirm) {
                    userService.reset(_this.data, function(res) {
                        window.location.href = "./result.html?type=password-reset";
                    }, function() {
                        error.show("密码修改失败");
                    });
                } else {
                    error.show("两次输入的密码不一致");
                }
            } else {
                error.show("密码应该大于六位");
            }
        }
    }
}

passwordReset.init();