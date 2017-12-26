package com.bocom.enums;

/**
 * 文件上传状态枚举类
 */
public enum FileStatus {

    uploading("0", "正在上传"),

    uploadSuccess("1", "上传成功"),

    delete("3", "删除"),

    uploadError("2", "上传失败");

    private String uploadCode;

    private String uploadStatusName;

    private FileStatus(String uploadCode, String uploadStatusName) {
        this.uploadCode = uploadCode;
        this.uploadStatusName = uploadStatusName;
    }


    public String getUploadCode() {
        return uploadCode;
    }

    public void setUploadCode(String uploadCode) {
        this.uploadCode = uploadCode;
    }

    public String getUploadStatusName() {
        return uploadStatusName;
    }

    public void setUploadStatusName(String uploadStatusName) {
        this.uploadStatusName = uploadStatusName;
    }
}
