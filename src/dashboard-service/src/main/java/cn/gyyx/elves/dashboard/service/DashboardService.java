package cn.gyyx.elves.dashboard.service;

import cn.gyyx.elves.dashboard.model.AgentInfo;

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

    public Map<String,Object> searchElvesDataSize(String toModule,String endTime,int seconds);

}
