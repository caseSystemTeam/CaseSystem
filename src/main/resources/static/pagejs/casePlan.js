var vm = new Vue({
    el: "#app",
    data: {//页面所需数据
        caseId:"2",   //当前案件的id
        fileList:[],  //文件列表
        dialogFormVisible: false,  //发送消息部分，弹出框可见性
        dialogCaseVersionVisible:false,
        userList:[],   //用户列表,
        caseInfo:[],    //当前案件的信息
        versionInfo:[],  //案件版本的信息，包含当前版本的操作人
        message:"",  //消息内容
        tomessage:"",  //消息内容
        userid:'bebede7a-4d27-4dfc-8fd7-35b8bc64f316',
        jstatus:1,   //案件执行到哪一步的状态
        pstatus:0,
        member1:'',
        member2:'',  //小组成员的id，不是名字
        member3:'',
        member4:'',
        groupmember:[],
        idear:'',  //小组成员的修改意见
        editorInfo:null,  //案件描述 富文本编辑器
        editorInfoContent:'', //内容
        editorResult:null,  // 咨询结果 富文本编辑器
        editorResultContent:'', //内容
        lian_info:'',
        lian_faguan:'',
        lian_number:'',
        lian_didian:'',
        lian_timestart:'',
        lian_timeend:'',
        kt_info:'',
        kt_timestart:'',
        kt_timeend:'',
        ts_info:'',
        ts_time:'',
    },
    beforeCreate: function () {
        instance = this;  //将当前vue对象，做成全局的对象，暴露给过滤器
    },
    //实例创建完成,进行步骤
    mounted: function () {
        // this.$nextTick(function () {
        //     //保证this.$el已经插入文档
        //     //初始化方法
        //     console.log("准备cartview");
        //     this.cartView();
        //     console.log("cartvied结束");
        // });
        //这个方法莫名会执行很久，所以把cartview方法放在它的回调方法中执行
        this.getCaseId();
        //创建富文本编辑器
        var E = window.wangEditor;
        this.editorInfo = new E('#editorInfo');
        this.editorInfo.customConfig.menus = [];
        this.editorInfo.create();
        this.editorInfo.$textElem.attr('contenteditable', false);

        this.editorResult = new E('#editorResult');
        this.editorResult.create();
    },

     filters: {  
     	versionStatus:function(num){
          	if(num==0){
               return "不同意";
          	}else if(num==1){
               return "同意";
          	}else if(num==3){
                return "未处理";
            }      
     	},
         //将用户id转为实际用户名
         userName:function(userId){
             var list = instance.userList;
             for(let i=0;i<list.length;i++){
                 if(list[i].Id===userId){
                    return list[i].name;
                 }
             }
         }
	},
    methods: {
    	//w展示页面的方法
        getCaseId: function () {
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/getCaseId', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据

                },
                success: function(result){ // 当请求成功时运行的函数
                    //result返回的是string类型的数组
                    let da = JSON.parse(result);
                    temp.caseId = da.data.caseId;
                    temp.userid = da.data.userid;
                    //调用初始化方法
                    temp.cartView();
                },
                error:function(result){ //失败的函数
                    console.log("获取案件id失败！！");
                }
            });
        },
        cartView: function () {
            this.getUserAll();
            this.getFileAll();
            this.getCaseInfo();

            this.getCaseVersionInfo();
            this.getMember();
        },
        //通用方法开始**************************************************
        endCase: function () {
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/endCase', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':temp.caseId,
                },
                success: function(result){ // 当请求成功时运行的函数
                    temp.cartView();
                    temp.$message({
                        message: '当前案件已被结束~~',
                        type: 'warning'
                    });
                },
                error:function(result){ //失败的函数
                    console.log("结束案件出错！！");
                }
            });
        },
        getUserAll: function () {
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/userCon/getAllLawer', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    //'caseId':temp.caseId,
                },
                success: function(result){ // 当请求成功时运行的函数
                    //result返回的是string类型的数组
                    let da = JSON.parse(result);
                    temp.userList= da.data.data;
                    //删除用户列表中的自己，因为发送消息时不能给自己发
                    for(let i=0;i<temp.userList.length;i++){
                        if(temp.userList[i].Id===temp.userid){
                            temp.userList.splice(i,1);
                        }
                    }
                },
                error:function(result){ //失败的函数
                    console.log("请求用户列表出错");
                }
            });
        },
        //提交案件咨询结果中的内容，可重复提交
        commitCaseResult: function () {
            let temp = this;
            //获取富文本信息
            temp.editorResultContent = temp.editorResult.txt.html();
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/commitCaseResult', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':temp.caseId,
                    'resultContent':temp.editorResultContent,
                },
                success: function(result){ // 当请求成功时运行的函数
                    //result返回的是string类型的数组
                    let da = JSON.parse(result);
                    temp.$message({
                        message: '提交成功~~',
                        type: 'success'
                    });

                },
                error:function(result){ //失败的函数
                    temp.$message({
                        message: '提交信息出错！！',
                        type: 'warning'
                    });
                }
            });
        },
        //获得案件版本信息列表的方法
        getCaseVersionInfo: function () {
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/getCaseVersionInfo', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':temp.caseId,
                },
                success: function(result){ // 当请求成功时运行的函数
                    //result返回的是string类型的数组
                    let da = JSON.parse(result);
                    temp.versionInfo = da.data.data;
                },
                error:function(result){ //失败的函数
                    console.log("请求案件版本列表出错");
                }
            });
        },
        //切换当前案件的版本，也就是+1
        addCaseVersion: function () {
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/addCaseVersion', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':temp.caseId,

                },
                success: function(result){ // 当请求成功时运行的函数
                    //result返回的是string类型的数组
                    temp.getCaseVersionInfo();
                    temp.$message({
                        message: '切换新版本成功！！',
                        type: 'success'
                    });
                },
                error:function(result){ //失败的函数
                    console.log("切换案件版本出错");
                }
            });
        },
        //律师分配中的提交方法
        submitPeo:function(){
            let temp = this;
            if(this.member1===this.member2||this.member1===this.member3||this.member1===this.member4){
                temp.$message({
                    message: '组员中包含了组长！！',
                    type: 'warning'
                });
                return;
            }
            if(this.member2===""&&this.member3===""&&this.member4===""){
                temp.$message({
                    message: '请选择组员后再提交！！',
                    type: 'warning'
                });
                return;
            }
            if(this.member2===this.member3||this.member2===this.member4){
                temp.$message({
                    message: '重复选择组员！！',
                    type: 'warning'
                });
                return;
            }
            if(this.member3===this.member4){
                temp.$message({
                    message: '重复选择组员！！',
                    type: 'warning'
                });
                return;
            }
            if(this.member2===""||this.member3===""||this.member4===""){
                temp.$message({
                    message: '组员不能为空！！',
                    type: 'warning'
                });
                return;
            }
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/setCaseGroup', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':temp.caseId,
                    'member1':temp.member1,
                    'member2':temp.member2,
                    'member3':temp.member3,
                    'member4':temp.member4,
                    'jstatus':temp.jstatus,
                },
                success: function(result){ // 当请求成功时运行的函数
                    //result返回的是string类型的数组

                    temp.getCaseVersionInfo();
                    temp.cartView();
                    temp.$message({
                        message: '小组成员提交成功~~',
                        type: 'success'
                    });

                },
                error:function(result){ //失败的函数
                    temp.$message({
                        message: '提交失败！！',
                        type: 'warning'
                    });
                }
            });

        },
        //获取当前案件的小组成员，如果已经有的话
        getMember:function(caseId) {
            caseId = this.caseId;
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/getMember', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':caseId
                },
                success: function(result){ // 当请求成功时运行的函数
                    let da = JSON.parse(result);
                    temp.groupmember = da.data;

                },
                error:function(result){ //失败的函数
                    console.log("执行出错");
                }
            });
        },
        //获取当前案件的信息
        getCaseInfo: function () {
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/getCaseInfo', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':temp.caseId,
                },
                success: function(result){ // 当请求成功时运行的函数
                    //result返回的是string类型的数组
                    let da = JSON.parse(result);
                    temp.caseInfo = da.data;
                    temp.member1 = temp.caseInfo.lawerid;//绑定当前案件的组长
                    temp.jstatus = temp.caseInfo.jstatus;
                    temp.pstatus = temp.caseInfo.p_status;
                    //向富文本编辑器赋值
                    temp.editorInfo.txt.html(temp.caseInfo.content);
                    temp.editorResult.txt.html(temp.caseInfo.resultContent);
                    if(temp.jstatus>=4){
                        temp.getCaseInfoAssist();
                    }
                },
                error:function(result){ //失败的函数
                    temp.$message({
                        message: '获取案件信息出错！！',
                        type: 'warning'
                    });
                }
            });
        },
        getCaseInfoAssist: function () {
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/getCaseInfoAssist', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':temp.caseId,
                },
                success: function(result){ // 当请求成功时运行的函数
                    //result返回的是string类型的数组
                    console.log("信息"+result);

                    let da = JSON.parse(result).data;
                    console.log("信息"+da);
                    temp.lian_info = da.lian_info;
                    console.log("信息"+temp.lian_info);
                    temp.lian_faguan = da.lian_faguan;
                    temp.lian_number = da.lian_number;
                    temp.lian_didian = da.lian_didian;
                    temp.lian_timestart = da.lian_timestart;
                    temp.lian_timeend = da.lian_timeend;
                    temp.kt_info = da.kt_info;
                    temp.kt_timestart = da.kt_timestart;
                    temp.kt_timeend = da.kt_timeend;
                    temp.ts_info = da.ts_info;
                    temp.ts_time = da.ts_time;

                },
                error:function(result){ //失败的函数
                    temp.$message({
                        message: '获取补充案件信息出错！！',
                        type: 'warning'
                    });
                }
            });
        },
        //查看小组成员对当前版本的意见
        watchIdear: function (rowdata) {
            let temp = this;
            temp.message = rowdata.message;
            temp.idear = rowdata.idear;
            temp.dialogCaseVersionVisible = true;

        },
        // sendMessage: function () {
        //     let temp = this;
        //     temp.dialogFormVisible = false;
        //     let receiver = this.$refs.multipleTable.selection;   //获取表格中被选中的数据
        //     receiver = JSON.stringify(receiver);
        //     console.log(receiver);
        //     $.ajax({
        //         type:'POST', // 规定请求的类型（GET 或 POST）
        //         url:'/case/sendMessage', // 请求的url地址
        //         dataType:'text', //预期的服务器响应的数据类型
        //         data:{   //规定要发送到服务器的数据
        //             'receiver':receiver,
        //             'message':temp.message,
        //             'sender':temp.userid
        //         },
        //         success: function(result){ // 当请求成功时运行的函数
        //             //result返回的是string类型的数组
        //             let da = JSON.parse(result);
        //             temp.message = "";  //清空发送的消息
        //             temp.$message({
        //                 message: '消息发送成功~~',
        //                 type: 'success'
        //             });
        //
        //         },
        //         error:function(result){ //失败的函数
        //             temp.$message({
        //                 message: '消息发送失败！！',
        //                 type: 'warning'
        //             });
        //         }
        //     });
        // },
        sendMessage: function () {
            let temp = this;
            temp.dialogFormVisible = false;
            let receiver = this.$refs.multipleTable.selection;   //获取表格中被选中的数据
            receiver = JSON.stringify(receiver);

            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/sendMessage', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':temp.caseId,
                    'tomessage':temp.tomessage,
                    'receiver':receiver
                },
                success: function(result){ // 当请求成功时运行的函数
                    //result返回的是string类型的数组
                    let da = JSON.parse(result);
                    temp.message = "";  //清空发送的消息
                    temp.getCaseVersionInfo();
                    temp.$message({
                        message: '消息发送成功~~',
                        type: 'success'
                    });

                },
                error:function(result){ //失败的函数
                    temp.$message({
                        message: '消息发送失败！！',
                        type: 'warning'
                    });
                }
            });
        },
        //通用方法结束**************************************************


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
        FileUploadError:function(result){
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
        watchFile:function (rowdata) {
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/comm/WatchFile', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'filepath':rowdata.url,
                    'fileid':rowdata.fileid
                },
                success: function(result){ // 当请求成功时运行的函数
                    let  message = JSON.parse(result);
                    if("office"===message.data.type||"common"===message.data.type){
                        window.open(message.data.filePathHtml);
                    }else{
                        temp.$message({
                            type: 'warning',
                            message: "不支持的文件，请下载后浏览"
                        });
                    }
                },
                error:function(result){ //失败的函数
                    temp.$message({
                        type: 'warning',
                        message: "文件转换出错，请选择标准格式的word、excel文档"
                    });
                }
            });
        },
        downloadFile:function (rowdata) {
            let temp = this;
            window.location.href='/comm/downloadFile?fileid='+rowdata.fileid;
        },
        deleteFileById:function (rowdata) {
            var temp = this;
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                $.ajax({
                    type:'POST', // 规定请求的类型（GET 或 POST）
                    url:'/case/deleteFileById', // 请求的url地址
                    dataType:'text', //预期的服务器响应的数据类型
                    data:{   //规定要发送到服务器的数据
                        'fileid':rowdata.fileid,
                        'filepath':rowdata.url
                    },
                    success: function(result){ // 当请求成功时运行的函数
                        let message = JSON.parse(result)
                        //status返回值是number类型，===先比较类型再比较值，如果用字符串比较，就会有问题
                        if(message.status===200){
                            temp.$message({
                                type: 'success',
                                message: message.info
                            });
                            //刷新文件列表的数据
                            temp.getFileAll(temp.caseId);
                        }else{
                            temp.$message({
                                type: 'warning',
                                message: message.info
                            });
                        }

                    },
                    error:function(result){ //失败的函数
                        temp.$message({
                            type: 'warning',
                            message: '删除失败，服务器出现错误'
                        });
                    }
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        //已上传文件处理相关的方法结束*************************

        //结束当前案件以及执行下一个流程的相关方法-------------------------
        lianEnd:function(caseId) {
            caseId = this.caseId;
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/nextCard', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':caseId,
                    'jstatus':temp.jstatus
                },
                success: function(result){ // 当请求成功时运行的函数
                    //状态修改完之后需要做的逻辑
                    temp.cartView();
                    temp.$message({
                        type: 'success',
                        message: '已切换到下一个状态'
                    });

                },
                error:function(result){ //失败的函数
                    console.log("执行出错");
                }
            });
        },
        szEnd:function(caseId) {
            caseId = this.caseId;
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/nextCard', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':caseId,
                    'jstatus':temp.jstatus
                },
                success: function(result){ // 当请求成功时运行的函数
                    //状态修改完之后需要做的逻辑
                    temp.cartView();
                    temp.$message({
                        type: 'success',
                        message: '已切换到下一个状态'
                    });

                },
                error:function(result){ //失败的函数
                    console.log("执行出错");
                }
            });
        },
        laEnd:function(caseId) {
            caseId = this.caseId;
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/nextCard', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':caseId,
                    'jstatus':temp.jstatus
                },
                success: function(result){ // 当请求成功时运行的函数
                    //状态修改完之后需要做的逻辑
                    temp.cartView();
                    temp.$message({
                        type: 'success',
                        message: '已切换到下一个状态'
                    });

                },
                error:function(result){ //失败的函数
                    console.log("执行出错");
                }
            });
        },
        ktEnd:function(caseId) {
            caseId = this.caseId;
            let temp = this;
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/nextCard', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'caseId':caseId,
                    'jstatus':temp.jstatus
                },
                success: function(result){ // 当请求成功时运行的函数
                    //状态修改完之后需要做的逻辑
                    temp.cartView();
                    temp.$message({
                        type: 'success',
                        message: '已切换到下一个状态'
                    });

                },
                error:function(result){ //失败的函数
                    console.log("执行出错");
                }
            });
        },
        saveInfo:function(caseId) {
            let temp = this;
            var map = new Map();
            if(temp.lian_info!==""||temp.lian_info!==null){
                map.set('lian_info',temp.lian_info);
            }
            if(temp.lian_faguan!==""||temp.lian_faguan!==null){
                map.set('lian_faguan',temp.lian_faguan);
            }
            if(temp.lian_number!==""||temp.lian_number!==null){
                map.set('lian_number',temp.lian_number);
            }
            if(temp.lian_didian!==""||temp.lian_didian!==null){
                map.set('lian_didian',temp.lian_didian);
            }
            if(temp.lian_timestart!==""||temp.lian_timestart!==null){
                map.set('lian_timestart',temp.lian_timestart);
            }
            if(temp.lian_timeend!==""||temp.lian_timeend!==null){
                map.set('lian_timeend',temp.lian_timeend);
            }
            if(temp.kt_info!==""||temp.kt_info!==null){
                map.set('kt_info',temp.kt_info);
            }
            if(temp.kt_timestart!==""||temp.kt_timestart!==null){
                map.set('kt_timestart',temp.kt_timestart);
            }
            if(temp.kt_timeend!==""||temp.kt_timeend!==null){
                map.set('kt_timeend',temp.kt_timeend);
            }
            if(temp.ts_info!==""||temp.ts_info!==null){
                map.set('ts_info',temp.ts_info);
            }
            if(temp.ts_time!==""||temp.ts_time!==null){
                map.set('ts_time',temp.ts_time);
            }
            map.set('id',temp.caseId);
            let obj= Object.create(null);
            for (let[k,v] of map) {
                obj[k] = v;
            }
            var jsonp = JSON.stringify(obj);

            console.log("json数据"+jsonp);
            $.ajax({
                type:'POST', // 规定请求的类型（GET 或 POST）
                url:'/case/saveInfo', // 请求的url地址
                dataType:'text', //预期的服务器响应的数据类型
                data:{   //规定要发送到服务器的数据
                    'data':jsonp
                },
                success: function(result){ // 当请求成功时运行的函数
                    //状态修改完之后需要做的逻辑
                    temp.cartView();
                    temp.$message({
                        type: 'success',
                        message: '保存信息成功~~'
                    });
                },
                error:function(result){ //失败的函数
                    temp.$message({
                        type: 'warning',
                        message: '保存信息失败！！'
                    });
                }
            });
        },

        //结束
       
    }
});
