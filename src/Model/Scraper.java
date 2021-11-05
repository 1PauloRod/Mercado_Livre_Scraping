package Model;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Scraper {
    private String url = "https://lista.mercadolivre.com.br/";
    private List<Product> products = new ArrayList<>();

    public Scraper(String chosen_product, Float price){
        try{
            Document html = Jsoup.connect(url+chosen_product).get();
            List<Element> list = html.getElementsByClass("ui-search-result__wrapper");
            for (Element element : list){
                Product product;
                String product_name = element.getElementsByClass("ui-search-item__title").get(0).text();
                String product_link = element.select("a").attr("href");
                String product_price_string = element.getElementsByClass("price-tag-text-sr-only").get(0).text();
                String[] product_price_string_split = product_price_string.split(" ");
                if (!product_price_string_split[0].equals("Antes:")) {
                    Float product_price = Float.parseFloat(product_price_string_split[0]);
                    if (product_price < price) {
                        product = new Product(product_name, product_price, product_link);
                        products.add(product);
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("Erro ao extrair dados.");
        }

    }

    public List<Product> getProducts(){
        return products;
    }


}
