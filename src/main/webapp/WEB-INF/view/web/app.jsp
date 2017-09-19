<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h1 class="page-title"> Dashboard页：
                <small>App信息列表</small>
            </h1>
        </div>
        <div class="col-md-4">
            <ul class="breadcrumb pull-right">
                <li>首页</li>
                <li><a href="#" class="active">App列表</a></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <!--charts start-->
        <div class="col-md-12 ">
            <div class="panel">
                <header class="panel-heading panel-border">
                    App列表
                </header>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table  class="table table-striped table-bordered dt-responsive nowrap" id="app-datatable">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $(document).on('click', '.yamm .dropdown-menu', function(e) {
            e.stopPropagation()
        });
        $('#app-datatable').DataTable({
            "bServerSide" : false, //是否启动服务器端数据导入("前端分页/后端分页")
            "sAjaxSource" : _ctx+"/appData",
            'bStateSave': true,
            "bAutoWidth": false,
            "order": [],
            "aoColumnDefs": [ { "bSortable": false, "aTargets": [ 0 ] }],
            "aoColumns" : [{
                "mDataProp" : "aaa",
                "sDefaultContent":"",
                "sTitle" : "序号",
                "sWidth":"4%"
            },{
                "mDataProp" : "app_name",
                "sDefaultContent":"",
                "sTitle" : "APP名称",
                "sWidth":"8%"
            },{
                "mDataProp" : "app",
                "sDefaultContent":"",
                "sTitle" : "App指令",
                "sWidth":"8%"
            },{
                "mDataProp" : "app_ver",
                "sDefaultContent":"",
                "sTitle" : "版本号",
                "sWidth":"5%"
            },{
                "mDataProp" : "last_update_time",
                "sDefaultContent":"",
                "sTitle" : "最后更新时间",
                "sWidth":"8%"
            }],
            "bProcessing": true,
            "processing" : true,
            "sPaginationType" : "full_numbers",
            "fnDrawCallback": function(){
                this.api().column(0).nodes().each(function(cell, i) {
                    cell.innerHTML =  i + 1;
                });
            },
            "bPaginate": false, //翻页功能
            "bSort": false, //排序功能
            "searching":false,//搜索功能
            "oLanguage" : {
                "sInfo": "",
                "sInfoEmpty": "找不到相关数据",
                "sProcessing": "数据正在加载中，请稍候...",
                "sSearch": "搜索",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "前一页",
                    "sNext": "后一页",
                    "sLast": "尾页"
                }
            }
        });
    });
</script>

