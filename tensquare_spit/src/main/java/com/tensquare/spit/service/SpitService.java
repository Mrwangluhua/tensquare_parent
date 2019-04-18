package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    //根据id查询
    public Spit findById(String id) {
        Spit spit = spitDao.findById(id).get();

        return spit;
    }

    //查询所有
    public List<Spit> findAll() {
        List<Spit> list = spitDao.findAll();

        return list;
    }

    //新增
    public void save(Spit spit) {
        String id = idWorker.nextId() + "";
        spit.set_id(id);
        //发布吐槽(就是新增),需要设置初始数据
        //发布日期
        spit.setPublishtime(new Date());
        //浏览量
        spit.setVisits(0);
        //点赞数
        spit.setThumbup(0);
        //分享数
        spit.setShare(0);
        //回复数
        spit.setComment(0);
        //是否可见
        spit.setComment(1);

        //对parentid进行处理,如果有parentid给父节点回复数加一,如果没有就不用进行类似的操作
        if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
            //如果有parentId,就对父节点的回复数加一
            Query query = new Query();
            //设置的条件是父的id
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            //设置回复数加一
            update.inc("comment", 1);

            mongoTemplate.updateFirst(query, update, "spit");
        }

        spitDao.save(spit);
    }

    //修改
    public void updateById(Spit spit) {
        spitDao.save(spit);
    }

    //删除
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> comment(String parentid, Integer page, Integer size) {

        PageRequest request = PageRequest.of(page-1, size);
        return spitDao.findByParentid(parentid,request);

    }

    public void thumbup(String spitId) {
        //性能较为低下 需要操作两次数据库
       /* Spit spit = spitDao.findById(spitId).get();
        spit.setThumbup(spit.getThumbup()+1);
        spitDao.save(spit);*/

       //进行点赞优化 只需操作一次数据库实现点赞加一

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);



        mongoTemplate.updateFirst(query,update,"spit");



    }
}
