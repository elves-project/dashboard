package cn.gyyx.elves.util.mq.cache;

import cn.gyyx.elves.util.mq.ElvesMqMessage;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author : east.Fu
 * @Description : ehcache 工具类
 * @Date : Created in  2017/9/21 15:50
 */
public class EhcacheHelper {

    private static final Logger LOG = LoggerFactory.getLogger(EhcacheHelper.class);

    public static CacheManager cacheManager;

    public static Cache elvesCache;

    private static final String  EHCACHE_CONFIG_PATH="/ehcache.xml";

    static {
       cacheManager = CacheManager.create(EhcacheHelper.class.getResource(EHCACHE_CONFIG_PATH));
       elvesCache = cacheManager.getCache("elvesCache");
    }

    /**
     * 插入数据到ehcache
     *
     * @param msg
     * @return
     */
    public static boolean save(ElvesMqMessage msg){
        if(elvesCache!=null){
            String key = UUID.randomUUID().toString();
            elvesCache.put(new Element(key, msg));
            LOG.debug("save elves data to ehcache success");
            return true;
        }else{
            LOG.error("elvesCache is null");
            return false;
        }
    }

    /**
     * 查询数据从ehcache
     *
     * @param fromModule
     * @param toModule
     * @return
     */
    public static List<ElvesMqMessage> find(String fromModule,String toModule){
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
            ElvesMqMessage msg=(ElvesMqMessage)rs.getValue();
            back.add(msg);
        }
        return back;
    }

    /**
     * 查询数据从ehcache
     *
     * @param toModule
     * @param startTime   HH:mm:ss
     * @param endTime   HH:mm:ss
     * @return
     */
    public static List<ElvesMqMessage> findByTime(String toModule,String startTime,String endTime){
        if(StringUtils.isBlank(toModule)||(StringUtils.isBlank(startTime)&&StringUtils.isBlank(endTime))){
           return new ArrayList<ElvesMqMessage>();
        }

        Query query = elvesCache.createQuery();
        query.includeValues();
        query.includeKeys();

        Attribute<String> toModuleAttr = elvesCache.getSearchAttribute("toModule");
        query.addCriteria(toModuleAttr.eq(toModule));
        query.includeAttribute(toModuleAttr);


        Attribute<String> time = elvesCache.getSearchAttribute("timestamp");
        if(StringUtils.isNotBlank(startTime)){
            query.addCriteria(time.ge(startTime));
        }
        if(StringUtils.isNotBlank(endTime)){
            query.addCriteria(time.le(endTime));
        }
        query.includeAttribute(time);

        List<Result> resultList=query.execute().all();

        List<ElvesMqMessage> back=new ArrayList<ElvesMqMessage>();
        for(Result rs:resultList){
            Object obj=rs.getValue();
            ElvesMqMessage msg=(ElvesMqMessage)rs.getValue();
            back.add(msg);
        }
        return back;
    }

    /**
     * cache数据统计
     */
    public static void statistics(){
//        if(resultList != null && !resultList.isEmpty()){
//            Result result = resultList.get(0);
//            //多个统计信息将会组成一个List进行返回
//            List<Object> aggregatorResults = result.getAggregatorResults();
//            Number averageAge = (Number)aggregatorResults.get(0);
//            Integer maxAge = (Integer)aggregatorResults.get(1);
//            System.out.println(averageAge + "---" + maxAge);
//        }
    }

}
