package org.tangdao.common.service;

import java.util.List;

public interface ITreeService<T> extends ICurdService<T>{

	/**
	 * 
	 * @param sourceList 源数据列表
	 * @param targetList 目标数据列表
	 * @param parentId   目标数据列表的顶级节点
	 */
	void convertTreeSort(List<T> sourceList, List<T> targetList, String parentId);

	/**
	 * 
	 * @param sourceList 源数据列表
	 * @param targetList 目标数据列表
	 * @param parentId   目标数据列表的顶级节点
	 */
	void convertChildList(List<T> sourceList, List<T> targetList, String parentId);
	
}
