var vm = new Vue({
    el: "#app",
    data: {//页面所需数据

    },
    //实例创建完成,进行步骤
    mounted: function () {
        this.$nextTick(function () {
            //保证this.$el已经插入文档
            //初始化方法
            this.cartView();
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
           console.log("已经执行了我的方法");
        },
        uploadFile: function (wtid) {
            //layer.alert("哈哈哈");
            // var Url = "detailLogs.html?wid=" + wid;
            // layer.open({
            //     type: 2,
            //     title: ['日志详情'],
            //     skin: 'layui-layer-rim', //加上边框
            //     area: ['90%', '90%'],
            //     content: Url
            // });
        }
       
    }
});
