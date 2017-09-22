package cn.gyyx.elves.dashboard.service.impl;

import cn.gyyx.elves.dashboard.model.AgentInfo;
import cn.gyyx.elves.dashboard.service.DashboardService;
import cn.gyyx.elves.util.DateUtils;
import cn.gyyx.elves.util.ExceptionUtil;
import cn.gyyx.elves.util.HttpUtil;
import cn.gyyx.elves.util.mq.ElvesMqMessage;
import cn.gyyx.elves.util.mq.MessageProducer;
import cn.gyyx.elves.util.mq.cache.EhcacheHelper;
import cn.gyyx.elves.util.zk.ZookeeperExcutor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author : east.Fu
 * @Description :
 * @Date : Created in  2017/8/21 10:31
 */
@Service("elvesConsumerService")
public class DashboardServiceImpl implements DashboardService{

    public static Cache elvesCache ;

    static {
        CacheManager cacheManager = CacheManager.create(DashboardServiceImpl.class.getResource("/ehcache.xml"));
        elvesCache = cacheManager.getCache("elvesCache");
    }


    private static final Logger LOG= org.slf4j.LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Autowired
    private MessageProducer messageProducer;

    @Override
    public Map<String, Object> getModuleStatus() {

        Map<String,Object> data=new HashMap<String,Object>();

        try {
            Map<String, Object> back = messageProducer.call("dashboard.heartbeat", "agentList",null,5000);
            LOG.info("search asset from heart back:"+JSON.toJSONString(back));
            List<Map<String, Object>> agentData =null;
            if(null!=back&&null!=back.get("result")){
                agentData = (List<Map<String, Object>>) back.get("result");
                data.put("agents",agentData.size());
            }else{
                data.put("agents","查询失败");
            }

        }catch (Exception e){
            LOG.error("get elves agent  online error : "+ExceptionUtil.getStackTraceAsString(e));
            data.put("agents","查询失败");
        }

        try {
            List<String> cronList=ZookeeperExcutor.getClient().getChildren().forPath("/elves_v2/cron");
            List<String> queueList=ZookeeperExcutor.getClient().getChildren().forPath("/elves_v2/queue");
            List<String> schedulerList=ZookeeperExcutor.getClient().getChildren().forPath("/elves_v2/scheduler");
            List<String> heartbeatList=ZookeeperExcutor.getClient().getChildren().forPath("/elves_v2/heartbeat");
            List<String> supervisorList=ZookeeperExcutor.getClient().getChildren().forPath("/elves_v2/supervisor");
            List<String> apiList=ZookeeperExcutor.getClient().getChildren().forPath("/elves_v2/openapi");

            Map<String,Integer> st=new HashMap<String,Integer>();
            if(cronList!=null&&cronList.size()>0){
                st.put("cron",cronList.size());
            }
            if(queueList!=null&&queueList.size()>0){
                st.put("queue",queueList.size());
            }
            if(schedulerList!=null&&schedulerList.size()>0){
                st.put("scheduler",schedulerList.size());
            }
            if(heartbeatList!=null&&heartbeatList.size()>0){
                st.put("heartbeat",heartbeatList.size());
            }
            if(supervisorList!=null&&supervisorList.size()>0){
                st.put("supervisor",supervisorList.size());
            }
            if(apiList!=null&&apiList.size()>0){
                st.put("openapi",apiList.size());
            }
            data.put("status",st);
        }catch (Exception e){
            LOG.error("get elves module status error : "+ExceptionUtil.getStackTraceAsString(e));
            data.put("status",new HashMap<String,Integer>());
        }
        return data;
    }

    @Override
    public List<AgentInfo> getAgents() {
        String syncUrl="";
        String result= HttpUtil.sendGet(syncUrl,null);

        List<AgentInfo> dt=new ArrayList<>();
        if(StringUtils.isNotBlank(result)){
            Map<String,String> rs = JSON.parseObject(result,new TypeReference<Map<String,String>>(){});
            dt =JSON.parseObject(rs.get("data"),new TypeReference<List<AgentInfo>>(){});
        }
        
        try {
            Map<String, Object> back = messageProducer.call("dashboard.heartbeat","agentList",null,3000);
            if(null!=back&&null!=back.get("result")){
                List<Map<String, Object>> agentData = (List<Map<String, Object>>) back.get("result");
                for(Map<String, Object> agent : agentData){
                    if(null==agent.get("ip")){
                        continue;
                    }
                    for(int i=0;i< dt.size();i++){
                        AgentInfo info =dt.get(i);
                        if(info.getAgentIp().equals(agent.get("ip").toString())){
                            info.setElvesStatus(1);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(ExceptionUtil.getStackTraceAsString(e));
        }
        return dt;
    }

    @Override
    public List<Map<String,String>> getAppData(){
        try {
            Map<String, Object> back = messageProducer.call("dashboard.supervisor","appAllInfo",null,3000);
            if(null!=back&&null!=back.get("result")){
                List<Map<String, String>> agentData =JSON.parseObject(back.get("result").toString(), new TypeReference<List<Map<String, String>>>() {}) ;
                return agentData;
            }
        } catch (Exception e) {
            LOG.error(ExceptionUtil.getStackTraceAsString(e));
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String,Object> searchElvesDataSize(String toModule,String endTime,int seconds) {
        Map<String,Object> rs=new HashMap<String,Object>();

        List<ElvesMqMessage> count= EhcacheHelper.find(null,toModule);
        rs.put("count",count==null?0:count.size());

        long start=DateUtils.string2Date(endTime,"HH:mm:ss").getTime()-seconds*1000;
        String startStr=DateUtils.date2String(new Date(start),"HH:mm:ss");
        List<ElvesMqMessage> avgSum= EhcacheHelper.findByTime(toModule,startStr,endTime);
        rs.put("avg",avgSum==null?0:avgSum.size()/2);

        return rs;
    }
}
