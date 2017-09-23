package cn.gyyx.elves.util.mq;

import cn.gyyx.elves.util.ExceptionUtil;
import cn.gyyx.elves.util.mq.cache.EhcacheHelper;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;

/**
 * @ClassName: MessageProcesserThread
 * @Description: rabbitmq消息处理器
 * @author East.F
 * @date 2017年5月27日 上午10:48:46
 */
public class MessageProcesserThread implements Runnable{

    private Logger LOG=Logger.getLogger(MessageProcesserThread.class);

    private MessageProducer messageProducer;

    private Message message;

    private  final String SERVICE_NAME = "elvesConsumerService";

    public MessageProcesserThread(MessageProducer messageProducer,Message message) {
        super();
        this.messageProducer = messageProducer;
        this.message=message;
    }

    @Override
    public void run() {
        LOG.debug("MessageProcesserThread process message start...");
        try {
            byte [] body=message.getBody();
            if(null==body){
                return;
            }
            String bodyStr = new String(body,"UTF-8");

            //消息数据转化为ElvesMqMessage，验证数据结构是否符合标准
            ElvesMqMessage elvesMsg = ElvesMqMessage.getInstance(bodyStr);
            if(null==elvesMsg){
                LOG.error("Message structure error!");
                return;
            }
            EhcacheHelper.save(elvesMsg);
        } catch (Exception e) {
            LOG.error(ExceptionUtil.getStackTraceAsString(e));
        }
        LOG.info("MessageProcesserThread process message end.");
    }
}
