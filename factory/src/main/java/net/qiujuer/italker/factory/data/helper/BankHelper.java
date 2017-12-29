package net.qiujuer.italker.factory.data.helper;

import android.text.TextUtils;

import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.bank.BankItemModel;
import net.qiujuer.italker.factory.model.api.bank.BankRspModel;
import net.qiujuer.italker.factory.net.Network;
import net.qiujuer.italker.factory.net.RemoteService;
import net.qiujuer.italker.factory.persistence.Account;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/29.
 */

public class BankHelper {
    public static void loadList(final DataSource.Callback<List<BankItemModel>> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.otherRemote();
        // 得到一个Call

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Account.getOtherToken());
        Call<BankRspModel> call = service.bankLoadList(map);
        call.enqueue(new Callback<BankRspModel>() {
            @Override
            public void onResponse(Call<BankRspModel> call, Response<BankRspModel> response) {
                if (response.body() != null) {
                    if (TextUtils.equals("1", response.body().getStatus())) {
                        callback.onDataLoaded(response.body().getList());
                        return;
                    }
                }
                callback.onDataNotAvailable(R.string.data_fetch_bank_list_error);
            }

            @Override
            public void onFailure(Call<BankRspModel> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}

