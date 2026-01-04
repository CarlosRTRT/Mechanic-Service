package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils<T> {

	//ruta
	private String filePath;
	
	private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

	public JsonUtils(String filePath){
		this.filePath = filePath;
	}
	
	public List<T> getAll(Class<T> temp) throws IOException{
		File file = new File(this.filePath);
		
		if(!file.exists()) {
			return new ArrayList<T>();
		}
		
		return JsonUtils.mapper.readValue(file, mapper.getTypeFactory().constructCollectionLikeType(List.class, temp));
	}
	
	public void saveElement(T t) throws IOException {
		List<T> temp = getAll((Class<T>) t.getClass());
		
		temp.add(t);
		
		JsonUtils.mapper.writeValue(new File(this.filePath), temp);
	}
}
