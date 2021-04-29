/*
* 功能：公司表单验证及异步提交
* 作者：江成军
* 时间：2019-12-28 11:04:50
* */
require(
    ['/jscustom/GlobleConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap3','bootstrap_validator','bootstrap_validator_CN','jqueryform','jqueryupload'],
            function ($) {
                //start 该处定义我们自己的脚本

                //利用bootstrapvalidator进行表单验证
                $('#form_Company').bootstrapValidator({
                    feedbackIcons: {
                        valid: 'fa fa-check',
                        invalid: 'fa fa-close',
                        validating: 'fa fa-refresh'
                    },
                    fields: {
                        comname: {
                            validators: {
                                notEmpty: {message: '公司名称不能为空'},
                                stringLength: {
                                    min: 4,
                                    max: 60,
                                    message: '公司名称长度在4-60个汉字范围内'
                                }
                            }
                        },
                        contactmobile: {
                            validators: {
                                notEmpty: {message: '手机号码不能为空'},
                                regexp: {
                                    regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
                                    message: '手机号码格式错误'
                                }
                            }
                        },
                        contactemail: {
                            validators: {
                                notEmpty: {message: '邮箱不能为空'},
                                threshold: 2, //有2字符以上才发送ajax请求,（input中输入一个字符，插件会向服务器发送一次，设置限制，2字符以上才开始）
                                remote: {
                                    url: '/CompanyModule/validateEmail',
                                    message: '邮箱已存在'
                                },
                                emailAddress: {message: '邮箱格式不正确'}
                            }
                        },
                        totaloutput:{
                            notEmpty: {message: '总收入不能为空'},
                            number: {message: '格式错误，只能是数字、包括小数点'}
                        },
                        comurl:{
                            notEmpty: {message: '网址不能为空'},
                            uri: {message: '网址格式错误'}
                        }
                    }
                });

                //异步提交表单
                $("#btn_Save").bind('click',function() {

                        //遍历class为‘filename’，获取当前上传成功的所有文件的原文件名，并重新赋值给（插件临时创建）隐藏表单‘upload’
                        //$(this).html()代表样式为‘filename’的div中的值，即原文件名称，如果是图像，则没有名称
                        $(".filename").each(function(){
                            $("[name=upload]").val($("[name=upload]").val()+','+$(this).html());
                        });

                        var bootstrapValidator = $('#form_Company').data('bootstrapValidator');//获取表单对象
                        bootstrapValidator.validate();//手动触发验证
                        if (bootstrapValidator.isValid())//全部验证通过，才能提交表单
                        {
                            var options = {
                                complete: function (data) {
                                    alert("公司添加成功");
                                    var mylay = parent.layer.getFrameIndex(window.name);
                                    parent.layer.close(mylay);//关闭当前窗口页
                                },
                                url: '/CompanyModule/save',
                                dataType: 'json',
                                resetForm: true  // 成功提交后，重置所有的表单元素的值
                            };
                            $('#form_Company').ajaxSubmit(options);
                        }
                    }
                );

                //文件上传
                $("#case").upload();


                //end 该处定义我们自己的脚本
            }
        )
    }
);