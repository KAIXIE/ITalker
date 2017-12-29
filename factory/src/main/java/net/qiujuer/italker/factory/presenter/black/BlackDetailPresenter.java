package net.qiujuer.italker.factory.presenter.black;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.data.helper.BlackHelper;
import net.qiujuer.italker.factory.model.api.black.BlackDetailModel;
import net.qiujuer.italker.factory.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/12/8.
 */

public class BlackDetailPresenter extends BasePresenter<BlackDetailContract.View> implements BlackDetailContract.Presenter {

    public BlackDetailPresenter(BlackDetailContract.View view) {
        super(view);
    }

    @Override
    public void loadBlackDetail(String blackId) {
        BlackHelper.loadDetail(blackId, new DataSource.Callback<BlackDetailModel>() {
            @Override
            public void onDataNotAvailable(final int strRes) {
                // 网络请求告知登录失败
                final BlackDetailContract.View view = getView();
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
            public void onDataLoaded(final BlackDetailModel blackDetailModel) {
                final BlackDetailContract.View view = getView();
                if (view == null) {
                    return;
                }
                // 此时是从网络回送回来的，并不保证处于主线程状态
                // 强制执行在主线程中
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        // 调用主界面登录成功
                        view.blackDetailLoaded(blackDetailModel);
                    }
                });
            }
        });

    }
}
