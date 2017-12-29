package net.qiujuer.italker.factory.model.api.person;

/**
 * Created by xieka on 2017/12/17.
 */

public class UserInfoModel {
    /*{
        "headimg": "xxx",
            "background": "dsadsa",
            "id": 1,
            "introduction": "此用户很懒,还没有设置过个人信息",
            "realname": "ddd",
            "huanXinId": "2222"
    }*/
    private String headimg;
    private String background;
    private String id;
    private String introduction;
    private String realname;
    private String huanXinId;

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getHuanXinId() {
        return huanXinId;
    }

    public void setHuanXinId(String huanXinId) {
        this.huanXinId = huanXinId;
    }
}
