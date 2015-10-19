<@override name="body">
    <div id="process" class="box">
        <div class="box-header">
            <span>${ process.name } (${ process.pid })</span>
        </div>
        <div class="box-content">
            <ul class="nav nav-tabs" role="tablist">
                <#if section == "overview">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/process.do?pid=${process.pid}">Overview</a>
                </li>
                <#if section == "environment">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/process.do?pid=${process.pid}&section=environment">Environment</a>
                </li>
                
                <#if section == "files">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/process.do?pid=${process.pid}&section=files">
                    	Open files <span class="badge">${ process.num_files }</span>
                    </a>
                </li>
                
            </ul>
            <@block name="process_body"></@block>
        </div>
    </div>
</@override>
  
<#if requestWith != 'XMLHttpRequest'>
	<@extends name="base.ftl"/>
<#else>
	<@block name="body"></@block>
</#if>