package net.qiujuer.italker.factory.model.api.diary;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DiaryPagerRspModel {
    private String total;
    private List<DiaryItemModel> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DiaryItemModel> getRows() {
        return rows;
    }

    public void setRows(List<DiaryItemModel> rows) {
        this.rows = rows;
    }
}
