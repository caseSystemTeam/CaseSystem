layui.use( ['jquery', 'layer'],
    function () {
       var layer =layui.layer,$ =layui.jquery;
       var lawername = getCookie("lawername")
       $("#username").html("<i class='iconfont icon-yonghu1'></i>"+lawername);
    }
)