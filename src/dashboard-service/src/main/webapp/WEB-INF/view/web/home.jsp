<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="../../../resources/plugin/vis/vis-network.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../../resources/plugin/vis/vis.js"></script>

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h1 class="page-title">Dashboard页：
                <small>Elves各模块健康状况</small>
            </h1>
        </div>
        <div class="col-md-4">
            <ul class="breadcrumb pull-right">
                <li>首页</li>
                <li>Elves各模块健康状况</li>
            </ul>
        </div>
    </div>
    <div class="col-md-12" id="elvesModule" style="height:700px;width:1100px;float:left;position:relative;"></div>
</div>
<script>
    $(function () {
        $(document).on('click', '.yamm .dropdown-menu', function (e) {
            e.stopPropagation()
        });
        var index = layer.load(1, {shade: false});
        $.ajax({
            url: _ctx+"/status",
            dataType:"json",
            success:function(data){
                layer.close(index);
                initModuleStatus(data.status,data.agents);
            },
            error:function(){
                layer.close(index);
                initModuleStatus('','0');
            }});
    });

    function initModuleStatus(data,agentData){

        var visData = new Array();
        if(data.openapi!=undefined){
            visData[0]={id:1, label: 'OpenAPI\n 在线('+data.openapi+')',color:'#7FFFD4'};
        }else{
            visData[0]={id:1, label: 'OpenAPI\n 离线',color:'#FF6A6A'};
        }

        if(data.cron!=undefined){
            visData[1]={id:2, label: 'Cron\n 在线('+data.cron+')',color:'#7FFFD4'};
        }else{
            visData[1]={id:2, label: 'Cron\n 离线',color:'#FF6A6A'};
        }

        if(data.queue!=undefined){
            visData[2]={id:3,label: 'Queue\n 在线('+data.queue+')',color:'#7FFFD4'};
        }else{
            visData[2]={id:3, label: 'Queue\n 离线',color:'#FF6A6A'};
        }

        if(data.scheduler!=undefined){
            visData[3]={id:4,label: 'Scheduler\n 在线('+data.scheduler+')',color:'#7FFFD4'};
        }else{
            visData[3]={id:4,label: 'Scheduler\n 离线',color:'#FF6A6A'};
        }

        if(data.heartbeat!=undefined){
            visData[4]={id:5,label: 'HeatBeat\n 在线('+data.heartbeat+')',color:'#7FFFD4'};
        }else{
            visData[4]={id:5,label: 'HeatBeat\n 离线',color:'#FF6A6A'};
        }

        if(data.supervisor!=undefined){
            visData[5]={id:6,label: 'Supervisor\n 在线('+data.supervisor+')',color:'#7FFFD4'};
        }else{
            visData[5]={id:6,label: 'Supervisor\n 离线',color:'#FF6A6A'};
        }

        visData[6]={id:7,label:"Agent\n 在线("+agentData+")",color:'#87CEFF'};

        if(data.dashboard!=undefined){
            visData[7]={id:8,label: 'Dashboard\n 在线('+data.dashboard+')',color:'#7FFFD4'};
        }else{
            visData[7]={id:8,label: 'Dashboard\n 离线',color:'#FF6A6A'};
        }

        var nodes = new vis.DataSet(visData);

        var edges = new vis.DataSet(
            [
                {id:1 ,from : 1, to : 2,label: '操作计划任务',arrows:'to;from'},
                {id:2 ,from : 1, to : 3,label: '操作队列任务',arrows:'to;from'},
                {id:3 ,from : 1, to : 4,label: '发送即时任务',arrows:'to;from'},
                {id:4 ,from : 1, to : 6,label: '权限认证',arrows:'to;from'},

                {id:5 ,from : 2, to : 4,label: '发起定时任务',arrows:'to;from'},

                {id:6 ,from : 3, to : 4,label: '发起队列任务',arrows:'to;from'},

                {id:7 ,from : 4, to : 7,label: '发起任务',arrows:'to;from', dashes:true},

                {id:8 ,from : 5, to : 6,label: 'App授权机器数据',arrows:'to;from'},

                {id:10,from : 7, to : 5,label: '发送心跳数据',arrows:'to;from', dashes:true},

                {id:11,from : 1, to : 8,label: '监控Elves',arrows:'to'},
                {id:12,from : 2, to : 8,label: '监控Elves',arrows:'to'},
                {id:13 ,from : 3, to : 8,label: '监控Elves',arrows:'to'},
                {id:14,from : 4, to : 8,label: '监控Elves',arrows:'to'},
                {id:15,from : 5, to : 8,label: '监控Elves',arrows:'to'},
                {id:16,from : 6, to : 8,label: '监控Elves',arrows:'to'}
            ]
        );

        // create a network
        var container = document.getElementById('elvesModule');
        var data = {
            nodes: nodes,
            edges: edges
        };
        var options = {
            "layout":{
                "randomSeed":18.8
            },
            "physics": {
                "minVelocity": 0.85,
                "barnesHut": {
                    "gravitationalConstant": -30000,
                    "centralGravity": 0,
                }
            },
            "interaction":{
                zoomView:true
            },
            "nodes": {
                shape: 'box',
                scaling: {
                    customScalingFunction: function (min,max,total,value) {
                        return value/total;
                    },
                    min:10,
                    max:150
                }
            }};
        var network = new vis.Network(container, data, options);
        network.on("click", function (params) {
            Console.log(params);
        });
    }
</script>

