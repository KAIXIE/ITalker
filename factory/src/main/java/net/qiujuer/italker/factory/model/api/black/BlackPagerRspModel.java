package net.qiujuer.italker.factory.model.api.black;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class BlackPagerRspModel {
    private String total;
    private List<BlackItemModel> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<BlackItemModel> getRows() {
        return rows;
    }

    public void setRows(List<BlackItemModel> rows) {
        this.rows = rows;
    }
}
