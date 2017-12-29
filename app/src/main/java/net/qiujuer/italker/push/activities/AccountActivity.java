package net.qiujuer.italker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.account.AccountTrigger;
import net.qiujuer.italker.push.frags.account.LoginFragment;
import net.qiujuer.italker.push.frags.account.RegisterFragment;

import butterknife.BindView;

public class AccountActivity extends Activity implements AccountTrigger {
    private Fragment mCurFragment;
    private Fragment mLoginFragment;
    private Fragment mRegisterFragment;

    public static final int IS_LOGIN = 1;
    public static final int IS_REGISTER = 2;

    public static final String PARAM_KEY = "PARAM_KEY";

    private int pageFlag = -1;

    @BindView(R.id.im_bg)
    ImageView mBg;

    /**
     * 账户Activity显示的入口
     *
     * @param context Context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        if (pageFlag == IS_REGISTER) {
            mCurFragment = mRegisterFragment = new RegisterFragment();
        } else {
            mCurFragment = mLoginFragment = new LoginFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<ImageView, GlideDrawable>(mBg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        Drawable drawable = resource.getCurrent();
                        drawable = DrawableCompat.wrap(drawable);
                        drawable.setColorFilter(UiCompat.getColor(getResources(), R.color.colorAccent), PorterDuff.Mode.SCREEN);
                        this.view.setImageDrawable(drawable);
                    }
                });

    }

    @Override
    public void triggerView() {
        Fragment fragment;
        if (mCurFragment == mLoginFragment) {
            if (mRegisterFragment == null) {
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        } else {
            if (mLoginFragment == null) {
                mLoginFragment = new LoginFragment();
            }
            fragment = mLoginFragment;
        }

        mCurFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.lay_container, fragment)
                .commit();

    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            pageFlag = b.getInt(PARAM_KEY);
        }
        return super.initArgs(bundle);
    }

}
