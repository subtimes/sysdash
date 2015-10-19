<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>psDash - Linux system dashboard</title>
        <link href="/sysdash/static/css/bootstrap.min.css" rel="stylesheet">
        <link href="/sysdash/static/css/psdash.css" rel="stylesheet">
    </head>
    <body>
        <div id="psdash">
            <div class="header">
                <div class="logo">
                    <a href="/sysdash/main.do">
                        <span class="app-name">System Dashbord 1.0</small></span>
                    </a>
                </div>
                <div class="top-nav">
                    <div class="host-info">
                        <div class="name">
                             <span class="hostname">${ hostname }</span>
                        </div>
                        <div class="info">
                            ${ os }, 运行时长: ${ uptime }
                        </div>
                    </div>
                </div>
            </div>
            <div class="table-container">
                <div class="content">
                    <div class="left-nav">
                        <ul class="menu">
                        
                        	<#if page=='overview'>
 								<li class="active">
							<#else>
								<li>
							</#if>
                                	<a href="/sysdash/main.do">
                                    <span class="glyphicon glyphicon-th"></span>
                                    <span class="option-text">总览</span>
                                </a>
                            </li>
                            
                            <#if page=='processes'>
 								<li class="active">
							<#else>
								<li>
							</#if>
                                	<a href="/sysdash/processes.do">
                                    <span class="glyphicon glyphicon-tasks"></span>
                                    <span class="option-text">进程信息</span>
                                </a>
                            </li>
                            
                            
                            <#if page=='network'>
 								<li class="active">
							<#else>
								<li>
							</#if>
                                <a href="/sysdash/network.do">
                                    <span class="glyphicon glyphicon-transfer"></span>
                                    <span class="option-text">网络信息</span>
                                </a>
                            </li>
                            
                            <#if page=='disks'>
 								<li class="active">
							<#else>
								<li>
							</#if>
                                <a href="/sysdash/disk.do">
                                    <span class="glyphicon glyphicon-hdd"></span>
                                    <span class="option-text">磁盘信息</span>
                                </a>
                            </li>
                            
                            <#if page=='ipmi'>
 								<li class="active">
							<#else>
								<li>
							</#if>
                                <a href="/sysdash/ipmi.do">
                                    <span class="glyphicon glyphicon-heart"></span>
                                    <span class="option-text">IPMI</span>
                                </a>
                            </li>
                            
                        </ul>
                    </div>
                    <div class="main-content">
                        <@block name="body"></@block>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="./static/js/jquery.min.js"></script>
        <script src="./static/js/bootstrap.min.js"></script>
        <script src="./static/js/psdash.js"></script>
    </body>
</html>