layui.use(['jquery','layer','form','layedit'],
    function () {
        var layer =layui.layer,$ =layui.jquery,form=layui.form,layedit=layui.layedit;
        //插入图片接口配置，要求接口返回的格式JSON如下
        /*{
          "code": 0 //0表示成功，其它失败
          ,"msg": "" //提示信息 //一般上传失败后返回
          ,"data": {
            "src": "图片路径"
            ,"title": "图片名称" //可选
          }
        }
        layedit.set({
            uploadImage: {
                url: '' //接口url
            }
        });*/
        var index = layedit.build('editor',{
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
            /*    , 'link' //超链接
                , 'unlink' //清除链接
                , 'face' //表情
                , 'image' //插入图片*/

            ]
        });
        //获取当前公司所有律师信息
        $.ajax({
            url: path + "/userCon/getAllLawer",
            type: "POST",
            success: function (data) {

                $.each(data.data.data, function (i, item) {
                    if(item.role_id != 4){
                        $("#mainp").append(
                            '<option value="' + item.Id
                            + '">' + item.name+'('+item.position+')'
                            + '</option>');
                    }
                });
                form.render();
            }
        });

        form.on('submit(submitBut)',function (data) {

            data.field.content = layedit.getContent(index);
            console.info("data",data)
            $.ajax({
                url:path+"/case/addCase",
                data:JSON.stringify(data.field),
                dataType:'json',
                type:'post',
                contentType: "application/json",
                success:function (data) {

                    if (data.status == 200){
                        layer.msg("添加成功");
                        setTimeout(function(){
                            location.href = path+"/page/addcase";
                            // menuCAClick("/page/unfinishcase",this)
                        },1000);

                    }else{
                        layer.msg('添加失败');
                    }
                }
            })

            return false;
        })




    }
)