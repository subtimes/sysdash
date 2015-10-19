<@override name="body">
    <div id="processes" class="box">
        <div class="box-header">
            <span>进程列表</span>
        </div>
        <div class="box-content">
            <ul class="nav nav-tabs" role="tablist">
            	<#if filter == "all">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/processes.do?filter=all">
						所有进程<span class="badge all">${num_procs}</span>
                    </a>
                </li>
                <#if filter == "user">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/processes.do?filter=user">
                    	用户进程<span class="badge">${num_user_procs}</span>
                    </a>
                </li>
            </ul>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>进程ID</th>
                        <th>进程名称</th>
                        <th>用户</th>
                        <th>进程状态</th>
                        <th>创建时间</th>
                        <th>实际占用内存</th>
                        <th>虚拟内存</th>
                        <th>共享内存</th>
                        <th>CPU时间</th>
                    </tr>
                </thead>
                <tbody>
                    <#list processes as p >
                        <tr>
                          <td>${ p.pid }</td>
                          <td title="${ p.name }">
                              <a href="/sysdash/process.do?pid=${p.pid}">${ p.pwd }</a><br/>
                              <small>${ p.name }</small>
                          </td>
                          <td>${ p.user }</td>
                          <td>${ p.state }</td>
                          <td>${ p.start_time }</td>
                          <td>${ p.mem_rss }</td>
                          <td>${ p.mem_size }</td>
                          <td>${ p.mem_share }</td>
                          <td>${ p.cpu_time }</td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</@override>
  
<#if requestWith != 'XMLHttpRequest'>
	<@extends name="base.ftl"/>
<#else>
	<@block name="body"></@block>
</#if>