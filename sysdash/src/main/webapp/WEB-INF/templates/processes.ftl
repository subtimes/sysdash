<@override name="body">
    <div id="processes" class="box">
        <div class="box-header">
            <span>�����б�</span>
        </div>
        <div class="box-content">
            <ul class="nav nav-tabs" role="tablist">
            	<#if filter == "all">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/processes.do?filter=all">
						���н���<span class="badge all">${num_procs}</span>
                    </a>
                </li>
                <#if filter == "user">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/processes.do?filter=user">
                    	�û�����<span class="badge">${num_user_procs}</span>
                    </a>
                </li>
            </ul>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>����ID</th>
                        <th>��������</th>
                        <th>�û�</th>
                        <th>����״̬</th>
                        <th>����ʱ��</th>
                        <th>ʵ��ռ���ڴ�</th>
                        <th>�����ڴ�</th>
                        <th>�����ڴ�</th>
                        <th>CPUʱ��</th>
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