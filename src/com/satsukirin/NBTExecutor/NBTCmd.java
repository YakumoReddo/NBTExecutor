package com.satsukirin.NBTExecutor;


public class NBTCmd {
	private String name;
	private String trigger;
	private String command;
	private String key;
	private String value;
	
	public NBTCmd(String n,String t,String c,String k,String v) {
		name=n;
		trigger=t;
		command=c;
		key=k;
		value=v;
	}
	
	public String getName() {
		return name;
	}
	public String getTrigger() {
		return trigger;
	}
	public String getCommand() {
		return command;
	}
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	
	
}
