package cn.nnnight.enums;

public enum Status {

    SUCCESS("1", "成功"),
    FAILURE("0", "失败");

    private String code;
    private String codeMsg;

    Status(String code, String codeMsg) {
        this.code = code;
        this.codeMsg = codeMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }
}
