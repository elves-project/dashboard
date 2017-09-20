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
                <div id="main1" style="height:240px;"></div>
            </div>
        </div>
        <div class="col-md-6 ">
            <div class="panel">
                <div id="main2" style="height:240px;"></div>
            </div>
        </div>

        <div class="col-md-6 ">
            <div class="panel">
                <div id="main3" style="height:240px;"></div>
            </div>
        </div>
        <div class="col-md-6 ">
            <div class="panel">
                <div id="main4" style="height:240px;"></div>
            </div>
        </div>
    </div>
</div>
<script>
    var data = [];
    var now = +new Date(2017, 9, 3);
    var oneDay = 24 * 3600 * 1000;
    var value = Math.random() * 1000;

    $(function () {
        $(document).on('click', '.yamm .dropdown-menu', function(e) {
            e.stopPropagation()
        });
        initEcharts();
    });

    function initEcharts() {
        for (var i = 0; i < 1000; i++) {
            data.push(randomData());
        }
        option = {
            title: {
                text: 'Elves-Openapi监控'
            },
            tooltip: {
                trigger: 'axis',
                formatter: function (params) {
                    params = params[0];
                    var date = new Date(params.name);
                    return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
                },
                axisPointer: {
                    animation: false
                }
            },
            xAxis: {
                type: 'time',
                splitLine: {
                    show: false
                }
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%'],
                splitLine: {
                    show: false
                }
            },
            series: [{
                name: '模拟数据',
                type: 'line',
                showSymbol: false,
                hoverAnimation: false,
                data: data
            }]
        };


        var myChart1 = echarts.init(document.getElementById('main1'),"dark");
        myChart1.setOption(option);
        setInterval(function () {
            for (var i = 0; i < 5; i++) {
                data.shift();
                data.push(randomData());
            }
            myChart1.setOption({
                series: [{
                    data: data
                }]
            });
        }, 2000);

        var myChart2 = echarts.init(document.getElementById('main2'),"dark");
        myChart2.setOption(option);
        setInterval(function () {
            for (var i = 0; i < 5; i++) {
                data.shift();
                data.push(randomData());
            }
            myChart2.setOption({
                series: [{
                    data: data
                }]
            });
        }, 2000);

        var myChart3 = echarts.init(document.getElementById('main3'),"dark");
        myChart3.setOption(option);
        setInterval(function () {
            for (var i = 0; i < 5; i++) {
                data.shift();
                data.push(randomData());
            }
            myChart3.setOption({
                series: [{
                    data: data
                }]
            });
        }, 2000);

        var myChart4 = echarts.init(document.getElementById('main4'),"dark");
        myChart4.setOption(option);
        setInterval(function () {
            for (var i = 0; i < 5; i++) {
                data.shift();
                data.push(randomData());
            }
            myChart4.setOption({
                series: [{
                    data: data
                }]
            });
        }, 2000);
    }



    function randomData() {
        now = new Date(+now + oneDay);
        value = value + Math.random() * 21 - 10;
        return {
            name: now.toString(),
            value: [
                [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
                Math.round(value)
            ]
        }
    }


</script>

