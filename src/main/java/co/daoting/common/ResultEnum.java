package co.daoting.common;

public enum ResultEnum {

    SUCCESS(200, "登陆成功"),
    FAIL(201, "请求失败"),
    WRONG_LOGIN_OR_PASSWORD(201, "账户名或密码输入错误"),
    INVALID_TOKEN(202, "登录已过期，请重新登录"),
    MISS_API_PARAM(203, "缺少接口参数"),
    WRONG_LOGIN(205,"账号错误"),
    WRONG_CODE(205,"验证码错误"),
    WRONG_PASSWORD(205,"登录失败,密码错误"),
    OUTTIME_CODE(206,"验证码过期"),
    USER_NOT_EXIT(207,"用户不存在"),
    VCODE_NOT_MISS(201,"验证码为空"),
    SERVER_EXCEPTION(500, "服务器异常");

    private int code;

    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
