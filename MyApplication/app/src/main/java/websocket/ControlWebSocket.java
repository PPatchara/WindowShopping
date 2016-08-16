package websocket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.justwyne.windowshopping.DetailsActivity;
import com.example.justwyne.windowshopping.MainActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

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

        String[] stringList = s.split(",");

        if(stringList.length <= 1) return;

        Integer trigger = Integer.parseInt(stringList[1]);

        if (trigger == null || !stringList[0].equalsIgnoreCase("s_event")) return ;

        trigger_event(trigger.intValue());
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

    private void trigger_event(int trigger) {
        Intent intent;

        if( trigger == TOUCHPAD_TRIGGER ) {
            intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        } else if( trigger == SHOPPING_TRIGGER ) {
            intent = new Intent(context, DetailsActivity.class);
            context.startActivity(intent);
        }
    }


}
