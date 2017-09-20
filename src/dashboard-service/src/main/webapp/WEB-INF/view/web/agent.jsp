<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h1 class="page-title"> Dashboard页：
                <small>服务器Elves状态</small>
            </h1>
        </div>
        <div class="col-md-4">
            <ul class="breadcrumb pull-right">
                <li>首页</li>
                <li><a href="#" class="active">服务器列表</a></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <!--charts start-->
        <div class="col-md-12 ">
            <div class="panel">
                <header class="panel-heading panel-border">
                    服务器列表
                </header>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table  class="table table-striped table-bordered dt-responsive nowrap" id="agent-datatable">
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
        $('#agent-datatable').DataTable({
            "bServerSide" : false, //是否启动服务器端数据导入("前端分页/后端分页")
            "sAjaxSource" : _ctx+"/agentData",
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
                "mDataProp" : "agentIp",
                "sDefaultContent":"",
                "sTitle" : "IP",
                "sWidth":"8%"
            },{
                "mDataProp" : "agentName",
                "sDefaultContent":"",
                "sTitle" : "Agent名称",
                "sWidth":"8%"
            },{
                "mDataProp" : "asset",
                "sDefaultContent":"",
                "sTitle" : "资产号",
                "sWidth":"5%"
            },{
                "mDataProp" : "manager",
                "sDefaultContent":"",
                "sTitle" : "负责人",
                "sWidth":"8%"
            },{
                "mDataProp" : "mainName",
                "sDefaultContent":"",
                "sTitle" : "一级分类",
                "sWidth":"10%"
            },{
                "mDataProp" : "subName",
                "sDefaultContent":"",
                "sTitle" : "二级分类",
                "sWidth":"10%"
            },{
                "mDataProp" : "elvesStatus",
                "sDefaultContent":"",
                "sTitle" : "Elves状态",
                "sWidth":"10%",
                "mRender": function ( data, type,row, full ) {
                    if(data==1){
                        return "<font style='color: #40E0D0'>在线</font>";
                    }
                    return "<font style='color: red'>不在线</font>";
                },
            }],
            "bProcessing": true,
            "processing" : true,
            /*"aLengthMenu" : [10,50,500], //更改显示记录数选项,
            "iDisplayLength" : 10, //默认显示的记录数*/
            "sPaginationType" : "full_numbers",
            "fnDrawCallback": function(){
                this.api().column(0).nodes().each(function(cell, i) {
                    cell.innerHTML =  i + 1;
                });
            },
            "oLanguage" : {
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "sZeroRecords": "抱歉，查询不到任何相关数据",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_条记录",
                "sInfoEmpty": "找不到相关数据",
                "sInfoFiltered": "(数据表中共为 _MAX_ 条记录)",
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

