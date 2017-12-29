package net.qiujuer.italker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.payment.PaymentCodeFragment;

import butterknife.BindView;

public class PaymentCodeActivity extends Activity {
    @BindView(R.id.appbar)
    View mLayAppbar;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_payment_code;
    }


    public static void show(Context context) {
        Intent intent = new Intent(context, PaymentCodeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Glide.with(this)
                .load(R.drawable.bg_login)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLayAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });
        getSupportFragmentManager().beginTransaction().add(R.id.lay_container, new PaymentCodeFragment(), PaymentCodeFragment.class.getName()).addToBackStack(null).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean b = ((PaymentCodeFragment) getSupportFragmentManager().findFragmentByTag(PaymentCodeFragment.class.getName())).onKeyDown(keyCode, event);
        return b || super.onKeyDown(keyCode, event);

    }
}
