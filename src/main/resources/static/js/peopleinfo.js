layui.use(['jquery','layer','form'],
    function () {
        var layer = layui.layer, $ = layui.jquery,form=layui.form;
        $.ajax({
            url:path+"/userCon/getUserInfo",
            dataType:'json',
            type:'post',
            contentType: "application/json",
            success:function (data) {
                console.info("data",data);
                if (data.status == 200){
                    var bus = data.data.business;
                    var user =data.data.user;
                  var html=' <form id="iform" class="layui-form" action=""><div class="layui-form-item">\n' +
                      '    <label class="layui-form-label laywidth">姓名:</label>\n' +
                      '    <label class="layui-form-label laywidth">'+user.name+'</label>\n' +
                      '</div>\n' +
                      '<div class="layui-form-item">\n' +
                      '    <label class="layui-form-label laywidth">性别:</label>\n' +
                      '    <label class="layui-form-label laywidth">'+user.gender+'</label>\n' +
                      '</div>\n' +
                      '\n' +
                      '<div class="layui-form-item">\n' +
                      '    <label class="layui-form-label laywidth">律所:</label>\n' +
                      '    <label class="layui-form-label laywidth">'+bus.name+'</label>\n' +
                      '</div>\n' +
                      '\n' +
                      '<div class="layui-form-item">\n' +
                      '    <label class="layui-form-label laywidth">职称:</label>\n' +
                      '    <label class="layui-form-label laywidth">'+user.position+'</label>\n' +
                      '</div>\n' +
                      '\n' +
                      '<div class="layui-form-item">\n' +
                      '    <label class="layui-form-label laywidth">手机号:</label>\n' +
                      '    <label class="layui-form-label laywidth">'+user.phonenumber+'</label>\n' +
                      '</div>\n' +
                      '\n' +
                      '<div class="layui-form-item">\n' +
                      '    <label class="layui-form-label laywidth">解决案件数:</label>\n' +
                      '    <label class="layui-form-label laywidth">'+user.solve+'</label>\n' +
                      '</div>\n' +
                      '\n' +
                      '<div class="layui-form-item">\n' +
                      '    <label class="layui-form-label laywidth">未解决案件数:</label>\n' +
                      '    <label class="layui-form-label laywidth">'+user.unsolve+'</label>\n' +
                      '</div>' +
                      '<div class="layui-form-item">\n' +
                      '     <div class="layui-input-block"> <button class="layui-btn" lay-submit lay-filter="submitBut">修改信息</button></div>'+
                      '</div>'+
                      '</form>';
                    $(".cBody").html(html);

                }else{
                    $(".cBody").html("加载失败");
                }
            },
            error:function(e){
                $(".cBody").html("加载失败");
            }
        })

        //监听提交
        form.on('submit(submitBut)', function (data) {
            window.location.href = path + "/page/updateInfo";
            return false;
        });

    }
)