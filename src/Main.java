import java.io.IOException;

import extract.BikeDekhoData;
import extract.ScooterBrand;
import extract.TwoWheelerBrand;
import service.BDEnum;

public class Main {

	public static void main(String[] args) throws IOException {

		
		// for Scooters
		final String scooterFilePath = "/home/robin/eclipse-workspace/BikeDekho/src/csv/scootersBrandAPIEndpoint.csv";
		final String scootersBrandURL = BDEnum.BIKE_DEKHO_SCOOTERS_BRAND_URL.getValue();

		final TwoWheelerBrand twScooterBrand = new ScooterBrand(scooterFilePath, scootersBrandURL);
		twScooterBrand.getBrands();

		
		// for Bikes
		final String bikesBrandApiEndPoint = "/home/robin/eclipse-workspace/BikeDekho/src/csv/bikesBrandAPIEndpoint.csv";
		final String bikessBrandURL = BDEnum.BIKE_DEKHO_BIKE_BRAND_URL.getValue();

		final TwoWheelerBrand twBikeBrand = new ScooterBrand(bikesBrandApiEndPoint, bikessBrandURL);
		twBikeBrand.getBrands();
		
		
		// write the data of Bike
		final String writeBikesData = "/home/robin/eclipse-workspace/BikeDekho/src/csv/bikesData.csv";
		
		final BikeDekhoData bikeData = new BikeDekhoData();
		bikeData.getBikeData(bikesBrandApiEndPoint, writeBikesData);
		
		
		System.out.println("Demo for BikeDekho");
	}

}