function UnixToDate(time,type="english") {
    if(time.length < 10 || time < 631123200){
        return "未知时间";
    }
    let unixtime = time;
    let unixTimestamp = new Date(unixtime * 1000);
    let Y = unixTimestamp.getFullYear();
    let M = ((unixTimestamp.getMonth() + 1) > 10 ? (unixTimestamp.getMonth() + 1) : '0' + (unixTimestamp.getMonth() + 1));
    let D = (unixTimestamp.getDate() > 10 ? unixTimestamp.getDate() : '0' + unixTimestamp.getDate());
    let toDay = "";
    if(type=="english"){
        toDay =  Y + '-' + M + '-' + D;
    }else if("chinses"){
        toDay =  Y + ' 年 ' + M + ' 月 ' + D + ' 日 ';
    }else{
        toDay =  Y + '-' + M + '-' + D;
    }
    return toDay;
}

function DataToUnix(time) {
    let strtime = "";
    strtime = time.replace(/-/g,'/');
    strtime = time.replace(/[\u4e00-\u9fa5]/g,'/');
    //解决低版本解释new Date('yyyy-mm-dd')这个对象出现NaN
    if(strtime){
        var date = new Date(strtime);
    }else{
        var date = new Date();
    }
    //time = date.getTime();   //会精确到毫秒---长度为13位
    //time = date.valueOf(); //会精确到毫秒---长度为13位
    time = Date.parse(date)/1000; //只能精确到秒，毫秒将用0来代替---长度为10位
    return time;
}

function FilterArrayData(filter,arr){
    let tempFilter = {};
    for(key in filter) {
        if(typeof(filter[key]) != "undefined" && typeof(filter[key]) != "null" && filter[key] != null && filter[key] != "") {
            tempFilter[key] = filter[key];
        }
    }
    //筛选
    let resultArr = arr.filter(
        (item) => {
            let flag = false;
            for(key in tempFilter) {
                if(item[key].toString().indexOf(tempFilter[key].toString()) >= 0) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                return item;
            }
        }
    );
    return resultArr;

}

//返回之前没页面则返回首页
function PushHistory() {
    if (history.length < 2) {
        var state = {
            title: "index",
            url: getHttpPrefix + "index"
        };
        window.history.pushState(state, "index", location.href);
        state = {
            title: "index",
            url: ""
        };
        window.history.pushState(state, "index", "");
    }
    //lll("history.state" + history.state)
    //console.log(history.state)
}