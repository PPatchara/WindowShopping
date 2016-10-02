package websocket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.justwyne.windowshopping.DetailsActivity;
import com.example.justwyne.windowshopping.MainActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * Created by Justwyne on 5/26/16 AD.
 */
public class ControlWebSocket extends WebSocketClient {

    int TOUCHPAD_TRIGGER = 0;
    int SHOPPING_TRIGGER = 1;

    Context context;

    public ControlWebSocket(Context context, URI serverURI) {
        super(serverURI);
        this.context = context;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println( "You are connected to RemoteServer: " + getURI() + "\n" );
    }

    @Override
    public void onMessage(String s) {
        Log.d("DEBUG", "onMessage " + s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            switchAction(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void switchAction(JSONObject jsonObject) throws JSONException {
        String action = jsonObject.getString("action");
        switch (action) {
            case "TiltUp":
                System.out.println( "Trigger: dfkgjfkg");
//                trigger_event(jsonObject);
                break;
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println( "Disconnected: " + getURI() + "\n" );
    }

    @Override
    public void onError(Exception e) {
        System.out.println( "Error: " + getURI() + "\n" );
        e.printStackTrace();
    }

    private void trigger_event(JSONObject jsonObject) throws JSONException {
        Intent intent;
        JSONObject event = jsonObject.getJSONObject("event");
        String event_id = event.getString("event_id");
        intent = new Intent(context, MainActivity.class);
        intent.putExtra("event", event_id);
        context.startActivity(intent);
        System.out.println( "Trigger: sent");
    }


}
