package net.qiujuer.italker.factory.presenter.contact;

import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.data.helper.UserHelper;
import net.qiujuer.italker.factory.model.api.user.UserUpdateModel;
import net.qiujuer.italker.factory.model.card.UserCard;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.persistence.Account;
import net.qiujuer.italker.factory.presenter.BasePresenter;
import net.qiujuer.italker.factory.presenter.user.UpdateInfoContract;
import net.qiujuer.italker.factory.presenter.user.UpdateInfoPresenter;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class PersonalPresenter extends BasePresenter<PersonalContract.View>
        implements PersonalContract.Presenter {

    private User user;

    public PersonalPresenter(PersonalContract.View view) {
        super(view);
    }


    @Override
    public void start() {
        super.start();

        // 个人界面用户数据优先从网络拉取
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                PersonalContract.View view = getView();
                if (view != null) {
                    String id = view.getUserId();
                    User user = UserHelper.searchFirstOfNet(id);
                    onLoaded(user);
                }
            }
        });

    }

    /**
     * 进行界面的设置
     *
     * @param user 用户信息
     */
    private void onLoaded(final User user) {
        this.user = user;
        // 是否就是我自己
        final boolean isSelf = user.getId().equalsIgnoreCase(Account.getUserId());
        // 是否已经关注
        final boolean isFollow = isSelf || user.isFollow();
        // 已经关注同时不是自己才能聊天
        final boolean allowSayHello = isFollow && !isSelf;

        // 切换到Ui线程
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                final PersonalContract.View view = getView();
                if (view == null)
                    return;
                view.onLoadDone(user);
                view.setFollowStatus(isFollow);
                view.allowSayHello(allowSayHello);
            }
        });
    }

    @Override
    public User getUserPersonal() {
        return user;
    }

    @Override
    public void saveProfile(User user) {
        start();

        final PersonalContract.View view = getView();
        if (user == null || TextUtils.isEmpty(user.getName()) || TextUtils.isEmpty(user.getPortrait()) || TextUtils.isEmpty(user.getDesc()) || user.getSex() == 0) {
            view.showError(R.string.data_profile_invalid_parameter);
        } else {
            UserUpdateModel model = new UserUpdateModel(user.getName(), user.getPortrait(), user.getDesc(), user.getSex());
            UserHelper.update(model, new DataSource.Callback<UserCard>() {
                @Override
                public void onDataNotAvailable(int strRes) {
                    view.showError(R.string.data_profile_update_invalid_parameter);
                }

                @Override
                public void onDataLoaded(UserCard userCard) {
                    onLoaded(userCard.build());
                }
            });
        }
    }
}
