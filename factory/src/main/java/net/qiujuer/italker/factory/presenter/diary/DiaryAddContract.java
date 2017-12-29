package net.qiujuer.italker.factory.presenter.diary;

import net.qiujuer.italker.factory.model.api.diary.DiaryPagerRspModel;
import net.qiujuer.italker.factory.model.api.diary.DiaryRspModel;
import net.qiujuer.italker.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2017/12/12.
 */

public interface DiaryAddContract {
    interface View extends BaseContract.View<Presenter> {
        // 列表加载完成
        void addDiarySucceed(DiaryRspModel model);
    }

    interface Presenter extends BaseContract.Presenter {
        // 请求黑板列表
        void addDiary(String content);
    }
}
