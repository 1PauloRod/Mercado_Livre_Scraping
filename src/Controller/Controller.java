package Controller;

import Model.Product;
import Model.Scraper;
import Model.WhatsappBot;

import java.util.List;

public class Controller {
    private WhatsappBot bot;


    protected void initialize_bot(String contact_name){
        try {
            bot = new WhatsappBot();
            bot.open_whatsapp();
            bot.search_contact(contact_name);
            bot.send_message("Bot mercado livre! Escolha o produto que deseja!");
        }catch (Exception e){
            System.out.println("Erro ao inicializar o bot.");
        }
    }

    protected void initialize_scraper_with_price(String last_message){
        try {
            String[] split_last_message = last_message.split(" ");
            if (Character.isDigit(split_last_message[split_last_message.length - 1].charAt(0))) {    //verifica se há preço após a vírgula.
                String product_name = split_last_message[0];
                Float product_price = Float.parseFloat(split_last_message[split_last_message.length - 1]);
                Scraper scraper = new Scraper(product_name, product_price);
                List<Product> productList = scraper.getProducts();
                if (!productList.isEmpty()) {
                    for (Product product : productList) {
                        bot.send_message(product.getName() + ", " + product.getPrice() + ", " + product.getLink());
                    }
                } else {
                    bot.send_message("Produto não encontrado :(");
                }
            } else {
                bot.send_message("Informe o preço depois da vírgula!");
            }
        }catch (Exception e){
            System.out.println("Erro ao extrair dados com preço.");
        }
    }

    protected void initialize_scraper_without_price(String last_message){
        try {
            Float product_price = 999999.9f;
            Scraper scraper = new Scraper(last_message, product_price);
            List<Product> productList = scraper.getProducts();
            if (!productList.isEmpty()) {
                for (Product product : productList) {
                    bot.send_message(product.getName() + ", " + product.getPrice() + ", " + product.getLink());
                }
            } else {
                bot.send_message("Produto não encontrado :(");
            }
        }catch (Exception e){
            System.out.println("Erro ao extrair dados sem preço.");
        }
    }

    protected void controllerBotScraper(String contact_name){
        try {
            initialize_bot(contact_name);
            while (true) {
                String last_message = bot.get_last_message();
                System.out.println(last_message);
                if (verify_with_price(last_message)) {
                    initialize_scraper_with_price(last_message);
                } else if (verify_without_price(last_message)) {
                    initialize_scraper_without_price(last_message);
                }
            }
        }catch (Exception e){
            System.out.println("Erro ao enviar dados extraídos para o bot.");
        }

    }

    private boolean verify_with_price(String message){
        return (message.startsWith("/") && message.contains(","));
    }

    private boolean verify_without_price(String message){
        return (message.startsWith("/") && !message.contains(","));
    }
}
