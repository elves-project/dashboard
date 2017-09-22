package cn.gyyx.elves.dashboard.listener;

import cn.gyyx.elves.util.SpringUtil;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Created by eastFu on 2017/7/17
 */
public class WebContextListener extends ContextLoaderListener {

    private final  static Logger LOG= LoggerFactory.getLogger(WebContextListener.class);

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {

        String confPath =System.getProperty("CONF_PATH");

        SpringUtil.PROPERTIES_CONFIG_PATH=confPath+ File.separator+"conf"+File.separator+"conf.properties";

        PropertyConfigurator.configure(confPath+File.separator+"conf"+File.separator+"log4j.properties");

        LOG.info("conf.properties path :"+ SpringUtil.PROPERTIES_CONFIG_PATH);

        return super.initWebApplicationContext(servletContext);

    }
}
