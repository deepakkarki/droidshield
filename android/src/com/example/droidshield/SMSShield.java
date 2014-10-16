package com.example.droidshield;

import android.telephony.SmsManager;
import android.util.Log;

public class SMSShield implements ActuatorShield
{		
	
	private void sendSMSMessage(String phoneNo, String message) {
		
		Log.i("Send SMS", message);

	      try {
	         SmsManager smsManager = SmsManager.getDefault();
	         smsManager.sendTextMessage(phoneNo, null, message, null, null);
	      } 
	      catch (Exception e) {
	         Log.e("sms-shield", "SMS faild, please try again.");   
	      }
	}

	/*
	 * format of incoming byte for sms:
	 * 	<sms-opcode:1 byte> <ph-no:10 bytes> <size-of-text:1byte> <text-message :n bytes>
	 */
	@Override
	public byte[] setValue(DroidConnect dev) {
		// get phone number
		byte [] ph_no = dev.read(10);
		//get the size of the following text - max size is 255 bytes
		int size = (int)(dev.readByte() & 0xFF);
		//read the message
		byte [] msg = dev.read(size);
		
		//get string for phoneNo, message
		String phoneNo = new String(ph_no);
		String message = new String(msg);
		
		sendSMSMessage(phoneNo, message);
		return null;
	}
}
