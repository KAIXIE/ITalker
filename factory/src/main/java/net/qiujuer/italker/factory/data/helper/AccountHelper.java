package net.qiujuer.italker.factory.data.helper;

import android.text.TextUtils;

import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.RspModel;
import net.qiujuer.italker.factory.model.api.account.AccountRspModel;
import net.qiujuer.italker.factory.model.api.account.LoginModel;
import net.qiujuer.italker.factory.model.api.account.RegisterModel;
import net.qiujuer.italker.factory.model.api.otherAccount.OtherLoginResultModel;
import net.qiujuer.italker.factory.model.api.otherAccount.OtherRegisterResultModel;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.net.Network;
import net.qiujuer.italker.factory.net.RemoteService;
import net.qiujuer.italker.factory.persistence.Account;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class AccountHelper {

    /**
     * 注册的接口，异步的调用
     *
     * @param model    传递一个注册的Model进来
     * @param callback 成功与失败的接口回送
     */
    public static void register(final RegisterModel model, final DataSource.Callback<User> callback) {
        final RemoteService otherService = Network.otherRemote();
        final HashMap<String, String> map = new HashMap<>();
        map.put("userName", model.getAccount());
        map.put("password", model.getPassword());
        Call<OtherRegisterResultModel> otherCall = otherService.accountRegister(map);
        otherCall.enqueue(new Callback<OtherRegisterResultModel>() {
            @Override
            public void onResponse(Call<OtherRegisterResultModel> call, Response<OtherRegisterResultModel> response) {
                if (response.body() != null) {
                    if (TextUtils.isEmpty(response.body().getErrorInfo())) {
                        RemoteService otherLoginService = Network.otherRemote();
                        HashMap<String, String> loginMap = new HashMap<>();
                        loginMap.put("userName", model.getAccount());
                        loginMap.put("passWord", model.getPassword());
                        Call<OtherLoginResultModel> otherLoginCall = otherLoginService.accountLogin(loginMap);
                        otherLoginCall.enqueue(new Callback<OtherLoginResultModel>() {
                            @Override
                            public void onResponse(Call<OtherLoginResultModel> call, Response<OtherLoginResultModel> response) {
                                if (response.body() != null) {
                                    if (TextUtils.equals("1", response.body().getStatus())) {
                                        Account.saveOtherToken(response.body().getToken());
                                        RemoteService service = Network.remote();
                                        Call<RspModel<AccountRspModel>> registerCall = service.accountRegister(model);
                                        registerCall.enqueue(new AccountRspCallback(callback));
                                        return;
                                    }
                                }
                                if (callback != null)
                                    callback.onDataNotAvailable(R.string.data_rsp_error_account_register);
                            }

                            @Override
                            public void onFailure(Call<OtherLoginResultModel> call, Throwable t) {
                                if (callback != null)
                                    callback.onDataNotAvailable(R.string.data_network_error);
                            }
                        });
                        return;
                    }
                    if (callback != null)
                        callback.onDataNotAvailable(R.string.data_rsp_error_account_register);
                } else {
                    if (callback != null)
                        callback.onDataNotAvailable(R.string.data_rsp_error_account_register);
                }
            }

            @Override
            public void onFailure(Call<OtherRegisterResultModel> call, Throwable t) {
                if (callback != null)
                    callback.onDataNotAvailable(R.string.data_network_error);
            }
        });


    }


    /**
     * 登录的调用
     *
     * @param model    登录的Model
     * @param callback 成功与失败的接口回送
     */
    public static void login(final LoginModel model, final DataSource.Callback<User> callback) {

        RemoteService otherService = Network.otherRemote();
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", model.getAccount());
        map.put("passWord", model.getPassword());
        Call<OtherLoginResultModel> otherCall = otherService.accountLogin(map);
        otherCall.enqueue(new Callback<OtherLoginResultModel>() {
            @Override
            public void onResponse(Call<OtherLoginResultModel> otherCall, Response<OtherLoginResultModel> response) {
                if (response.body() != null) {
                    if (TextUtils.equals("1", response.body().getStatus())) {

                        String token = response.body().getToken();
                        Account.saveOtherToken(token);

                        // 调用Retrofit对我们的网络请求接口做代理
                        RemoteService service = Network.remote();
                        // 得到一个Call
                        Call<RspModel<AccountRspModel>> call = service.accountLogin(model);
                        // 异步的请求
                        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
                            @Override
                            public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
                                // 请求成功返回
                                // 从返回中得到我们的全局Model，内部是使用的Gson进行解析
                                RspModel<AccountRspModel> rspModel = response.body();
                                if (rspModel.success()) {
                                    // 拿到实体
                                    AccountRspModel accountRspModel = rspModel.getResult();
                                    // 获取我的信息
                                    User user = accountRspModel.getUser();
                                    DbHelper.save(User.class, user);

                                    // 同步到XML持久化中
                                    Account.login(accountRspModel);

                                    // 判断绑定状态，是否绑定设备
                                    if (accountRspModel.isBind()) {
                                        // 设置绑定状态为True
                                        Account.setBind(true);
                                        // 然后返回
                                        if (callback != null)
                                            callback.onDataLoaded(user);
                                    } else {
                                        // 进行绑定的唤起
                                        bindPush(callback);
                                    }
                                } else {
                                    final RemoteService service = Network.remote();
                                    // 得到一个Call
                                    RegisterModel registerModel = new RegisterModel(model.getAccount(), model.getPassword(), model.getAccount());
                                    Call<RspModel<AccountRspModel>> registerCall = service.accountRegister(registerModel);
                                    registerCall.enqueue(new Callback<RspModel<AccountRspModel>>() {
                                        @Override
                                        public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
                                            if (response.isSuccessful()) {
                                                Call<RspModel<AccountRspModel>> loginCall = service.accountLogin(model);
                                                loginCall.enqueue(new AccountRspCallback(callback));
                                            } else {
                                                Account.deleteOtherToken();
                                                Factory.decodeRspCode(response.body(), callback);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                                            callback.onDataNotAvailable(R.string.data_network_error);
                                        }
                                    });


                                }
                            }

                            @Override
                            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                                callback.onDataNotAvailable(R.string.data_network_error);
                            }
                        });
                        return;
                    }
                }
                callback.onDataNotAvailable(R.string.data_rsp_error_account_login);
            }

            @Override
            public void onFailure(Call<OtherLoginResultModel> call, Throwable t) {
                // 网络请求失败
                if (callback != null)
                    callback.onDataNotAvailable(R.string.data_network_error);
            }
        });


    }

    /**
     * 对设备Id进行绑定的操作
     *
     * @param callback Callback
     */
    public static void bindPush(final DataSource.Callback<User> callback) {
        // 检查是否为空
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId))
            return;

        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountBind(pushId);
        call.enqueue(new AccountRspCallback(callback));
    }


    /**
     * 请求的回调部分封装
     */
    private static class AccountRspCallback implements Callback<RspModel<AccountRspModel>> {

        final DataSource.Callback<User> callback;

        AccountRspCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call,
                               Response<RspModel<AccountRspModel>> response) {
            // 请求成功返回
            // 从返回中得到我们的全局Model，内部是使用的Gson进行解析
            RspModel<AccountRspModel> rspModel = response.body();
            if (rspModel.success()) {
                // 拿到实体
                AccountRspModel accountRspModel = rspModel.getResult();
                // 获取我的信息
                User user = accountRspModel.getUser();
                DbHelper.save(User.class, user);

                // 第一种，之间保存
                // user.save();
                /*
                // 第二种通过ModelAdapter
                FlowManager.getModelAdapter(User.class)
                        .save(user);

                // 第三种，事务中
                DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
                definition.beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        FlowManager.getModelAdapter(User.class)
                                .save(user);
                    }
                }).build().execute();
                */
                // 同步到XML持久化中
                Account.login(accountRspModel);

                // 判断绑定状态，是否绑定设备
                if (accountRspModel.isBind()) {
                    // 设置绑定状态为True
                    Account.setBind(true);
                    // 然后返回
                    if (callback != null)
                        callback.onDataLoaded(user);
                } else {
                    // 进行绑定的唤起
                    bindPush(callback);
                }
            } else {
                // 错误解析
                Factory.decodeRspCode(rspModel, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            // 网络请求失败
            if (callback != null)
                callback.onDataNotAvailable(R.string.data_network_error);
        }
    }

}
