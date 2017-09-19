package cn.gyyx.elves.dashboard.service;

import cn.gyyx.elves.dashboard.model.AgentInfo;
import cn.gyyx.elves.util.mq.ElvesMqMessage;

import java.util.List;
import java.util.Map;

/**
 * @Author : east.Fu
 * @Description :
 * @Date : Created in  2017/8/21 10:31
 */
public interface DashboardService {

    public Map<String,Object> getModuleStatus();

    public List<AgentInfo> getAgents();

    public List<Map<String,String>> getAppData();

    public void addElvesDataToCache(ElvesMqMessage msg);

    public List<ElvesMqMessage> searchElvesData(String fromModule,String toModule);

}
