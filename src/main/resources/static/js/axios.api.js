/* 服务器地址 */
var base_url = 'http://localhost:8080'; //测试服务器
//var base_url = 'http://**********'; //正式服务器


/******************* 配置的拦截器 封装的axios ***********************/
// 创建axios实例
const service = axios.create({
    baseURL: base_url, // api的base_url
    timeout: 120000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(config => {
    //config.headers['token'] = 66;
    config.headers['Content-Type'] = "application/json";
    // config.headers['id'] = window.sessionStorage.getItem("id");
    // console.log(config.headers)
    return config
}, error => {
    // Do something with request error
    // console.log(error) // for debug
    Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(
    response => {
        const res = response.data

        return res
    },
    error => {
        console.log(error) // for debug
        Toast('服务器异常, 请稍后重试')

        return Promise.reject(error)
    }
)

function SubmitUserLogin(postdata) { //面签页面--保存提交
    axios.post("/users/checkuserlogin",postdata).then(response=> {
        if (response.data.status !== 200) {
            alert(response.data.msg);
        }
        this.username = "";
        this.password = "";
    }).catch(error=>{
        alert(error);
    });
}

/* ***************************incoming*********************************** */
function findById(params) { //面签页面--根据apid查询已有信息
    return service({
        url: '/api/backend/approvalProcess/findById',
        method: 'get',
        params
    })
}

