package net.qiujuer.italker.factory.model.api.otherAccount;

/**
 * Created by Administrator on 2017/12/29.
 */

public class OtherLoginResultModel {
    private OtherUser user;
    private String token;
    private String status;
    private String loseTime;

    public OtherUser getUser() {
        return user;
    }

    public void setUser(OtherUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoseTime() {
        return loseTime;
    }

    public void setLoseTime(String loseTime) {
        this.loseTime = loseTime;
    }
}
