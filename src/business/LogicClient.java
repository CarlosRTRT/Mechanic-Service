package business;

import java.util.ArrayList;

import domain.Client;

public class LogicClient<T> {
	
	public static boolean checkId(ArrayList<Client> cliente, String id) {
		
		for(Client temp: cliente) {
			if(temp.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
}
