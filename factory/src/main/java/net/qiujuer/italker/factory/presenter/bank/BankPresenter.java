package net.qiujuer.italker.factory.presenter.bank;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.data.helper.BankHelper;
import net.qiujuer.italker.factory.model.api.bank.BankItemModel;
import net.qiujuer.italker.factory.presenter.BasePresenter;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */

public class BankPresenter extends BasePresenter<BankContract.View> implements BankContract.Presenter {
    public BankPresenter(BankContract.View view) {
        super(view);
    }

    @Override
    public void loadList() {
        start();
        BankHelper.loadList(new DataSource.Callback<List<BankItemModel>>() {
            @Override
            public void onDataNotAvailable(final int strRes) {
                final BankContract.View view = getView();
                if (view == null) {
                    return;
                }
                // 此时是从网络回送回来的，并不保证处于主线程状态
                // 强制执行在主线程中
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        // 调用主界面注册失败显示错误
                        view.showError(strRes);
                    }
                });
            }

            @Override
            public void onDataLoaded(final List<BankItemModel> list) {
                final BankContract.View view = getView();
                if (view == null) {
                    return;
                }
                // 此时是从网络回送回来的，并不保证处于主线程状态
                // 强制执行在主线程中
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        // 调用主界面登录成功
                        view.listLoaded(list);
                    }
                });
            }
        });
    }
}
