package com.konka.demo.alias;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AliasBeanParser {
	private static String TEST_JSON = "[{\"module_key\": \"MEMC\",\"original_name\": \"MEMC Pro\",\"original_desc\": \"MEMC Pro技术\",\"alias_name\": [{\"LEDxxA1\": \"芯片变频\"},{\"LEDxxR1\": \"芯片变频\"}],\"alias_desc\": [{\"LEDxxA1\": \"芯片变频技术\"},{\"LEDxxR1\": \"芯片变频技术\"}]},{\"module_key\": \"EQ\",\"original_name\": \"HiFi Pro\",\"alias_name\": [{\"LEDxxA1\": \"哈曼卡顿\"},{\"LEDxxR1\": \"哈曼卡顿\"}],\"original_desc\": \"采用HiFi Pro\",\"alias_desc\": [{\"LEDxxA1\": \"哈曼卡顿\"},{\"LEDxxR1\": \"哈曼卡顿\"}]}]";

	public static Map<String, AliasBean> parse(String content) {
		Map<String, AliasBean> modules = new HashMap<>();
		List<AliasBean> tmp = JSON.parseArray(content, AliasBean.class);
		for (AliasBean aliasBean : tmp) {
			modules.put(aliasBean.key, aliasBean);
		}
		return modules;
	}
	
	public static Map<String, AliasBean> parseTest() {
		Map<String, AliasBean> modules = new HashMap<>();
		List<AliasBean> tmp = JSON.parseArray(TEST_JSON, AliasBean.class);
		for (AliasBean aliasBean : tmp) {
			modules.put(aliasBean.key, aliasBean);
		}
		return modules;
	}
	
}
