package net.qiujuer.italker.factory.model.api.otherAccount;

/**
 * Created by Administrator on 2017/12/29.
 */

public class OtherUser {
    private String id;

    private String username;

    private String realname;

    private String headimg;

    private int usertype;

    private long myinvcode;

    private long invitedCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public long getMyinvcode() {
        return myinvcode;
    }

    public void setMyinvcode(long myinvcode) {
        this.myinvcode = myinvcode;
    }

    public long getInvitedCode() {
        return invitedCode;
    }

    public void setInvitedCode(long invitedCode) {
        this.invitedCode = invitedCode;
    }
}
