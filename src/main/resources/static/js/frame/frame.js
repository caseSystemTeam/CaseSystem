layui.use( ['jquery', 'layer'],
    function () {
       var layer =layui.layer,$ =layui.jquery;
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



    }
)