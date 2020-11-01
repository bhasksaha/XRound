import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utils {

	static WebDriver driver;

	public static void view(String name) {
		
		//*[text()='" + name + "']
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		List<WebElement> main = driver.findElements(By.xpath("//*[text()='" + name + "']"));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if(main.size()!=0)
		{
		List<WebElement> li = driver.findElements(By.xpath("//tr/following::td[1]"));
		for (int i = 0; i <= li.size(); i++) {
				if (li.get(i).getText().equalsIgnoreCase(name)) {
					driver.findElement(By.xpath("//tr/td[text()='" + name + "']//following::button[2]")).click();
					break;
				}

			}
		}
		
		else {
			driver.findElement(By.xpath("//*[text()='»']")).click();
			view(name);
	}
	
	}
}
