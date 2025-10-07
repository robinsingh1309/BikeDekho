package service;

public enum BDEnum {
	
//																																			     ||
	BIKE_DEKHO_URL("https://api.bikedekho.com/v1/pwa/brandPage?_format=json&lang_code=en&connectoid=aa231c8c-9dd7-b75c-7f82-a2dc741a8141&url=%%2F%s&devicePlatform=web"),	// USING the concept of String format
	
	BIKE_DEKHO_BIKE_BRAND_URL("https://www.bikedekho.com/new-bikes#brands"),
	
	BIKE_DEKHO_SCOOTERS_BRAND_URL("https://www.bikedekho.com/scooters#all_brands"),
//																																															||
	BIKE_DEKHO_UPCOMING_SCOOTERS_URL("https://api.bikedekho.com/v1/pwa/getBikeFilterResult?pageType=upcoming&_format=json&lang_code=en&connectoid=aa231c8c-9dd7-b75c-7f82-a2dc741a8141&page=%d&pageSize=18&styleslug=scooters&url=%%2Fupcoming-scooters&source=web"),
//																																														 ||	
	BIKE_DEKHO_UPCOMING_BIKES_URL("https://api.bikedekho.com/v1/pwa/getBikeFilterResult?pageType=upcoming&_format=json&lang_code=en&connectoid=aa231c8c-9dd7-b75c-7f82-a2dc741a8141&page=%d&pageSize=18&styleslug=bikes&url=%%2Fupcoming-bikes&source=web");
		
	
	// Fields
	
	private String url;
	
	// Constructor
	
	private BDEnum(String url) {
		this.url = url;
	}
	
	// Getter
	
	public String getValue() 
	{
		return url;
	}
	
}