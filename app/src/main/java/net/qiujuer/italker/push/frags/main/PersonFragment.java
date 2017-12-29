package net.qiujuer.italker.push.frags.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.PortraitView;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.api.person.UserInfoModel;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.persistence.Account;
import net.qiujuer.italker.factory.presenter.contact.PersonalContract;
import net.qiujuer.italker.factory.presenter.contact.PersonalPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.BankcardActivity;
import net.qiujuer.italker.push.activities.ContinuousCaptureActivity;
import net.qiujuer.italker.push.activities.DiaryActivity;
import net.qiujuer.italker.push.activities.PaymentCodeActivity;
import net.qiujuer.italker.push.activities.PocketMoneyActivity;
import net.qiujuer.italker.push.activities.SectionActivity;
import net.qiujuer.italker.push.activities.SettingActivity;
import net.qiujuer.italker.push.helper.PersonRVModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends PresenterFragment<PersonalContract.Presenter> implements PersonalContract.View {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.tv_name)
    TextView mName;

    @BindView(R.id.tv_desc)
    TextView mDesc;

    @BindView(R.id.im_sex)
    ImageView mSex;

    private Adapter mAdapter = new Adapter();

    public PersonFragment() {
        mAdapter.add(new PersonRVModel("相册", R.drawable.album_person));
        mAdapter.add(new PersonRVModel("日记本", R.drawable.note_person));
        mAdapter.add(new PersonRVModel("设置", R.drawable.set_person));
        mAdapter.add(new PersonRVModel("段子", R.drawable.satin_person));
        mAdapter.add(new PersonRVModel("零花钱", R.drawable.money_person));
        mAdapter.add(new PersonRVModel("扫一扫", R.drawable.person_scan));
        mAdapter.add(new PersonRVModel("付款码", R.drawable.person_payment_code));
        mAdapter.add(new PersonRVModel("银行卡", R.drawable.person_bank_card));
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    public String getUserId() {
        return Account.getUserId();
    }

    @Override
    public void onLoadDone(User user) {
        if (user == null)
            return;
        mPortrait.setup(Glide.with(this), user);
        mName.setText(user.getName());
        mDesc.setText(user.getDesc());
        boolean isMan = user.getSex() == 0 ? true : false;
        Drawable drawable = getResources().getDrawable(isMan ?
                R.drawable.ic_sex_man : R.drawable.ic_sex_woman);
        mSex.setImageDrawable(drawable);
    }

    @Override
    public void allowSayHello(boolean isAllow) {

    }

    @Override
    public void setFollowStatus(boolean isFollow) {

    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return new PersonalPresenter(this);
    }


    private class Adapter extends RecyclerAdapter<PersonRVModel> {

        @Override
        protected int getItemViewType(int position, PersonRVModel personRVModel) {
            return R.layout.cell_person_rv;
        }

        @Override
        protected ViewHolder<PersonRVModel> onCreateViewHolder(View root, int viewType) {
            return new PersonFragment.ViewHolder(root);
        }
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<PersonRVModel> {
        @BindView(R.id.iv_image)
        ImageView mImageView;
        @BindView(R.id.tv_name)
        TextView mName;

        @OnClick(R.id.function_cell)
        void onFunctioCellClick(View view) {
            String functionName = mName.getText().toString();

            if (TextUtils.equals(functionName, "相册")) {
                Toast.makeText(getContext(), "相册", Toast
                        .LENGTH_SHORT).show();

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickPhoto.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
            }
            if (TextUtils.equals(functionName, "日记本")) {
                Toast.makeText(getContext(), "日记本", Toast
                        .LENGTH_SHORT).show();
                DiaryActivity.show(getContext());
            }


            if (TextUtils.equals(functionName, "设置")) {
                Toast.makeText(getContext(), "设置", Toast
                        .LENGTH_SHORT).show();
                SettingActivity.show(getContext());
            }

            if (TextUtils.equals(functionName, "段子")) {
                Toast.makeText(getContext(), "段子", Toast
                        .LENGTH_SHORT).show();
                SectionActivity.show(getContext());
            }


            if (TextUtils.equals(functionName, "零花钱")) {
                Toast.makeText(getContext(), "零花钱", Toast
                        .LENGTH_SHORT).show();

                PocketMoneyActivity.show(getContext());
            }

            if (TextUtils.equals(functionName, "扫一扫")) {
                Toast.makeText(getContext(), "扫一扫", Toast
                        .LENGTH_SHORT).show();
                ContinuousCaptureActivity.show(getContext());
            }


            if (TextUtils.equals(functionName, "付款码")) {
                Toast.makeText(getContext(), "付款码", Toast
                        .LENGTH_SHORT).show();
                PaymentCodeActivity.show(getContext());
            }

            if (TextUtils.equals(functionName, "银行卡")) {
                Toast.makeText(getContext(), "银行卡", Toast
                        .LENGTH_SHORT).show();
                BankcardActivity.show(getContext());
            }
        }


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(PersonRVModel personRVModel) {
            mImageView.setImageResource(personRVModel.getImagePath());
            mName.setText(personRVModel.getName());


        }
    }

}
