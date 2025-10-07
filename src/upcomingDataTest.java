import java.io.IOException;

import extract.upcoming.UpcomingBike;
import extract.upcoming.UpcomingVehicleData;
import service.BDEnum;

public class upcomingDataTest {

	public static void main(String[] args) throws IOException {
		
		final String upcomingBikeUrl = BDEnum.BIKE_DEKHO_UPCOMING_BIKES_URL.getValue();
		
		// write data of Upcoming Bikes
		final String writeUpcomingBikesData = "/home/robin/eclipse-workspace/BikeDekho/src/csv/upcomingBikesData.csv";
		
		final UpcomingVehicleData upcomingBikeVehicleData = new UpcomingBike(upcomingBikeUrl);
		upcomingBikeVehicleData.getData(writeUpcomingBikesData);
		
		System.out.println("--------------------------------------------------------------------------------------------------------");
		
	}

}