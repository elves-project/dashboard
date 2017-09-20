<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="jstl.jsp" %>
<nav class="navbar navbar-inverse yamm navbar-default hori-nav " role="navigation">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="collapse navbar-collapse" id="main-navigation">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a class="dropdown-toggle" href="javascript:void(0);" onclick="loadContent('/web/home')" data-toggle="dropdown" data-hover="dropdown" data-submenu>
                                首页</span>
                            </a>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" href="javascript:void(0);" onclick="loadContent('/web/agent')" data-toggle="dropdown" data-hover="dropdown" data-submenu>
                                机器列表</span>
                            </a>
                        </li>

                        <li class="dropdown">
                            <a class="dropdown-toggle" href="javascript:void(0);" onclick="loadContent('/web/app')"  data-toggle="dropdown" data-hover="dropdown" data-submenu>
                                APP列表</span>
                            </a>
                        </li>

                        <li class="dropdown">
                            <a class="dropdown-toggle" href="javascript:void(0);" onclick="loadContent('/web/statistics')" data-toggle="dropdown" data-hover="dropdown" data-submenu>
                                Elves监控</span>
                            </a>
                        </li>

                        <li class="dropdown">
                            <a class="dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown" data-hover="dropdown" data-submenu>
                                任务查询 <span class="fa fa-angle-down"></span>
                            </a>

                            <ul class="dropdown-menu">
                                <li><a tabindex="0" href="javascript:void(0);" ><i class="fa fa-home"></i>即时任务</a></li>
                                <li><a tabindex="1" href="javascript:void(0);" ><i class="fa fa-compass"></i>队列任务</a></li>
                                <li><a tabindex="2" href="javascript:void(0);" ><i class="fa fa-dashboard"></i>计划任务</a></li>
                            </ul>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
    </div>
</nav>