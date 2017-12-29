package net.qiujuer.italker.factory.model.api.diary;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DiaryFenyeModel {
    public DiaryFenyeModel(String rows, String page, String token) {
        this.rows = rows;
        this.page = page;
        this.token = token;
    }

    private String rows;
    private String page;
    private String token;

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
