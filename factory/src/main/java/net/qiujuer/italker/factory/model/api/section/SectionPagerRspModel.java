package net.qiujuer.italker.factory.model.api.section;


import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class SectionPagerRspModel {
    private String total;
    private List<SectionItemModel> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<SectionItemModel> getRows() {
        return rows;
    }

    public void setRows(List<SectionItemModel> rows) {
        this.rows = rows;
    }
}
