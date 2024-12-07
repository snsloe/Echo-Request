import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requests {
    public static void getRequest(HttpURLConnection connection, String url) {
        try {
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            System.out.println("Код ответа: " + code);
            if (code >= 200 && code < 300) {
                InputStream os = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(os));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                System.out.println("Ответ на GET-запрос: \n");
                parsing(response.toString(), url);
            }
            connection.disconnect();

        } catch (IOException e) {
            System.out.println("Ошибка при выполнении GET-запроса: " + e.getMessage());
        }
    }

    public static void postRequest(URL url, String postData) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = postData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int code = connection.getResponseCode();
            System.out.println("Код ответа: " + code);
            if (code >= 200 && code < 300) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                System.out.println("Ответ на POST-запрос: \n");
                parsing(response.toString(), url.toString());
            }
            System.out.println("\n");
        } catch (IOException e) {
            System.err.println("Ошибка при выполнении POST-запроса: " + e.getMessage());
        }
    }

    public static void parsing(String inData, String url) {
        String miss = "Hey ya! Great to see you here. Btw, nothing is configured for this request path. Create a rule and start building a mock API.\n";
        if (inData.contains("nothing is configured for this request path")) {
            System.out.println(inData);
        }
        else {
            inData = inData.substring(1, inData.length() - 1);
            String[] blocks = inData.split("\\},\\{");
            for (int i = 0; i < blocks.length; i++) {
                blocks[i] = blocks[i].replaceAll("[\\{\\}]", "");
                String[] dataPair = blocks[i].split(",");
                for (i = 0; i < dataPair.length; i ++) {
                    String[] keyValue = dataPair[i].split(":", 2);
                    if (keyValue.length == 2) {
                        String key = keyValue[0].replaceAll("\"", "");
                        String value = keyValue[1].replaceAll("\"", "");
                        System.out.println(key + ": " + "\t" + value);
                    } else {
                        System.out.println("Ошибка парсинга: " + dataPair[0] + ", " + dataPair[1]);
                    }

                }
                System.out.println("\n");
            }
        }
    }
}
