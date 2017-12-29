package net.qiujuer.italker.factory.data.helper;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.setting.UploadPortraitRspModel;
import net.qiujuer.italker.factory.model.api.setting.UploadProfileModel;
import net.qiujuer.italker.factory.net.Network;
import net.qiujuer.italker.factory.net.RemoteService;
import net.qiujuer.italker.factory.persistence.Account;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/15.
 */

public class SettingHelper {

    public static void uploadProfile(String portraitUrl, String nickname, String motto, String sex, final DataSource.Callback<String> callback){
        final RemoteService remote = Network.remote();
        HashMap<String, String> map = new HashMap<>();
        map.put("headImg",portraitUrl);
        map.put("realName",nickname);
        map.put("introduction",motto);
        map.put("uSex",sex);
        map.put("token", Account.getToken());
        Call<UploadProfileModel> call = remote.uploadProfile(map);
        call.enqueue(new Callback<UploadProfileModel>() {
            @Override
            public void onResponse(Call<UploadProfileModel> call, Response<UploadProfileModel> response) {



            }

            @Override
            public void onFailure(Call<UploadProfileModel> call, Throwable t) {

            }
        });
    }

    public static void uploadFile(Context context, Uri fileUri, final DataSource.Callback<String> callback) {

        String filePath = getRealPathFromUri(context, fileUri);
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                RemoteService service = Network.remote();

                // creates RequestBody instance from file
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                // MultipartBody.Part is used to send also the actual filename
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                // adds another part within the multipart request
                String descriptionString = "Sample description";
                RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
                // executes the request
                Call<UploadPortraitRspModel> call = service.postFile(body, description);
                call.enqueue(new Callback<UploadPortraitRspModel>() {
                    @Override
                    public void onResponse(Call<UploadPortraitRspModel> call,
                                           Response<UploadPortraitRspModel> response) {


                    }

                    @Override
                    public void onFailure(Call<UploadPortraitRspModel> call, Throwable t) {

                    }
                });
            }
        }
    }


    public static String getRealPathFromUri(Context mContext, final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(mContext, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(mContext, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(mContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(mContext, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}
