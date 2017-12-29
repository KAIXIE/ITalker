package net.qiujuer.italker.factory.model.api.diary;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DiaryItemModel implements Comparable<DiaryItemModel>,Serializable {
    private String brief;
    private String updateTime;
    private String id;
    private String userid;
    private String content;

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(@NonNull DiaryItemModel o) {
        return o.getUpdateTime().compareTo(this.getUpdateTime());
    }
}
