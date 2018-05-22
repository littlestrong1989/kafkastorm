package com.wyd.kafkastorm;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class DailyStatisticsAnalysisBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2262767962772699286L;
	private OutputCollector _collector;
	LogInfoHandler loginfohandler = new LogInfoHandler();
	@Override
	public void execute(Tuple tuple) {
		// 存入mysql
		try{
			String value = tuple.getString(0);  
            loginfohandler.splitHandl(value);
            DbUtil.insert(loginfohandler.getTarget(), loginfohandler.getTime(),
					loginfohandler.getDistrictServer(), loginfohandler.getChannel(), loginfohandler.getCounts());
			_collector.ack(tuple);
		}catch(Exception e){
			_collector.fail(tuple);
			e.printStackTrace();
		}
		
	}
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this._collector = collector;
	}
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
