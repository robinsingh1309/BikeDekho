package extract;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import service.BDEnum;

public class BikeBrands {

	// file path to store the end points of bike brands urls
	private final String filePath = "/home/robin/eclipse-workspace/BikeDekho/src/csv/bikeBrandsUrl.csv";
	
	private final String brandUrl = BDEnum.BIKE_DEKHO_BIKE_BRAND_URL.getValue();
	
	public void getBikeBrand() throws IOException 
	{
		
		Document document = Jsoup.connect(brandUrl)
								.timeout(10000)
								.get();
		
		Elements brandElementLists = document.select("#all_brands > ul > li");
		
		try( BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)) )
		{
			for(Element ele : brandElementLists) 
			{	
				String urlName = ele.getElementsByTag("a").attr("href");
				
				writer.write(urlName+"\n");	// write it to the file
			}	
			
		} catch (Exception e) {
			System.out.println("Error in writing it in file.");
		}
	}
}