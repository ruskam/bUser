package com.example.esr14.signupplay.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.esr14.signupplay.ConnectThread;
import com.example.esr14.signupplay.MessageThread;

import java.io.IOException;
import java.util.UUID;



public class AbstractActivity extends AppCompatActivity {
    private BluetoothSocket btSocket = null;
    protected boolean isBtConnected;
    int handlerState = 0;
    public ConnectThread myConnectThread;
    private StringBuilder recDataString = new StringBuilder();
    private String thisID = "93";
    public boolean ledon = false;
    private BluetoothDevice device = null;
    public MessageThread myMessageThread = null;
    public Handler handler = new Handler() {


        public void handleMessage(android.os.Message message) {
            if (message.what == handlerState) {                                     //if message is what we want
                String readMessage = (String) message.obj;                                                                // msg.arg1 = bytes from connect thread
                recDataString.append(readMessage);                                      //keep appending to string until +
                int endOfLineIndex = recDataString.indexOf("+");                    // determine the end-of-line
                if (endOfLineIndex > 0) {                                           // make sure there data before +
                    String dataInPrint = recDataString.substring(1, endOfLineIndex);    // extract string
                    //msg(dataInPrint); //remover
                    if (recDataString.charAt(0) == '~')
                    {
                       String id = dataInPrint.substring(0,dataInPrint.indexOf(","));
                        if(id.equals(thisID)) {
                            detectedCard();
                        }
                        else turnOnLed();
                    }
                    if(dataInPrint.equals("NXT")||dataInPrint.equals("XT"))
                        nextStop();
                    
                    recDataString.delete(0, recDataString.length());                    //clear all string data
                }
            }
        }

    };


    public void nextStop() {
    }

    public void detectedCard() {
    }

    private String address = "98:D3:34:90:6F:BA";


    public boolean connect (){
        
        BluetoothAdapter btAdapter;
        final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        try {
            if(myConnectThread != null) {
                myConnectThread.cancel();
                myConnectThread.interrupt();
            }
            myConnectThread = new ConnectThread(address,handler,handlerState);
            myConnectThread.start();
            btSocket = myConnectThread.getBTsocket();
            myMessageThread = myConnectThread.getMessageThread();
            isBtConnected = true;
            return true;

        } catch (Exception e) {
            return false;//if the try failed, you can check the exception here
        }

    }
    // fast way to call Toast
   public void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    protected void turnOffLed()
    {
        if (btSocket!=null)
        {
            ledon = false;
            if(myMessageThread==null)
                myMessageThread = myConnectThread.getMessageThread();
            myMessageThread.write("TF");

        }
    }

    public void turnOnLed() {
        if (btSocket != null) {
            ledon = true;
            if(myMessageThread==null)
                myMessageThread = myConnectThread.getMessageThread();
            myMessageThread.write("TO");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            if(btSocket != null)
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
            btSocket = null;
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }}
