package br.org.brito.tests;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    WebDriver driver = new ChromeDriver(getOptions());
    String name;

    @Test
    public void shouldAddTodo() {
        driver.navigate().to("http://localhost:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        name = new Faker().name().firstName();
        addTask(name, true, true);
        Assertions.assertEquals(returnWebElement("//p[@id='message']").getText(),  "Success!");
        driver.quit();
    }

    @Test
    public void shouldNotAddTodoWithoutDescription() {
        driver.navigate().to("http://localhost:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        name = new Faker().name().firstName();
        addTask(name, false, true);
        Assertions.assertEquals(returnWebElement("//p[@id='message']").getText(),"Fill the task description");
        driver.quit();
    }

    @Test
    public void shouldNotAddTodoWithoutData() {
        driver.navigate().to("http://localhost:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        name = new Faker().name().firstName();
        addTask(name, true, false);
        Assertions.assertEquals(returnWebElement("//p[@id='message']").getText(),"Fill the due date");
        driver.quit();
    }

    @Test
    public void shouldNotAddTodoWithoutDescriptionAndData() {
        driver.navigate().to("http://localhost:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        name = new Faker().name().firstName();
        addTask(name, false, false);
        Assertions.assertEquals(returnWebElement("//p[@id='message']").getText(),"Fill the task description");
        driver.quit();
    }

    @Test
    public void shouldDeleteTask() {
        driver.navigate().to("http://localhost:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        name = new Faker().name().firstName();
        addTask(name, true, true);
        returnWebElement("//td[text()='"+ name +"']/following-sibling::td[2]/a[text()='Remove']").click();
        driver.quit();
    }


    public void addTask(String taskName, Boolean description, Boolean data) {
        returnWebElement("//a[@id='addTodo']").click();
        if(description) {
            returnWebElement("//input[@id='task']").sendKeys(taskName);
        }
        if (data){
            returnWebElement("//input[@id='dueDate']").sendKeys("25/10/2050");
        }
        returnWebElement("//input[@id='saveButton']").click();
    }

    public WebElement returnWebElement(String elementName) {
        WebElement element = driver.findElement(By.xpath(elementName));
        return element;
    }

    public ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        options.setAcceptInsecureCerts(true);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        return options;
    }

}
