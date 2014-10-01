/**
 * Author : Deepak Karki
 * copyright 2014
 */

package com.example.droidshield;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
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
    
    //whether or not the connection is established 
    private boolean connected;
    
    public DroidConnect()
	{
		btAdpt = BluetoothAdapter.getDefaultAdapter();
		connected = false;
	}
	
    
	public DroidConnect(String mac)
	{
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
			//TODO make toast and exit
			return false;
		}
		
		//if there is already an active connection; close it. 
		if(isConnected()){
			this.close();
		}
		
		//make sure bt module is enabled.
		while(!btAdpt.isEnabled()){
		   Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		   //TODO : check if this would even work!
		   new Activity().startActivityForResult(enableBluetooth, 0);
		}
		
		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
		
		try{
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
		catch(IOException e){
			//make a toast? --no, let caller handle it!
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
				//TODO : do what if the device itself is not connected?
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
