package net.qiujuer.italker.factory.model.api.person;

/**
 * Created by xieka on 2017/12/17.
 */

public class PersonRspModel {
    /*{
        "status": "1",
            "ErrorInfo": "",
            "userInfo":

    }*/
    private String status;
    private String ErrorInfo;
    private UserInfoModel userInfo;

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

    public UserInfoModel getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoModel userInfo) {
        this.userInfo = userInfo;
    }
}
