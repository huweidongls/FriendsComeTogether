package com.yiwo.friendscometogether.network;

/**
 * Created by Administrator on 2018/7/12.
 */

public class NetConfig {

//    public static String BaseUrl = "http://39.104.102.152/index.php/";
    public static String BaseUrl = "http://47.92.136.19/index.php/";
    //登录
    public static String loginUrl = "action/ac_login/login";
    //获取验证码
    public static String getCodeUrl = "action/ac_login/SendCode";
    //用户注册
    public static String registerUrl = "action/ac_login/UseRregister";
    //找回密码
    public static String forgetPwUrl = "action/ac_login/Retrievethepassword";
    //城市列表
    public static String cityUrl = "action/ac_login/get_all_city";
    //文章列表
    public static String friendsRememberUrl = "action/ac_article/article_list";
    //文章详情
    public static String articleContentUrl = "action/ac_article/article_slide";
    //友聚列表
    public static String friendsTogetherUrl = "action/ac_activity/activity_all_list";
    //友聚详情
    public static String friendsTogetherDetailsUrl = "action/ac_activity/activity_info";
    //首页友记热门列表
    public static String homeHotFriendsRememberUrl = "action/ac_article/index_fmrecommend";
    //热门城市
    public static String hotCityUrl = "action/ac_activity/hot_city";
    //获取用户信息
    public static String userInformation = "action/ac_user/UserInformation";
    //我的关注
    public static String userFocus = "action/ac_user/my_look";
    //我的收藏
    public static String userCollection = "action/ac_user/Usercollection";
    //我的评论
    public static String userComment = "action/ac_article/article_comment_list";
    //获取标签
    public static String userLabel = "action/ac_public/Label";
    //友记发布
    public static String userRelease = "action/ac_article/InsertArticle";
    //用户友记列表(草稿)
    public static String userRemember = "action/ac_article/Listofarticles";
    //添加插文
    public static String userIntercalation = "action/ac_article/Intercalation";
    //续写友记
    public static String userRenewTheArticle = "action/ac_article/RenewTheArticle";
    //创建活动
    public static String createActivityUrl = "action/ac_activity/add_travel";
    //用户插文列表
    public static String userIntercalationListUrl = "action/ac_article/MylistoFinserts";
    //编辑友记
    public static String editorFriendRememberUrl = "action/ac_article/Friendeditor";
    //删除续写
    public static String deleteRenewUrl = "action/ac_article/DeleteRenew";
    //活动报名
    public static String applyActivityUrl = "action/ac_order/user_join";
    //删除友记
    public static String deleteFriendRememberUrl = "action/ac_article/delArticles";
    //发布草稿
    public static String releaseDraftUrl = "action/ac_article/draftRadio";
    //搜索友聚列表
    public static String searchFriendTogetherUrl = "action/ac_activity/search_activity_list";
    //关注活动
    public static String focusOnToFriendTogetherUrl = "action/ac_activity/follow_attention";
    //补充内容
    public static String addContentFriendTogetherUrl = "action/ac_activity/add_travel_info";
    //是否实名认证
    public static String isRealNameUrl = "action/ac_order/check_usercodeok";
    //相册列表
    public static String myPictureListUrl = "action/ac_user/AlbumList";
    //相册上传图片
    public static String myPictureUploadUrl = "action/ac_user/Photoalbum";
    //相册删除图片
    public static String myPictureDeleteUrl = "action/ac_user/deleteAlbum";
    //文章详情
    public static String detailsOfFriendsUrl = "action/ac_article/ArticleContent";
    //浏览历史
    public static String lookHistoryUrl = "action/ac_article/BrowsEhistory";
    //删除浏览历史
    public static String deleteLookHistoryUrl = "action/ac_article/deletehistory";
    //文章点赞
    public static String articlePraiseUrl = "action/ac_article/Give";
    //文章收藏
    public static String articleCollectionUrl = "action/ac_article/Collection";
    //保存用户信息
    public static String saveUserInformationUrl = "action/ac_user/saveUserinfo";
    //用户上传头像
    public static String userUploadHeaderUrl = "action/ac_user/UploadSheader";
    //实名认证
    public static String realNameUrl = "action/ac_user/Realnameauthentication";
    //删除收藏
    public static String deleteCollectionUrl = "action/ac_user/deletecollection";
    //获取插文位置列表
    public static String intercalationLocationUrl = "action/ac_article/IntercalationPosition";
    //添加插文
    public static String insertIntercalationUrl = "action/ac_article/Intercalation";
    //添加关注
    public static String userFocusUrl = "action/ac_user/ExecutiveAttention";
    //取消关注人
    public static String userCancelFocusUrl = "action/ac_user/AbolishConcern";
    //个人中心插文删除
    public static String userDeleteIntercalationFocusUrl = "action/ac_article/Deletetheinsert";
    //队友插文列表
    public static String teamIntercalationListUrl = "action/ac_article/IntercalationList";
    //设置插文是否展示
    public static String intercalationShowUrl = "action/ac_article/Exhibition";
    //设置屏蔽插文
    public static String sheildIntercalationUrl = "action/ac_article/ShieldedArticles";
    //文章评论
    public static String articleCommentUrl = "action/ac_article/ArticleReviews";
    //文章评论列表
    public static String articleCommentListUrl = "action/ac_article/ReviewList";
    //首页友聚
    public static String homeTogetherListUrl = "action/ac_activity/index_activity";
    //回复评论
    public static String replyCommentUrl = "action/ac_article/Reply";
    //关注领队
    public static String focusOnLeaderUrl = "action/ac_user/attention_captain";
    //订单列表
    public static String myOrderListUrl = "action/ac_order/my_order";
    //消息列表
    public static String messageListUrl = "action/ac_activity/add_activity";
    //历史意见反馈
    public static String historicalFeedBackUrl = "action/ac_user/feedbackList";
    //提交意见反馈
    public static String submitFeedBackUrl = "action/ac_user/feedback";
    //订单详情
    public static String detailsOrderUrl = "action/ac_order/order_detail";
    //发起的活动
    public static String initiativesListUrl = "action/ac_activity/activity_list";
    //参加的活动
    public static String activitiesAttendedUrl = "action/ac_activity/activity_join";
    //取消订单行程
    public static String cancelOrderTripUrl = "action/ac_order/order_out";
    //删除订单行程
    public static String deleteOrderTripUrl = "action/ac_order/del_order";
    //取消活动
    public static String cancleActivityUrl = "action/ac_activity/activity_cancel";
    //订单去支付
    public static String orderToPayUrl = "action/ac_order/go_pay";
    //获取修改活动页面
    public static String getEditorFriendTogetherUrl = "action/ac_activity/get_activity_info";
    //修改主活动信息
    public static String editorFriendTogetherUrl = "action/ac_activity/update_activity";
    //删除活动子标题全部内容
    public static String deleteFriendTogetherSubtitleContentUrl = "action/ac_activity/del" +
            "_activity_info";
    //修改活动子标题信息
    public static String editorFriendTogetherSubtitleContentUrl = "action/ac_activity/update_activity_info";
    //用户协议地址
    public static String userAgreementUrl = "http://47.92.136.19/index.php/action/ac_public/user_agreement";
    //获取用户的活动列表
    public static String userActiveListUrl = "action/ac_article/ActivityList";
    //显示实名认证信息
    public static String userRealNameInfoUrl = "action/ac_user/Realdisplay";
    //活动评价列表
    public static String activeEvaluationListUrl = "action/ac_activity/activity_comment_list";
    //友记修改基础信息
    public static String modifyFriendRememberUrl = "action/ac_article/AmendFriends";
    //保存友记或草稿的修改信息
    public static String saveFriendRememberUrl = "action/ac_article/SaveFriendsText";
    //获取续写信息（修改）
    public static String modifyIntercalationUrl = "action/ac_article/RenewInfo";
    //保存续写修改信息
    public static String saveModifyIntercalationUrl = "action/ac_article/SaveRenewInfo";
    //黑色个人中心
    public static String otherUserInformationUrl = "action/ac_user/PersonalcenterOthers";
    //活动发布评价
    public static String orderCommentUrl = "action/ac_activity/activityReviews";
    //续写信息(修改描述和删除)
    public static String savePicAndDescribeUrl = "action/ac_article/SaveDescribe";
    //添加好友
    public static String addFriendsUrl = "action/ac_user/Addfriends";
    //进入聊天室
    public static String enterChatRoomUrl = "action/ac_activity/GroupActives";
    //获取要修改的活动标题内容和图片
    public static String getActiveIntercalationInfoUrl = "action/ac_activity/get_title_info";
    //删除活动图片
    public static String delActivePicUrl = "action/ac_activity/del_activity_info";
    //修改活动图片描述
    public static String modifyActivePicInfoUrl = "action/ac_activity/update_image_info";
    //活动回复
    public static String activeReturnCommentUrl = "action/ac_activity/reply_activity_comment";
    //活动分享
    public static String activeShareUrl = "action/ac_activity/SharingInformation";
    //发起邀请
    public static String activeInvitationUrl = "action/ac_activity/Invitation";
    //活动列表（邀请）
    public static String activeInvitationListUrl = "action/ac_activity/ActivityList";
    //首页消息中心
    public static String homeMessageCenterUrl = "action/ac_public/get_message";
    //系统、热门消息列表
    public static String systemHotMessageListUrl = "action/ac_public/system_hot_list";
    //邀请列表（消息中心）
    public static String messageInvitationListUrl = "action/ac_activity/InvitationList";
    //所有幻灯片
    public static String allBannerUrl = "action/ac_article/article_slide";
    //好友消息（消息中心）
    public static String messageFriendsUrl = "action/ac_user/FriendsList";
    //同意好友请求（消息中心）
    public static String messageFriendsOkUrl = "action/ac_user/AssentRequestFriends";
    //大家都在搜
    public static String allSearchUrl = "action/ac_public/all_search";
    //评价消息列表
    public static String messageCommentUrl = "action/ac_public/comment_message_list";
    //系统、热门消息详情
    public static String systemHotMessageDetailsUrl = "action/ac_public/system_hot_content";
    //获取报名信息(邀请同意时)
    public static String invitationOkUrl = "action/ac_activity/ActivityInfo";
    //消息中心邀请(拒绝)
    public static String invitationNoUrl = "action/ac_activity/refuse";
    //视频相关活动
    public static String videoActiveUrl = "action/ac_activity/video_activity";
    //我关注的活动
    public static String MyFocusActiveUrl = "action/ac_user/party_follow_attention";
    //好友列表
    public static String MyFriendListUrl = "action/ac_user/Myfriend";
    //删除好友
    public static String deleteFriendUrl = "action/ac_user/DeleteUser";
    //将好友拉入黑名单
    public static String blackFriendUrl = "action/ac_user/BlackUser";
    //黑名单列表
    public static String blackUserListUrl = "action/ac_user/BlacklistInfo";
    //取消拉黑
    public static String userCancelBlackUrl = "action/ac_user/CancelBlackout";
    //彻底删除(黑名单)
    public static String deleteBlackUserUrl = "action/ac_user/DeleteBlackUser";
    //清空消息
    public static String deleteMessageUrl = "action/ac_public/clear_message";
    //活动草稿发布接口
    public static String activeDraftReleaseUrl = "action/ac_activity/show_activity";
    //删除参加/发起的活动
    public static String deleteActiveUrl = "action/ac_activity/del_activity";
    //获取活动列表(发布友记)
    public static String getFriendActiveListUrl = "action/ac_article/FriendsList";
    //参加的活动获取活动评价信息接口
    public static String joinGetCommentInfoUrl = "action/ac_activity/get_join_comment";
    //解散群组
    public static String disbandedGroupUrl = "action/ac_public/DisbandedGroup";
    //获取全球国家名称
    public static String otherCityUrl = "action/ac_public/get_country";
    //删除评论
    public static String deleteCommentUrl = "action/ac_article/CommentaryDeleting";
}
