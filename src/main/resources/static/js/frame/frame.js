layui.use( ['jquery', 'layer','form'],
    function () {
       var layer =layui.layer,$ =layui.jquery,form=layui.form;
       /*var lawername = getCookie("lawername")
       $("#username").html("<i class='iconfont icon-yonghu1'></i>"+lawername);*/
        $.ajax({
            url: path + "/userCon/menu",
            type: "POST",
            success: function (data) {

                $.each(data, function (i, item) {
                    var t=0;
                    if(item.parentId==0 || item.parentId==null){
                        var html ="" ;
                        for(var j=0;j<data.length;j++){
                            if(data[j].parentId == item.permissionId){
                                t=1;
                                html =html+"<dt><a href=\"javascript:void(0)\" onclick=\"menuCAClick('"+data[j].url+"',this)\">"+data[j].name+"</a></dt>"
                            }
                        }
                        if(t==0){
                            html ="<li ><a class=\"menuFA\" href=\"javascript:void(0)\" onclick=\"menuCAClick('"+item.url+"',this)\"><i class=\"  left\"></i>"+item.name+"</a><dl>" ;
                        }
                        else{
                            html="<li><a class=\"menuFA\" href=\"javascript:void(0)\"><i class=\"  left\"></i>"+item.name+"<i class=\"iconfont icon-dajiantouyou right\"></i></a><dl>"+html;
                        }
                        html=html+ "</dl></li>"
                        $("#ulmenu").append(html);
                    }
                });

            }
        });
        var time = window.setInterval(function(){
            $.ajax({
                url:path+"/notice/selectMsg",
                type:'post',
                dataType:'json',
                success:function(data){
                    if(data.status == 200){
                        var data = data.data;
                        layer.open({
                            type: 1,
                            title: '公告提醒',
                            offset: 'auto',
                            btnAlign: 'c',
                            area: ['420px', '220px'],
                            offset: 'auto',
                            content: "<div style='text-align:center;padding:42px 0 26px 0;'>" +data.descript + "</div><hr class='layui-bg-gray' style='margin:29px 0 0'>",
                            btn: ['我知道了'],
                            yes: function (index, layero) {

                                layer.close(index);

                            },
                            success: function (index, layero) {
                                $(':focus').blur();
                            },
                            no: function (index, layero) {

                            }
                        })
                        form.render();
                    }


                }
            })
        },3000);




    }
)