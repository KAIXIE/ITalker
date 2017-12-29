package net.qiujuer.italker.push.activities;


import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.setting.ChangePortraitFragment;
import net.qiujuer.italker.push.frags.setting.SettingFragment;

public class SettingActivity extends Activity {


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getSupportFragmentManager().beginTransaction().add(R.id.lay_container, new SettingFragment(), SettingFragment.class.getName()).addToBackStack(null).commit();
    }


    public static void show(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentByTag(ChangePortraitFragment.class.getName());
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
