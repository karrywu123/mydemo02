/*
* 功能：公司列表相关
* 作者：江成军
* 时间：2019-12-23 23:00:16
* */
require(
    ['/jscustom/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','custom','bootstrap_table','bootstrap_table_CN','layer'],
            function($){
                //start 该处定义我们自己的脚本

                //绑定列表中的修改和删除图标对应的事件
                window.cellEvents={
                    'click .modify':function(e,value,row,index){
                        //执行修改操作
                        alert("点击了修改图标:"+row.comname);
                    },
                    'click .delete':function(e,value,row,index){
                        //执行删除操作
                        alert("点击了删除图标："+row.uuid);
                    }
                };

                //数据列表展示
                $('#tb_Company').bootstrapTable({
                    url: '/CompanyModule/queryDynamic',         //请求后台的URL（*）
                    method: 'post',                      //请求方式（*）post/get
                    //striped: true,                      //是否显示行间隔色
                    classes:"table table-bordered table-hover",//启用bootstrap的表格样式
                    theadClasses:"bg-info",         //表头的背景色
                    //toolbar: '#toolbar',                //工具按钮用哪个容器
                    cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,                   //是否显示分页（*）
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    //queryParamsType:'',                //'limit'：RESTFul风格，包括limit, offset, search, sort, order，如果为空''：包括pageSize, pageNumber, searchText, sortName, sortOrder
                    pageNumber:1,                       //初始化加载第一页，默认第一页
                    pageSize: 5,                       //每页的记录行数（*）
                    pageList: [3,5,20],                 //可供选择的每页的行数（*）
                    search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch: true,                  //默认为false，设置为 true启用 全匹配搜索，否则为模糊搜索
                    showColumns: false,                  //是否显示所有的列
                    showRefresh: false,                  //是否显示刷新按钮
                    minimumCountColumns: 10,             //最少允许的列数
                    clickToSelect: true,                //是否启用点击选中行
                    //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数设置表格高度
                    uniqueId: "uuid",                     //每一行的唯一标识，一般为主键列
                    showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
                    cardView: false,                    //是否显示详细视图
                    detailView: false,                   //是否显示父子表
                    onClickRow:function(row,$element){   //行单击事件
                        var detail="<h2><strong>"+row.comname+"详细信息</strong></h2>";
                        detail+="<p>UUID：<b>"+row.uuid+"</b></p>";
                        detail+="<p>公司名称：<b>"+row.comname+"</b></p>";
                        detail+="<p>公司地址：<b>"+row.comaddress+"</b></p>";
                        detail+="<p>公司网址：<b>"+row.comurl+"</b></p>";
                        detail+="<p>公司座机：<b>"+row.comtelephone+"</b></p>";
                        detail+="<p>成立日期：<b>"+row.establishdate+"</b></p>";
                        detail+="<p>员工人数：<b>"+row.employeenumber+"</b></p>";
                        detail+="<p>总产值：<b>"+row.totaloutput+"</b></p>";
                        detail+="<p>营运状态：<b>"+row.comstatus+"</b></p>";
                        detail+="<p>联系人姓名：<b>"+row.contactname+"</b></p>";
                        detail+="<p>联系人手机号：<b>"+row.contactmobile+"</b></p>";
                        detail+="<p>联系人邮箱：<b>"+row.contactemail+"</b></p>";

                        //赋值给页面ID为detail_Company的元素
                        $("#detail_Company").html(detail);
                    },
                    rowStyle: function(row,index){       //根据行中某列的值，动态渲染整行的文字颜色
                        if(row.employeenumber<60)
                        {
                            return {
                                css:{color:'blue'},//本行文字的颜色(自定义)
                                classes:'table-warning'//本行背景色（bootstrap样式）
                            };
                        }
                        else if (row.employeenumber>1000)//利用bootstrap的颜色体系
                        {
                            return {css:{background:'#DA9111'}};//本行背景色（自定义）
                        }
                        else//保持原来的颜色
                        {
                            return {};
                        }

                    },
                    queryParams: function(params){
                        //重写参数，这样方便后期增加查询条件
                        var param = {
                            size: params.limit,//页面大小
                            page: (params.offset / params.limit),//页码

                            //页面的查询条件
                            comname:$("#comname").val(),
                            employeenumber:$("#employeenumber").val(),
                            comstatus:$("#comstatus").val()
                        };
                        return param;
                    },
                    columns: [{
                        title: '编号',
                        formatter:function(value,row,index)
                        {
                            var pageSize=$('#tb_Company').bootstrapTable('getOptions').pageSize;
                            var pageNumber=$('#tb_Company').bootstrapTable('getOptions').pageNumber;
                            return pageSize * (pageNumber - 1) + index + 1;
                        },
                        align:'center',
                        width:55
                    },{
                        field: 'uuid',
                        title: 'UUID',
                        visible:false
                    },{
                        field: 'comname',
                        title: '公司名称'
                    }, {
                        field: 'comaddress',
                        title: '公司地址'
                    }, {
                        field: 'comurl',
                        title: '公司网址'
                    }, {
                        field: 'comtelephone',
                        title: '公司座机'
                    },{
                        field: 'establishdate',
                        title: '成立日期'
                    }, {
                        field: 'employeenumber',
                        title: '员工人数'

                    },{
                        field: 'totaloutput',
                        title: '总产值',
                        cellStyle:function(value,row,index){
                            if(value>500&&value<1000){
                                return {classes:'table-success'}
                            }
                            else{
                                return {};
                            }
                        }
                    },  {
                        field: 'comdesc',
                        title: '公司简介',
                        visible:false
                    }, {
                        field: 'comstatus',
                        title: '营运状态',
                        formatter: function indexFormatter(value, row, index){
                            var newvalue="";
                            if(value == "正常运营"){newvalue= '<span class="badge badge-success">'+value+'</span>';}
                            else if(value == "正在注销"){newvalue= '<span class="badge badge-warning">'+value+'</span>';}
                            else {newvalue= '<span class="badge badge-danger">'+value+'</span>';}
                            return newvalue;
                        }
                    },{
                        field: 'contactname',
                        title: '联系人姓名',
                        visible:false
                    },{
                        field: 'contactmobile',
                        title: '联系人手机号',
                        visible:false
                    },{
                        field: 'contactemail',
                        title: '联系人邮箱',
                        visible:false
                    },{
                        field: '',
                        title: '操 作',
                        events: cellEvents,
                        formatter:function(value,row,index){
                            return '<i class="modify fa fa-edit fa-lg text-warning" title="修改记录"></i>&nbsp;&nbsp;&nbsp;'+'<i class="delete fa fa-close fa-lg text-danger" title="删除记录"></i>'
                        }
                    }]
                });

                //多条件查询刷新
                $("#btnSearch").click(function(){
                    $("#tb_Company").bootstrapTable('refresh');
                });

                //弹出新增公司窗口
                $("#btn_add").on('click',function(){
                    layer.open({
                        type: 2,
                        skin: 'layui-layer-molv',
                        title: '新增公司',
                        shadeClose: true,
                        shade: 0.4,
                        maxmin: false,
                        area: ['800px', '800px'],
                        content: '/CompanyModule/addCompanyHtml',
                        end: function () {
                            $("#tb_Company").bootstrapTable('refresh');
                        }
                    });
                });

                //end 该处定义我们自己的脚本
            }
        )
    }
);
