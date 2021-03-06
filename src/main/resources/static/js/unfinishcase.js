layui.use(['form', 'laydate', 'table', 'jquery', 'layer'], function () {
    var form = layui.form,
        laydate = layui.laydate,
        table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;


    //监听提交
    form.on('submit(submit)', function (data) {

        data.field.pstatus=0;
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

    var newDate = new Date();
    var nowTime = newDate.getFullYear() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getDate();
    laydate.render({
        elem: '#time',
        range: true
//					    	,type: 'datetime'	//开启时分秒
        , format: 'yyyy-MM-dd'
        , max: nowTime	//min/max - 最小/大范围内的日期时间值
    });




    tableload = function () {
        var datas={pstatus:0}
        table.render({
            elem: '#saleTable',
            id: 'saleTable',
            height: 465,
            url: path + "/caseList/getCaseById",
            method: 'post',
            page:true,
            limit: 10,
            text: {
                none: '暂无相关数据' //默认：暂无相关数据。注：该属性为 layui 2.2.5 开始新增
            },
            where:{
                key: JSON.stringify(datas)
            },
            cols: [
                [ //标题栏

                    {
                        field: 'name',
                        title: '案件名称',
                        templet: function (d) {
                            return "<div class='layui-elip cursor-p' title='" + d.name + "'>" + d.name + "</div>";
                        }
                    }, {
                    field: 'money',
                    title: '拟定金额',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + (d.money != undefined ? d.money : "") + "'>" + (d.money != undefined ? d.money : "") + "</div>";
                    }
                }, {
                    field: 'cusname',
                    title: '申述人姓名',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + d.cusname + "'>" + d.cusname + "</div>";
                    }
                }, {
                    field: 'cus_telphone',
                    title: '联系电话',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + (d.cus_telphone != undefined ? d.cus_telphone : "") + "'>" + (d.cus_telphone != undefined ? d.cus_telphone : "") + "</div>";
                    }
                },{
                    field: 'lawername',
                    title: '责任律师',
                },{
                    field: 'p_status',
                    title: '案件状态',
                    templet: function (d) {
                        return "<div class='layui-elip cursor-p' title='" + d.p_status + "'>" + (d.p_status != 0 ? "已结束" : "正在进行") + "</div>";
                    }
                }, {
                    field: 'rtime',
                    title: '登记时间',

                }, {
                    field: 'cname',
                    title: '登记人名称',

                },{
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
            //跳转案件详情页面，id为当前案件id
            window.location.href = path + "/page/tocase?caseId=" + data.Id+"&lawerid="+data.lawerid;
        }
        if (obj.event === 'create') {
            window.location.href = path + "/case/generateWord?caseId=" + data.Id;
        }

    });

});