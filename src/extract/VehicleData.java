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


public class VehicleData {

    // Fields

    private final Logger logger = Logger.getLogger(VehicleData.class.getName()); // for Log purpose

    private String webUrl;
    private BDConnect connect;


    // Constructor

    public VehicleData(String webUrl) {
        this.connect = new BDConnect();
        this.webUrl = webUrl;
    }


    // Methods

    public void getData(String readBrandApiEndPointFilePath, String writeDataFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(readBrandApiEndPointFilePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(writeDataFilePath))) {
            writer.write("Brand,Model,Type,Price_Range,Image_Url,cc_value");
            writer.newLine();
            String line;

            while ((line = reader.readLine()) != null) {

                String apiUrl = String.format(webUrl, line.split("/")[1]);
                logger.info("Fetching URL: " + apiUrl);

                String jsonResponse = connect.getJsonResponse(apiUrl);
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
                    logger.info("No item is present in the items array in json response and total_item_count: "
                            + items.length());
                    continue;
                }

                for (int i = 0; i < items.length(); i++) {
                    JSONObject itemsCurrentObject = items.getJSONObject(i);

                    String brandName = itemsCurrentObject.optString("brandName", "N/A");
                    String modelName = itemsCurrentObject.optString("modelName", "N/A");
                    String type = itemsCurrentObject.optString("fuelType", "N/A");
                    String priceRange = itemsCurrentObject.optString("priceRange", "N/A");
                    String image = itemsCurrentObject.optString("image", "N/A");
                    int engineCcValue = itemsCurrentObject.optInt("ccValue", 0);
                    
                    String cleanedBrandName = escapeCsvField(brandName);
                    String cleanedModelName = escapeCsvField(modelName);
                    String cleanedType = escapeCsvField(type);
                    String cleanedPriceRange = escapeCsvField(priceRange);
                    String cleanedImage = escapeCsvField(image);
                    String cleanedEngineCCValue= escapeCsvField(String.valueOf(engineCcValue));
                    

                    writer.write(cleanedBrandName + "," + cleanedModelName + "," + cleanedType + "," + cleanedPriceRange + "," + cleanedImage + ","
                            + cleanedEngineCCValue);
                    
                    writer.newLine();
                }

                Thread.sleep(3000);
            }

        } catch (Exception e) {
            logger.severe("Failed to parse JSON response: " + e.getMessage());
        }
    }
    
    private String escapeCsvField(String field) {

        if (field == null) {
            return "\"\"";
        }

        // Check if field needs quoting
        boolean needsQuotes =
                field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r");

        if (needsQuotes) {
            // Escape internal quotes by doubling them
            String escaped = field.replace("\"", "\"\"");
            return "\"" + escaped + "\"";
        } else {
            return field;
        }
    }
}
