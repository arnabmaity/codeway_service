package com.codeway.user.dao;


import com.codeway.pojo.user.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ResourceDao extends JpaRepository<Resource, String>, JpaSpecificationExecutor<Resource>, QuerydslPredicateExecutor<Resource> {

    @Query(value = "SELECT * FROM us_resource WHERE id IN (SELECT resource_id FROM us_role_resource WHERE role_id in (:resId))", nativeQuery = true)
    Set<Resource> findResourceByRoleIds(@Param("resId") List<String> resId);

    /**
     * 查询当前用户的资源列表
     *
     * @param userId 用户id
     * @return 资源数组
     */
    @Query(value = "SELECT * FROM us_resource WHERE id in (SELECT us_resource_id FROM us_role_resource WHERE us_role_id in (SELECT id FROM us_role WHERE id in (SELECT us_role_id FROM us_user_role  WHERE us_user_id = :userId)))order by sort Asc"
            , nativeQuery = true)
    List<Resource> findResourcesOfUser(@Param("userId") String userId);


    /**
     * 根据此角色id查询匹配的资源列表
     * @param roleId 角色id
     */
    @Query(value = "SELECT * FROM us_resource WHERE id in (SELECT us_resource_id FROM us_role_resource WHERE us_role_id in (SELECT id FROM us_role WHERE id = :roleId ))"
            , nativeQuery = true)
    List<Resource> findResourcesOfRole(@Param("roleId") String roleId);

    @Modifying
    @Query("delete from Resource where id in (:ids)")
    void deleteBatch(@Param("ids") List<String> ids);
}
