package net.qiujuer.italker.factory.presenter.diary;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.data.helper.DiaryHelper;
import net.qiujuer.italker.factory.model.api.diary.DiaryFenyeModel;
import net.qiujuer.italker.factory.model.api.diary.DiaryPagerRspModel;
import net.qiujuer.italker.factory.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DiaryPresenter extends BasePresenter<DiaryContract.View> implements DiaryContract.Presenter {
    public DiaryPresenter(DiaryContract.View view) {
        super(view);
    }

    @Override
    public void loadList(String rows, String page, String token) {
        {
            DiaryFenyeModel diaryFenyeModel = new DiaryFenyeModel(rows, page, token);
            DiaryHelper.loadList(diaryFenyeModel, new DataSource.Callback<DiaryPagerRspModel>() {

                @Override
                public void onDataNotAvailable(final int strRes) {
                    final DiaryContract.View view = getView();
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
                public void onDataLoaded(final DiaryPagerRspModel diaryPagerRspModel) {
                    final DiaryContract.View view = getView();
                    if (view == null) {
                        return;
                    }
                    // 此时是从网络回送回来的，并不保证处于主线程状态
                    // 强制执行在主线程中
                    Run.onUiAsync(new Action() {
                        @Override
                        public void call() {
                            // 调用主界面登录成功
                            view.listLoaded(diaryPagerRspModel);
                        }
                    });
                }
            });
        }
    }

}
