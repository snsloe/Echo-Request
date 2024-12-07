import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String[] listURL = {"https://dummy-json.mock.beeceptor.com/todos", "https://dummy-json.mock.beeceptor.com/posts"};
        for (int i = 0; i < listURL.length; i++) {
            try {
                URL u = new URL(listURL[i]);
                System.out.println("Запрос к: " + listURL[i]);
                System.out.println("Протокол: " + u.getProtocol());
                System.out.println("Хост: " + u.getHost());
                System.out.println("Порт: " + u.getPort());
                System.out.println("Файл: " + u.getFile());
                System.out.println("Якорь: " + u.getRef());
                HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                Requests.getRequest(connection, listURL[i]);

                String postDataTodos = "{\"userId\": 21,  \"id\": 21, \"title\": \"Buy groceries\", \"completed\": false}";
                String postDataPosts = "{\"userId\": 11,  \"id\": 11, \"title\": \"Introduction to Blockchain\", \"body\": \"Learn about Blockchain technology\", \"link\": \"https://example.com/article11\", \"comment_count\": 8}";

                if (listURL[i].contains("/todos")) {
                    Requests.postRequest(u, postDataTodos);
                }
                else if (listURL[i].contains("/posts")) {
                    Requests.postRequest(u, postDataPosts);
                }
            } catch (IOException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }

        }
    }
}
