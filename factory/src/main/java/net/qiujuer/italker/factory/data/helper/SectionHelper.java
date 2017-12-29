package net.qiujuer.italker.factory.data.helper;

import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.section.SectionDetailModel;
import net.qiujuer.italker.factory.model.api.section.SectionDetailRspModel;
import net.qiujuer.italker.factory.model.api.section.SectionFenyeModel;
import net.qiujuer.italker.factory.model.api.section.SectionPagerRspModel;
import net.qiujuer.italker.factory.model.api.section.SectionRspModel;
import net.qiujuer.italker.factory.net.Network;
import net.qiujuer.italker.factory.net.RemoteService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/11.
 */

public class SectionHelper {


    public static void loadDetail(String sectionId, final DataSource.Callback<SectionDetailModel> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.otherRemote();
        // 得到一个Call

        HashMap<String, String> map = new HashMap<>();
        map.put("id", sectionId);
        Call<SectionDetailRspModel> call = service.sectionLoadDetail(map);
        call.enqueue(new Callback<SectionDetailRspModel>() {
            @Override
            public void onResponse(Call<SectionDetailRspModel> call, Response<SectionDetailRspModel> response) {
                if (response.body()==null){
                    callback.onDataNotAvailable(R.string.data_network_error);
                    return;
                }
                callback.onDataLoaded(response.body().getDetail());

            }

            @Override
            public void onFailure(Call<SectionDetailRspModel> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }


    public static void loadList(final SectionFenyeModel model, final DataSource.Callback<SectionPagerRspModel> callback) {

        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.otherRemote();
        // 得到一个Call

        HashMap<String, String> map = new HashMap<>();
        map.put("page", model.getPage());
        map.put("rows", model.getRows());
        map.put("articleType", model.getArticleType());
        Call<SectionRspModel> call = service.sectionLoadList(map);
        call.enqueue(new Callback<SectionRspModel>() {
            @Override
            public void onResponse(Call<SectionRspModel> call, Response<SectionRspModel> response) {
                if (response.body() == null) {
                    callback.onDataNotAvailable(R.string.data_network_error);
                    return;
                }
                callback.onDataLoaded(response.body().getArticlePager());
            }

            @Override
            public void onFailure(Call<SectionRspModel> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

}
