package sql;



import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import jxl.Workbook;
import jxl.write.*;



public class Pachong {
    public static void main(String[] args) throws Exception{

        File f = new File("D:\\java\\Spider\\华为商城手机文件.txt");
        File xlsFile = new File("D:\\java\\Spider","华为手机信息表.xls");

        Writer out = new FileWriter(f);
        try{
            Document doc = Jsoup.connect("https://www.vmall.com/list-36")
                    .userAgent("'User-Agent':'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) ' 'Chrome/51.0.2704.63 Safari/537.36'")
                    .get();	//http:kyfw.12306.cn/otn/leftTicket/init?linktypeid=dc
            Elements ertyhu = doc.select("p[class=p-name]");

            System.out.println(doc);

            for (Element link : ertyhu) {
                String linkText =  link.text();
                System.out.println(linkText);
                out.write(linkText+"\r\n");
            }

            Elements oiuyt = doc.select("p[class=p-price]");
            System.out.println(oiuyt);
            for (Element link : oiuyt) {
                String linkText =  link.text();
                System.out.println(linkText);
                out.write(linkText+"\r\n");
            }
            Elements dfghj = doc.select("label[class=p-button-score]");

            for (Element link : dfghj) {
                String linkText =  link.text();
                System.out.println(linkText);
                out.write(linkText+"\r\n");
            }
            out.flush();
            out.close();

        }catch (IOException link) {
            link.printStackTrace();
        }


        Reader r = new FileReader(f);
        BufferedReader bf = new BufferedReader(r);
        String[] title = {"华为手机名称","华为手机价格","评论数量"};
        WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
        WritableSheet sheet = workbook.createSheet("华为手机信息", 0);

        sheet.setRowView(0,300);
        for(int i = 0;i < 20;i++){
            sheet.setColumnView(i, 50);
        }

        Label label = null; //给sheet页添加数据
        for (int i = 0; i < title.length; i++) {
            label = new Label(i, 0, title[i]);//第一行添加标题数据
            sheet.addCell(label);
        }


        for (int i = 0;i < 20; i++) {
            String data = bf.readLine();
            label = new Label(0, i+1, data);//第一个参数代表列，第二个代表行，第三个代表数据
            sheet.addCell(label);
        }

        bf.readLine();
        bf.readLine();
        bf.readLine();

        for(int i = 0;i < 20;i++){
            String s = bf.readLine();
            label = new Label(1,i+1,s);
            sheet.addCell(label);
        }

        for(int i = 0;i < 20;i++){
            String s = bf.readLine();
            label = new Label(2,i+1,s);
            sheet.addCell(label);
        }

        workbook.write();
        workbook.close();
    }

}