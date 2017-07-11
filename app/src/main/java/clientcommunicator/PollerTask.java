package clientcommunicator;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import clientfacade.ClientFacade;
import commandresults.CommandResult;
import commandresults.LoginResultData;
import commands.Command;

/**
 * Created by Rachael on 7/11/2017.
 */

public class PollerTask extends AsyncTask<Command, Void, CommandResult> {
    private String ip;
    private String port;

    public PollerTask() {
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected CommandResult doInBackground(Command... command) {

        Timer time= new Timer();
        time.schedule(new poll(),0, 1000);
        return new CommandResult();
    }
    protected void onPostExecute(CommandResult result){




    }
    private class poll extends TimerTask
    {
        @Override
        public void run()
        {

        }
    }
}
