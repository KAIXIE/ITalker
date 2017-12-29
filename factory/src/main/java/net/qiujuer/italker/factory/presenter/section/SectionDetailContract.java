package net.qiujuer.italker.factory.presenter.section;

import net.qiujuer.italker.factory.model.api.section.SectionDetailModel;
import net.qiujuer.italker.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2017/12/8.
 */

public interface SectionDetailContract {

    interface View extends BaseContract.View<Presenter>{
        // 详情加载完成
        void sectionDetailLoaded(SectionDetailModel model);
    }

    interface Presenter extends BaseContract.Presenter{
        // 请求一条黑板信息的详情
        void loadSectionDetail(String sectionId);
    }
}
