package net.qiujuer.italker.factory.model.api.section;

import net.qiujuer.italker.factory.model.api.black.BlackDetailModel;

/**
 * Created by Administrator on 2017/12/8.
 */

public class SectionDetailRspModel {
    private String status;
    private String ErrorInfo;
    private SectionDetailModel detail;

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

    public SectionDetailModel getDetail() {
        return detail;
    }

    public void setDetail(SectionDetailModel detail) {
        this.detail = detail;
    }
}
