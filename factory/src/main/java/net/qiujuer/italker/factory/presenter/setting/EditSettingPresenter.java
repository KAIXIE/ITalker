package net.qiujuer.italker.factory.presenter.setting;

import android.content.Context;
import android.net.Uri;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.data.helper.SettingHelper;
import net.qiujuer.italker.factory.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/12/6.
 */

public class EditSettingPresenter extends BasePresenter<EditSettingContract.View> implements EditSettingContract.Presenter, DataSource.Callback<String> {


    public EditSettingPresenter(EditSettingContract.View view) {
        super(view);
    }

    @Override
    public void uploadProfile(String portraitUrl, String nickname, String motto, String sex) {
        SettingHelper.uploadProfile(portraitUrl, nickname, motto, sex, this);
    }

    @Override
    public void uploadPortrait(Context context, Uri uri) {
        SettingHelper.uploadFile(context, uri, this);
    }

    @Override
    public void uploadBackground(Context context, Uri uri) {

    }

    @Override
    public void onDataLoaded(final String result) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                EditSettingContract.View view = getView();
                view.updateSucceed(result);
            }
        });
    }


    @Override
    public void onDataNotAvailable(int strRes) {

    }
}
