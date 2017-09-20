package cn.gyyx.elves.dashboard.model;

/**
 * @Author : east.Fu
 * @Description : agent info pojo
 * @Date : Created in  2017/8/21 10:32
 */
public class AgentInfo {

    /* AgentIP */
    private String agentIp;

    /* Agent名称 */
    private String agentName;

    /* 资产号 */
    private String asset;

    /* 负责人 */
    private String manager;

    /* 一级分类 */
    private String mainName;

    /* 二级分类 */
    private String subName;

    /* 操作系统 */
    private String os;

    /* elves 在线状态*/
    private int elvesStatus;


    public String getAgentIp() {
        return agentIp;
    }

    public void setAgentIp(String agentIp) {
        this.agentIp = agentIp;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public int getElvesStatus() {
        return elvesStatus;
    }

    public void setElvesStatus(int elvesStatus) {
        this.elvesStatus = elvesStatus;
    }
}
