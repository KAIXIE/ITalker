package net.qiujuer.italker.push.activities;

import android.content.Context;
import android.content.Intent;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.BankcardFragment;

public class BankcardActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_bankcard;
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context, BankcardActivity.class));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getSupportFragmentManager().beginTransaction().add(R.id.lay_container, new BankcardFragment(), BankcardFragment.class.getName()).addToBackStack(null).commit();
    }


}
