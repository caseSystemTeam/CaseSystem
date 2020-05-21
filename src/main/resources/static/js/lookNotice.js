layui.use(['upload','form','layer','jquery','layedit'], function() {
    var form = layui.form;
    var upload = layui.upload;
    var layer = layui.layer;
    var $ = layui.jquery;
    var layedit = layui.layedit;


    //获取案件信息
    $.ajax({
        url:path+"/notice/selectNotice",
        type:'post',
        contentType: "application/json",
        dataType:'json',
        success:function(data){
            var data = data.data;
            $("span[name=title]").html(data.title);
            $("#name").html(data.name);
            $("#scount").html(data.scount);
            $("#create_time").html(data.create_time);
            $("#content").html(data.content);
            form.render(); //更新全部
        }
    })

    form.on('submit(submitBut)',function (data) {

        data.field.content = layedit.getContent(index);
        var text=layedit.getText(index);
        if(text==null ||text==""){
            layer.msg("公告内容不能为空");
            return false;
        }
        $.ajax({
            url:path+"/notice/updateNotice",
            data:JSON.stringify(data.field),
            dataType:'json',
            type:'post',
            contentType: "application/json",
            success:function (data) {
                if (data.status == 200) {
                    layer.msg("修改成功");
                    setTimeout(function() {
                        window.location.reload();
                    },1000);
                }else{
                    layer.msg("修改失败");
                }
            }
        })
        return false;
    })



})