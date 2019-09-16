package org.tangdao.modules.sms.service.impl;

import org.springframework.stereotype.Service;

import org.tangdao.common.service.impl.CrudServiceImpl;
import org.tangdao.modules.sms.service.ISmsMtTaskPacketsService;
import org.tangdao.modules.sms.model.domain.SmsMtTaskPackets;
import org.tangdao.modules.sms.mapper.SmsMtTaskPacketsMapper;

/**
 * 下行短信任务分包ServiceImpl
 * @author ruyang
 * @version 2019-09-06
 */
@Service
public class SmsMtTaskPacketsServiceImpl extends CrudServiceImpl<SmsMtTaskPacketsMapper, SmsMtTaskPackets> implements ISmsMtTaskPacketsService{
		
}