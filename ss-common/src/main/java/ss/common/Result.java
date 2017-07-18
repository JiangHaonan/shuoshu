package ss.common;

import java.io.Serializable;

import ss.common.enums.CodeEnums;

/**
 * Created by mutou on 2017/5/24.
 */
public class Result implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
     * 结果码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回内容
     */
    private Object data;

    public Result() {

    }

    public Result(CodeEnums code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
