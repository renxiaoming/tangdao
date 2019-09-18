package org.tangdao.modules.sms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.tangdao.modules.sms.model.domain.SmsPassage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 通道管理Mapper接口
 * @author ruyang
 * @version 2019-09-06
 */
@Mapper
public interface SmsPassageMapper extends BaseMapper<SmsPassage> {
	
	@Select("select distinct(code) from sms_passage")
	public List<String> selectAvaiableCodes();
	
	@Select("select s.id, s.status, s.create_time from sms_passage s where s.code = #{code} limit 1")
	public SmsPassage getPassageByCode(@Param("code") String code);
}