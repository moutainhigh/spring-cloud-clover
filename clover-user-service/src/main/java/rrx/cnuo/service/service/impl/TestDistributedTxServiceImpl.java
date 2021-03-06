package rrx.cnuo.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rrx.cnuo.cncommon.util.ClientToolUtil;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.cncommon.vo.order.TradeVo;
import rrx.cnuo.service.dao.UserAccountMapper;
import rrx.cnuo.service.feignclient.BizFeignService;
import rrx.cnuo.service.feignclient.OrderFeignService;
import rrx.cnuo.service.po.UserAccount;
import rrx.cnuo.service.service.TestDistributedTxService;

@Service
public class TestDistributedTxServiceImpl implements TestDistributedTxService {

	@Autowired private BasicConfig basicConfig;
	@Autowired private UserAccountMapper userAccountMapper;
	@Autowired private OrderFeignService orderFeignService;
	@Autowired private BizFeignService bizFeignService;
	
	@Override
//	@LcnTransaction
	public void insertUserOrderStatisUser(Boolean rollBack,TradeVo tradeVo) {
		if(rollBack == null){
			rollBack = false;
		}
		orderFeignService.insertTrade(tradeVo);
		
		bizFeignService.insertStatisUser();
		
		//本地事务
		UserAccount record = new UserAccount();
		record.setUid(ClientToolUtil.getDistributedId(basicConfig.getSnowflakeNode()));
		userAccountMapper.insertSelective(record);
		
		if(rollBack != null && rollBack){
			throw new IllegalStateException("by exFlag");
		}
	}

}
