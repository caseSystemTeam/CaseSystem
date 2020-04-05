var vm = new Vue({
    el: "#app",
    data: {//页面所需数据
        maparam: {
        	NAME_LIKE:"",
        	REALNAME_LIKE:"",
        	DWBMMC:"",
        },
        lists: [],

    },
    //实例创建完成,进行步骤
    mounted: function () {
        this.$nextTick(function () {
            //保证this.$el已经插入文档
            //初始化方法
            //this.cartView();
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
        cartView: function (num) {
        	 var _this = this;
			var cs = vm.maparam;
			if(num==1){
            	page = 1;
            }
			cs['lx'] = '5';  //限制查询的日志类型是5(群体预测)
            cs['pageNum'] = page;
            cs['pageSize'] = pagesize;
            //请求后台数据
				sendAjax('yf/SysLog/select',cs, function (data) {
                if (data.success) {
                	//后台数据赋值给前台
                	//total,totalPage都是showPage这个框架里的参数
                    _this.lists = data.mapParam.rows;
                    total = data.mapParam.total;
                    totalPage = parseInt((total - 1) / 10) + 1;
					showPage();
                } else {
                    layer.alert(data.message);
                }
            }, null);			
        },
        //跳转到日志详情
        showDetails: function (wtid) {
            var wid = wtid;
            var Url = "detailLogs.html?wid=" + wid;
            layer.open({
                type: 2,
                title: ['日志详情'],
               	skin: 'layui-layer-rim', //加上边框
		        area: ['90%', '90%'],
                content: Url
            });
        },
        uploadFile: function (wtid) {
            layer.alert("哈哈哈");
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
