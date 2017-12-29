package net.qiujuer.italker.factory.presenter.section;

import net.qiujuer.italker.factory.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/12/8.
 */

public class SectionDetailPresenter extends BasePresenter<SectionDetailContract.View> implements SectionDetailContract.Presenter {

    public SectionDetailPresenter(SectionDetailContract.View view) {
        super(view);
    }

    @Override
    public void loadSectionDetail(String sectionId) {

    }
}
