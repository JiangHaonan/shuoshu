package ss.common.enums;

/**
 * Created by mutou on 2017/5/23.
 */
public enum CodeEnums {

	SUCCESS(100, "成功"),
	FAIL(999, "失败"),
    USER_NOT_LOGIN(101, "用户未登陆"),
    USER_REGISTER_SUCCESS(100,"注册成功"),
    USER_NOT_EXIST(102,"用户不存在"),
    USER_LOGIN_FAIL(103,"用户名或密码错误"),
    USER_LOGIN_SUCCESS(104,"登陆成功");

    private Integer code;

    private String msg;

    CodeEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
