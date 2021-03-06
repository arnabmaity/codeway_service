package com.codeway.fallback.user;

import com.codeway.api.user.ResourceServiceRpc;
import com.codeway.enums.StatusEnum;
import com.codeway.pojo.user.Resource;
import com.codeway.utils.JsonData;
import com.codeway.utils.LogBack;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 接口调用失败处理
 **/

@Component
public class ResourceServiceRpcFallbackFactory implements FallbackFactory<ResourceServiceRpc> {

	private static final String ERROR_INFO = "ResourceServiceRpc.{}远程调用失败，该服务已经停止或者不可访问,参数为:{}";

	@Override
	public ResourceServiceRpc create(Throwable throwable) {
		return new ResourceServiceRpc() {

			@Override
            public JsonData<List<Resource>> findResourceByCondition(Resource resource) {
                LogBack.error(ERROR_INFO, "findResourceByCondition", resource, throwable);
                return JsonData.failed(StatusEnum.RPC_ERROR);

            }
			@Override
            public JsonData<List<Resource>> findResourceByRoleIds(String[] roleIds) {
                LogBack.error(ERROR_INFO, "findResourceByCondition", roleIds, throwable);
                return JsonData.failed(StatusEnum.RPC_ERROR);

            }
		};
	}
}
