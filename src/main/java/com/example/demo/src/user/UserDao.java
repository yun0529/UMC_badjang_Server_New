package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Random;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "SELECT User.userIdx, userNickName, userImageUrl, userBackgroundImageUrl, " +
                "count(subscribeUserIdx) as subscribeCount, count(Video.userIdx) as videoCount, " +
                "userComment, userAccount,userSubscribeAccess,userSavePlayListAccess, User.createdAt, " +
                "sum(viewCount) as totalViews " +
                "from YouTubeDB.User " +
                "left join UserSubscribe on User.userIdx = UserSubscribe.subscribeUserIdx " +
                "left join Video on User.userIdx = Video.userIdx " +
                "left join VideoViewCount on Video.videoIdx = VideoViewCount.videoIdx " +
                "group by User.userIdx ";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userNickname"),
                        rs.getString("userImageUrl"),
                        rs.getString("userBackgroundImageUrl"),
                        rs.getInt("subscribeCount"),
                        rs.getInt("videoCount"),
                        rs.getString("userComment"),
                        rs.getString("userAccount"),
                        rs.getString("userSubscribeAccess"),
                        rs.getString("userSavePlayListAccess"),
                        rs.getString("createdAt"),
                        rs.getString("totalViews"))
                );
    }

    public List<GetUserRes> getUsersByEmail(int userIdx){
        String getUsersByEmailQuery = "SELECT User.userIdx, userNickName, userImageUrl, userBackgroundImageUrl, " +
                "count(subscribeUserIdx) as subscribeCount, count(Video.userIdx) as videoCount, " +
                "userComment, userAccount,userSubscribeAccess,userSavePlayListAccess, User.createdAt, " +
                "sum(viewCount) as totalViews " +
                "from YouTubeDB.User " +
                "left join UserSubscribe on User.userIdx = UserSubscribe.subscribeUserIdx " +
                "left join Video on User.userIdx = Video.userIdx " +
                "left join VideoViewCount on Video.videoIdx = VideoViewCount.videoIdx " +
                "group by User.userIdx " +
                "having User.userIdx = ?";
        int getUsersByEmailParams = userIdx;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userNickname"),
                        rs.getString("userImageUrl"),
                        rs.getString("userBackgroundImageUrl"),
                        rs.getInt("subscribeCount"),
                        rs.getInt("videoCount"),
                        rs.getString("userComment"),
                        rs.getString("userAccount"),
                        rs.getString("userSubscribeAccess"),
                        rs.getString("userSavePlayListAccess"),
                        rs.getString("createdAt"),
                        rs.getString("totalViews")),
                getUsersByEmailParams);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (userAccount, userPw, userImageUrl, userBackgroundImageUrl," +
                " userNickname, userName, userComment, nationalityIdx, userPhoneNumber, userBirth, userGender, userSubscribeAccess, userSavePlayListAccess) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserAccount(), postUserReq.getUserPw(), postUserReq.getUserImageUrl(),
                postUserReq.getUserBackgroundImageUrl(), postUserReq.getUserNickname(), postUserReq.getUserName(), postUserReq.getUserComment(),
                postUserReq.getNationalityIdx(), postUserReq.getUserPhoneNumber(), postUserReq.getUserBirth(), postUserReq.getUserGender(),
                postUserReq.getUserSubscribeAccess(), postUserReq.getUserSavePlayListAccess()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String userAccount){
        String checkEmailQuery = "select exists(select userAccount from User where userAccount = ?)";
        String checkEmailParams = userAccount;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int modifyUserStatusLogIn(int userIdx){
        String modifyUserNameQuery = "update User set status = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{"Active", userIdx};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, userNickname, userAccount,userPw,status from User where userAccount = ?";
        String getPwdParams = postLoginReq.getUserAccount();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("userNickname"),
                        rs.getString("userAccount"),
                        rs.getString("userPw"),
                        rs.getString("status")
                ), getPwdParams);

    }
}
