package com.konka.demo.alias;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AliasBean {
	@JSONField(name = "module_key")
	public String key;
	public Map<String, String> aliasNames;
	public Map<String, String> aliasDescs;

	public AliasBean() {
		aliasNames = new HashMap<>();
		aliasDescs = new HashMap<>();
	}

	@JSONField(name = "alias_name")
	public void setAliasName(List<Map<String, String>> names) {
		for (Map<String, String> map : names) {
			for (String key : map.keySet()) {
				aliasNames.put(key, map.get(key));
			}
		}
	}

	@JSONField(name = "alias_desc")
	public void setAliasDesc(List<Map<String, String>> names) {
		for (Map<String, String> map : names) {
			for (String key : map.keySet()) {
				aliasDescs.put(key, map.get(key));
			}
		}
	}
	
	public String getNameBySeries(String series) {
		String ret = null;
		if (!aliasNames.isEmpty() && aliasNames.containsKey(series)) {
			ret = aliasNames.get(series);
		}
		return ret;
	}
	
	public String getDescBySeries(String series) {
		String ret = null;
		if (!aliasDescs.isEmpty() && aliasDescs.containsKey(series)) {
			ret = aliasDescs.get(series);
		}
		return ret;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("module_key : ").append(key).append("\n");
		sb.append("alias_name [ ").append("\n");
		for (Entry<String, String> entry : aliasNames.entrySet()) {
			sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
		}
		sb.append("]\n");
		sb.append("alias_desc [ ").append("\n");
		for (Entry<String, String> entry : aliasDescs.entrySet()) {
			sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}

}
