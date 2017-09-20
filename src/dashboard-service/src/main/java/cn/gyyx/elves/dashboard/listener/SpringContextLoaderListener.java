package cn.gyyx.elves.dashboard.listener;

import cn.gyyx.elves.util.ExceptionUtil;
import cn.gyyx.elves.util.SpringUtil;
import cn.gyyx.elves.util.mq.PropertyLoader;
import cn.gyyx.elves.util.zk.ZookeeperExcutor;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @ClassName: SpringContextLoaderListener
 * @Description: spring 监听器，获取ApplicationContext
 * @author East.F
 * @date 2017年6月7日 下午2:36:01
 */
public class SpringContextLoaderListener implements ServletContextListener{

	private static final Logger LOG = Logger.getLogger(SpringContextLoaderListener.class);

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		SpringUtil.app=ctx;
		LOG.info("WebApplicationContext listener start ,ctx :" + ctx);

		if("true".equalsIgnoreCase(PropertyLoader.ZOOKEEPER_ENABLED)){
			try {
				ZookeeperExcutor zke=new ZookeeperExcutor(PropertyLoader.ZOOKEEPER_HOST,
						PropertyLoader.ZOOKEEPER_OUT_TIME, PropertyLoader.ZOOKEEPER_OUT_TIME);

				//创建模块根节点
				if(null==zke.getClient().checkExists().forPath(PropertyLoader.ZOOKEEPER_ROOT)){
					zke.getClient().create().creatingParentsIfNeeded().forPath(PropertyLoader.ZOOKEEPER_ROOT);
				}
				if(null==zke.getClient().checkExists().forPath(PropertyLoader.ZOOKEEPER_ROOT+"/dashboard")){
					zke.getClient().create().creatingParentsIfNeeded().forPath(PropertyLoader.ZOOKEEPER_ROOT+"/dashboard");
				}

				String nodeName=zke.createNode(PropertyLoader.ZOOKEEPER_ROOT+"/dashboard/", "");
				if(null!=nodeName){
					zke.addListener(PropertyLoader.ZOOKEEPER_ROOT+"/dashboard/", "");
					LOG.info("register zookeeper supervisor node success");
				}
			}catch (Exception e){
				LOG.error("register zookeeper supervisor node fail , msg:"+ ExceptionUtil.getStackTraceAsString(e));
			}

		}
	}

    @Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}
}