package com.example.droidshield;

import android.telephony.SmsManager;
import android.util.Log;

public class SMSShield implements ActuatorShield
{		
	private ShieldService ss;
	
	public SMSShield(ShieldService s)
	{
		ss = s;
	}
	
	private void sendSMSMessage(String phoneNo, String message) {

	      try {
	         SmsManager smsManager = SmsManager.getDefault();
	         Log.i("sms-shield", "Trying to send now....");
	         smsManager.sendTextMessage(phoneNo, null, message, null, null);
	      } 
	      catch (Exception e) {
	         Log.e("sms-shield", "SMS faild, please try again.");
	         Log.e("sms-Failed", e.getMessage());
	      }
	}

	/*
	 * format of incoming byte for sms:
	 * 	<sms-opcode:1 byte> <ph-no:10 bytes> <size-of-text:1byte> <text-message :n bytes>
	 */
	@Override
	public byte[] setValue(DroidConnect dev) {
		
		try {
			//sleep for 50 ms, wait for data to come. 
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// get phone number
		byte [] ph_no = dev.read(10);
		//get the size of the following text - max size is 255 bytes
		int size = (int)(dev.readByte() & 0xFF);
		//read the message
		byte [] msg = dev.read(size);
		
		//get string for phoneNo, message
		String phoneNo = new String(ph_no);
		String message = new String(msg);
		Log.i("PhoneNo", phoneNo);
		Log.i("size of message", Integer.toString(size, 10));
		Log.i("message", message);
		sendSMSMessage(phoneNo, message);
		return null;
	}
}
