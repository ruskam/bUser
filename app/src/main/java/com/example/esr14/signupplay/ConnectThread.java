package com.example.esr14.signupplay;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private BluetoothAdapter mBluetoothAdapter;
    final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private MessageThread myMessageThread;
    Handler handler;
    int handlerState;
    public ConnectThread(String address, Handler hand, int state) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        handler = hand;
        handlerState = state;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);//connec
        mmDevice = device;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(myUUID);
        } catch (IOException e) { }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }
        manageConnectedSocket(mmSocket);

    }

    private void manageConnectedSocket(BluetoothSocket mmSocket) {
        myMessageThread = new MessageThread(mmSocket,handler,handlerState);
        myMessageThread.start();
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

    public BluetoothSocket getBTsocket() {
        return mmSocket;
    }
    public MessageThread getMessageThread(){
        return myMessageThread;
    }
}