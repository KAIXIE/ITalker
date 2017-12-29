package net.qiujuer.italker.factory.data.helper;

import android.text.TextUtils;

import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.black.BlackDetailModel;
import net.qiujuer.italker.factory.model.api.black.BlackDetailRspModel;
import net.qiujuer.italker.factory.model.api.black.BlackFenyeModel;
import net.qiujuer.italker.factory.model.api.black.BlackPagerRspModel;
import net.qiujuer.italker.factory.model.api.black.BlackRspModel;
import net.qiujuer.italker.factory.net.Network;
import net.qiujuer.italker.factory.net.RemoteService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/7.
 */

public class BlackHelper {

    public static void loadList(final BlackFenyeModel model, final DataSource.Callback<BlackPagerRspModel> callback) {

        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.otherRemote();
        // 得到一个Call

        HashMap<String, String> map = new HashMap<>();
        map.put("page", model.getPage());
        map.put("rows", model.getRows());
        map.put("articleType", model.getArticleType());
        Call<BlackRspModel> call = service.blackLoadList(map);
        call.enqueue(new Callback<BlackRspModel>() {
            @Override
            public void onResponse(Call<BlackRspModel> call, Response<BlackRspModel> response) {
                if (response.body()==null){
                    callback.onDataNotAvailable(R.string.data_network_error);
                    return;
                }
                callback.onDataLoaded(response.body().getArticlePager());
            }

            @Override
            public void onFailure(Call<BlackRspModel> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    public static void loadDetail(final String blackId, final DataSource.Callback<BlackDetailModel> callback) {

        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.otherRemote();
        // 得到一个Call

        HashMap<String, String> map = new HashMap<>();
        map.put("id", blackId);
        Call<BlackDetailRspModel> call = service.blackLoadDetail(map);
        call.enqueue(new Callback<BlackDetailRspModel>() {
            @Override
            public void onResponse(Call<BlackDetailRspModel> call, Response<BlackDetailRspModel> response) {
                if (response.body()==null){
                    callback.onDataNotAvailable(R.string.data_network_error);
                    return;
                }
            callback.onDataLoaded(response.body().getDetail());
            }

            @Override
            public void onFailure(Call<BlackDetailRspModel> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}
