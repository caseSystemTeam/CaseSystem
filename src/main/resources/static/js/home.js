//获取当前网址
var curPath=window.document.location.href;
//获取主机地址之后的目录
var pathName=window.document.location.pathname;
var pos=curPath.indexOf(pathName);
//获取主机地址
var localhostPaht=curPath.substring(0,pos);

var path= localhostPaht;

function getRootPath(){
    var curPageUrl = window.document.location.href;
    var rootPath = curPageUrl.split("//")[0] +
        curPageUrl.split("//")[1].split("/")[0] +
        "/" + curPageUrl.split("//")[1].split("/")[1];
    return rootPath;
}

function getCookie(cookie_name) {
    var allcookies = document.cookie;
    //索引长度，开始索引的位置
    var cookie_pos = allcookies.indexOf(cookie_name);

    // 如果找到了索引，就代表cookie存在,否则不存在
    if (cookie_pos != -1) {
        // 把cookie_pos放在值的开始，只要给值加1即可
        //计算取cookie值得开始索引，加的1为“=”
        cookie_pos = cookie_pos + cookie_name.length + 1;
        //计算取cookie值得结束索引
        var cookie_end = allcookies.indexOf(";", cookie_pos);

        if (cookie_end == -1) {
            cookie_end = allcookies.length;

        }
        //得到想要的cookie的值
        var value = unescape(allcookies.substring(cookie_pos, cookie_end));
    }
    return value;
}