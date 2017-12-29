package net.qiujuer.italker.push.frags.main;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.github.mzule.ninegridlayout.NineGridLayout;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.api.black.BlackItemModel;
import net.qiujuer.italker.factory.model.api.black.BlackPagerRspModel;
import net.qiujuer.italker.factory.presenter.black.BlackContract;
import net.qiujuer.italker.factory.presenter.black.BlackPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.BlackDetailActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlackFragment extends PresenterFragment<BlackContract.Presenter> implements BlackContract.View {

    @BindView(R.id.rv_black_list)
    RecyclerView mRecyclerView;

    private BlackDetailFragment mBlackDetailFragment;

    private Adapter mAdapter = new Adapter();

    public BlackFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_black;
    }

    @Override
    protected void initData() {
        super.initData();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadList("1", "10", "1");
            }
        }).start();

    }

    @Override
    public void listLoaded(BlackPagerRspModel model) {
        mAdapter.add(model.getRows());
    }

    @Override
    protected BlackContract.Presenter initPresenter() {
        return new BlackPresenter(this);
    }


    public class Adapter extends RecyclerAdapter<BlackItemModel> {

        public Adapter() {
            mDataList.add(new BlackItemModel());
        }

        @Override
        protected int getItemViewType(int position, BlackItemModel blackItemModel) {
            if (position == 0) {
                return R.layout.cell_black_head;
            }

            if (TextUtils.isEmpty(blackItemModel.getPreviewimg())) {
                return R.layout.cell_black_list_no_image;
            }
            return R.layout.cell_black_list;
        }

        @Override
        protected ViewHolder<BlackItemModel> onCreateViewHolder(View root, int viewType) {
            if (viewType == R.layout.cell_black_head) {
                return new HeadViewHolder(root);
            }
            return new BlackFragment.ViewHolder(root);
        }
    }

    public class HeadViewHolder extends RecyclerAdapter.ViewHolder<BlackItemModel> {

        public HeadViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(BlackItemModel blackItemModel) {

        }
    }

    public class ViewHolder extends RecyclerAdapter.ViewHolder<BlackItemModel> {
        @BindView(R.id.iv_head)
        ImageView mHeadImg;
        @BindView(R.id.tv_author)
        TextView mAuthor;
        @BindView(R.id.tv_date)
        TextView mDate;
        @Nullable
        @BindView(R.id.nineGridLayout)
        NineGridLayout mNineGridLayout;
        @BindView(R.id.tv_look_num)
        TextView mLookNum;
        @BindView(R.id.tv_introduction)
        TextView mIntroduction;
        @BindView(R.id.tv_like_num)
        TextView mLikeNum;

        @BindView(R.id.btn_detail)
        Button mDetailButton;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(BlackItemModel blackItemModel) {
            Glide.with(getContext())
                    .load(blackItemModel.getHeadImg()) // 加载路径
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 不使用缓存，直接从原图加载
                    .placeholder(net.qiujuer.italker.common.R.color.grey_200)// 默认颜色
                    .into(mHeadImg);
            mAuthor.setText(blackItemModel.getAuthor());
            mIntroduction.setText(blackItemModel.getIntroduction());
            mLookNum.setText(blackItemModel.getView());
            mLikeNum.setText(blackItemModel.getCollect());
            mDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BlackDetailActivity.show(getContext(), mData.getId(), mData.getHeadImg());
                }
            });


            if (!TextUtils.isEmpty(blackItemModel.getPreviewimg())) {
                String previewimg = blackItemModel.getPreviewimg();
                String[] previewArr = previewimg.split(";");
                for (String preview : previewArr) {
                    ImageView imageView = new ImageView(getContext());
                    Glide.with(getContext())
                            .load(preview) // 加载路径
                            .diskCacheStrategy(DiskCacheStrategy.ALL) // 不使用缓存，直接从原图加载
                            .placeholder(net.qiujuer.italker.common.R.color.grey_200)// 默认颜色
                            .into(imageView);
                    mNineGridLayout.addView(imageView);
                }
            }


        }
    }
}
