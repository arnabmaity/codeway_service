package com.codeway.base.service.backstage;

import com.codeway.api.user.UserServiceRpc;
import com.codeway.base.dao.OptLogDao;
import com.codeway.exception.custom.ResourceNotFoundException;
import com.codeway.pojo.QueryVO;
import com.codeway.pojo.article.Category;
import com.codeway.pojo.base.OptLog;
import com.codeway.pojo.base.QOptLog;
import com.codeway.pojo.user.User;
import com.codeway.utils.QuerydslUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 操作日志
 **/
@Service
public class OptLogService {

    private final OptLogDao optLogDao;
    private final UserServiceRpc userServiceRpc;
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    public OptLogService(OptLogDao optLogDao, UserServiceRpc userServiceRpc) {
        this.optLogDao = optLogDao;
        this.userServiceRpc = userServiceRpc;
    }

    /**
     * 按照条件查询全部操作日志
     * @return IPage<OptLog>
     */
    public Page<OptLog> findOptLogByCondition(OptLog optLog, Pageable pageable) {
        Specification<OptLog> condition = (root, query, builder) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(optLog.getClientIp())) {
                predicates.add(builder.like(root.get("clientIp"), "%" + optLog.getClientIp()));
            }
            return query.where(predicates.toArray(new javax.persistence.criteria.Predicate[0])).getRestriction();
        };
        Page<OptLog> queryResults = optLogDao.findAll(condition, pageable);
        List<User> userList = userServiceRpc.findUser().getData().getResults();
        queryResults.getContent().forEach(
                optLogList -> userList.forEach(
                        user -> {
                            if (user.getId().equals(optLogList.getUserId())) {
                                optLogList.setUserName(user.getUserName());
                            }
                        }
                )
        );
        return queryResults;
    }

	/**
	 * 根据ID查询操作日志
	 *
	 * @param id 操作日志id
	 * @return OptLog
	 */
	public OptLog findById(String id) {
		Optional<OptLog> byId = optLogDao.findById(id);
		return byId.orElseThrow(ResourceNotFoundException::new);
	}

	/**
	 * 添加操作日志
	 *
	 * @param optLog 操作日志实体
	 */
	public void insertOptLog(OptLog optLog) {
		optLogDao.save(optLog);
	}

	public void deleteBatch(List<String> resId) {
		optLogDao.deleteBatch(resId);
	}
}

