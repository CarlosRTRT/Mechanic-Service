package data;

import java.io.IOException;
import java.util.ArrayList;

import business.LogicClient;
import domain.Client;

public class ClientData {
	private static String filePath = "data.json";
	
	 private static JsonUtils<Client> jsonUtils = new JsonUtils<Client>(filePath);
	 
	 public ClientData() {
		 
	 }
	 
	 public static void saveClient(Client client) {
		 if (LogicClient.checkId(getList(), client.getId())) {
			 System.out.println("Ya existe el cliente");
			 return;
		 }
		 
		 try {
			jsonUtils.saveElement(client);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al guardar en data.json");
		}
	 }
	 
	 public static boolean editClient(Client clientUpdated) {
		 
		 
		 try {
			 ArrayList<Client> clients = getList();
				 
			 int index = 0;
			 boolean found = false;
				 
			 for(Client temp : clients) {
				 if(temp.getId().equals(clientUpdated.getId())) {
					 clients.set(index, clientUpdated);
					 found = true;
					 break;
				 }
				 index++;
			 }
			 
			if(!found) {
	            System.out.println("Cliente no encontrado");
	            return false;
			}		
			
			jsonUtils.saveAll(clients);
			System.out.println("Cliente Modificado");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al modificar el cliente");
			return false;
		}
		 
		 
	 }
	 
	 public static boolean deleteClient(Client clientToEliminate, String id) {
		 
		 try {
			 ArrayList<Client> clients = getList();
				 
			 int index = 0;
			 boolean found = false;
				 
			 for(Client temp : clients) {
				 if(temp.getId().equals(id)) {
					 clients.remove(index);
					 found = true;
					 break;
				 }
				 index++;
			 }
			 
			if(!found) {
	            System.out.println("Cliente no encontrado");
	            return false;
			}		
			
			jsonUtils.saveAll(clients);
			System.out.println("Cliente Eliminado");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al eliminar el cliente");
			return false;
		}
		 
		 
	 }
	 
	 public static ArrayList<Client> getList(){
		 try {
			return (ArrayList<Client>) jsonUtils.getAll(Client.class);
		 } catch (IOException e) {
			 System.out.println("Error al recuperar la lista de datos");
			 return new ArrayList<Client>();
		 }
	 }
}
