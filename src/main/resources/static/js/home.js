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