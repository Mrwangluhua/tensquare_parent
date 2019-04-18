package com.tensquare.gathering.service;

import com.tensquare.gathering.dao.GatheringDao;
import com.tensquare.gathering.pojo.Gathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

@Service
public class GatheringService {

    @Autowired
    private GatheringDao gatheringDao;

    public List<Gathering> findAll() {
        return gatheringDao.findAll();
    }

    @Cacheable(value = "gathering",key = "#id")
    public Gathering findById(String id) {
        return gatheringDao.findById(id).get();
    }

    @CacheEvict(value = "gathering",key = "#gathering.id")
    public void update(Gathering gathering) {
        gatheringDao.save(gathering);
    }

    @CacheEvict(value = "gathering",key = "#id")
    public void delete(String id) {
        gatheringDao.deleteById(id);
    }

    public void add(Gathering gathering) {
            IdWorker idWorker = new IdWorker();
            gathering.setId(idWorker.nextId()+"");
            gatheringDao.save(gathering);
    }


}
