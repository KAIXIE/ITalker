package net.qiujuer.italker.push.frags.main;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.factory.model.api.black.BlackDetailModel;
import net.qiujuer.italker.factory.presenter.black.BlackDetailContract;
import net.qiujuer.italker.factory.presenter.black.BlackDetailPresenter;
import net.qiujuer.italker.push.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlackDetailFragment extends PresenterFragment<BlackDetailContract.Presenter> implements BlackDetailContract.View,Html.ImageGetter {

    public static final String DETAIL_ID = "DETAIL_ID";
    public static final String HEAD_IMG_PATH = "HEAD_IMG_PATH";

    @BindView(R.id.iv_head)
    ImageView mHeadImg;
    @BindView(R.id.tv_author)
    TextView mAuthor;
    @BindView(R.id.tv_date)
    TextView mDate;
    @BindView(R.id.tv_content)
    TextView mContent;
    @BindView(R.id.tv_look_num)
    TextView mLookNum;
    @BindView(R.id.tv_like_num)
    TextView mLikeNum;

    private String mHeadImgPath;
    private String mBlackId;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public static BlackDetailFragment newInstance(String detailId, String headImgPath) {
        BlackDetailFragment fragment = new BlackDetailFragment();
        Bundle args = new Bundle();
        args.putString(DETAIL_ID, detailId);
        args.putString(HEAD_IMG_PATH, headImgPath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initArgs(Bundle bundle) {
        if (bundle != null) {
            mBlackId = bundle.getString(DETAIL_ID);
            mHeadImgPath = bundle.getString(HEAD_IMG_PATH);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        Glide.with(getContext())
                .load(mHeadImgPath) // 加载路径
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 不使用缓存，直接从原图加载
                .placeholder(net.qiujuer.italker.common.R.color.grey_200)// 默认颜色
                .into(mHeadImg);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadBlackDetail(mBlackId);
            }
        }).start();
    }

    public BlackDetailFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_black_detail;
    }

    @Override
    public void blackDetailLoaded(BlackDetailModel model) {
        mAuthor.setText(model.getAuthor());
        mDate.setText(model.getAddTime());
        Spanned spanned = Html.fromHtml(model.getContent(), this, null);
        mContent.setText(spanned);
        mLookNum.setText(model.getView());
        mLikeNum.setText(model.getCollect());
    }

    @Override
    protected BlackDetailContract.Presenter initPresenter() {
        return new BlackDetailPresenter(this);
    }


    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.drawable.default_portrait);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);
        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];

            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = mContent.getText();
                mContent.setText(t);
            }
        }
    }
}
