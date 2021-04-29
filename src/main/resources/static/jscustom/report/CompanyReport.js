/*
* 功能：公司报表网页对应的JS文件
* 作者：江成军
* 时间：2020-01-06 21:49:11
* */
require(
    ['/jscustom/GlobleConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap', 'custom'],
            function ($) {
                //start 该处定义我们自己的脚本

                //页面上显示报表
                $.ajax({
                    url:'/CompanyReport/html',
                    type:'post',
                    async:true,//true为异步，false为同步
                    success:function(data){
                        $("#myreport").html(data);
                    }
                });

                //导出Excel
                $("#btn_ExportExcel").click(function(){
                    window.location="/CompanyReport/excel";
                });


                //end 该处定义我们自己的脚本
            }
        )
    }
);