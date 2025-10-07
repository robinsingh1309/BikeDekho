import java.io.IOException;

import extract.BikeDekhoBikeData;
import extract.BikeDekhoScooterData;
import extract.ScooterBrand;
import extract.TwoWheelerBrand;
import extract.VehicleData;
import service.BDEnum;

public class Main {

	public static void main(String[] args) throws IOException {

		
		// for Scooters
		final String scooterBrandApiEndPoint = "/home/robin/eclipse-workspace/BikeDekho/src/csv/scootersBrandAPIEndpoint.csv";
		final String scootersBrandURL = BDEnum.BIKE_DEKHO_SCOOTERS_BRAND_URL.getValue();

		final TwoWheelerBrand twScooterBrand = new ScooterBrand(scooterBrandApiEndPoint, scootersBrandURL);
		twScooterBrand.getBrands();

		
		// for Bikes
		final String bikesBrandApiEndPoint = "/home/robin/eclipse-workspace/BikeDekho/src/csv/bikesBrandAPIEndpoint.csv";
		final String bikessBrandURL = BDEnum.BIKE_DEKHO_BIKE_BRAND_URL.getValue();

		final TwoWheelerBrand twBikeBrand = new ScooterBrand(bikesBrandApiEndPoint, bikessBrandURL);
		twBikeBrand.getBrands();
		
		
		// URL to connect to BikeDekho web-site
		final String url = BDEnum.BIKE_DEKHO_URL.getValue();
		
		
		// write data of Bikes
		final String writeBikesData = "/home/robin/eclipse-workspace/BikeDekho/src/csv/bikesData.csv";
		
		final VehicleData bikeVehicleData = new BikeDekhoBikeData(url);
		bikeVehicleData.getData(bikesBrandApiEndPoint, writeBikesData);
		
		
		// write data of Scooters
		final String writeScootersData = "/home/robin/eclipse-workspace/BikeDekho/src/csv/scootersData.csv";
		
		final VehicleData scooterVehicleData = new BikeDekhoScooterData(url);
		scooterVehicleData.getData(scooterBrandApiEndPoint, writeScootersData);
		
		
		System.out.println("--------------------------------------------------------------------------------------------------------");
	}

}