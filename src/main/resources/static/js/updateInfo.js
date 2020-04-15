layui.use(['upload','form','layer','jquery'], function() {
    var form = layui.form;
    var upload = layui.upload;
    var layer = layui.layer;
    var $ = layui.jquery;
    var s=1;
    //监听提交
    form.on('submit(submitBut)', function(data) {

        if(i!=0 &&t!=0 && n!=0 &&s!=0){
            $.ajax({
                url:path+"/userCon/updateUser",
                data:JSON.stringify(data.field),
                type:'post',
                contentType: "application/json",
                dataType:'json',
                success:function(data){
                    console.info("data",data);
                    if(data==1){
                        layer.msg("修改成功");
                        setTimeout(function(){
                            location.href = path+"/page/allpeople";
                        },1000);
                    }else {
                        layer.msg("修改失败")
                    }
                }
            })
        }else{
            layer.msg('修改失败');
        }

        return false;
    });
    //获取员工信息
    $.ajax({
        url:path+"/userCon/getUserById",
        type:'post',
        contentType: "application/json",
        dataType:'json',
        success:function(data){
           var user =data.data.result;
            $("input[name=username]").val(user.username);
            $("input[name=name]").val(user.name);
            if(user.gender=="男") {
                $("#radio2").removeAttr("checked");
                $("#radio1").attr("checked", true);
            }else{
                $("#radio1").removeAttr("checked");
                $("#radio2").attr("checked",true);
            }
            $("#position").val(user.position);
            $("input[name=phonenumber]").val(user.phonenumber);
            form.render(); //更新全部
        }
    })

    // $('#pwd').blur(function() {
    //     var reg = /^[\w]{6,12}$/;
    //     if(!($('#pwd').val().match(reg))){
    //         //layer.msg('请输入合法密码');
    //         $('#pwr').removeAttr('hidden');
    //         $('#pri').attr('hidden','hidden');
    //
    //         i=0;
    //     }else {
    //         $('#pri').removeAttr('hidden');
    //         $('#pwr').attr('hidden','hidden');
    //         i=1;
    //     }
    // });
    //
    // //验证两次密码是否一致
    // $('#rpwd').blur(function() {
    //     if($('#pwd').val() != $('#rpwd').val()){
    //         $('#rpwr').removeAttr('hidden');
    //         $('#rpri').attr('hidden','hidden');
    //
    //         t=0;
    //     }else {
    //         $('#rpri').removeAttr('hidden');
    //         $('#rpwr').attr('hidden','hidden');
    //         t=1;
    //     };
    // });
    //手机号是否合法
    $('#pnumber').blur(function() {
        if(!isMobileNumber($('#pnumber').val())){
            $('#rpwrp').removeAttr('hidden');
            $('#rprip').attr('hidden','hidden');

            s=0;
        }else {
            $('#rprip').removeAttr('hidden');
            $('#rpwrp').attr('hidden','hidden');
            s=1;
        };
    });

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


});