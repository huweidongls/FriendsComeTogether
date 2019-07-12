package com.yiwo.friendscometogether.network;

/**
 * Created by Administrator on 2018/7/12.
 */

public class NetConfig {

    //测试服务器
    public static String BaseUrl = "http://www.91yiwo.com/ylyy/index.php/";
//    正式服务器
//    public static String BaseUrl = "http://www.tongbanapp.com/index.php/";


//    public static String BaseUrl = "http://www.youlaiyouyue.com/";
//    public static String BaseUrl = "http://47.92.136.19/index.php/";

    //检测版本号 返回 an_version 安卓版本号  an_download下载地址
    public static String checkVersion = "action/ac_public/check_version";
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
    //•根据相册里面的图片更改头像
    public static String setupHeaderFromPics = "action/ac_user/SetupHeader";
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
//    public static String userAgreementUrl = "http://47.92.136.19/index.php/action/ac_public/user_agreement";
    public static String userAgreementUrl = "http://39.104.102.152/index.php/action/ac_public/userDeal";
//      报名协议
    public static String joinDealUrl = "http://tb.91yiwo.cn/action/ac_public/joinDeal";
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

    //获取参加的活动（报名活动、往期活动）
    public static String activityJoin = "action/ac_activity/activity_join";
    //获取关注我的人
    public static String guanZhuWoDe = "action/ac_user/get_AttentionTome";
    //首页新的列表数据接口
    public static String newHomeData = "action/ac_activity/Home_data";
    //友记新的列表数据接口
    public static String newYoujiData = "action/ac_article/Friends_List";
    //他人主页接口
    public static String personMain = "action/ac_user/Homepage";
    //获取标签列表
    public static String userlabel = "action/ac_user/userlabel";
    //获取用户以保存的标签
    public static String usersavelabel = "action/ac_user/User_Label_List";
    //保存用户选择的标签
    public static String saveuserlabel = "action/ac_user/SaveUserLabel";
    //超级喜欢匹配好友
    public static String matching_user = "action/ac_user/matching_user";
    //http://47.92.136.19/index.php/action/ac_user/Sayhello 匹配成功、打招呼
    public static String sayHello = "action/ac_user/Sayhello";
    //http://47.92.136.19/index.php/action/ac_user/Privateletterlist 私信列表
    public static String privateLetterList = "action/ac_user/Privateletterlist";
    //http://47.92.136.19/index.php/action/ac_user/Agree_or_refuse  同意或者拒绝聊天
    public static String agreeOrRefuse = "action/ac_user/Agree_or_refuse";
    //首页标签类别
    public static String indexLabel = "action/ac_user/index_label";
    //点赞收藏消息
    public static String zanAndSoucang = "action/ac_user/settings";
    //取消关注(他人主页)   http://47.92.136.19/index.php/action/ac_user/RemoveConcerns
    public static String removeConcerns = "action/ac_user/RemoveConcerns";
    //友记评论消息
    public static String youjiPinglun = "action/ac_user/ArticleReview";
    //获取活动期数
    public static String getPhase = "action/ac_activity/getphase";
    //清除用户浏览历史
    public static String clearLookHistory = "action/ac_activity/clear_look_history";
    //个人主页 关注&粉丝
    public static String lookUserAttention ="action/ac_user/lookUserAttention";
    //私信消息 删除     传uid用户id    type =0删除一条  =1全部清空   id要删除一条的id
    public static String delFriendInfo =  "action/ac_user/delFriendInfo";
    //友记详情web页面获取数据
    public static String getInfo = "action/ac_public/getInfo";
    //友聚详情web页面数据
    public static String getActivityInfo = "action/ac_activity/getActivityInfo";
    //申请进群
    public static String inGroupInfo = "action/ac_public/inGroupInfo";
    //拒接进群
    public static String noInGroup = "action/ac_public/noInGroup";
    //同意进群
    public static String agreeIngroup = "action/ac_public/agreeInGroup";
    //删除活动评价
    public static String delComments = "action/ac_public/delComments";
    //更多活动评价接口
    public static String  commentListAll = "action/ac_activity/commentListAll";//action/ac_activity/commentListAll  传 pfID  活动的ID    全部评论接口   返回 fctime时间  userpic用户头像   username用户名  userID用户ID   pctitle评论内容
    //获取关注数量
    public static String getAttentionNum = "action/ac_public/getAttentionNum";//attentionNum我关注的数量   attentionMe关注我的数量   attentionActivity关注的活动 的数量
//    action/ac_user/clearAdmire 清空赞收藏接口  传userID
    public static String clearAdmire = "action/ac_user/clearAdmire";
//    action/ac_user/clearComment 清空评论接口  传userID
    public static String clearComment = "action/ac_user/clearComment";
    // 搜索好友
    public static String searchUser = "action/ac_user/searchUser";//    action/ac_user /     searchUser   搜索用户接口     传 userLogin用户账号 userID 我的ID  返回  userID 用户id  username 用户名   userpic 头像 status  0 不是好友  1是好友

   //获取心动状态接口
    public static String heartBeatStatus = "action/ac_user/heartBeatStatus" ; //action/ ac_user  /  heartBeatStatus 状态接口   传userID用户ID   heartbeatID 新东人的id   返回 status 0不是 1是
    //点击心动操作
    public static String heartbeat = "action/ac_user/heartbeat"; //点击心动操作   传userID用户id  heartbeatID心动人的id
    //对我心动的人
    public static String heartList = "action/ac_user/heartList"; // 列表    传userID用户id           返回userID用户id  username用户名   userpic头像\n"
    //获取所有个人评论标签接口
    public static String commentLabel = "action/ac_user/commentLabel"; //action/ac_user/commentLabel  评论标签接口   传 userID  被评论人的ID   uid登录用户的ID   返回 id 标签的ID   label_name标签名 status 0未评价过  1评价过
    //论标签接口
    public static String userCommentLabel = "action/ac_user/userCommentLabel";//action/ac_user/userCommentLabel  评论标签接口  传userID登录用户的ID   buserID 被评论用户的ID    labelID 标签ID
    //获取个人评论标签接口
    public static String userCommentLabelList = "action/ac_user/userCommentLabelList";//action/ac_user/userCommentLabelList  我的评论标签接口   传 userID 登录用户的ID   type=0返回 10个     1返回全部 返回  label_name 标签名   num评论数量
    //获取评论的人 接口
    public static String clickUserList = "action/ac_user/clickUserList";//action/ac_user/clickUserList 点击的用户列表接口  传 userID登录用户ID  labelID标签ID   page分页
    //action/ac_public/Report 举报
    public static String report = "action/ac_public/Report";//  传userID 举报人ID  reportID 友记或友聚的ID    type 0友聚 1友记 info举报内容
    //action/ac_public/addGroup 邀请加入群   传userID群主ID   phone电话号   gid 群id
    public static String addGroup = "action/ac_public/addGroup";
    //群列表
    public static String groupList = "action/ac_public/groupList";//群列表    传userID 用户id    返回 name群名称   groupid群ID
    //退出群
    public static String moveOutGroup = "action/ac_public/moveOutGroup";  //action/ac_public/moveOutGroup 退出群   传group_id群 ID   userID用户ID
    //新个人主页
    public static String homepagePartOne = "action/ac_user/HomepagePartOne"; //action/ac_user/HomepagePartOne 用户基本信息接口 传status  tid  uid
    public static String homepagePartTwo = "action/ac_user/HomepagePartTwo"; //action/ac_user/HomepagePartTwo 友记数据 传status  tid  uid
    public static String homepagePartthree = "action/ac_user/HomepagePartThree"; //action/ac_user/HomepagePartTwo 友聚数据 传status  tid  uid
    public static String homepagePartFour = "action/ac_user/HomepagePartFour"; //action/ac_user/HomepagePartTwo 照片 传status  tid  uid

    //任务URL
    public static String gameList = "action/ac_coupon/gameList";
    //添加视频接口
    public static String upLoadVideo = "action/ac_video/uploadVideo"; //添加视频接口   传userID用户id  vname视频名称   vurl视频地址
    //获取视频信息接口
    public static String videoInfo = "action/ac_video/videoInfo";//   传vid"
    //我的视频列表
    public static String myVideo = "action/ac_video/myVideo"; //传userID 用户id   page分页  返回 vID 视频ID   vname名称   vurl地址  vtime时间  img图片地址
    //编辑视频名称
    public static String videoEdit = "action/ac_video/videoEdit";//编辑   传vname 名称  vID视频ID   userID用户id
    //删除视频
    public static String videoDel = "action/ac_video/videoDel"; // 删除   传userID 用户id   vID视频ID
    //视频点赞
    public static String videoPraise = "action/ac_video/videoPraise";//action/ac_video / videoPraise  点赞/取消点赞     传id视频id  uid用户id
    //视频点赞数据
    public static String videoNumInfo = "action/ac_video/videoNumInfo";//action/ac_video  /  videoNumInfo   传vID视频ID   uid用户ID    返回 praise_num赞数量   comment_num评论数量   status 0未赞过  1已赞
    //视频评论列表
    public static String videoReviewsList = "action/ac_video/videoReviewsList";//action/ac_article  /   videoReviewsList  评论列表   传vID视频ID   page分页     返回 vcID评论ID   vcontent内容   vctime时间  username用户名    userpic用户图片   replyList回复列表
    //视频评论
    public static String videoReviews = "action/ac_video/videoReviews";//action/ac_article / videoReviews  评论  传id视频id  fctitle内容  uid用户id
    //回复视频评论
    public static String replyVideoReviews = "action/ac_video/reply";// 回复接口   传userID用户ID  vcID评论的ID  vinfo回复内容

}
