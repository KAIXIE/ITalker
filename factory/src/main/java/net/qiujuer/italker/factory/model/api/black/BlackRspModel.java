package net.qiujuer.italker.factory.model.api.black;


/**
 * Created by Administrator on 2017/12/7.
 */

public class BlackRspModel {
    private String status;
    private String ErrorInfo;
    private BlackPagerRspModel articlePager;
    private String pageCount;

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

    public BlackPagerRspModel getArticlePager() {
        return articlePager;
    }

    public void setArticlePager(BlackPagerRspModel articlePager) {
        this.articlePager = articlePager;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }
}
