package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {
    //根据用户id和好友id查询数据条数
    @Query(value = "SELECT count(1) FROM tb_friend WHERE userid = ? and friendid = ?",nativeQuery = true)
    public int selectUseridAndFriendid(String userid,String friendid);

    //根据用户id和好友id修改islike
    @Modifying
    @Query(value = "UPDATE tb_friend set islike = ? WHERE userid = ? and friendid = ?",nativeQuery = true)
    public void updateIslike(String islike,String userid,String friendid);
}
