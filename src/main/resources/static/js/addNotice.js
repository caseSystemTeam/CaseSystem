layui.use(['jquery','layer','form','layedit','upload'],
    function () {
        var layer =layui.layer,$ =layui.jquery,form=layui.form,layedit=layui.layedit;


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


        form.on('submit(submitBut)',function (data) {

            data.field.content = layedit.getContent(index);
            var text=layedit.getText(index);
            if(text==null ||text==""){
                layer.msg("公告内容不能为空");
                return false;
            }
            $.ajax({
                url:path+"/notice/addNotice",
                data:JSON.stringify(data.field),
                dataType:'json',
                type:'post',
                contentType: "application/json",
                success:function (data) {

                    if (data.status == 200){
                        layer.msg(data.info);
                        setTimeout(function(){
                            location.href = path+"/page/addNotice";
                            // menuCAClick("/page/unfinishcase",this)
                        },1000);

                    }else{
                        layer.msg(data.info);
                    }
                }
            })

            return false;
        })




    }
)