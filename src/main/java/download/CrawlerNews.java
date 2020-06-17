package download;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.List;


public class CrawlerNews {

    public static void main(String[] args) throws Exception {
        String url = "https://news.163.com/";
        new CrawlerNews().jsoupMethod(url);

        String url1 = "https://temp.163.com/special/00804KVA/cm_yaowen.js?callback=data_callback";
//        new CrawlerNews().httpclientMethod(url1);
//        new CrawlerNews().selenium(url);
    }


    public void httpclientMethod(String url) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity, "GBK");
            // 先替换掉最前面的 data_callback(
            body = body.replace("data_callback(", "");
            // 过滤掉最后面一个 ）右括号
            body = body.substring(0, body.lastIndexOf(")"));
            // 将 body 转换成 JSONArray

            JSONArray jsonArray = JSON.parseArray(body);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject data = jsonArray.getJSONObject(i);
                System.out.println("文章标题：" + data.getString("title") + " ,文章链接：" + data.getString("docurl"));
            }
        } else {
            System.out.println("处理失败！！！返回状态码：" + response.getStatusLine().getStatusCode());
        }

    }


    public void jsoupMethod(String url) throws Exception {


        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(3 * 1000);
        webClient.getOptions().setUseInsecureSSL(true);

        HtmlPage page = webClient.getPage(url);

        webClient.waitForBackgroundJavaScript(1000 * 6);
        String html = page.asXml();

        Document document = Jsoup.parse(html);

        Elements elements = document.select(".ndi_main .news_title h3 a ");
        elements.addAll(document.select("h3 a"));

        for (Element element : elements) {
            String article_url = element.attr("href");
            String title = element.ownText();
            if (article_url.contains("https://news.163.com/")) {
                System.out.println("文章标题：" + title + " ,文章链接：" + article_url);
            }
        }
    }


    public void selenium(String url) {

        System.getProperties().setProperty("webdriver.chrome.driver", "D:\\java\\Spider\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");

        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.get(url);

        List<WebElement> webElements = webDriver.findElements(By.xpath("//div[@class='news_title']/h3/a"));
        for (WebElement webElement : webElements) {

            String article_url = webElement.getAttribute("href");

            String title = webElement.getText();
            if (article_url.contains("https://news.163.com/")) {
                System.out.println("文章标题：" + title + " ,文章链接：" + article_url);
            }
        }
        webDriver.close();
    }
}