package org.tangdao.modules.sms.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tangdao.common.service.impl.CrudServiceImpl;
import org.tangdao.common.utils.StringUtils;
import org.tangdao.modules.sms.mapper.SmsMtTaskPacketsMapper;
import org.tangdao.modules.sms.model.domain.SmsMtTaskPackets;
import org.tangdao.modules.sms.model.domain.SmsPassage;
import org.tangdao.modules.sms.service.ISmsMtTaskPacketsService;
import org.tangdao.modules.sms.service.ISmsPassageService;
import org.tangdao.modules.sys.model.domain.Area;
import org.tangdao.modules.sys.service.IAreaService;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 下行短信任务分包ServiceImpl
 * @author ruyang
 * @version 2019-09-06
 */
@Service
public class SmsMtTaskPacketsServiceImpl extends CrudServiceImpl<SmsMtTaskPacketsMapper, SmsMtTaskPackets> implements ISmsMtTaskPacketsService{
	
	@Autowired
	private IAreaService areaService;
	
	@Autowired
	private ISmsPassageService smsPassageService;
	
	@Override
	public IPage<SmsMtTaskPackets> page(IPage<SmsMtTaskPackets> page, Wrapper<SmsMtTaskPackets> queryWrapper){
		IPage<SmsMtTaskPackets> pageData = baseMapper.selectPage(page, queryWrapper);
		Map<String, String> areaMap = new HashMap<>();
	    Map<String, String> passageMap = new HashMap<>();
		pageData.getRecords().stream().forEach(r->{
			// 组装省份信息
            if (StringUtils.isNotBlank(r.getAreaCode())) {
                if (areaMap.containsKey(r.getAreaCode())) {
                    r.setAreaName(areaMap.get(r.getAreaCode()));
                } else {
                    // 根据省份代码查询省份名称
                	Area area = areaService.get(r.getAreaCode());
                    r.setAreaName(area == null ? "未知" : area.getAreaName());
                    areaMap.put(r.getAreaCode(), r.getAreaName());
                }
            }

            // 组装通道信息
            if (StringUtils.isNotBlank(r.getFinalPassageId())) {
                if (passageMap.containsKey(r.getFinalPassageId())) {
                    r.setPassageName(passageMap.get(r.getFinalPassageId()));
                } else {
                    SmsPassage passage = smsPassageService.findById(r.getFinalPassageId());
                    r.setPassageName(passage == null ? "未知" : passage.getName());
                    passageMap.put(r.getFinalPassageId(), r.getPassageName());
                }
            }
		});
		return pageData;
	}
}