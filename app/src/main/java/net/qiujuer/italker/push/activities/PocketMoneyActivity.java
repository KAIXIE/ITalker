package net.qiujuer.italker.push.activities;

import android.content.Context;
import android.content.Intent;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.pocketmoney.PocketMoneyFragment;

public class PocketMoneyActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_pocket_money;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        PocketMoneyFragment pocketMoneyFragment = new PocketMoneyFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.lay_container, pocketMoneyFragment,PocketMoneyFragment.class.getName()).addToBackStack(null).commit();
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, PocketMoneyActivity.class);
        context.startActivity(intent);
    }
}
