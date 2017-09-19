package cn.gyyx.elves.dashboard.service.impl;

import cn.gyyx.elves.dashboard.model.AgentInfo;
import cn.gyyx.elves.dashboard.service.DashboardService;
import cn.gyyx.elves.util.ExceptionUtil;
import cn.gyyx.elves.util.HttpUtil;
import cn.gyyx.elves.util.mq.ElvesMqMessage;
import cn.gyyx.elves.util.mq.MessageProducer;
import cn.gyyx.elves.util.zk.ZookeeperExcutor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
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
@Service
public class DashboardServiceImpl implements DashboardService{

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
            List<String> openapiList=ZookeeperExcutor.getClient().getChildren().forPath("/elves_v2/openapi");

            Map<String,Integer> st=new HashMap<String,Integer>();
            if(cronList!=null&&cronList.size()>0){
                st.put("cron",cronList.size());
            }
            if(queueList!=null&&queueList.size()>0){
                st.put("queue",cronList.size());
            }
            if(schedulerList!=null&&schedulerList.size()>0){
                st.put("scheduler",cronList.size());
            }
            if(heartbeatList!=null&&heartbeatList.size()>0){
                st.put("heartbeat",cronList.size());
            }
            if(supervisorList!=null&&supervisorList.size()>0){
                st.put("supervisor",cronList.size());
            }
            if(openapiList!=null&&openapiList.size()>0){
                st.put("openapi",cronList.size());
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
    public void addElvesDataToCache(ElvesMqMessage msg) {
        CacheManager cacheManager = CacheManager.create(getClass().getResource("/ehcache.xml"));
        Cache elvesCache = cacheManager.getCache("elvesCache");
        if(elvesCache!=null){
            String key = UUID.randomUUID().toString();
            elvesCache.put(new Element(key, msg));
            LOG.info("save elves data to ehcache success");
        }else{
            LOG.error("elvesCache is null");
        }
    }

    @Override
    public List<ElvesMqMessage> searchElvesData(String fromModule,String toModule) {
        for(int i=0;i<100;i++){
            ElvesMqMessage e =new ElvesMqMessage();
            e.setFromModule("opeanapi");
            e.setToModule("scheduler");
            addElvesDataToCache(e);
        }

        CacheManager cacheManager = CacheManager.create(getClass().getResource("/ehcache.xml"));
        Cache elvesCache = cacheManager.getCache("elvesCache");

        Query query = elvesCache.createQuery();
        query.includeValues();
        query.includeKeys();

        if(StringUtils.isNotBlank(fromModule)){
            Attribute<String> fromModuleAttr = elvesCache.getSearchAttribute("fromModule");
            query.addCriteria(fromModuleAttr.eq(fromModule));
            query.includeAttribute(fromModuleAttr);
        }

        if(StringUtils.isNotBlank(toModule)){
            Attribute<String> toModuleAttr = elvesCache.getSearchAttribute("toModule");
            query.addCriteria(toModuleAttr.eq(toModule));
            query.includeAttribute(toModuleAttr);
        }


        List<Result> resultList=query.execute().all();

        List<ElvesMqMessage> back=new ArrayList<ElvesMqMessage>();
        for(Result rs:resultList){
            Object obj=rs.getValue();
            LOG.info(obj.toString());
            ElvesMqMessage msg=(ElvesMqMessage)rs.getValue();
            back.add(msg);
        }

//        if(resultList != null && !resultList.isEmpty()){
//            Result result = resultList.get(0);
//            //多个统计信息将会组成一个List进行返回
//            List<Object> aggregatorResults = result.getAggregatorResults();
//            Number averageAge = (Number)aggregatorResults.get(0);
//            Integer maxAge = (Integer)aggregatorResults.get(1);
//            System.out.println(averageAge + "---" + maxAge);
//        }
        return  back;
    }
}
