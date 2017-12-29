package net.qiujuer.italker.factory.data.helper;

import android.text.TextUtils;

import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.diary.DiaryFenyeModel;
import net.qiujuer.italker.factory.model.api.diary.DiaryItemModel;
import net.qiujuer.italker.factory.model.api.diary.DiaryPagerRspModel;
import net.qiujuer.italker.factory.model.api.diary.DiaryRspModel;
import net.qiujuer.italker.factory.net.Network;
import net.qiujuer.italker.factory.net.RemoteService;
import net.qiujuer.italker.factory.persistence.Account;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DiaryHelper {
    public static void loadList(final DiaryFenyeModel model, final DataSource.Callback<DiaryPagerRspModel> callback) {

        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.otherRemote();
        // 得到一个Call

        HashMap<String, String> map = new HashMap<>();
        map.put("page", model.getPage());
        map.put("rows", model.getRows());
        map.put("token", model.getToken());
        Call<DiaryRspModel> call = service.diaryLoadList(map);
        call.enqueue(new Callback<DiaryRspModel>() {
            @Override
            public void onResponse(Call<DiaryRspModel> call, Response<DiaryRspModel> response) {
                if (response.body() != null) {
                    if (TextUtils.equals("1", response.body().getStatus())) {
                        callback.onDataLoaded(response.body().getUserNoteList());
                        return;
                    }
                }
                callback.onDataNotAvailable(R.string.data_fetch_diary_list_error);
            }

            @Override
            public void onFailure(Call<DiaryRspModel> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }


    public static void addDiary(final String content, final DataSource.Callback<DiaryRspModel> callback) {

        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.otherRemote();
        // 得到一个Call

        HashMap<String, String> map = new HashMap<>();
        map.put("content", content);
        map.put("token", Account.getOtherToken());
        Call<DiaryRspModel> call = service.addDiary(map);
        call.enqueue(new Callback<DiaryRspModel>() {
            @Override
            public void onResponse(Call<DiaryRspModel> call, Response<DiaryRspModel> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (TextUtils.equals("1", response.body().getStatus())) {
                            callback.onDataLoaded(response.body());
                            return;
                        } else {
                            callback.onDataNotAvailable(R.string.data_fetch_diary_list_error);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DiaryRspModel> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    public static void editDiary(final DiaryItemModel model, final DataSource.Callback<DiaryRspModel> callback) {

        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.otherRemote();
        // 得到一个Call

        HashMap<String, String> map = new HashMap<>();
        map.put("content", model.getContent());
        map.put("id", model.getId());
        map.put("token", Account.getOtherToken());
        Call<DiaryRspModel> call = service.editDiary(map);
        call.enqueue(new Callback<DiaryRspModel>() {
            @Override
            public void onResponse(Call<DiaryRspModel> call, Response<DiaryRspModel> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (TextUtils.equals("1", response.body().getStatus())) {
                            callback.onDataLoaded(response.body());
                            return;
                        } else {
                            callback.onDataNotAvailable(R.string.data_update_diary_error);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DiaryRspModel> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    public static void deleteDiary(String id, final DataSource.Callback<DiaryRspModel> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.otherRemote();
        // 得到一个Call

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("token", Account.getOtherToken());
        Call<DiaryRspModel> call = service.deleteDiary(map);
        call.enqueue(new Callback<DiaryRspModel>() {
            @Override
            public void onResponse(Call<DiaryRspModel> call, Response<DiaryRspModel> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (TextUtils.equals("1", response.body().getStatus())) {
                            callback.onDataLoaded(response.body());
                            return;
                        } else {
                            callback.onDataNotAvailable(R.string.data_fetch_diary_list_error);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DiaryRspModel> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}
