package net.qiujuer.italker.factory.model.api.diary;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DiaryRspModel {

    private String status;
    private String ErrorInfo;
    private DiaryPagerRspModel userNoteList;
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

    public DiaryPagerRspModel getUserNoteList() {
        return userNoteList;
    }

    public void setUserNoteList(DiaryPagerRspModel userNoteList) {
        this.userNoteList = userNoteList;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }
}
