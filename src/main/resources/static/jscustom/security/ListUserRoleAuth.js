/*
* 功能：角色权限综合页面对应的脚本
* 作者：江成军
* 时间：2020-01-02 10:21:11
* */
require(
    ['/jscustom/GlobleConfig.js'],
    function () {
        requirejs(
            ['jquery','bootstrap','custom','bootstrap_table','bootstrap_table_CN','layer','ztree','jqueryform'],
            function ($) {
                //start 该处定义我们自己的脚本

                //弹出新增账号窗口
                $("#btn_addUser").bind('click',function(){
                    layer.open({
                        type: 2,
                        skin: 'layui-layer-molv',
                        title: '新增账号',
                        shadeClose: true,
                        shade: 0.4,
                        maxmin: false,
                        area: ['800px', '380px'],
                        content: '/security/ridirectAddSysUserHtml',
                        end: function () {
                            $("#tb_SysUser").bootstrapTable('refresh');
                        }
                    });
                });

                //弹出新增角色层
                $("#btn_addRole").bind('click',function(){
                    layer.open({
                        type: 1,
                        title: '新增角色',
                        shadeClose: true,
                        shade: 0.2,
                        maxmin: false,
                        skin: 'layui-layer-molv', //加上边框
                        area: ['400px', '280px'], //宽高
                        content: $('#addrolediv')
                    });

                });

                //保存新增角色
                $("#btn_SaveRole").bind('click',function(){
                    var newRoleName=$("#rolename").val();

                    //遍历页面上所有radio，得到已有的角色名称，并与输入项进行比较，判断是否重复
                    var items = $("input[name='radiorole']");
                    for(var i=0,n=items.length;i<n;i++)
                    {
                        var tempName=items[i].nextSibling.nodeValue;//获取每个radio的显示文本（不是value）,注意获取的这个值是带空格的，用trim()去除头尾空格
                        if(tempName.trim()==newRoleName)
                        {
                            alert("角色名称重复，请重新输入!");
                            return false;
                        }
                    }

                    //保存数据到数据库
                    var options = {
                        complete:function(data){
                            layer.alert("新增角色【"+newRoleName+"】信息成功！", {
                                icon: 1,
                                closeBtn: 0,
                            },function(){ window.location.reload();});
                        },
                        url:'/security/saveRole',
                        dataType:'json',
                        resetForm: true,  // 成功提交后，重置所有的表单元素的值
                        timeout: 5000
                    };
                    $('#FormSysRole').ajaxSubmit(options);
                });

                //删除角色
                $("#btn_deleteRole").bind('click',function(){
                    var seluuid=$('input:radio[name="radiorole"]:checked').val();
                    layer.confirm('是否要删除选定的角色？',{
                        icon: 0,
                        closeBtn:0,
                        btn:['取 消','确 定']
                    },function(){
                        layer.closeAll();
                    },function(){
                        $.ajax({
                            url:'/security/deleteRole',
                            type:'post',
                            data:{uuid:seluuid},
                            async:true,//true为异步，false为同步
                            complete:function(){
                                window.location.reload();
                            }
                        });
                    });

                });

                //弹出新增一级权限层，父节点的id设置为0
                $("#btn_addMainAuth").bind('click',function(){
                    $("#nowid").val(0);
                    layer.open({
                        type: 1,
                        title: '新增一级权限节点',
                        shadeClose: true,
                        shade: 0.2,
                        maxmin: false,
                        skin: 'layui-layer-molv', //加上边框
                        area: ['400px', '190px'], //宽高
                        content: $('#addauthdiv')
                    });

                });

                //弹出新增子权限层
                $("#btn_addAuth").bind('click',function(){

                    var authname=$("#nowname").val();
                    if(authname=="无")
                    {
                        alert("请先点击相应的节点，再进行操作！");
                        return;
                    }

                    layer.open({
                        type: 1,
                        title: '增加【'+authname+'】的子节点',
                        shadeClose: true,
                        shade: 0.2,
                        maxmin: false,
                        skin: 'layui-layer-molv', //加上边框
                        area: ['400px', '190px'], //宽高
                        content: $('#addauthdiv')
                    });

                });


                //保存添加的权限
                $("#btn_SaveAuth").bind('click',function(){
                    var newAuthName=$("#authname").val();
                    var nowid=$("#nowid").val();

                    //根据新增加的子节点名称和当前节点的id，判断子节点名称是否重复,如果不重复则保存数据
                    $.ajax({
                        url:'/security/saveChildAuth',
                        type:'post',
                        data:{"id":nowid,"name":newAuthName},
                        async:true,//true为异步，false为同步
                        success:function(data){
                            var msg=data.msg;
                            if(msg=="exist"){alert("节点名称已存在，请重新添加！")}
                            else{alert("节点添加成功！");}

                            $("#nowid").val("无");
                            $("#nowpid").val("无");
                            $("#nowname").val("无");

                            window.location.reload();
                        }

                    });

                });

                //删除当前节点（如果有子节点则一并删除）
                $("#btn_deleteAuth").click(function(){
                    var authname=$("#nowname").val();
                    var id=$("#nowid").val();
                    if(authname=="无")
                    {
                        alert("请先点击相应的节点，再进行操作！");
                        return;
                    }

                    layer.confirm('是否删除节点【'+authname+'】？',{
                        icon: 0,
                        btn:['取 消','确 定']
                    },function(index){
                        parent.layer.close(index);
                    },function(index){
                        $.ajax({
                            url:'/security/deleteByChild',
                            type:'post',
                            data:{id:id},
                            async:true,//true为异步，false为同步
                            success:function(){
                                $("#nowid").val("无");
                                $("#nowpid").val("无");
                                $("#nowname").val("无");
                                window.location.reload();
                            }

                        });
                    });

                });

                //保存相应角色勾选的权限
                $("#btn_assignRole").bind('click',function(){
                    var seluuid=$('input:radio[name="radiorole"]:checked').val();
                    if(seluuid==null)
                    {
                        alert("请先选中对应的角色");
                        return;
                    }

                    var arrAuths=new Array()//存放勾选的所有权限id的数组
                    var zTreeObj = $.fn.zTree.getZTreeObj("AuthTree");
                    var checkedNodes = zTreeObj.getCheckedNodes();
                    if(checkedNodes.length<1)
                    {
                        alert("还未选择权限！");
                        return;
                    }

                    for(var j=0,len=checkedNodes.length;j<len;j++)
                    {
                        arrAuths[j]=checkedNodes[j].id;
                    }

                    var strAuths=arrAuths.join('$');//数组转换成以'$'分割的字符串

                    //把角色对应的所有权限映射进行保存
                    $.ajax({
                        url:'/security/editRole',
                        type:'post',
                        data:{uuid:seluuid,authinfo:strAuths},
                        async:true,//true为异步，false为同步
                        success:function(){
                            alert("角色对应的权限分配成功！");
                        }

                    });

                });

                //角色勾选状态变化后，重新加载权限树
                $('input:radio[name="radiorole"]').change( function(){
                    showAuthTree();
                })

                //绑定列表中各按钮的事件
                window.operateEvents={
                    'click .delete':function(e,value,row,index) {
                        //删除记录
                        layer.confirm('是否删除记录？',{
                            icon: 0,
                            btn:['取 消','确 定']
                        },function(){
                            layer.closeAll();
                        },function(){
                            $.ajax({
                                url:'/company/deleteByUuid',
                                type:'post',
                                data:{uuid:row.uuid},
                                async:true,//true为异步，false为同步
                                complete:function(){
                                    $("#tb_Company").bootstrapTable('refresh');
                                }

                            });
                        });

                    },
                    'click .modify':function(e,value,row,index){
                        layer.alert("当前行的下标:"+row.username, {icon: 2});
                    }
                };

                //数据列表展示
                $('#tb_SysUser').bootstrapTable({
                    url: '/security/listuser',         //请求后台的URL（*）
                    method: 'post',                      //请求方式（*）post/get
                    //striped: true,                      //是否显示行间隔色
                    classes:"table table-bordered table-hover",//启用bootstrap的表格样式
                    theadClasses:"thead-light",         //表头的背景色
                    //toolbar: '#toolbar',                //工具按钮用哪个容器
                    cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,                   //是否显示分页（*）
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber:1,                       //初始化加载第一页，默认第一页
                    pageSize: 8,                       //每页的记录行数（*）
                    pageList: [5,8,12],                 //可供选择的每页的行数（*）
                    search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch: true,                  //默认为false，设置为 true启用 全匹配搜索，否则为模糊搜索
                    showColumns: false,                  //是否显示所有的列
                    showRefresh: false,                  //是否显示刷新按钮
                    minimumCountColumns: 2,             //最少允许的列数
                    clickToSelect: true,                //是否启用点击选中行
                    height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数设置表格高度
                    uniqueId: "uuid",                     //每一行的唯一标识，一般为主键列
                    showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
                    cardView: false,                    //是否显示详细视图
                    detailView: false,                   //是否显示父子表
                    onClickRow:function (row,$element) {
                        $('.info').removeClass('info');
                        $($element).addClass('info');//点击改变颜色
                    },
                    queryParams : function (params) {
                        //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
                        var temp = {
                            size: params.limit,//页面大小
                            page: (params.offset / params.limit)//页码
                        };
                        return temp;
                    },
                    columns: [{
                        title: '编号',
                        formatter:function(value,row,index)
                        {
                            var pageSize=$('#tb_SysUser').bootstrapTable('getOptions').pageSize;
                            var pageNumber=$('#tb_SysUser').bootstrapTable('getOptions').pageNumber;
                            return pageSize * (pageNumber - 1) + index + 1;
                        },
                        align:'center',
                        width:'50'
                    },{
                        field: 'uuid',
                        title: 'UUID'

                    },{
                        field: 'username',
                        title: '账号名称'
                    }, {
                        field: 'useremail',
                        title: '邮箱'
                    }, {
                        field: 'usermobile',
                        title: '手机'
                    }, {
                        field: 'sysrolename',
                        title: '角色'
                    }, {
                        field: 'sysroleid',
                        title: '角色uuid',
                        visible:false

                    },{
                        field:'',
                        title:'操 作',
                        width:'160',
                        events:operateEvents,
                        formatter:function (value, row, index){
                            var btnInfo='';
                            btnInfo+='<button style="margin-right: 10px;padding: 2px" type="button" class="delete btn btn-outline btn-danger btn-sm">删 除</button>';
                            btnInfo+='<button style="margin-right: 10px;padding: 2px" type="button" class="modify btn btn-outline btn-warning btn-sm">修 改</button>';
                            return btnInfo;
                        }
                    }]
                });

                //******加载权限树 Start******
                function showAuthTree(){
                    //清空系统权限模块页面内容
                    $("#AuthTree").html("");

                    //获取对应的角色
                    var seluuid=$('input:radio[name="radiorole"]:checked').val();
                    if(seluuid==null)//如果没有选中任何角色，则赋一个默认值
                    {
                        seluuid="nouuid";
                    }

                    //ztree参数设置
                    var setting = {
                        check: {
                            enable: true,
                            chkboxType: {"Y":"p","N":"ps"}
                        },
                        data: {
                            simpleData: {
                                enable: true
                            }
                        },
                        callback:{
                            onClick: onClick
                        }
                    };

                    //节点单击事件，获取节点的相关信息，并赋值给统一的隐藏表单
                    function onClick(event, treeId, treeNode, clickFlag)
                    {
                        $("#nowid").val(treeNode.id);
                        $("#nowpid").val(treeNode.pId);//当前节点为根节点时，该值为空
                        $("#nowname").val(treeNode.name);
                    }

                    //加载权限明细树
                    $.ajax({
                        url:'/security/listauth',
                        type:'post',
                        datatype:'json',
                        data:{roleid:seluuid},
                        async:true,//true为异步，false为同步
                        success:function(auths){
                            //alert(JSON.stringify(auths));
                            $.fn.zTree.init($("#AuthTree"), setting, auths);
                        }

                    });
                }

                showAuthTree();
                //******加载权限树 End******

                //end 该处定义我们自己的脚本
            }
        )
    }
);