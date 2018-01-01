package net.qiujuer.italker.push.frags.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import net.qiujuer.italker.common.app.Application;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.presenter.user.UpdateInfoContract;
import net.qiujuer.italker.factory.presenter.user.UpdateInfoPresenter;
import net.qiujuer.italker.push.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeNickFragment extends PresenterFragment<UpdateInfoContract.Presenter>
        implements UpdateInfoContract.View {
    public static final String USER_KEY = "USER_KEY";
    private User mUser;

    @BindView(R.id.et_nick)
    EditText mNick;

    @OnClick(R.id.btn_confirm)
    void onConfirmClick(View view) {
        String name = mNick.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Application.showToast("昵称不能为空");
            return;
        }
        mPresenter.updateName(name);
    }

    public static ChangeNickFragment newInstance(User user) {
        ChangeNickFragment fragment = new ChangeNickFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_KEY, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mUser = (User) bundle.getSerializable(USER_KEY);
    }

    public ChangeNickFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_change_nick;
    }

    @Override
    public void updateSucceed() {
        Application.showToast("更新昵称成功");
    }

    @Override
    protected UpdateInfoContract.Presenter initPresenter() {
        return new UpdateInfoPresenter(this);
    }
}
