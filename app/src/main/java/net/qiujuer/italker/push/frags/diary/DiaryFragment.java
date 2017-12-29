package net.qiujuer.italker.push.frags.diary;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import net.qiujuer.italker.common.app.Application;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.api.diary.DiaryItemModel;
import net.qiujuer.italker.factory.model.api.diary.DiaryPagerRspModel;
import net.qiujuer.italker.factory.persistence.Account;
import net.qiujuer.italker.factory.presenter.diary.DiaryContract;
import net.qiujuer.italker.factory.presenter.diary.DiaryPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.DiaryActivity;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends PresenterFragment<DiaryContract.Presenter> implements DiaryContract.View {
    boolean isRefresh = false;

    boolean isLoaded = false;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private Adapter mAdapter = new Adapter();

    public DiaryFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_diary;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<DiaryItemModel>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, DiaryItemModel model) {
                EditDiaryFragment editDiaryFragment = EditDiaryFragment.newInstance(model);
                ((DiaryActivity)getActivity()).hideAddButton();
                ((DiaryActivity)getActivity()).isAdd = false;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lay_container,editDiaryFragment, EditDiaryFragment.class.getName()).addToBackStack(null).commit();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        if (!isLoaded) {
            if (TextUtils.isEmpty(Account.getOtherToken())) {
                Application.showToast("请先登录");
            }
            mPresenter.loadList("1000", "1", Account.getOtherToken());
            isRefresh = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadList("1000", "1", Account.getOtherToken());
    }

    @Override
    public void listLoaded(DiaryPagerRspModel model) {
        if (isRefresh){
            List<DiaryItemModel> rows = model.getRows();
            Collections.sort(rows);
            mAdapter.replace(rows);
        } else {
            List<DiaryItemModel> rows = model.getRows();
            Collections.sort(rows);
            mAdapter.add(rows);
        }

    }

    @Override
    protected DiaryContract.Presenter initPresenter() {
        return new DiaryPresenter(this);
    }

    public class Adapter extends RecyclerAdapter<DiaryItemModel> {

        @Override
        protected int getItemViewType(int position, DiaryItemModel diaryItemModel) {
            return R.layout.cell_diary_list;
        }

        @Override
        protected ViewHolder<DiaryItemModel> onCreateViewHolder(View root, int viewType) {
            return new DiaryFragment.ViewHolder(root);
        }
    }

    public class ViewHolder extends RecyclerAdapter.ViewHolder<DiaryItemModel> {
        @BindView(R.id.tv_brief)
        TextView mBrief;
        @BindView(R.id.tv_updateTime)
        TextView mUpdateTime;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(DiaryItemModel diaryItemModel) {
            mBrief.setText(diaryItemModel.getBrief());
            mUpdateTime.setText(diaryItemModel.getUpdateTime());
        }
    }

    @Override
    public boolean onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
        return super.onBackPressed();
    }
}
