layui.use(['upload','form','layer','jquery','layedit'], function() {
    var form = layui.form;
    var upload = layui.upload;
    var layer = layui.layer;
    var $ = layui.jquery;
    var layedit = layui.layedit;

    layedit.set({
        uploadImage: {
            url: path+'/comm/ImgUpload' //接口url
        }
    });
    var index = layedit.build('edt',{
        tool: [
            'strong' //加粗
            , 'italic' //斜体
            , 'underline' //下划线
            , 'del' //删除线
            , '|' //分割线
            , 'left' //左对齐
            , 'center' //居中对齐
            , 'right' //右对齐
            , '|' //分割线
            , 'link' //超链接
            , 'unlink' //清除链接
            , 'face' //表情
            , 'image' //插入图片

        ]
    });

    //获取案件信息
    $.ajax({
        url:path+"/notice/selectNotice",
        type:'post',
        contentType: "application/json",
        dataType:'json',
        success:function(data){
            var data = data.data;
            $("input[name=id]").val(data.id);
            $("input[name=title]").val(data.title);
            layedit.setContent(index,data.content);
            if(data.isTop==1) {
                $("input#radio2").removeAttr("checked");
                $("input#radio1").attr("checked", true);
            }else{
                $("input#radio1").removeAttr("checked");
                $("input#radio2").attr("checked",true);
            }
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