package net.qiujuer.italker.push.frags.bankcard;


import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.italker.common.app.Application;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.push.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankcardIDCodeFragment extends Fragment {

    @BindView(R.id.appbar)
    View mLayAppbar;

    public BankcardIDCodeFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_bankcard_idcode;
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
    public boolean onBackPressed() {
        Application.showToast("onBackPressed");
        getActivity().getSupportFragmentManager().popBackStack();
        return true;
    }

    @OnClick(R.id.ib_back)
    void onBackClicked(View view) {
        getActivity().getSupportFragmentManager().popBackStack();
    }

}
