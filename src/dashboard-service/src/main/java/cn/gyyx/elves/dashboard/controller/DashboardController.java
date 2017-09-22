package cn.gyyx.elves.dashboard.controller;

import cn.gyyx.elves.dashboard.service.DashboardService;
import cn.gyyx.elves.util.JsonFilter;
import cn.gyyx.elves.util.mq.MessageProducer;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : east.Fu
 * @Description : 控制器 DashboardController
 * @Date : Created in  2017/8/21 10:31
 */
@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardServiceImpl;

    @Autowired
    private MessageProducer messageProducer;

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/web/{page}")
    public String home(@PathVariable("page") String page){
        return "web/"+page;
    }

    @RequestMapping("/status")
    @ResponseBody
    public String status(){
        Map<String,Object> back=dashboardServiceImpl.getModuleStatus();
        return JSON.toJSONString(back);
    }

    @RequestMapping("/agentData")
    @ResponseBody
    public String agentData(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("data",dashboardServiceImpl.getAgents());
        return JSON.toJSONString(map);
    }


    @RequestMapping("/statistics")
    @ResponseBody
    public String statisticsElvesData(String toModule,String endTime,int seconds){
        Map<String,Object> rs=dashboardServiceImpl.searchElvesDataSize(toModule,endTime,seconds);

        return JSON.toJSONString(rs, JsonFilter.filter);
    }

    @RequestMapping("/appData")
    @ResponseBody
    public String appInfo(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("data",dashboardServiceImpl.getAppData());
        return JSON.toJSONString(map);
    }
}
