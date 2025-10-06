package extract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import service.BDConnect;
import service.BDEnum;

public class BikeDekhoData {
	
	// for log info
	private final Logger logger = Logger.getLogger(BikeDekhoData.class.getName());
	
	//file path to read the end points of bike brand to extract the data and pass the info to the webUrl
	private final String bikeBrandsFilePath = "/home/robin/eclipse-workspace/BikeDekho/src/csv/bikeBrandsUrl.csv";
	
	//file path to store the data of bikes
	private final String bikesDataFilePath = "/home/robin/eclipse-workspace/BikeDekho/src/csv/bikesData.csv";
		
	private String webUrl = BDEnum.BIKE_DEKHO_BEST_BIKE_URL.getValue();
	private BDConnect connect;
	
	public BikeDekhoData() {
		this.connect = new BDConnect();
	}
	
	public void getBikeData() throws IOException 
	{
		try(
				BufferedReader reader = new BufferedReader(new FileReader(bikeBrandsFilePath));
				BufferedWriter writer = new BufferedWriter(new FileWriter(bikesDataFilePath))
			)
		{
			writer.write("Brand,Model,Type,Price_Range,Image_Url,cc_value\n");
			String line;
			
			while( (line = reader.readLine()) != null ) 
			{
				
				String apiUrl = String.format(webUrl, line.split("/")[1]);
				logger.info("Fetching URL: " + apiUrl);
				
				String jsonResponse = connect.getJsonResponseOfBikeDekho(apiUrl);
				if (jsonResponse == null) {
					logger.warning("NO JSON Response received from the server: " + jsonResponse);
					continue;
				}
				
				JSONObject responseJsonObject = new JSONObject(jsonResponse);
				
				JSONObject data = responseJsonObject.optJSONObject("data");
				if (data == null) {
				    logger.warning("'data' object not found in JSON");
				    continue;
				}
				
				JSONObject primaryData = data.optJSONObject("primaryData");
				if (primaryData == null) {
					logger.info("PrimaryData JSONObject is null");
					continue;
				}
				
				JSONArray items = primaryData.getJSONArray("items");
				if (items == null || items.length() == 0) {
					logger.info("No item is present in the items array in json response and total_item_count: " + items.length());
					continue;
				}
				
				for(int i = 0; i < items.length(); i++) 
				{
					JSONObject itemsCurrentObject = items.getJSONObject(i);
					
					String brandName = itemsCurrentObject.optString("brandName", "N/A");
					String modelName = itemsCurrentObject.optString("modelName", "N/A");
					String type = itemsCurrentObject.optString("fuelType", "N/A");
					String priceRange = itemsCurrentObject.optString("priceRange", "N/A");
					String image = itemsCurrentObject.optString("image", "N/A");
					int engineCcValue = itemsCurrentObject.optInt("ccValue", 0);
					
					writer.write(brandName + "," + modelName + "," + type + "," + priceRange + "," + image + "," + engineCcValue + "\n");
				}
				
				Thread.sleep(3000);
			}
			
		} catch (Exception e) {
			logger.severe("Failed to parse JSON response: " + e.getMessage());
		}
	}
}