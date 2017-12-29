package net.qiujuer.italker.factory.presenter.black;

import net.qiujuer.italker.factory.model.api.black.BlackPagerRspModel;
import net.qiujuer.italker.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2017/12/7.
 */

public interface BlackContract {
    interface View extends BaseContract.View<Presenter>{
        // 列表加载完成
        void listLoaded(BlackPagerRspModel model);
    }

    interface Presenter extends BaseContract.Presenter{
       // 请求黑板列表
        void loadList(String page, String rows, String articleType);
    }
}
