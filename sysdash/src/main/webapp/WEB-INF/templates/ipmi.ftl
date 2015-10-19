<@override name="body">
    <div id="ipmi" class="box">
        <div class="box-header">
            <div class="dropdown pull-right">
	        	<span class="dropdown-text">Switch server:</span>
	            	<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
	                	${ current_nodename } (${ current_nodeip })
	            			<span class="caret"></span>
	                </button>
	            <ul class="dropdown-menu" role="menu">
	            	<#list hosts as host>
	                	<li><a href="/sysdash/ipmi.do?ip=${ host.ip }">${ host.name } (${ host.ip })</a></li>
	                </#list>
	            </ul>
        	</div>
        </div>
        <div class="box-content">
            <ul class="nav nav-tabs" role="tablist">
                <!-- FRU -->
                <#if section == "fru">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/ipmi.do?section=fru&ip=${ current_nodeip }">FRU</a>
                </li>
                
                <!-- CHASSIS -->
                <#if section == "chassis">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/ipmi.do?section=chassis&ip=${ current_nodeip }">Chassis</a>
                </li>
                
                <!-- SESSION -->
                <#if section == "session">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/ipmi.do?section=session&ip=${ current_nodeip }">
                    	Sessions
                    </a>
                </li>
                
                <!-- SENSSOR -->
                <#if section == "senssor">
                	<li class="active">
                <#else>
                	<li>
                </#if>
                    <a href="/sysdash/ipmi.do?section=senssor&ip=${ current_nodeip }">Sensor</a>
                </li>
                
            </ul>
            <@block name="ipmi_body"></@block>
        </div>
    </div>
</@override>
  
<#if requestWith != 'XMLHttpRequest'>
	<@extends name="base.ftl"/>
<#else>
	<@block name="body"></@block>
</#if>