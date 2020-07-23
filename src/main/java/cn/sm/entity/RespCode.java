package cn.sm.entity;

import java.util.List;
import java.util.Map;

public class RespCode {
    //SUCCESS(0, "请求成功"),
   //WARN(-1, "网络异常，请稍后重试");
    // 0 表示数据有问题
    // -1 表示数据库操作异常
    // 1 表示成功
    private int code;
    private String msg;
    private List<Map<String, Object> > data;
    public RespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public RespCode() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }
}
