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
        //上传文件相关的钩子方法开始----------------------------------------
        beforeFileUpload:function (file) {
            //这里不返回true，是不能上传文件的
            return true;
        },
        FileUploadSuccess:function(result){
            this.$message({
                message: '文件上传成功！',
                type: 'success'
            });
            //刷新已上传文件列表
            this.getFileAll(this.caseId);
        },
        FileUploadError:function(data){
            this.$message({
                message: '文件上传失败，请检查是否符合文件要求',
                type: 'warning'
            });
        },
        //上传文件相关的钩子方法结束----------------------------------------

        //已上传文件处理相关的方法开始*************************
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
                    let  data = JSON.parse(result);
                   alert("result的结果"+data.data.fileHtml);
                    window.open(data.data.fileHtml);
                },
                error:function(result){ //失败的函数
                    alert("ajax执行失败！！");
                }
            });
        }
        //已上传文件处理相关的方法结束*************************
       
    }
});
