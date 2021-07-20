import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

//    public String OTP(String phoneNumber) {
//        try {
//            ProcessBuilder process = new ProcessBuilder("psql", "--user=otp_service", "--host=10.120.2.59", "--dbname=otp_service_integration", "-x", "-c", "select otp from otp_requests where phone='" + phoneNumber + "' order by created_at desc limit 1");
//            process.environment().put("PGPASSWORD", "307267f6f24c9678290f0895ac8cf17f");
//            Process p;
//            p = process.start();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            StringBuilder builder = new StringBuilder();
//            String line = null;
//
//            while ((line = reader.readLine()) != null) {
//                builder.append(line);
//                builder.append(System.getProperty("line.separator"));
//            }
//            String result = builder.toString();
//
//            return result;
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }

    public String GetOTP(String phoneNumber) {
        try {
            Integer responseCode;
            String responseStatus;
            String inputLine;
            String requestBody = "{\"phone\":\"" + phoneNumber + "\"}";
            java.net.URL url = new URL("http://10.120.4.21:9000/otp");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("clientid", "red_robin");
            httpURLConnection.setRequestProperty("passkey", "robin1234");
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            outputStreamWriter.write(requestBody);
            outputStreamWriter.flush();
            outputStreamWriter.close();
            outputStream.close();
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
