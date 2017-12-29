package net.qiujuer.italker.factory.model.api.section;

/**
 * Created by Administrator on 2017/12/7.
 */

public class SectionFenyeModel {

    public SectionFenyeModel(String page, String rows, String articleType) {
        this.page = page;
        this.rows = rows;
        this.articleType = articleType;
    }

    private String page;
    private String rows;
    private String articleType;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }
}
