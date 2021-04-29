/*
* 功能：添加系统账号
* 作者：江成军
* 时间：2020-01-02 10:32:55
* */
require(
    ['/jscustom/GlobleConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap3','bootstrap_validator','bootstrap_validator_CN','jqueryform'],
            function ($) {
                //start 该处定义我们自己的脚本

                //从后台获取角色，自动填充到下拉表单
                $.ajax({
                    url:'/security/findAllRoles',
                    type:'post',
                    async:true,//true为异步，false为同步
                    success:function(data){
                        var options = '';
                        for (var i = 0; i <data.length; i++)
                        {
                            options += '<option value="' + data[i].uuid + '">' + data[i].rolename + '</option>';
                        }
                        $("#sysroleid").html(options);
                    }

                });

                //利用bootstrapvalidator进行表单验证
                $(document).ready(function(){
                    $('#FormSysUser').bootstrapValidator({
                        feedbackIcons: {
                            valid: 'fa fa-check',
                            invalid: 'fa fa-close',
                            validating: 'fa fa-refresh'
                        },
                        fields: {
                            username: {
                                validators: {
                                    notEmpty: {
                                        message: '账号不能为空'
                                    },
                                    stringLength: {
                                        min: 2,
                                        max: 20,
                                        message: '账号长度在2-20个字符范围内'
                                    },
                                    threshold: 2, //有2字符以上才发送ajax请求,（input中输入一个字符，插件会向服务器发送一次，设置限制，2字符以上才开始）
                                    remote: {
                                        url: '/security/validateUsername',
                                        message: '账号已存在',
                                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                                        type: 'POST'//请求方式
                                    },
                                    regexp: {
                                        regexp: /^[a-zA-Z0-9_\.]+$/,
                                        message: '账号以字母开头，可包含数字'
                                    }
                                }
                            },
                            password: {
                                validators: {
                                    notEmpty: {message: '密码不能为空'},
                                    stringLength: {
                                        min: 6,
                                        message: '密码长度不小于6位'
                                    }
                                }
                            },
                            useremail: {
                                validators: {
                                    notEmpty: {
                                        message: '邮箱不能为空'
                                    },
                                    threshold: 2, //有2字符以上才发送ajax请求,（input中输入一个字符，插件会向服务器发送一次，设置限制，2字符以上才开始）
                                    remote: {
                                        url: '/security/validateEmail',
                                        message: '邮箱已存在',
                                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                                        type: 'POST'//请求方式
                                    },
                                    emailAddress: {message: '邮箱格式不正确'}
                                }
                            },
                            usermobile: {
                                validators: {
                                    notEmpty: {
                                        message: '手机号不能为空'
                                    },
                                    threshold: 10, //有10字符以上才发送ajax请求,（input中输入一个字符，插件会向服务器发送一次，设置限制，2字符以上才开始）
                                    remote: {
                                        url: '/security/validateMobile',
                                        message: '手机号已存在',
                                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                                        type: 'POST'//请求方式
                                    },
                                    regexp: {
                                        regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
                                        message: '手机号码格式错误'
                                    }
                                }
                            }
                        }
                    })
                });

                //异步提交表单
                $("#btn_Save").bind('click',function() {
                        var bootstrapValidator = $('#FormSysUser').data('bootstrapValidator');//获取表单对象
                        bootstrapValidator.validate();//手动触发验证
                        if (bootstrapValidator.isValid())//全部验证通过，才能提交表单
                        {
                            var options = {
                                complete: function (data) {
                                    alert("客户添加成功");
                                    var mylay = parent.layer.getFrameIndex(window.name);
                                    parent.layer.close(mylay);//关闭当前窗口页
                                },
                                url: '/security/saveSysUser',
                                dataType: 'json',
                                resetForm: true  // 成功提交后，重置所有的表单元素的值
                            };
                            $('#FormSysUser').ajaxSubmit(options);
                        }
                    }
                );

                //end 该处定义我们自己的脚本
            }
        )
    }
);