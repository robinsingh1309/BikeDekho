import java.io.IOException;

import extract.BikeBrands;
import extract.BikeDekhoData;

public class Main {

	public static void main(String[] args) throws IOException {
		
		// first extract the Bike brands which will be used in the next steps to extract the data from their brands which is published in BikeDekho web-site
		BikeBrands brands = new BikeBrands();
		brands.getBikeBrand();
		
		
		BikeDekhoData testData = new BikeDekhoData();		
		testData.getBikeData();
		
		System.out.println("Demo for BikeDekho");
	}

}