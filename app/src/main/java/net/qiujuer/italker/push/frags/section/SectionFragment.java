package net.qiujuer.italker.push.frags.section;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.mzule.ninegridlayout.NineGridLayout;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.tools.UiTool;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.api.black.BlackItemModel;
import net.qiujuer.italker.factory.model.api.section.SectionItemModel;
import net.qiujuer.italker.factory.model.api.section.SectionPagerRspModel;
import net.qiujuer.italker.factory.presenter.section.SectionContract;
import net.qiujuer.italker.factory.presenter.section.SectionPresenter;
import net.qiujuer.italker.push.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SectionFragment extends PresenterFragment<SectionContract.Presenter> implements SectionContract.View {

    boolean isLoaded = false;
    @BindView(R.id.rv_section_list)
    RecyclerView mRecyclerView;
    Adapter mAdapter = new Adapter();

    public SectionFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_section;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        if (!isLoaded) {
            isLoaded = true;
            mPresenter.loadList("1", "10", "2");
        }
    }

    @Override
    public void listLoaded(SectionPagerRspModel model) {
        mAdapter.add(model.getRows());
    }

    @Override
    protected SectionContract.Presenter initPresenter() {
        return new SectionPresenter(this);
    }

    public class Adapter extends RecyclerAdapter<SectionItemModel> {
        @Override
        protected int getItemViewType(int position, SectionItemModel itemModel) {
            if (TextUtils.isEmpty(itemModel.getPreviewimg())) {
                return R.layout.cell_section_list_no_image;
            }
            return R.layout.cell_section_list;
        }

        @Override
        protected ViewHolder<SectionItemModel> onCreateViewHolder(View root, int viewType) {
            return new SectionFragment.ViewHolder(root);
        }
    }

    public class ViewHolder extends RecyclerAdapter.ViewHolder<SectionItemModel> {

        @BindView(R.id.iv_head)
        ImageView mHeadImage;
        @BindView(R.id.tv_author)
        TextView mAuthor;
        @Nullable
        @BindView(R.id.nineGridLayout)
        NineGridLayout mNineGridLayout;
        @BindView(R.id.tv_introduction)
        TextView mIntroduction;
        @BindView(R.id.ib_section_bad)
        ImageButton mSectionBad;
        @BindView(R.id.tv_section_bad_num)
        TextView mBadNum;

        @BindView(R.id.ib_section_good)
        ImageButton mSectionGood;

        @BindView(R.id.tv_section_good_num)
        TextView mGoodNum;

        @OnClick(R.id.tv_introduction)
        void onGoSectionDetailClick(View view) {
            SectionDetailFragment sectionDetailFragment = SectionDetailFragment.newInstance(mData.getId(), mData.getHeadImg());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lay_container, sectionDetailFragment, SectionDetailFragment.class.getName()).addToBackStack(null).commit();
        }


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(SectionItemModel itemModel) {
            Glide.with(getContext()).load(itemModel.getHeadImg()).asBitmap().centerCrop().into(new BitmapImageViewTarget(mHeadImage) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    mHeadImage.setImageDrawable(circularBitmapDrawable);
                }
            });
            mAuthor.setText(itemModel.getAuthor());
            if (!TextUtils.isEmpty(itemModel.getPreviewimg())) {
                String[] previewImgs = itemModel.getPreviewimg().split(";");
                for (final String previewImg : previewImgs) {
                    ImageView imageView = new ImageView(getContext());
                    Glide.with(getContext())
                            .load(previewImg) // 加载路径
                            .diskCacheStrategy(DiskCacheStrategy.ALL) // 不使用缓存，直接从原图加载
                            .placeholder(net.qiujuer.italker.common.R.color.grey_200)// 默认颜色
                            .into(imageView);
                    mNineGridLayout.addView(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "点击放大", Toast.LENGTH_SHORT).show();
                            showPopupWindow(previewImg);
                        }
                    });
                }

            }

            mIntroduction.setText(itemModel.getIntroduction());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showPopupWindow(String previewImg) {
        //设置contentView
        final ImageView contentView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.popup_window_enlarge_photo, null);
        final PopupWindow mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);
        Glide.with(getContext())
                .load(previewImg) // 加载路径
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 不使用缓存，直接从原图加载
                .placeholder(net.qiujuer.italker.common.R.color.grey_200)// 默认颜色
                .into(contentView);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.cell_section_list, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }
}
