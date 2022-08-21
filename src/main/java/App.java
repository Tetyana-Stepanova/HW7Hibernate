import storage.DBInitService;

public class App {
    public static void main(String[] args) {
        new DBInitService().initDb();
    }
}
