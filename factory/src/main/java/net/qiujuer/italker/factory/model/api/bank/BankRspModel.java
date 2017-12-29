package net.qiujuer.italker.factory.model.api.bank;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */

public class BankRspModel {
    private String status;
    private String ErrorInfo;
    private List<BankItemModel> list;

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

    public List<BankItemModel> getList() {
        return list;
    }

    public void setList(List<BankItemModel> list) {
        this.list = list;
    }
}
