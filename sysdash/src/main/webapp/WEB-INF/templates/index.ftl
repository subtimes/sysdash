
<@override name="body">
    <div id="dashboard">
        <div class="box cpu">
            <div class="box-header">
                <span>CPU</span>
            </div>
            <div class="box-content">
                <table class="table">
                	<tr>
                        <td class="label-col">平均负载</td>
                        <td class="user">${cpu_load} %</td>
                    </tr>
                    <tr>
                        <td class="label-col">用户模式百分比</td>
                        <td class="user">${cpu_user} %</td>
                    </tr>
                    <tr>
                        <td class="label-col">系统模式百分比</td>
                        <td class="system">${cpu_system} %</td>
                    </tr>
                    <tr>
                        <td class="label-col">空闲模式百分比</td>
                        <td class="idle">${cpu_idle} %</td>
                    </tr>
                    <tr>
                        <td class="label-col">型号</td>
                        <td class="iowait">${cpu_vender} ${cpu_model}</td>
                    </tr>
                    <tr>
                        <td class="label-col">核心数量</td>
                        <td class="iowait">${num_cpus}</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="box disks">
            <div class="box-header">
                <span>Disks</span>
            </div>
            <div class="box-content">
                <table class="table">
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>挂载点</th>
                            <th>类型</th>
                            <th>容量</th>
                            <th>已用</th>
                            <th>剩余</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list disks as disk>
                            <tr>
                                <td>${disk.drive_letter}</td>
                                <td>${disk.dir}</td>
                                <td>${disk.type}</td>
                                <td>${disk.total}</td>
                                <td>${disk.used}(${disk.usedpercent})</td>
                                <td>${disk.free}</td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="box memory">
            <div class="box-header">
                <span>内存</span>
            </div>
            <div class="box-content">
                <table class="table">
                    <tr>
                        <td class="label-col">总内存</td>
                        <td class="total">${mem_total} GB</td>
                    </tr>
                    <tr>
                        <td class="label-col">可用内存</td>
                        <td class="available">${mem_avail} GB</td>
                    </tr>
                    <tr>
                        <td class="label-col">已用</td>
                        
                        <td class="used_excl">${mem_used} GB (${mem_used_perc} %)</td>
                    </tr>
                    <tr>
                        <td class="label-col">空闲</td>
                        <td class="free">${mem_free} GB</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="box users">
            <div class="box-header">
                <span>用户信息</span>
            </div>
            <div class="box-content">
                <table class="table">
                    <thead>
                        <tr>
                            <th>用户</th>
                            <th>登录时间</th>
                            <th>登录主机</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list users as u>
                            <tr>
                                <td>${ u.user }</td>
                                <td>${ u.time }</td>
                                <td>${ u.host }</td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="box network">
            <div class="box-header">
                <span>网络</span>
            </div>
            <div class="box-content">
                <table class="table">
                    <thead>
                        <tr>
                            <th>接口名称</th>
                            <th>IP</th>
                            <th>接收速率</th>
                            <th>发送速率</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list interfaces as nic>
                            <tr>
                                <td>${ nic.nicName }</td>
                                <td>${ nic.ipAddress }</td>
                                <td>${ nic.tx_per_sec }</td>
                                <td>${ nic.rx_per_sec }</td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</@override>
  
<#if requestWith != 'XMLHttpRequest'>
	<@extends name="base.ftl"/>
<#else>
	<@block name="body"></@block>
</#if>
