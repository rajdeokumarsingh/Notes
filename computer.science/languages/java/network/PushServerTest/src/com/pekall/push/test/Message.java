package com.pekall.push.test;

public class Message {
	String id;//":-236673578,"
	String type;//":0,"
	String command;//":0,"
	String  result;//":0,"
	String reason;
	Params params;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Params getParams() {
		return params;
	}
	public void setParams(Params params) {
		this.params = params;
	} 
	
	
}
class Params
{
	String device;//":"
	String device_uuid_00001;//","
	String message;//":"1234567890sdfsfsfewfweafawef
	@Override
	public String toString() {
		return "Params [device=" + device + ", device_uuid_00001="
				+ device_uuid_00001 + ", message=" + message + "]";
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getDevice_uuid_00001() {
		return device_uuid_00001;
	}
	public void setDevice_uuid_00001(String device_uuid_00001) {
		this.device_uuid_00001 = device_uuid_00001;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}