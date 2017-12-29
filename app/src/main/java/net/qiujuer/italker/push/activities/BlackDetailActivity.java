package net.qiujuer.italker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.main.BlackDetailFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class BlackDetailActivity extends Activity {
    @BindView(R.id.appbar)
    View mLayAppbar;


    private String mBlackDetailId;
    private String mHeadImgPath;

    public static void show(Context context, String detailId, String headImg) {
        Intent intent = new Intent(context, BlackDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BlackDetailFragment.DETAIL_ID, detailId);
        bundle.putString(BlackDetailFragment.HEAD_IMG_PATH, headImg);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_black_detail;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (bundle != null) {
            mBlackDetailId = bundle.getString(BlackDetailFragment.DETAIL_ID);
            mHeadImgPath = bundle.getString(BlackDetailFragment.HEAD_IMG_PATH);
        }
        return super.initArgs(bundle);
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
        BlackDetailFragment detailFragment = BlackDetailFragment.newInstance(mBlackDetailId, mHeadImgPath);
        getSupportFragmentManager().beginTransaction().add(R.id.lay_container, detailFragment).commit();
    }
    @OnClick(R.id.ib_back)
    public void onBackClick(View view){
        finish();
    }
}
