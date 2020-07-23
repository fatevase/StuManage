function  ShowNav() {
    $('.ui.sidebar')
        .sidebar('toggle')
    ;
}

var show_list = new Vue({
    el: '#show_nav',
    data :{
        navs:[{url : "index",name: "首页"},
            {url : "studentMsg",name: "学生信息"},
            { url : "teacherMsg",name: "教师信息" },
            { url : "courseMsg",name: "课程信息" },
            {url : "userManage",name: "用户管理"},
            { url : "courseManage",name: "课程管理" },
            { url : "chooseManage",name: "选课管理" },
        ]

    },
    mounted(){
        axios.get('/users/getall')
            .then(response=>(this.items = response.data))
            .catch(function(error){
                alert(error);
            });
    }

});