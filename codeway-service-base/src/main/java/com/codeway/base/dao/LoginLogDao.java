package com.codeway.base.dao;

import com.codeway.pojo.base.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoginLogDao extends JpaRepository<LoginLog, String>, JpaSpecificationExecutor<LoginLog>{
    @Modifying
    @Query("delete from LoginLog where id in (:ids)")
    void deleteBatch(@Param("ids") List<String> ids);

}
