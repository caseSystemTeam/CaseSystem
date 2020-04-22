layui.use(['upload','form','layer'], function() {
    var form = layui.form;
    var upload = layui.upload;
    var layer = layui.layer;
    var n=1,i=1,t=1,s=1;
    //监听提交
    form.on('submit(submitBut)', function(data) {

        if(i!=0 &&t!=0 && n!=0 &&s!=0){
            $.ajax({
                url:path+"/userCon/addUser",
                data:JSON.stringify(data.field),
                type:'post',
                contentType: "application/json",
                dataType:'json',
                success:function(data){

                    if(data==1){
                        layer.msg("添加成功");
                        setTimeout(function(){
                            location.href = path+"/page/addpeople";
                        },1000);
                    }else {
                        layer.msg("添加失败")
                    }
                }
            })
        }else{
            layer.msg('添加失败');
        }

        return false;
    });
    $('#user').blur(function() {
        var username = $(this).val();
        $.ajax({
            url:path+"/userCon/checkName",
            type:'post',
            data:{username:username},
            // contentType:"application/json;charset=UTF-8",
            dataType:'text',
            success:function(data){

                if (data == 0) {
                    $('#ri').removeAttr('hidden');
                    $('#wr').attr('hidden','hidden');
                    n=1;
                } else {
                    $('#wr').removeAttr('hidden');
                    $('#ri').attr('hidden','hidden');
                    n=0;
                }

            }

        })
    });

    $('#pwd').blur(function() {
        var reg = /^[\w]{6,12}$/;
        if(!($('#pwd').val().match(reg))){
            //layer.msg('请输入合法密码');
            $('#pwr').removeAttr('hidden');
            $('#pri').attr('hidden','hidden');

            i=0;
        }else {
            $('#pri').removeAttr('hidden');
            $('#pwr').attr('hidden','hidden');
            i=1;
        }
    });

    //验证两次密码是否一致
    $('#rpwd').blur(function() {
        if($('#pwd').val() != $('#rpwd').val()){
            $('#rpwr').removeAttr('hidden');
            $('#rpri').attr('hidden','hidden');

            t=0;
        }else {
            $('#rpri').removeAttr('hidden');
            $('#rpwr').attr('hidden','hidden');
            t=1;
        };
    });
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