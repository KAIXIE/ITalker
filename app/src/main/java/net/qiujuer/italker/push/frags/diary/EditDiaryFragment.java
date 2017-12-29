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
import net.qiujuer.italker.factory.model.api.diary.DiaryItemModel;
import net.qiujuer.italker.factory.model.api.diary.DiaryRspModel;
import net.qiujuer.italker.factory.presenter.diary.DiaryEditContract;
import net.qiujuer.italker.factory.presenter.diary.DiaryEditPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.DiaryActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditDiaryFragment extends PresenterFragment<DiaryEditContract.Presenter> implements DiaryEditContract.View {
    private static final String DIARY_ITEM_MODEL_KEY = "DIARY_ITEM_MODEL_KEY";
    private DiaryItemModel mDiaryItemModel;
    @BindView(R.id.et_diary_content)
    EditText mEditContent;

    public static EditDiaryFragment newInstance(DiaryItemModel diaryItemModel) {
        EditDiaryFragment myFragment = new EditDiaryFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(DIARY_ITEM_MODEL_KEY, diaryItemModel);
        myFragment.setArguments(bundle);

        return myFragment;
    }

    @OnClick(R.id.frameLayout)
    public void onFrameClick(View view){
        mEditContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(mEditContent, 0);
            }
        },50);
    }

    public EditDiaryFragment() {
        // Required empty public constructor
    }
    public void submitEditDiary() {
        mPresenter.editDiary(mDiaryItemModel);
    }

    public void deleteDiary(){
        mPresenter.deleteDiary(mDiaryItemModel.getId());
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_edit_diary;
    }

    @Override
    public void editDiarySucceed(DiaryRspModel model) {
        if (!TextUtils.isEmpty(model.getErrorInfo())){
            Application.showToast(model.getErrorInfo());
        }else {
            Application.showToast("成功修改一条日记");
        }
        onBackPressed();
    }

    @Override
    public void deleteDiarySucceed(DiaryRspModel model) {
        if (!TextUtils.isEmpty(model.getErrorInfo())){
            Application.showToast(model.getErrorInfo());
        }else {
            Application.showToast("成功删除一条日记");
        }
        onBackPressed();
    }

    @Override
    protected DiaryEditContract.Presenter initPresenter() {
        return new DiaryEditPresenter(this);
    }

    @Override
    public boolean onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
        ((DiaryActivity)getActivity()).displayAddButton();
        ((DiaryActivity)getActivity()).hideDeleteButton();
        return true;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mEditContent.requestFocus();

        mEditContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(mEditContent, 0);
            }
        },50);
        mEditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mDiaryItemModel.setContent(s.toString());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mDiaryItemModel = (DiaryItemModel) getArguments().getSerializable(
                DIARY_ITEM_MODEL_KEY);
        mEditContent.setText(mDiaryItemModel.getContent());
        mEditContent.setSelection(mDiaryItemModel.getContent().length());
        ((DiaryActivity)getActivity()).displayCompleteTextView();
        ((DiaryActivity)getActivity()).displayDeleteButton();
    }



}
