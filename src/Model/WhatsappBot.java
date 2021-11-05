package Model;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class WhatsappBot {

    private ChromeOptions options;
    private ChromeDriver driver;

    public WhatsappBot(){
        try {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\paulo\\chromedriver\\chromedriver_win32\\chromedriver.exe");
            options = new ChromeOptions();
            options.addArguments("user-data-dir=" + "C:\\Users\\paulo\\IdeaProjects\\Mercado Livre Scraping\\profile\\wpp");
            driver = new ChromeDriver(options);
        }catch (Exception e){
            System.out.println("Erro ao construir o bot.");
        }
    }

    public void open_whatsapp() {
        try {
            driver.get("https://web.whatsapp.com/");
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        }catch (Exception e){
            System.out.println("Erro ao abrir o WhatsApp.");
        }
    }

    public void search_contact(String contact_name){
        try {
            WebElement search_box = driver.findElementByClassName("_13NKt");
            search_box.sendKeys(contact_name);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            WebElement chat_contact = driver.findElementByCssSelector("[title ^= " + contact_name + "]");
            chat_contact.click();
        }catch (Exception e){
            System.out.println("Erro ao procurar contato.");
        }
    }

    public void send_message(String message){
        try {
            List<WebElement> message_box = driver.findElementsByClassName("_13NKt");
            message_box.get(1).sendKeys(message);
            WebElement send_button = driver.findElementByXPath("//span[@data-testid='send']");
            send_button.click();
        }catch (Exception e){
            System.out.println("Erro ao enviar mensagem.");
        }
    }

    public String get_last_message(){
        List<WebElement> messages = driver.findElementsByClassName("_1Gy50");
        int len = messages.size() - 1;
        String message = messages.get(len).getText();
        return message;
    }

}
