package net.qiujuer.italker.factory.model.api.black;

/**
 * Created by Administrator on 2017/12/8.
 */

public class BlackDetailRspModel {
    private String status;
    private String ErrorInfo;
    private BlackDetailModel detail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorInfo() {
        return ErrorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        ErrorInfo = errorInfo;
    }

    public BlackDetailModel getDetail() {
        return detail;
    }

    public void setDetail(BlackDetailModel detail) {
        this.detail = detail;
    }
}
