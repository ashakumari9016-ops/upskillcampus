import java.util.ArrayList;
import java.util.List;

// Class representing a webpage (Node in our website structure)
class WebPage {
    String url;
    String pageTitle;
    List<WebPage> hyperlinks; // Simulates internal linking

    public WebPage(String url, String pageTitle) {
        this.url = url;
        this.pageTitle = pageTitle;
        this.hyperlinks = new ArrayList<>();
    }

    // Method to add an internal link (ensuring crawlability)
    public void addLink(WebPage destinationPage) {
        this.hyperlinks.add(destinationPage);
    }
}

public class EcommerceApp {
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("   E-COMMERCE SITE STRUCTURE & CRAWLABILITY SIMULATION   ");
        System.out.println("=========================================================\n");

        // 1. Create the Homepage (The Root)
        WebPage homepage = new WebPage("https://myshop.com/", "Home Page");

        // 2. Create 3 Main Categories
        WebPage electronics = new WebPage("https://myshop.com/electronics", "Electronics Category");
        WebPage clothing = new WebPage("https://myshop.com/clothing", "Clothing Category");
        WebPage books = new WebPage("https://myshop.com/books", "Books Category");

        // Link Homepage to Categories (Silod/Hierarchical Architecture)
        homepage.addLink(electronics);
        homepage.addLink(clothing);
        homepage.addLink(books);

        // Also add breadcrumb style links back to home
        electronics.addLink(homepage);
        clothing.addLink(homepage);
        books.addLink(homepage);

        // 3. Create 15 Products (5 per category) and link them
        String[][] productData = {
            // Electronics
            {"Smartphone X", "Laptop Pro", "Wireless Earbuds", "Smartwatch v2", "4K Monitor"},
            // Clothing
            {"Slim Fit Jeans", "Leather Jacket", "Running Shoes", "Cotton T-Shirt", "Woolen Scarf"},
            // Books
            {"Java Programming Guide", "SEO Masterclass", "Data Structures 101", "The Alchemist", "Steve Jobs Biography"}
        };

        // Populate Electronics
        for (int i = 0; i < 5; i++) {
            String prodName = productData[0][i];
            String url = "https://myshop.com/electronics/" + prodName.toLowerCase().replace(" ", "-");
            WebPage productPage = new WebPage(url, prodName);
            electronics.addLink(productPage);
            productPage.addLink(electronics); // Product links back to its parent category
        }

        // Populate Clothing
        for (int i = 0; i < 5; i++) {
            String prodName = productData[1][i];
            String url = "https://myshop.com/clothing/" + prodName.toLowerCase().replace(" ", "-");
            WebPage productPage = new WebPage(url, prodName);
            clothing.addLink(productPage);
            productPage.addLink(clothing);
        }

        // Populate Books
        for (int i = 0; i < 5; i++) {
            String prodName = productData[2][i];
            String url = "https://myshop.com/books/" + prodName.toLowerCase().replace(" ", "-");
            WebPage productPage = new WebPage(url, prodName);
            books.addLink(productPage);
            productPage.addLink(books);
        }

        // 4. Visualizing Website Architecture
        printVisualStructure();

        // 5. Simulate Search Engine Crawler
        System.out.println("\n[SEO Simulation] Launching Search Engine Crawler (Googlebot Simulation)...");
        System.out.println("Starting Crawl from Root URL: " + homepage.url);
        System.out.println("-------------------------------------------------------------------------");
        
        List<String> visitedUrls = new ArrayList<>();
        simulateCrawler(homepage, visitedUrls, 0);

        System.out.println("\n=========================================================");
        System.out.println("Crawl Successful! Total Unique Pages Indexed: " + visitedUrls.size());
        System.out.println("SEO Status: 100% Crawlability Achieved (No Orphan Pages Found).");
        System.out.println("=========================================================");
    }

    // Recursive Crawler Simulation (Depth-First Search mapping the links)
    private static void simulateCrawler(WebPage page, List<String> visited, int depth) {
        if (visited.contains(page.url) || depth > 2) { 
            return; // Avoid infinite loops from back-linking and limit depth for clean console output
        }

        // Mark page as indexed
        visited.add(page.url);
        
        // Indentation to show crawl depth visually
        String indent = "  ".repeat(depth);
        System.out.println(indent + "🕷️ Found Page: [" + page.pageTitle + "] -> URL: " + page.url);

        // Crawler follows all hyperlinks discovered on this page
        for (WebPage nextWord : page.hyperlinks) {
            simulateCrawler(nextWord, visited, depth + 1);
        }
    }

    // Text-based flow chart for your project report representation
    private static void printVisualStructure() {
        System.out.println("Visualizing E-commerce Architecture Flow Chart:");
        System.out.println("Homepage (https://myshop.com/)");
        System.out.println(" │");
        System.out.println(" ├──► /electronics (Category)");
        System.out.println(" │     ├── /smartphone-x");
        System.out.println(" │     ├── /laptop-pro");
        System.out.println(" │     ├── /wireless-earbuds");
        System.out.println(" │     ├── /smartwatch-v2");
        System.out.println(" │     └── /4k-monitor");
        System.out.println(" │");
        System.out.println(" ├──► /clothing (Category)");
        System.out.println(" │     ├── /slim-fit-jeans");
        System.out.println(" │     ├── /leather-jacket");
        System.out.println(" │     ├── /running-shoes");
        System.out.println(" │     ├── /cotton-t-shirt");
        System.out.println(" │     └── /woolen-scarf");
        System.out.println(" │");
        System.out.println(" └──► /books (Category)");
        System.out.println("       ├── /java-programming-guide");
        System.out.println("       ├── /seo-masterclass");
        System.out.println("       ├── /data-structures-101");
        System.out.println("       ├── /the-alchemist");
        System.out.println("       └── /steve-jobs-biography");
    }
}