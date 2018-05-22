package com.wyd.kafkastorm;

import java.util.UUID;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;

public class DailyStatisticsAnalysisTopology {
	private static String topicName = "dailyStatisticsAnalysis";  
    private static String zkRoot = "/stormKafka/"+topicName;  
      
    public static void main(String[] args) {  
          
        BrokerHosts hosts = new ZkHosts("192.168.100.130:2181");  
  
          
        SpoutConfig spoutConfig = new SpoutConfig(hosts,topicName,zkRoot,UUID.randomUUID().toString());  
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());  
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);  
          
        TopologyBuilder builder = new TopologyBuilder();          
        builder.setSpout("kafkaSpout",kafkaSpout);          
        builder.setBolt("dailyStatisticsAnalysisBolt", new DailyStatisticsAnalysisBolt(), 2).shuffleGrouping("kafkaSpout");  
  
        Config conf = new Config();  
        conf.setDebug(true);  
          
        if(args != null && args.length > 0) {  
            conf.setNumWorkers(1);  
            try {  
                StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());  
            }catch (Exception e) {  
                e.printStackTrace();  
            }  
              
        } else {  
              
            conf.setMaxSpoutPending(3);  
              
            LocalCluster cluster = new LocalCluster();  
            cluster.submitTopology("dailyAnalysis", conf, builder.createTopology());  
              
              
        }  
  
    }
}
