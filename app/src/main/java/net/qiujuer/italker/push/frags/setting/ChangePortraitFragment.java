package net.qiujuer.italker.push.frags.setting;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.qiujuer.italker.common.app.Application;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.presenter.user.UpdateInfoContract;
import net.qiujuer.italker.factory.presenter.user.UpdateInfoPresenter;
import net.qiujuer.italker.push.App;
import net.qiujuer.italker.push.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePortraitFragment extends PresenterFragment<UpdateInfoContract.Presenter>
        implements UpdateInfoContract.View, View.OnClickListener {
    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "net.qiujuer.italker.push.fileprovider";
    public static final String USER_KEY = "USER_KEY";
    private User mUser;
    private PopupWindow mPopWindow;
    @BindView(R.id.imageView)
    ImageView mImageView;
    Uri outputFileUri;

    public ChangePortraitFragment() {
        // Required empty public constructor
    }

    public static ChangePortraitFragment newInstance(User user) {
        ChangePortraitFragment fragment = new ChangePortraitFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_KEY, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mUser = (User) bundle.getSerializable(USER_KEY);
    }

    @Override
    protected void initData() {
        super.initData();
        Glide.with(getContext()).load(mUser.getPortrait()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageView);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_change_portrait;
    }

    @OnClick(R.id.imageView)
    void onChoosePhotoClick(View view) {
        showPopupWindow();
    }


    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popup_window_change_photo, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        TextView fromTakePhoto = (TextView) contentView.findViewById(R.id.pop_from_take_photo);
        TextView fromPhotoGallery = (TextView) contentView.findViewById(R.id.pop_from_photo_gallery);
        TextView savePhoto = (TextView) contentView.findViewById(R.id.save_photo);
        TextView cancel = (TextView) contentView.findViewById(R.id.cancel);
        fromTakePhoto.setOnClickListener(this);
        fromPhotoGallery.setOnClickListener(this);
        savePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
        //显示PopupWindow
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.fragment_change_portrait, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.pop_from_take_photo: {
                Toast.makeText(getContext(), "clicked 拍照", Toast.LENGTH_SHORT).show();
                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File path = new File(getActivity().getFilesDir(), "capture");
                if (!path.exists()) path.mkdirs();
                File image = new File(path, "image.jpg");
                Uri imageUri = FileProvider.getUriForFile(getActivity(), CAPTURE_IMAGE_FILE_PROVIDER, image);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 0);
                mPopWindow.dismiss();
            }
            break;
            case R.id.pop_from_photo_gallery: {
                Toast.makeText(getContext(), "clicked 从相册中选择", Toast.LENGTH_SHORT).show();
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickPhoto.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
                mPopWindow.dismiss();
            }
            break;
            case R.id.save_photo: {
                Toast.makeText(getContext(), "clicked 保存头像图片", Toast.LENGTH_SHORT).show();
                mPresenter.update(outputFileUri.getPath(), mUser.getDesc(), mUser.getSex() == 0);
                mPopWindow.dismiss();
            }
            break;
            case R.id.cancel: {
                Toast.makeText(getContext(), "clicked 取消", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getContext(), "拍照OK", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "拍照NOT OK", Toast.LENGTH_SHORT).show();
                }
                File path = new File(getContext().getFilesDir(), "capture");
                if (!path.exists()) path.mkdirs();
                File imageFile = new File(path, "image.jpg");
                outputFileUri = Uri.fromFile(imageFile);
                mImageView.setImageURI(outputFileUri);
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getContext(), "相册返回OK", Toast.LENGTH_SHORT).show();
                    File file = getBitmapFile(imageReturnedIntent);
                    outputFileUri = Uri.fromFile(file);
                    mImageView.setImageURI(outputFileUri);
                }
                break;
        }
    }

    public File getBitmapFile(Intent data) {
        Uri selectedImage = data.getData();
        Cursor cursor = getContext().getContentResolver().query(selectedImage, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA}, null, null, null);
        cursor.moveToFirst();

        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String selectedImagePath = cursor.getString(idx);
        cursor.close();

        return new File(selectedImagePath);
    }

    @Override
    public void updateSucceed() {
        Application.showToast("保存头像成功");
    }

    @Override
    protected UpdateInfoContract.Presenter initPresenter() {
        return new UpdateInfoPresenter(this);
    }

    @Override
    public boolean onBackPressed() {
        Application.showToast("onBackPressed");
        getActivity().getSupportFragmentManager().popBackStack();
        return true;
    }
}
