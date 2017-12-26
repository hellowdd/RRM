package com.bocom.enums;

public enum OperationEnum {

    download("1","下载"),

    upload("0","上传");

    private String OperationTypeCode;

    private String OperationTypeName;

    public String getOperationTypeCode() {
        return OperationTypeCode;
    }

    public void setOperationTypeCode(String operationTypeCode) {
        OperationTypeCode = operationTypeCode;
    }

    public String getOperationTypeName() {
        return OperationTypeName;
    }

    public void setOperationTypeName(String operationTypeName) {
        OperationTypeName = operationTypeName;
    }

    private OperationEnum(String operationTypeCode, String operationTypeName) {
        OperationTypeCode = operationTypeCode;
        OperationTypeName = operationTypeName;
    }
}
