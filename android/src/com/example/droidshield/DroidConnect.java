/**
 * Author : Deepak Karki
 * copyright 2014
 */

package com.example.droidshield;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.app.Activity;
/*
 * This class acts as a wrapper for accessing bluetooth 
 * Makes life simpler to have this here 
 */

public class DroidConnect {
	
	//adapter object representing the bluetooth module
	BluetoothAdapter btAdpt;
	
	//socket opened via the adapter to stream the data
	//we get the input and output stream object from sock
    BluetoothSocket sock;
    
    //the reference to the device we connect to
    BluetoothDevice dev;
    
    //corresponding in/out put stream 
    InputStream dev_in;
    OutputStream dev_out;
    
    //48 bit MAC identifier for the bluetooth module. 
    String dev_mac;
    
    //whether or not the connection is established 
    boolean connected;
    
	public DroidConnect(String mac)
	{
		btAdpt = BluetoothAdapter.getDefaultAdapter();
		
		dev_mac = mac;
	}
	
	public boolean connect()
	{
		
		//make sure bt module is enabled.
		while(!btAdpt.isEnabled())
		{
		   Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		   //TODO : check if this would even work!
		   new Activity().startActivityForResult(enableBluetooth, 0);
		}
		
		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
		
		try
		{
			//get the device with the required mac address
			dev = btAdpt.getRemoteDevice(dev_mac);
			
			//create a socket to communicate
	        sock = dev.createRfcommSocketToServiceRecord(uuid);
	        
	        //establish the connection
	        sock.connect();
	        
	        //set the input and output streams
	        dev_out = sock.getOutputStream();
	        dev_in = sock.getInputStream();
	        connected = true;
	        return true;
		}
		catch(Exception e)
		{
			//make a toast?
			return false; 
		}
	}
	
	public void sendByte(byte ch)
	{
		if(connected){
			int c = (int)ch;
			
			try
			{	
				dev_out.write(c);
			}
			
			catch(Exception e)
			{
				
			}
		}
	}
	
	public byte readByte()
	{
		//does it make sense to r/w in terms of int instead?? 
		if(connected){
			
			try
			{	
				return (byte) dev_in.read();
			}
			
			catch(Exception e)
			{
				return (byte)0;
			}
		}
		return (Byte) null;
	}
	
	void close() 
    {
		if (connected){
		
			try
			{
		        dev_out.close();
		        dev_in.close();
		        sock.close();
			}
			
			catch (Exception e){
			}
		}
    }
}
