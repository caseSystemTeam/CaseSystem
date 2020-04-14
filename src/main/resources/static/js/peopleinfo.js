layui.use(['jquery','layer'],
    function () {
        var layer = layui.layer, $ = layui.jquery;
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
                  var html='<div class="layui-form-item">\n' +
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
                      '</div>';
                    $(".cBody").html(html);

                }else{
                    $(".cBody").html("加载失败");
                }
            },
            error:function(e){
                $(".cBody").html("加载失败");
            }
        })
    }
)