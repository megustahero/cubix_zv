package hu.cubix.logistics.service;

import hu.cubix.logistics.model.LogisticsUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class LogisticsUserService {
	
	public LogisticsUser getUserByName(String username) {
		
		Map<String, LogisticsUser> users = new HashMap<String, LogisticsUser>();
		Set<String> roles = new HashSet<String>();
		
		roles.add("AddressManager");
		users.put("addressManager", new LogisticsUser("addressManager", new BCryptPasswordEncoder().encode("password"), roles));
		
		roles.add("TransportManager");
		users.put("transportManager", new LogisticsUser("transportManager", new BCryptPasswordEncoder().encode("password"), roles));
		
		
		if(users.containsKey(username))
		{
			return users.get(username);
		}
		
		return null;
	}

}
