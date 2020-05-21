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
            , 'link' //超链接
            , 'unlink' //清除链接
            , 'face' //表情
            , 'image' //插入图片

        ]
    });

    //获取案件信息
    $.ajax({
        url:path+"/caseList/selectCase",
        type:'post',
        contentType: "application/json",
        dataType:'json',
        success:function(data){
            var data = data.data;

            $("input[name=Id]").val(data.Id);
            $("input[name=name]").val(data.name);
            $("input[name=money]").val(data.money);
            $("input[name=cusname]").val(data.cusname);
            layedit.setContent(index,data.content);
            $("input[name=cus_telphone]").val(data.cus_telphone);
            form.render(); //更新全部


        }
    })
    var s=1;
    //手机号是否合法
    $('input[name=cus_telphone]').blur(function() {
        $("input[name=cus_telphone]").val($(this).val())
        if(!isMobileNumber($('input[name=cus_telphone]').val())){
            $('i#rpwrp').removeAttr('hidden');
            $('i#rprip').attr('hidden','hidden');
            s=0;
        }else {
            $('i#rprip').removeAttr('hidden');
            $('i#rpwrp').attr('hidden','hidden');
            s=1;
        };
    });
    form.on('submit(submitBut)',function (data) {

        data.field.content = layedit.getContent(index);
        var text=layedit.getText(index);
        if(text==null ||text==""){
            layer.msg("案件描述不能为空");
            return false;
        }
        if(s==0){
            layer.msg("请输入正确的手机号");
            return false;
        }
        $.ajax({
            url:path+"/caseList/updateCase",
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


    //判断手机号是否合法
    function isMobileNumber(phone) {
        var flag = false;
        var message = "";
        var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(15[0-3]{1})|(15[4-9]{1})|(18[0-9]{1})|(199))+\d{8})$/;
        if (phone == '') {
            // console.log("手机号码不能为空");
            message = "手机号码不能为空！";
        } else if (phone.length != 11) {
            //console.log("请输入11位手机号码！");
            message = "请输入11位手机号码！";
        } else if (!myreg.test(phone)) {
            //console.log("请输入有效的手机号码！");
            message = "请输入有效的手机号码！";
        } else {
            flag = true;
        }
        if (message != "") {
            // alert(message);
        }
        return flag;
    }
})