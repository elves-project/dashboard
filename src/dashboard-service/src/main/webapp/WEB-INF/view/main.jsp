<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home</title>
    <%@ include file="../../resources/jsp/common.jsp" %>
</head>
<body>
<div id="ui" class="ui ui-aside-none">
    <header id="header" class="ui-header">
        <div class="container" style="height: 30px;">
            <div class="row">
                <div class="col-md-12">
                    <div class="navbar-header">
                        <!--logo start-->
                        <a href="index.html" tppabs="index.html" class="navbar-brand">
                            <span class="logo"><img src="${ctx}/resources/imgs/logo-dark.png" alt=""/></span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <%@ include file="common/head.jsp" %>
    <%@ include file="common/menu.jsp" %>

    <div id="content" class="ui-content ui-content-aside-overlay">
        <div class="ui-content-body" id="rightDiv">
        </div>
    </div>

    <div id="footer" class="ui-footer">
        <div class="container">
            <div class="row">
                <div class="col-md-12" style="text-align: center;">
                    Copyright © 2004-2017, 光宇游戏系统部, All Rights Reserved.
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var allIntervalIds=[];

    $(function () {
        $(document).on('click', '.yamm .dropdown-menu', function (e) {
            e.stopPropagation()
        });
        loadContent("/web/home");
    });

    function loadContent(url) {
        //清空所有的js定时周期任务
        if(url.indexOf("statistics")!=-1){
            for(var item in allIntervalIds){
                clearInterval(allIntervalIds[item]);
            }
        }
        $.ajax({
            url:_ctx+url,
            type:'get',
            dataType:'html',
            success:function(data){
                $("#rightDiv").html(data);
            }
        });
    }

    //定时任务的添加
    function addTask(intervalId){
        allIntervalIds.push(intervalId);
    }
</script>
</body>
</html>

