package ktu.bombit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RequestOperator extends Thread {

    public interface RequestOperatorListener{
        void success(ArrayList<Player> player);
        void failed(int responseCode);
    }

    private RequestOperatorListener listener;
    private int responseCode;

    public void SetListener(RequestOperatorListener listener){
        this.listener = listener;
    }

    @Override
    public void run() {
        super.run();
        try{
            ArrayList<Player> players = request();

            if(players != null)
               success(players);
                //System.out.println("a");
            else
                failed(responseCode);
        } catch (IOException e){
            failed(-1);
        } catch (JSONException e){
            failed(-2);
        }
    }

    private ArrayList<Player> request() throws IOException, JSONException{
        //URL obj = new URL("https://api.myjson.com/bins/sg8js"); //vienas
        URL obj = new URL("https://api.myjson.com/bins/g9qa8"); //array

        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        connection.setRequestProperty("Content-Type", "application/json");

        responseCode = connection.getResponseCode();
        System.out.println("ResponseCode " + responseCode);

        InputStreamReader streamReader;

        if(responseCode == 200){
            streamReader = new InputStreamReader(connection.getInputStream());
        }else {
            streamReader = new InputStreamReader(connection.getErrorStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);

        if(responseCode == 200){
            return parsingJsonObject(response.toString());
        }else
            return  null;
    }

    private ArrayList<Player> parsingJsonObject(String response) throws JSONException {
        JSONArray a = new JSONArray(response);
        ArrayList<Player> players = new ArrayList<Player>();

        for(int i = 0; i <a.length(); i++) {
            JSONObject object = new JSONObject(a.get(i).toString());
            Player player = new Player();
            player.setImageId(object.optInt("id", 0));
            player.setTitle(object.getString("name"));
            player.setBestScore(object.optInt("bestScore", 0));

            players.add(player);
        }

        return players;
    }

    private void failed(int code){
        if(listener != null) {
            listener.failed(code);
        }
    }

    private  void success(ArrayList<Player> players){
            if (listener != null) {
                listener.success(players);
        }
    }
}
