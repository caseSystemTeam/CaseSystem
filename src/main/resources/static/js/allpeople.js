layui.use(['form', 'laydate', 'table', 'jquery', 'layer'], function () {
    var form = layui.form,
        laydate = layui.laydate,
        table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;

      console.info("sss","ddddd");

    //监听提交
    form.on('submit(submit)', function (data) {

        table.reload('saleTable', {
            page: {
                curr: 1 //重新从第 1 页开始
            }
            ,where: {
                key: JSON.stringify(data.field)
            }
        });
        return false;
    });


    tableload = function () {
        table.render({
            elem: '#saleTable',
            id: 'saleTable',
            height: 465,
            url: path + "/userCon/getBusinessUser",
            method: 'post',
            page:true,
            limit: 10,
            text: {
                none: '暂无相关数据' //默认：暂无相关数据。注：该属性为 layui 2.2.5 开始新增
            },
            cols: [
                [ //标题栏
                    {
                        field: 'name',
                        title: '员工姓名',
                        templet: function (d) {
                            return "<div class='layui-elip cursor-p' title='" + d.name + "'>" + d.name + "</div>";
                        }
                    }, {
                    field: 'username',
                    title: '用户名',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + d.username + "'>" + d.username + "</div>";
                    }
                }, {
                    field: 'gender',
                    title: '员工性别',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + d.gender + "'>" + d.gender + "</div>";
                    }
                }, {
                    field: 'position',
                    title: '员工职称',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + d.position + "'>" + d.position + "</div>";
                    }
                }, {
                    field: 'role',
                    title: '角色',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + d.role + "'>" + d.role + "</div>";
                    }
                },  {
                    field: 'phonenumber',
                    title: '联系方式',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + d.phonenumber + "'>" + d.phonenumber + "</div>";
                    }
                },{
                    field: 'solve',
                    title: '解决案件数',
                },{
                    field: 'unsolve',
                    title: '未解决案件数',

                },  {
                    filed:'caozuo',
                    fixed: 'right',
                    title: '操作',
                    align:'center',
                    width:"14%",
                    unresize:true,
                    toolbar: '#bianjikuang'
                }
                ]
            ],
            done: function (res, curr) {


                $(".layui-table-fixed-r .layui-table-body").css({
                    'overflow': 'hidden'
                })
                //获取分页下拉选改变事件
                $(".layui-laypage-limits").find('select').change(function () {
                    lock = false;
                    $(".layui-table-fixed-r .layui-table-body").css({
                        'overflow': 'hidden'
                    })					//console.log($(".layui-laypage-limits").find('select option:selected').val());
                })

                $(".more-div").hover(function (e) {
                    var aaa = document.documentElement.clientHeight;
                    var len = $(this).children('ul').children().length;
                    if (len == 2) {
                        $(this).children('ul').css('bottom', '-49px');
                    } else if (len == 1) {
                        $(this).children('ul').css('bottom', '-29px');
                    } else if (len == 3) {
                        $(this).children('ul').css('bottom', '-69px');
                    }else{
                        $(this).children('ul').css('bottom', '-89px');
                    }
                    var screen = window.screen.height;
                    var width = 500;
                    if (screen < 800) {
                        width = 200;
                    }else if( screen <= 900 && screen >= 800){
                        width = 400;
                    }
                    if ($(this).offset().top - $(".layui-table-fixed-r").offset().top > width) {
                        $(this).children('ul').addClass("bottom").removeClass("top");
                        $(this).children('ul').css('bottom', '29px');
                    }
                    var scroll = $(".layui-table-fixed-r").css("right").replace("px", "");
                    if (scroll < 0) {
                        $(".layui-table-fixed-r .layui-table-body").css({
                            'overflow': 'visible'
                        })
                    }else{
                        $(".layui-table-fixed-r .layui-table-body").css({
                            'overflow': 'hidden'
                        })
                    }
                    $(this).children('ul').show();
                }, function () {
                    $(this).children('ul').css('bottom', '-89px');
                    $(this).children('ul').hide();
                });
                $(".more-div ul").hover(function () {
                    $(this).show();
                })

            }
        });
    }
    $.ajax({
        url:path+"/role/rolelist",
        type:'post',
        contentType: "application/json",
        dataType:'json',
        success:function(data){
            console.info("role",data);
            $.each(data.data.roleList, function (i, item) {
                $("#urole").append(
                    '<option value="' + item.roleId
                    + '">' + item.name
                    + '</option>');
            });
            form.render(); //更新全部
        }
    });

    var roleId=null;
    form.on("select(urole)",function (data) {
        roleId = data.value;
        form.render("select");
    })
    tableload();
    //获取角色列表

    //监听工具条的操作
    table.on('tool(saleTable)', function (obj) {
        var data = obj.data; //获得当前行数据
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (obj.event === 'edit') {
            window.location.href = path + "/page/updateInfo?id=" + data.Id ;
        }else if(obj.event=='look'){
            layer.open({
                type: 1,
                title: '用户信息',   //标题
                area: ['700px', '500px'],   //宽高
                content:$("#lookpeople").html(),
                success:function(layero,index){
                    $("span[name=username]").text(data.username);
                    $("span[name=zname]").text(data.name);
                    $("span[name=gender]").text(data.gender);
                    $("span[name=ption]").text(data.position);
                    $("span[name=srole]").text(data.role);
                    $("span[name=solve]").text(data.solve);
                    $("span[name=unsolve]").text(data.unsolve);
                    $("span[name=phonenumber]").text(data.phonenumber);
                }
            });
            form.render();
        } else if(obj.event=='delete'){
            layer.open({
                type: 1,
                title: '提示',
                offset: 'auto',
                btnAlign: 'c',
                area: ['420px', '220px'],
                offset: 'auto',
                content: "<div style='text-align:center;padding:42px 0 26px 0;'><span class='layui-badge'>!</span>" + "   " + "确定删除该用户吗？</div><hr class='layui-bg-gray' style='margin:29px 0 0'>",
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    var json={id:data.Id};

                    $.ajax({
                        url: path + "/userCon/deleteUser",
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(json),
                        success: function (data) {
                            if (data.status == 200) {

                                layer.msg("删除成功");
                                setTimeout(function() {
                                    window.location.reload();
                                },1000);
                            }else{

                                if(data.info=="未完成"){
                                    layer.msg("该用户还有未完成案件，无法删除");
                                }else{
                                    layer.msg("删除失败");
                                }
                            }
                        }
                    });
                    layer.close(index);

                },
                success: function (index, layero) {
                    $(':focus').blur();
                },
                no: function (index, layero) {

                }
            })
        }else if(obj.event=='role'){

            layer.open({
                type: 1,
                title: '分配角色',   //标题
                area: ['390px', '200px'],   //宽高
                content: $("#rolediv").html(),
                btn: ['确定', '取消'], //按钮组
                yes: function(index){//layer.msg('yes');    //点击确定回调

                  var json ={userId:data.Id,roleId:roleId};
                  if(roleId!=null){
                      $.ajax({
                          url: path + "/role/updateRole",
                          type: "POST",
                          contentType: "application/json",
                          data: JSON.stringify(json),
                          success: function (data) {
                              if (data.status == 200) {
                                  layer.msg(data.info);
                                  setTimeout(function() {
                                      window.location.reload();
                                  },1000);
                              }else{
                                  layer.msg(data.info);
                              }
                          }
                      });
                  }else{
                      layer.msg("分配失败");
                  }

                    layer.close(index);
                },

            });
            form.render();
        }

    });

});