/**
 * Author : Deepak Karki
 * copyright 2014
 */

package com.example.droidshield;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import android.app.Activity;
/*
 * This class acts as a wrapper for accessing bluetooth device
 * Makes life simpler to have this here 
 */

public class DroidConnect {
	
	//adapter object representing the bluetooth module
	private BluetoothAdapter btAdpt;
	
	//socket opened via the adapter to stream the data
	//we get the input and output stream object from sock
    private BluetoothSocket sock;
    
    //the reference to the device we connect to
    private BluetoothDevice dev;
    
    //corresponding in/out put stream 
    private InputStream dev_in;
    private OutputStream dev_out;
    
    //48 bit MAC identifier for the bluetooth module. 
    private String dev_mac;
    
    //every DroidConect object has a activity with which it is associated
    private Activity activity;
    
    //whether or not the connection is established 
    private boolean connected;
    
    
    public DroidConnect(Activity a)
	{
    	activity = a;
		btAdpt = BluetoothAdapter.getDefaultAdapter();
		connected = false;
	}
	
    
	public DroidConnect(Activity a, String mac)
	{
		activity = a;
		btAdpt = BluetoothAdapter.getDefaultAdapter();
		connected = false;
		dev_mac = mac;
	}
	
	public void setMAC(String mac)
	{
		dev_mac = mac;
	}
	
	public boolean setDevice()
	{
		//This function opens a chooser for the user to choose bt device
		//if everything okay, call setMAC, and then return true
		//else return false
		return false;
	}
	
	public boolean connect()
	{
		if(dev_mac == null){
			return false;
		}
		
		//if there is already an active connection; close it.
		//then continue 
		if(isConnected()){
			this.close();
		}
		Log.i("DroidConnect", "try connect procedure");
		
		// I have moved this functionality to MainActivity
		//make sure bt module is enabled.
		if(!btAdpt.isEnabled()){
			btAdpt.enable();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.getMessage();
			}
		}
		
		try{
			//get the device with the required mac address
			dev = btAdpt.getRemoteDevice(dev_mac);
			Log.i("DroidConnect", "got dev");
			
			//create a socket to communicate
	        //sock = dev.createRfcommSocketToServiceRecord(uuid);
			Method m;
			try {
				m = dev.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
				sock = (BluetoothSocket) m.invoke(dev, 1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("DroidConnect", e.getMessage());
			} 
	         
	        Log.i("DroidConnect", "creating socket");
	        
	        //establish the connection
	        sock.connect();
	        Log.i("DroidConnect", "connection successful");
	        
	        //set the input and output streams
	        dev_out = sock.getOutputStream();
	        dev_in = sock.getInputStream();
	        connected = true;
	        return true;
		}
		catch(IOException e){
			Log.i("DroidConnect-exception", e.getMessage());
			return false; 
		}
	}
	
	
	public void sendByte(byte ch)
	{
		if(isConnected()){
			int c = (int)ch;
			
			try{	
				dev_out.write(c);
			}
			
			catch(IOException e){
			}
		}
	}
	
	public void send(byte[] data)
	{
		if(isConnected())
		{
			try{
				dev_out.write(data);
			}
			
			catch(IOException e){
				
			}
		}
	}
	
	public byte[] read(int num)
	{
		byte [] data =  new byte[num];
		if (isConnected()){
			
			try{
				dev_in.read(data);
			}
			
			catch(IOException e){
				
			}
		}
		return data;
	}
	
	public byte readByte()
	{
		//does it make sense to r/w in terms of int instead?? 
		if(isConnected()){
			
			try{	
				return (byte) dev_in.read();
			}
			
			catch(IOException e){
				return (byte)0;
			}
		}
		//TODO : what do I return if there was a problem
		return (Byte) null;
	}
	
	void close() 
    {
		if (isConnected()){
		
			try
			{
		        dev_out.close();
		        dev_in.close();
		        sock.close();
		        connected = false;
			}
			
			catch (Exception e){
			}
		}
    }

	public boolean isConnected() {
		return this.connected;
	}
}
