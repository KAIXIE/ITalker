package net.qiujuer.italker.factory.presenter.setting;

import android.content.Context;
import android.net.Uri;

import net.qiujuer.italker.factory.presenter.BaseContract;

/**
 * 更新用户信息的基本契约
 * Created by Administrator on 2017/12/6.
 */

public interface EditSettingContract {
    interface Presenter extends BaseContract.Presenter{
        // 更新
        void uploadProfile(String portraitUrl, String nickname, String motto, String sex);

        void uploadPortrait(Context context, Uri uri);

        void uploadBackground(Context context, Uri uri);
    }

    interface View extends BaseContract.View<Presenter> {
        // 回调成功
        void updateSucceed(String result);
    }
}
