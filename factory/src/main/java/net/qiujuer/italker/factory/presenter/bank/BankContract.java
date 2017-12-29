package net.qiujuer.italker.factory.presenter.bank;

import net.qiujuer.italker.factory.model.api.bank.BankItemModel;
import net.qiujuer.italker.factory.presenter.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */

public interface BankContract {
    interface View extends BaseContract.View<Presenter> {
        // 列表加载完成
        void listLoaded(List<BankItemModel> model);
    }

    interface Presenter extends BaseContract.Presenter {
        // 请求黑板列表
        void loadList();
    }
}
