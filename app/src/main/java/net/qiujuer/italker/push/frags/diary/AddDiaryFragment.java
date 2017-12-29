package net.qiujuer.italker.push.frags.diary;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import net.qiujuer.italker.common.app.Application;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.factory.model.api.diary.DiaryRspModel;
import net.qiujuer.italker.factory.presenter.diary.DiaryAddContract;
import net.qiujuer.italker.factory.presenter.diary.DiaryAddPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.DiaryActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDiaryFragment extends PresenterFragment<DiaryAddContract.Presenter> implements DiaryAddContract.View {
    @BindView(R.id.et_diary_content)
    EditText mAddContent;

    @OnClick(R.id.frameLayout)
    public void onFrameClick(View view){
        mAddContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(mAddContent, 0);
            }
        },50);
    }

    public AddDiaryFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_add_diary;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mAddContent.requestFocus();

        mAddContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(mAddContent, 0);
            }
        },50);
        ((DiaryActivity)getActivity()).displayCompleteTextView();
    }

    public void submitAddDiary() {
        mPresenter.addDiary(mAddContent.getText().toString());
    }

    @Override
    public void addDiarySucceed(DiaryRspModel model) {
        if (!TextUtils.isEmpty(model.getErrorInfo())){
            Application.showToast(model.getErrorInfo());
        }else {
            Application.showToast("成功增加一条日记");
        }

        onBackPressed();
    }

    @Override
    protected DiaryAddContract.Presenter initPresenter() {
        return new DiaryAddPresenter(this);
    }

    @Override
    public boolean onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
        ((DiaryActivity)getActivity()).displayAddButton();
        return true;
    }
}
