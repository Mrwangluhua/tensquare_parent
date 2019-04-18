package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    @Query(value = "SELECT * FROM tb_problem WHERE id in(SELECT problemid FROM tb_pl WHERE labelid=?) " +
            "ORDER BY replytime DESC", nativeQuery = true)
    public Page<Problem> newlist(String labelId, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem WHERE id in(SELECT problemid FROM tb_pl WHERE labelid=?) " +
            "AND reply>0 ORDER BY reply DESC", nativeQuery = true)
    public Page<Problem> hotlist(String labelId, Pageable pageable);

    @Query(value = "select * from tb_problem where id in (select problemid from tb_pl where labelid = ?)" +
            " and reply = 0 ORDER BY createtime Desc",nativeQuery = true)
    public Page<Problem> waitlist(String labelId,Pageable pageable);

}
