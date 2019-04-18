package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userid, String friendid) {
        //根据用户id和好友id查询数据条数，
        int count = friendDao.selectUseridAndFriendid(userid, friendid);
        // 判断查询结果是否为0，如果返回的结果是0表示没有添加好友，就可以添加好友
        if(count == 0){
            Friend friend = new Friend();
            friend.setUserid(userid);
            friend.setFriendid(friendid);
            //默认情况下不是相互喜欢
            friend.setIslike("0");
            //在好友表中添加数据
            friendDao.save(friend);
            //查询并判断对方是否已经把自己加为好友了
            //如果对方已经把自己加为好友了 就需要都进行修改islike字段为 "1";
        }
        return count;
    }
}
