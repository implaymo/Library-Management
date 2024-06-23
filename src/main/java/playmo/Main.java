package playmo;

/**
 * main
 */
import java.util.Scanner;
import org.apache.commons.text.WordUtils;

 public class Main {
 
     /**
     * @param args
     */
    public static void main(String[] args) {
        Database db = new Database();
        db.connectDb();
        Api api = new Api();
        api.apiRequest();
        
        Scanner scanner = new Scanner(System.in);
        
        // Start of program 
        System.out.print("What's your name? ");
        String name = scanner.nextLine();
        String capName = WordUtils.capitalize(name);
        String user_answer;  
        System.out.println("Hello " + capName);

        while (true){ 
        System.out.println("Would you like to get a random book, search or add a book to the library? Write 'random' or 'add''or 'search': ");

        user_answer = scanner.nextLine();
        

        if (!user_answer.equals("random") && !user_answer.equals("add") && !user_answer.equals("search")){
            System.out.println("You must choose random, add or search");

        }


        if (user_answer.equals("random")) {
            db.getRandomBook();
            System.exit(0);
        }
        else if (user_answer.equals("add")) {
            System.out.println("Which book do you want to add to Library? ");
            String nameBook = scanner.nextLine();

            System.out.println("Which year was it published? ");
            String publicationDate = scanner.nextLine();
            Integer pubDate = Integer.parseInt(publicationDate);
            db.addBook(nameBook, pubDate);
            System.out.println("Who are the authors? If the authors are more than 1, right 'and' after each author. ");
            String authorToAdd = scanner.nextLine();
            String[] words = authorToAdd.split("\\s+");
            for (String word: words){
                if (word.equals("and")){
                    continue;
                }
                else {
                    db.addAuthor(word);
                    db.addJuncTable(db.bookId, db.authorId);       
                }
            }
            break;
        }
        else if (user_answer.equals("search")) {
            System.out.println("What do you want to search? Type 'book' or 'author': ");
            String typeSearch = scanner.nextLine();
            if (typeSearch.equals("book")) {
                try {
                    System.out.println("What book do you want to find? ");
                    String itemSearch = scanner.nextLine();
                    itemSearch = WordUtils.capitalize(itemSearch);
                    db.queryBookTable(itemSearch);
                    db.queryJuncTable(db.bookId);
                    db.queryAuthorTable();
                } catch (Exception e) {
                    System.out.println("Wrong or book not in library. Try again.");
                }
            } else if (typeSearch.equals("author")) {
                System.out.println("HE WANTS AN AUTHOR");
            }
        }
        }
    scanner.close();     
    }
 }