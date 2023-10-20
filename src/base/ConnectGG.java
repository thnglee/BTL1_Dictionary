import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.Gson;
public class ConnectGG {
    public static void ConnecttoGGAPI() {
        try {
            // Xác định URL
            Scanner scan = new Scanner(System.in);
            String qu1 = scan.nextLine();
            String quencode = URLEncoder.encode(qu1, "UTF-8");
            String apiUrl = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=vi&dt=t&q=";
            apiUrl += quencode;
            // Tạo URL object
            URI uri = new URI(apiUrl);
            URL url = uri.toURL();
            // Tạo kết nối HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Đọc phản hồi từ API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder responseContent = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
            String result = responseContent.toString();
            String ans ="";
            int  i = 0;
            while(result.charAt(i) != '"') {
                i++;
            }
            i++;
            while(result.charAt(i) != '"') {
                ans+=result.charAt(i);
                i++;
            }
            scan.close();
            System.out.println(ans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class API{
        public String API;
        public String des;
        public String Auth;
        public String cors;
        public String link;
        public String cate;
    }
    class Data{
        public String count;
        public String name;
        public String age;
    }
}
