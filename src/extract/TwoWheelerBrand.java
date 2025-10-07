package extract;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TwoWheelerBrand {
	
	// Fields
	private String filePath;
	private String url;


	// Constructors
	
	public TwoWheelerBrand(String filePath, String url) {
		
		this.filePath = filePath;
		this.url = url;
	}


	// Methods
	
	public void getBrands() throws IOException 
	{
		
		Document document = Jsoup.connect(url)
								.timeout(10000)
								.get();
		
		Elements brandElementLists = document.select("#all_brands > ul > li");
		
		try( BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)) )
		{
			for(Element ele : brandElementLists) 
			{	
				String urlName = ele.getElementsByTag("a").attr("href");
				
				writer.write(urlName+"\n");
			}	
			
		} catch (Exception e) {
			System.out.println("Error in writing it in file.");
		}
	}
	
}