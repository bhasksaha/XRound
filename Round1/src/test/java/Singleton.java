import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Singleton {
	
	
	private static WebDriver driver;
	
	private Singleton(){
		if(driver==null) {
			driver=new ChromeDriver();
		}
		
	}
	
	public static WebDriver getinstance() {
		
		Singleton si=new Singleton();
		return driver;
		
	} 
	
	
	
	

}
