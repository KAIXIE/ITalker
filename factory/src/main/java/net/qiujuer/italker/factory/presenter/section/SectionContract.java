package net.qiujuer.italker.factory.presenter.section;

import net.qiujuer.italker.factory.model.api.section.SectionPagerRspModel;
import net.qiujuer.italker.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2017/12/7.
 */

public interface SectionContract {
    interface View extends BaseContract.View<Presenter>{
        // 列表加载完成
        void listLoaded(SectionPagerRspModel model);
    }

    interface Presenter extends BaseContract.Presenter{
       // 请求黑板列表
        void loadList(String page, String rows, String articleType);
    }
}
