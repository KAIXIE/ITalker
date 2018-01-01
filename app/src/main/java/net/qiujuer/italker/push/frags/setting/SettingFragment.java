package net.qiujuer.italker.push.frags.setting;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.PortraitView;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.persistence.Account;
import net.qiujuer.italker.factory.presenter.contact.PersonalContract;
import net.qiujuer.italker.factory.presenter.contact.PersonalPresenter;
import net.qiujuer.italker.push.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PresenterFragment<PersonalContract.Presenter> implements PersonalContract.View {

    @BindView(R.id.appbar)
    View mLayAppbar;

    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;

    @BindView(R.id.tv_phone)
    TextView mPhone;

    public SettingFragment() {
    }

    @OnClick(R.id.ib_back)
    void onBackClick(View view){
        getActivity().finish();
    }

    @OnClick(R.id.edit_profile)
    void onEditProfileClick(View view) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lay_container, new EditProfileFragment(), EditProfileFragment.class.getName()).addToBackStack(null).commit();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Glide.with(this)
                .load(R.drawable.bg_login)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLayAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public String getUserId() {
        return Account.getUserId();
    }

    @Override
    public void onLoadDone(User user) {
        mPortraitView.setup(Glide.with(this), user);
        mPhone.setText(user.getPhone());
    }

    @Override
    public void allowSayHello(boolean isAllow) {

    }

    @Override
    public void setFollowStatus(boolean isFollow) {

    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return new PersonalPresenter(this);
    }
}
