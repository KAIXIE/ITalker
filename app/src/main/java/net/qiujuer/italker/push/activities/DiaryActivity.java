package net.qiujuer.italker.push.activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.diary.AddDiaryFragment;
import net.qiujuer.italker.push.frags.diary.DiaryFragment;
import net.qiujuer.italker.push.frags.diary.EditDiaryFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class DiaryActivity extends Activity {
    public boolean isAdd = false;

    @BindView(R.id.add_diary)
    ImageView mAddButton;
    @BindView(R.id.tv_complete)
    TextView mCompleteButton;
    @BindView(R.id.tv_delete)
    TextView mDeleteButton;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_diary;
    }


    @OnClick(R.id.tv_delete)
    public void onDeleteButtonClick(){
        EditDiaryFragment fragment = (EditDiaryFragment) getSupportFragmentManager().findFragmentByTag(EditDiaryFragment.class.getName());
        fragment.deleteDiary();
    }

    @OnClick(R.id.add_diary)
    public void onAddDiaryClick(View view) {
        Toast.makeText(this,"添加",Toast.LENGTH_SHORT).show();
        mAddButton.setVisibility(View.GONE);
        isAdd = true;
        getSupportFragmentManager().beginTransaction().replace(R.id.lay_container, new AddDiaryFragment(), AddDiaryFragment.class.getName()).addToBackStack(null).commit();
    }

    @OnClick(R.id.tv_complete)
    public void onCompleteButtonClick(View view) {
        if (isAdd){
            AddDiaryFragment fragment = (AddDiaryFragment) getSupportFragmentManager().findFragmentByTag(AddDiaryFragment.class.getName());
            fragment.submitAddDiary();
        } else {
            EditDiaryFragment fragment = (EditDiaryFragment) getSupportFragmentManager().findFragmentByTag(EditDiaryFragment.class.getName());
            fragment.submitEditDiary();
        }


    }

    @OnClick(R.id.ib_back)
    public void onBackClick(View view) {
        displayAddButton();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
        hideDeleteButton();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        DiaryFragment diaryFragment = new DiaryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.lay_container, diaryFragment, DiaryFragment.class.getName()).addToBackStack(null).commit();
    }


    public static void show(Context context) {
        context.startActivity(new Intent(context, DiaryActivity.class));
    }


    public void displayCompleteTextView() {
        mCompleteButton.setVisibility(View.VISIBLE);
    }

    public void displayAddButton(){
        mAddButton.setVisibility(View.VISIBLE);
        mCompleteButton.setVisibility(View.GONE);
    }


    public void hideAddButton() {
        mAddButton.setVisibility(View.GONE);
    }

    public void hideCompleteButton() {
        mCompleteButton.setVisibility(View.GONE);
    }

    public void displayDeleteButton(){
        mDeleteButton.setVisibility(View.VISIBLE);
    }

    public void hideDeleteButton() {
        mDeleteButton.setVisibility(View.GONE);
    }
}
