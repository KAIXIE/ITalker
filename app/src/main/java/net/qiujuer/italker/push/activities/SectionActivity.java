package net.qiujuer.italker.push.activities;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.section.SectionFragment;

import butterknife.OnClick;

public class SectionActivity extends Activity {

    public static void show(Context context) {
        context.startActivity(new Intent(context, SectionActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_section;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        SectionFragment sectionFragment = new SectionFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.lay_container,sectionFragment,SectionFragment.class.getName()).addToBackStack(null).commit();
    }

    @OnClick(R.id.ib_back)
    public void onBackClick(View view) {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
