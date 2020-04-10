var vm = new Vue({
    el: "#app",
    data: {//页面所需数据
        caseId:"2",
        fileList:[]
    },

    //实例创建完成,进行步骤
    mounted: function () {
        this.$nextTick(function () {
            //保证this.$el已经插入文档
            //初始化方法
            this.cartView();
            this.getFileAll();
        })
    },
     filters: {  
     	status:function(num){
          	if(num=="0"){
               return "失败";
          	}else if(num=="1"){
               return "成功";
          	}      
     	}
	},
    methods: {
    	//w展示页面的方法
        cartView: function () {

        },
        beforeFileUpload:function (file) {

            return false;
        },
        getFileAll:function(caseId) {
            caseId = this.caseId;
            var aa = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/getFileAll', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':caseId
                },
                success: function(result){ // 当请求成功时运行的函数
                    //result返回的是string类型的数组
                   aa.fileList = JSON.parse(result);
                   console.log("点击执行！！！");
                },
                error:function(result){ //失败的函数
                    console.log("请求文件列表出错")
                }
            });
        },
        watchFile:function (data) {
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/comm/WatchFile', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'filename':data.filename,
                    'filepath':data.url
                },
                success: function(result){ // 当请求成功时运行的函数
                   alert("ajax执行成功！！")
                },
                error:function(result){ //失败的函数
                    alert("ajax执行失败！！")
                }
            });
        }
       
    }
});
