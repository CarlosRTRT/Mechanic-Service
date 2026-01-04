package data;

import java.io.IOException;
import java.util.ArrayList;

import business.LogicClient;
import domain.Client;

public class ClientData {
	private static final String filePath = "CientData.json";
	
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
			System.out.println("Error al guardar en Cliente.json");
		}
	 }
	 
	 public static ArrayList<Client> getList(){
		 try {
			return (ArrayList<Client>) jsonUtils.getAll(Client.class);
		 } catch (IOException e) {
			 System.out.println("Error al recuperar la lista de clientes");
			 return new ArrayList<Client>();
		 }
	 }
}
