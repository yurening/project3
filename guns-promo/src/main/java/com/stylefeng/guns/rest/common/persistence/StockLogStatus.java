package com.stylefeng.guns.rest.common.persistence;

public enum StockLogStatus {

    INIT(1, "初始化"),
    SUCCESS(2, "成功"),
    FAIL(3, "失败");

    private int code;
    private String status;

    StockLogStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
