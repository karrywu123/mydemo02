/*
* 功能：公司图表展示
* 作者：江成军
* 时间：2019-12-31 20:44:35
* */
require(
    ['/jscustom/GlobleConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap', 'custom'],
            function ($) {
                //start 该处定义我们自己的脚本

                // 基于准备好的dom，初始化echarts实例
                var myChartLine = echarts.init(document.getElementById('mainLine'));
                var myChartBar = echarts.init(document.getElementById('mainBar'));
                var myChartArea = echarts.init(document.getElementById('mainArea'));
                var myChartPie = echarts.init(document.getElementById('mainPie'));

                //定义每个图表X轴、Y轴的数组
                var LineX=[];
                var LineY=[];

                var BarX=[];
                var BarY=[];

                var AreaX=[];
                var AreaY=[];

                var Pie=[];

                //数据加载前先显示简单的Loading动画
                myChartLine.showLoading();
                myChartBar.showLoading();
                myChartArea.showLoading();
                myChartPie.showLoading();

                //向远端请求数据
                $.ajax({
                    type : "post",
                    async : true,
                    url : "/CompanyModule/chart",
                    dataType : "json",
                    success : function(result){
                        //请求成功时执行该函数内容，result即为服务器返回的json对象
                        if (result){
                            var listperson= result["listperson"];
                            var listoutput = result["listoutput"];

                            //遍历listperson，组装现状图和柱状图的数据
                            for(var i=0,num=listperson.length;i<num;i++)
                            {
                                LineX.push(listperson[i].name);
                                LineY.push(listperson[i].value);

                                BarX.push(listperson[i].name);
                                BarY.push(listperson[i].value);
                            }

                            //编列listoutput，组装面状图和饼状图的数据
                            for(var i=0,num=listoutput.length;i<num;i++)
                            {
                                AreaX.push(listoutput[i].name);
                                AreaY.push(listoutput[i].value);

                                Pie.push(listoutput[i]);
                            }

                            //隐藏加载动画
                            myChartLine.hideLoading();
                            myChartBar.hideLoading();
                            myChartArea.hideLoading();
                            myChartPie.hideLoading();

                            //设置图表相关参数,并显示图表
                            myChartLine.setOption({
                                legend:{data:['员工数量1','员工数量2']},
                                tooltip:{formatter: "公司名称:{b}<br/> 人员数量: {c}个"},
                                xAxis: {
                                    data: LineX,
                                    axisLabel: {interval:0,rotate:40 }
                                },

                                yAxis: {},
                                series: [{
                                    name:"员工数量1",
                                    data: LineY,
                                    type: 'line'
                                },{
                                    name:"员工数量2",
                                    data: LineY,
                                    type: 'bar'
                                }]
                            });

                            myChartBar.setOption({
                                legend:{data:['员工数量']},
                                tooltip:{formatter: "公司名称:{b}<br/> 人员数量: {c} 名"},
                                xAxis: {data: BarX},
                                yAxis: {},
                                series: [{
                                    name:"员工数量",
                                    data: BarY,
                                    type: 'bar'
                                }]
                            });

                            myChartArea.setOption({
                                legend:{data:['营业收入']},
                                tooltip:{formatter: "公司名称:{b}<br/> 收入: {c}万元"},
                                xAxis: {
                                    boundaryGap: false,
                                    data: AreaX},
                                yAxis: {},
                                series: [{
                                    name:"营业收入",
                                    data: AreaY,
                                    type: 'line',
                                    areaStyle: {}
                                }]
                            });

                            myChartPie.setOption({
                                title:{text:['营业收入']},
                                tooltip : {
                                    trigger: 'item',
                                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                                },
                                series: [{
                                    name:"营业收入",
                                    type:'pie',
                                    radius : [30, 110],
                                    center : ['75%', '50%'],
                                    roseType : 'area',
                                    data:Pie
                                }]
                            });



                        }
                    }
                });



                //end 该处定义我们自己的脚本
            }
        )
    }
);