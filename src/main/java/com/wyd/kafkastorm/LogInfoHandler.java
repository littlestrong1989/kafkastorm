package com.wyd.kafkastorm;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogInfoHandler implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5591284815660881470L;
	private String target;
    private Date time;
    private String districtServer;
    private String channel;
    private Integer counts;
	
    public LogInfoHandler(){
    	
    }
    
    public void splitHandl(String line){
    	SimpleDateFormat sdf_final = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	String[] lineSplit = line.split("\\|");
    	try {
			time = sdf_final.parse(lineSplit[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	target = lineSplit[1];
    	districtServer = lineSplit[2];
    	channel = lineSplit[3];
    	counts = Integer.parseInt(lineSplit[4]);
    }
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getDistrictServer() {
		return districtServer;
	}
	public void setDistrictServer(String districtServer) {
		this.districtServer = districtServer;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	
}
