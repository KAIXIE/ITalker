package net.qiujuer.italker.factory.presenter.black;

import net.qiujuer.italker.factory.model.api.black.BlackDetailModel;
import net.qiujuer.italker.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2017/12/8.
 */

public interface BlackDetailContract {

    interface View extends BaseContract.View<Presenter>{
        // 详情加载完成
        void blackDetailLoaded(BlackDetailModel model);
    }

    interface Presenter extends BaseContract.Presenter{
        // 请求一条黑板信息的详情
        void loadBlackDetail(String blackId);
    }
}
