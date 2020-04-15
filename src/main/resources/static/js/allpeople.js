layui.use(['form', 'laydate', 'table', 'jquery', 'layer'], function () {
    var form = layui.form,
        laydate = layui.laydate,
        table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;



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
                        width: '14%',
                        templet: function (d) {
                            return "<div class='layui-elip cursor-p' title='" + d.name + "'>" + d.name + "</div>";
                        }
                    }, {
                    field: 'gender',
                    title: '员工性别',
                    width: "10%",

                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + d.gender + "'>" + d.gender + "</div>";
                    }
                }, {
                    field: 'position',
                    title: '员工职称',
                    width: '12%',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + d.position + "'>" + d.position + "</div>";
                    }
                }, {
                    field: 'phonenumber',
                    title: '联系方式',
                    width: '12%',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + d.phonenumber + "'>" + d.phonenumber + "</div>";
                    }
                },{
                    field: 'solve',
                    title: '解决案件数',
                    width: '10%'
                },{
                    field: 'unsolve',
                    title: '未解决案件数',
                    width: '10%',

                },  {
                    filed:'caozuo',
                    fixed: 'right',
                    title: '操作',
                    align:'center',
                    unresize:true,
                    toolbar: '#bianjikuang'
                }
                ]
            ],
            done: function (res, curr) {
                console.info("sssss",res);

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
    tableload();

    //监听工具条的操作
    table.on('tool(saleTable)', function (obj) {
        var data = obj.data; //获得当前行数据
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (obj.event === 'edit') {
            window.location.href = path + "/page/updateInfo?id=" + data.Id ;
        }

    });

});