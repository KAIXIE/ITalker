package net.qiujuer.italker.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.push.activities.AccountActivity;

import butterknife.OnClick;

public class ChooseLoginRegisterActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_choose_login_register;
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context, ChooseLoginRegisterActivity.class));
    }

    @OnClick(R.id.ib_login)
    public void onLoginClick(View view){
        skip(AccountActivity.IS_LOGIN);
    }
    @OnClick(R.id.ib_register)
    public void onRegisterClick(View view){
        skip(AccountActivity.IS_REGISTER);
    }

    private void skip(int skipParam) {
        Intent intent = new Intent(this, AccountActivity.class);
        Bundle b = new Bundle();
        b.putInt(AccountActivity.PARAM_KEY, skipParam); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        finish();
    }

}
