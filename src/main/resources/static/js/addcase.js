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
                    $("#mainp").append(
                        '<option value="' + item.Id
                        + '">' + item.name+'('+item.position+')'
                        + '</option>');
                });
                form.render();
            }
        });

        form.on('submit(submitBut)',function (data) {
            data.field.content = layedit.getContent(index);

            $.ajax({
                url:path+"/case/addCase",
                data:JSON.stringify(data.field),
                dataType:'json',
                type:'post',
                contentType: "application/json",
                success:function (data) {
                    console.info("data",data);
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

        //二级菜单点击后的处理方法
        function menuCAClick(url,_this){

            //处理frameMain url地址
            $("#mainIframe").attr("src",url);

            //处理frameMain title名称变化
            if($(_this).find("i").attr("class") == "iconfont icon-yonghu1"){
                $("#frameMainTitle span").html('<i class="iconfont icon-xianshiqi"></i>个人资料');
                return;
            }
            if($(_this).text() == "修改密码"){
                $("#frameMainTitle span").html('<i class="iconfont icon-xianshiqi"></i>'+$(_this).text());
                return;
            }
            if($(_this).attr("class") == "menuFA"){
                $("#frameMainTitle span").html('<i class="iconfont icon-xianshiqi"></i>'+$(_this).text());
            }else{
                //显示父菜单
//		$("#frameMainTitle span").html('<i class="iconfont icon-xianshiqi"></i>'+$(_this).parent().parent().siblings(".menuFA").text());
                //显示子菜单
                $("#frameMainTitle span").html('<i class="iconfont icon-xianshiqi"></i>'+$(_this).text());
            }

            //处理菜单样式变化
            $(_this).css("cssText", "background-color:#fbcc19 !important;").css("color","#FFF");
            $(_this).parent().siblings().find("a").css("cssText", "background-color:#transparent").css("color","#c2c2c2");
            $(_this).parent().parent().parent().siblings().find("dl dt a").css("cssText", "background-color:#transparent").css("color","#c2c2c2")

        }


    }
)