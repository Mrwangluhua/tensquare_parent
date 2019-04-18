package com.tensquare.comment.service;

import com.tensquare.comment.dao.CommentDao;
import com.tensquare.comment.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;

    public void save(Comment comment){
        String id = idWorker.nextId() + "";
        comment.set_id(id);
        commentDao.save(comment);
    }

    public Page<Comment> findByPage(String articleId, Integer page,Integer size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return commentDao.findByArticleid(articleId,pageRequest);
    }

    public void deleteById(String id){
        commentDao.deleteById(id);
    }

    public List<Comment> findAll() {
        return commentDao.findAll();
    }
}
