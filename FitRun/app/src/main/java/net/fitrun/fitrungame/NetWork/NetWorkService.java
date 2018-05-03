package net.fitrun.fitrungame.NetWork;

import net.fitrun.fitrungame.sportSelect.courseSelect.CourseBean;
import net.fitrun.fitrungame.sportSelect.courseSelect.CourseTable;
import net.fitrun.fitrungame.sportSelect.courseSelect.CourseUpdateLog;
import net.fitrun.fitrungame.sportSelect.courseSelect.Message;
import net.fitrun.fitrungame.sportSelect.courseSelect.QuestionResult;
import net.fitrun.fitrungame.sportSelect.courseSelect.StartSportBean;
import net.fitrun.fitrungame.sportSelect.courseSelect.UserBean;
import net.fitrun.fitrungame.sportSelect.courseSelect.question.QuestionBean;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UploadSportLog;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 晁东洋 on 2017/3/31.
 */

public interface NetWorkService {
    //上传运动数据
    @FormUrlEncoded
    @POST("quickMovement/addQuickMovement")
    Call<UploadSportLog> updateUser(@Field("json") String log);

    //上传快捷运动的运动数据收集
    @FormUrlEncoded
    @POST("quickMovement/quickMovementDataCollect")
    Call<UploadSportLog> updateSpeedLog(@Field("uid") String uid,@Field("timeLength") String timeLength,@Field("length") String length,@Field("calorie") String calorie,@Field("maxHeartRate") String maxHeartRate,@Field("fastSpeed") String fastSpeed,@Field("averageSpeed") String averageSpeed,@Field("averageSpeedConfig") String averageSpeedConfig,@Field("fastSpeedConfig") String fastSpeedConfig,@Field("slowestSpeedConfig") String slowestSpeedConfig,@Field("collectDatas") String collectDatas);

    //更新用户的运动数据
    @FormUrlEncoded
    @POST("quickMovement/updateAccountUserExercise")
    Call<UploadSportLog> updateAccountUser(@Field("unionId") String unionId,@Field("calorie") String calorie,@Field("length") String length,@Field("time") String time);

    //获取减肥课程的问卷调查列表
    @GET("question/getLoseWeightQuestionList")
    Call<QuestionBean> getQuestionList(@Query("gender") String gender);

    //查询问卷结果信息
    @POST("question/getLoseWeightQuestionResult")
    @FormUrlEncoded
    Call<QuestionResult> queryQusetionResult(@Field("uid") String uid, @Field("resultIds") String resultIds);

    //获取用户的详细信息
    @POST("account/getAccountMsg")
    @FormUrlEncoded
    Call<UserBean> postUserMessage(@Field("uid") String uid);

    //更新用户信息
    @FormUrlEncoded
    @POST("account/updateAccountMsg")
    Call<UserBean> updateUserWeight(@Field("uid") String uid,@Field("gender") String gender,@Field("weight") String weight,@Field("height") String height,@Field("age") String age);

    //查询课程列表
    @GET("account/getAccountCourseStatus")
    Call<CourseBean> queryCourse(@Query("uid") String uid);

    //设置课程
    @POST("loseWeightCourse/setLoseWeightCourse")
    @FormUrlEncoded
    Call<Message> postSetCourse(@Field("uid") String uid, @Field("startTime") String startTime, @Field("period") String period, @Field("trainingDay") String trainingDay, @Field("courseParams") String courseParams);

    //获取设置的课程信息
    @POST("loseWeightCourse/getLoseWeightCurrentCourseInformation")
    @FormUrlEncoded
    Call<CourseTable> postCourseTable(@Field("uid") String uid);

    //查询单次运动的初始数据
    @GET("loseWeightCourse/getLoseWeightCourseSetSingle")
    Call<StartSportBean> getStartSport(@Query("uid") String uid);

    //减肥课程的运动数据收集
    @POST("loseWeightCourse/loseWeightCourseDataCollect")
    @FormUrlEncoded
    Call<CourseUpdateLog> postUpdateCourselog(@Field("uid") String uid, @Field("timeLength") String timeLength, @Field("length") String length, @Field("calorie") String calorie,@Field("maxHeartRate") String maxHeartRate,@Field("fastSpeed") String fastSpeed,@Field("averageSpeed") String averageSpeed,@Field("averageSpeedConfig") String averageSpeedConfig
            ,@Field("fastSpeedConfig") String fastSpeedConfig,@Field("slowestSpeedConfig") String slowestSpeedConfig
            , @Field("configSpeed") String configSpeed, @Field("collectDatas") String collectDatas);

}
