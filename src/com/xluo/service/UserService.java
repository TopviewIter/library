package com.xluo.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.xluo.po.User;

public class UserService {

	public Map<String, String> changeToMap(List<User> users){
		Map<String, String> result = new LinkedHashMap<String, String>();
		result.put("select-one", null);
		for(User user : users){
			result.put(user.getUsername() + "-" + user.getEmail(), user.getId());
		}
		return result;
	}
	
}
