package net.qiujuer.italker.factory.net;

import net.qiujuer.italker.factory.model.api.RspModel;
import net.qiujuer.italker.factory.model.api.account.AccountRspModel;
import net.qiujuer.italker.factory.model.api.account.LoginModel;
import net.qiujuer.italker.factory.model.api.account.RegisterModel;
import net.qiujuer.italker.factory.model.api.bank.BankRspModel;
import net.qiujuer.italker.factory.model.api.black.BlackDetailRspModel;
import net.qiujuer.italker.factory.model.api.black.BlackRspModel;
import net.qiujuer.italker.factory.model.api.diary.DiaryRspModel;
import net.qiujuer.italker.factory.model.api.group.GroupCreateModel;
import net.qiujuer.italker.factory.model.api.group.GroupMemberAddModel;
import net.qiujuer.italker.factory.model.api.message.MsgCreateModel;
import net.qiujuer.italker.factory.model.api.otherAccount.OtherLoginResultModel;
import net.qiujuer.italker.factory.model.api.otherAccount.OtherRegisterResultModel;
import net.qiujuer.italker.factory.model.api.section.SectionDetailRspModel;
import net.qiujuer.italker.factory.model.api.section.SectionRspModel;
import net.qiujuer.italker.factory.model.api.setting.UploadPortraitRspModel;
import net.qiujuer.italker.factory.model.api.setting.UploadProfileModel;
import net.qiujuer.italker.factory.model.api.user.UserUpdateModel;
import net.qiujuer.italker.factory.model.card.GroupCard;
import net.qiujuer.italker.factory.model.card.GroupMemberCard;
import net.qiujuer.italker.factory.model.card.MessageCard;
import net.qiujuer.italker.factory.model.card.UserCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * 网络请求的所有的接口
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public interface RemoteService {

    /**
     * 注册接口
     *
     * @param model 传入的是RegisterModel
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 登录接口
     *
     * @param model LoginModel
     * @return RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 绑定设备Id
     *
     * @param pushId 设备Id
     * @return RspModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

    // 用户更新的接口
    @PUT("user")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

    // 用户搜索的接口
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> userSearch(@Path("name") String name);

    // 用户关注接口
    @PUT("user/follow/{userId}")
    Call<RspModel<UserCard>> userFollow(@Path("userId") String userId);

    // 获取联系人列表
    @GET("user/contact")
    Call<RspModel<List<UserCard>>> userContacts();

    // 查询某人的信息
    @GET("user/{userId}")
    Call<RspModel<UserCard>> userFind(@Path("userId") String userId);

    // 发送消息的接口
    @POST("msg")
    Call<RspModel<MessageCard>> msgPush(@Body MsgCreateModel model);

    // 创建群
    @POST("group")
    Call<RspModel<GroupCard>> groupCreate(@Body GroupCreateModel model);

    // 拉取群信息
    @GET("group/{groupId}")
    Call<RspModel<GroupCard>> groupFind(@Path("groupId") String groupId);

    // 群搜索的接口
    @GET("group/search/{name}")
    Call<RspModel<List<GroupCard>>> groupSearch(@Path(value = "name", encoded = true) String name);

    // 我的群列表
    @GET("group/list/{date}")
    Call<RspModel<List<GroupCard>>> groups(@Path(value = "date", encoded = true) String date);

    // 我的群的成员列表
    @GET("group/{groupId}/member")
    Call<RspModel<List<GroupMemberCard>>> groupMembers(@Path("groupId") String groupId);

    // 给群添加成员
    @POST("group/{groupId}/member")
    Call<RspModel<List<GroupMemberCard>>> groupMemberAdd(@Path("groupId") String groupId,
                                                         @Body GroupMemberAddModel model);


    /**
     * 注册接口
     *
     * @param map 传入的是 Map<String, String>
     * @return 返回的是RspModel<AccountRspModel>
     */
    @FormUrlEncoded
    @POST("classmateApp/app/user/registerUser.do")
    Call<OtherRegisterResultModel> accountRegister(@FieldMap Map<String, String> map);

    /**
     * 登录接口
     *
     * @param map Map<String, String>
     * @return <AccountRspModel>
     */
    @FormUrlEncoded
    @POST("classmateApp/app/login/appUserlogin.do")
    Call<OtherLoginResultModel> accountLogin(@FieldMap Map<String, String> map);

    /**
     * 获取黑板列表
     *
     * @param map HashMap<String, String>
     * @return Call<BlackRspModel>
     */
    @FormUrlEncoded
    @POST("classmateApp/article/articlePager.do")
    Call<BlackRspModel> blackLoadList(@FieldMap HashMap<String, String> map);

    /**
     * 获取黑板详情
     *
     * @param map HashMap<String, String>
     * @return Call<BlackDetailRspModel>
     */
    @FormUrlEncoded
    @POST("classmateApp/article/articleDetail.do")
    Call<BlackDetailRspModel> blackLoadDetail(@FieldMap HashMap<String, String> map);

    /**
     * 获取段子列表
     *
     * @param map HashMap<String, String>
     * @return Call<BlackRspModel>
     */
    @FormUrlEncoded
    @POST("classmateApp/article/articlePager.do")
    Call<SectionRspModel> sectionLoadList(@FieldMap HashMap<String, String> map);

    /**
     * 获取段子详情
     *
     * @param map HashMap<String, String>
     * @return Call<SectionDetailRspModel>
     */
    @FormUrlEncoded
    @POST("classmateApp/article/articleDetail.do")
    Call<SectionDetailRspModel> sectionLoadDetail(@FieldMap HashMap<String, String> map);

    /**
     * 加载日记本列表
     *
     * @param map ashMap<String, String>
     * @return Call<DiaryRspModel>
     */
    @FormUrlEncoded
    @POST("classmateApp/userNote/userNotePager.do")
    Call<DiaryRspModel> diaryLoadList(@FieldMap HashMap<String, String> map);

    /**
     * 增加一条日记
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("classmateApp/userNote/addOrUpdateUserNote.do")
    Call<DiaryRspModel> addDiary(@FieldMap HashMap<String, String> map);

    /**
     * 修改一条日记
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("classmateApp/userNote/addOrUpdateUserNote.do")
    Call<DiaryRspModel> editDiary(@FieldMap HashMap<String, String> map);

    /**
     * 删除一条日记
     *
     * @param map HashMap<String, String>
     * @return Call<DiaryRspModel>
     */
    @FormUrlEncoded
    @POST("classmateApp/userNote/deleteUserNote.do")
    Call<DiaryRspModel> deleteDiary(@FieldMap HashMap<String, String> map);

    /**
     * 上传文件
     */
    @Multipart
    @POST("upload/upload.do")
    Call<UploadPortraitRspModel> postFile(@Part MultipartBody.Part file, @Part("description") RequestBody description);


    /**
     * 修改一条日记
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("classmateApp/sys/userMessage/addOrUpdateUserMsg.do")
    Call<UploadProfileModel> uploadProfile(@FieldMap HashMap<String, String> map);

    @PUT("user")
    Call<RspModel<UserCard>> userUpdatePortrait(@Body String url);

    @FormUrlEncoded
    @POST("classmateApp/bankCard/bankCardList.do")
    Call<BankRspModel> bankLoadList(@FieldMap HashMap<String, String> map);
}
