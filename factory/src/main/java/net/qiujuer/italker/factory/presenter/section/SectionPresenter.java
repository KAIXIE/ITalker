package net.qiujuer.italker.factory.presenter.section;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.data.helper.SectionHelper;
import net.qiujuer.italker.factory.model.api.section.SectionFenyeModel;
import net.qiujuer.italker.factory.model.api.section.SectionPagerRspModel;
import net.qiujuer.italker.factory.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/12/7.
 */

public class SectionPresenter extends BasePresenter<SectionContract.View> implements
        SectionContract.Presenter {


    public SectionPresenter(SectionContract.View view) {
        super(view);
    }

    @Override
    public void loadList(String page, String rows, String articleType) {
        SectionFenyeModel model = new SectionFenyeModel(page, rows, articleType);

        SectionHelper.loadList(model, new DataSource.Callback<SectionPagerRspModel>() {
            @Override
            public void onDataNotAvailable(final int strRes) {
                // 网络请求告知登录失败
                final SectionContract.View view = getView();
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
            public void onDataLoaded(final SectionPagerRspModel model) {
// 当网络请求成功，登录好了，回送一个用户信息回来
                // 告知界面，登录成功
                final SectionContract.View view = getView();
                if (view == null) {
                    return;
                }
                // 此时是从网络回送回来的，并不保证处于主线程状态
                // 强制执行在主线程中
                Run.onUiAsync(new Action() {
                    @Override
                    public void call() {
                        // 调用主界面登录成功
                        view.listLoaded(model);
                    }
                });
            }
        });
    }
}
