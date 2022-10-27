package br.org.brito.tests;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {
    String name;
    WebDriver driver = new ChromeDriver();

    @Test
    public void shouldAddTodo(){
        name = new Faker().name().firstName();
        driver.navigate().to("http://localhost:8001/tasks/");
        addTask(name, true, true);
        Assertions.assertEquals(returnWebElement("//p[@id='message']").getText(),  "Success!2");
        driver.quit();
    }

    @Test
    public void shouldNotAddTodoWithoutDescription() {
        name = new Faker().name().firstName();
        driver.navigate().to("http://localhost:8001/tasks/");
        addTask(name, false, true);
        Assertions.assertEquals(returnWebElement("//p[@id='message']").getText(),"Fill the task description");
        driver.quit();
    }

    @Test
    public void shouldNotAddTodoWithoutData() {
        name = new Faker().name().firstName();
        driver.navigate().to("http://localhost:8001/tasks/");
        addTask(name, true, false);
        Assertions.assertEquals(returnWebElement("//p[@id='message']").getText(),"Fill the due date");
        driver.quit();
    }

    @Test
    public void shouldNotAddTodoWithoutDescriptionAndData() {
        name = new Faker().name().firstName();
        driver.navigate().to("http://localhost:8001/tasks/");
        addTask(name, false, false);
        Assertions.assertEquals(returnWebElement("//p[@id='message']").getText(),"Fill the task description");
        driver.quit();
    }

    @Test
    public void shouldDeleteTask(){
        name = new Faker().name().firstName();
        driver.navigate().to("http://localhost:8001/tasks/");
        addTask(name, true, true);
        returnWebElement("//td[text()='"+ name +"']/following-sibling::td[2]/a[text()='Remove']").click();
        driver.quit();
    }


    public void addTask(String taskName, Boolean description, Boolean data){
        returnWebElement("//a[@id='addTodo']").click();
        if(description) {
            returnWebElement("//input[@id='task']").sendKeys(taskName);
        }
        if (data){
            returnWebElement("//input[@id='dueDate']").sendKeys("25/10/2050");
        }
        returnWebElement("//input[@id='saveButton']").click();
    }

    public WebElement returnWebElement(String elementName){
        WebElement element = driver.findElement(By.xpath(elementName));
        return element;
    }

}
