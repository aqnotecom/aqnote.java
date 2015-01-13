package com.madding.shared.components.resultcode;

import java.util.Map;

/**
 * 富文案显示扩展接口
 * 
 * @author madding.lip
 */
public interface IRichResultCode extends IResultCode {

	/**
     * 获取富文本,如：html
     */
	public String getRichMessage(Map<String, Object> param);
}
