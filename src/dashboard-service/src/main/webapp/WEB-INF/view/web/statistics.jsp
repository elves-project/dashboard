<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h1 class="page-title"> Dashboard页：
                <small>Elves监控</small>
            </h1>
        </div>
        <div class="col-md-4">
            <ul class="breadcrumb pull-right">
                <li>首页</li>
                <li><a href="#" class="active">Elves监控</a></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <!--charts start-->
        <div class="col-md-6 ">
            <div class="panel">
                <div id="supervisor" style="height:240px;"></div>
            </div>
        </div>
        <div class="col-md-6 ">
            <div class="panel">
                <div id="scheduler" style="height:240px;"></div>
            </div>
        </div>

        <div class="col-md-6 ">
            <div class="panel">
                <div id="queue" style="height:240px;"></div>
            </div>
        </div>
        <div class="col-md-6 ">
            <div class="panel">
                <div id="cron" style="height:240px;"></div>
            </div>
        </div>
        <div class="col-md-6 ">
            <div class="panel">
                <div id="heartbeat" style="height:240px;"></div>
            </div>
        </div>
        <div class="col-md-6 ">
            <div class="panel">
                <div id="dashboard" style="height:240px;"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $(document).on('click', '.yamm .dropdown-menu', function(e) {
            e.stopPropagation()
        });
        var index = layer.load(1, {shade: false});
        initEcharts('supervisor');
        initEcharts('scheduler');
        initEcharts('queue');
        initEcharts('cron');
        initEcharts('heartbeat');
        initEcharts('dashboard');
        layer.close(index);
    });

    function initEcharts(moduleId) {
        var option = {
            title: {
                text: moduleId+'监控',
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#283b56'
                    }
                }
            },
            legend: {
                data:['消费总数','每秒消费速度']
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            dataZoom: {
                show: false,
                start: 0,
                end: 100
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: true,
                    data: (function (){
                        var now = new Date();
                        var res = [];
                        var len = 5;
                        while (len--) {
                            res.unshift(new Date().pattern("hh:mm:ss"));
                            now = new Date(now - 2000);
                        }
                        return res;
                    })()
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    scale: true,
                    name: '消息条数',
                    min: 0,
                    boundaryGap: [0.2, 0.2]
                }
            ],
            series: [
                {
                    name:'消费总数',
                    type:'line',
                    data:(function (){
                        var res = [];
                        var len = 5;
                        while (len--) {
                            res.push("");
                        }
                        return res;
                    })()
                },
                {
                    name:'每秒消费速度',
                    type:'line',
                    data:(function (){
                        var res = [];
                        var len = 5;
                        while (len--) {
                            res.push("");
                        }
                        return res;
                    })()
                }
            ]
        };
        var myChart = echarts.init(document.getElementById(moduleId),"dark");
        myChart.setOption(option);

        setInterval(function () {
            var axisData = new Date().pattern("hh:mm:ss");

            var back=getModuleData(moduleId,axisData);

            var data0 = option.series[0].data;
            var data1 = option.series[1].data;

            data0.shift();
            data0.push(back.count);
            data1.shift();
            data1.push(back.avg);

            option.xAxis[0].data.shift();
            option.xAxis[0].data.push(axisData);

            myChart.setOption(option);
        }, 2000);
    }

    function getModuleData(module,time){
        var num={};
        $.ajax({
            url:_ctx+"/statistics",
            type:"get",
            data:{'toModule':module,'endTime':time,'seconds':2},
            dataType:'json',
            success:function(data){
                num=data;
            },
            error:function () {
                num=data;
            },
            async:false
        });
        return num;
    }
</script>


