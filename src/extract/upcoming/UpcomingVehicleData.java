package extract.upcoming;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import service.BDConnect;

public class UpcomingVehicleData {

	
	//Fields
	
	private final Logger logger = Logger.getLogger(UpcomingVehicleData.class.getName());	// for Log purpose
	
	private String webUrl;
	private BDConnect connect;
	
	
	//Constructor
	
	public UpcomingVehicleData(String webUrl) {
		this.connect = new BDConnect();
		this.webUrl = webUrl;
	}
	
	
	//Methods
	
	public void getData(String writeUpcomingDataFilePath) throws IOException 
	{
		try(
				BufferedWriter writer = new BufferedWriter(new FileWriter(writeUpcomingDataFilePath))
			)
		{
			writer.write("Brand,Model,Type,Price_Range,Image_Url,cc_value\n");
			
			
			// Logic for Pagination
			int currentPage = 1;
			int pageCount = 1;
			
			String apiUrl = String.format(webUrl, currentPage);
			logger.info("Fetching URL: " + apiUrl);
			
			
			while( currentPage <= pageCount ) 
			{
				
				String jsonResponse = connect.getJsonResponse(apiUrl);
				if (jsonResponse == null) {
					logger.warning("NO JSON Response received from the server: " + jsonResponse);
					return;
				}
				
				JSONObject responseJsonObject = new JSONObject(jsonResponse);
				
				JSONObject data = responseJsonObject.optJSONObject("data");
				if (data == null) {
				    logger.warning("'data' object not found in JSON");
				    return;
				}
				
				
				// extracting the page_count data from the web-page
				JSONObject _meta = data.optJSONObject("_meta");
				if (_meta == null) {
				    logger.warning("'_meta' object not found in JSON");
				    return;
				}
				
				if(currentPage == 1)
					pageCount = _meta.getInt("pageCount");	// extracting the page_count data from the _meta JSON Object and to make sure this variable does not update every-time
				
				
				JSONArray items = data.getJSONArray("items");
				if (items == null || items.length() == 0) {
					logger.info("No item is present in the items array in json response and total_item_count: " + items.length());
					return;
				}
				
				for(int i = 0; i < items.length(); i++) 
				{
					JSONObject itemsCurrentObject = items.getJSONObject(i);
					
					String variant_brand_name = itemsCurrentObject.optString("brandName", "N/A");
					String variant_model_name = itemsCurrentObject.optString("name", "Model name yet to be revealed");
					String variant_fuel_type = itemsCurrentObject.optString("variant_fuel_type", "Fuel type yet to be revealed");
					String variant_price_range = itemsCurrentObject.optString("priceRange", "Price yet to be revealed");
					String variant_image = itemsCurrentObject.optString("image", "Image is yet to be revealed");
					String engine_cc_value = itemsCurrentObject.optString("engine", "CC details yet to be revealed");
					
					writer.write(variant_brand_name + "," + variant_model_name + "," + variant_fuel_type + "," + variant_price_range + "," + variant_image + "," + engine_cc_value + "\n");
				}
				
				currentPage++;
				
				Thread.sleep(3000);	
			}
			
		} catch (Exception e) {
			logger.severe("Failed to parse JSON response: " + e.getMessage());
		}
	}

}