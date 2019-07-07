package threemodernsystems.com.notes;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ekaranja on 12/12/17.
 */

public class VolleySingleton {

    private static VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private static Context context;

    public VolleySingleton(Context ctx) {
        this.context = ctx;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue(){


        if(requestQueue==null)
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());

        return requestQueue;
    }

    public static synchronized VolleySingleton getVolleySingletonInstance(Context context){
        if(volleySingleton==null)
            volleySingleton=new VolleySingleton(context);
        return  volleySingleton;


    }

    public <T> void addToRequestque(Request<T> request){
        requestQueue.add(request);


    }
}
