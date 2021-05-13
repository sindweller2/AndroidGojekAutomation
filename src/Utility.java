import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utility {

    String MessageLog = "Log" + "\n";
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");

    public String Log(String Message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        MessageLog += sdf.format(timestamp.getTime()) + " - " + Message + "\n";

        return MessageLog;
    }

    public String executeCommand(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            return reader.readLine();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String OTP(String phoneNumber) {
        try {
            ProcessBuilder process = new ProcessBuilder("psql", "--user=otp_service", "--host=10.120.2.59", "--dbname=otp_service_integration", "-x", "-c", "select otp from otp_requests where phone='" + phoneNumber + "' order by created_at desc limit 1");
            process.environment().put("PGPASSWORD", "307267f6f24c9678290f0895ac8cf17f");
            Process p;
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            String result = builder.toString();

            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String SendGETRequest(String URL) {
        try {
            Integer responseCode;
            String responseStatus;
            String inputLine;
            java.net.URL url = new URL(URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Authorization", "Basic aWNwLWlkZW50aXR5LXFhOjA2Zjk5NTU5LWEyMjgtNGNiZC1hN2JmLWE5YzhlZDhlMjlhNg==");
            httpURLConnection.connect();
            responseCode = httpURLConnection.getResponseCode();
            responseStatus = httpURLConnection.getResponseMessage();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                return "Response code " + responseCode.toString() + " : " + responseStatus;
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
