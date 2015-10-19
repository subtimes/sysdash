
<@override name="body">
    <div id="dashboard">
        <div class="box cpu">
            <div class="box-header">
                <span>CPU</span>
            </div>
            <div class="box-content">
                <table class="table">
                	<tr>
                        <td class="label-col">ƽ������</td>
                        <td class="user">${cpu_load} %</td>
                    </tr>
                    <tr>
                        <td class="label-col">�û�ģʽ�ٷֱ�</td>
                        <td class="user">${cpu_user} %</td>
                    </tr>
                    <tr>
                        <td class="label-col">ϵͳģʽ�ٷֱ�</td>
                        <td class="system">${cpu_system} %</td>
                    </tr>
                    <tr>
                        <td class="label-col">����ģʽ�ٷֱ�</td>
                        <td class="idle">${cpu_idle} %</td>
                    </tr>
                    <tr>
                        <td class="label-col">�ͺ�</td>
                        <td class="iowait">${cpu_vender} ${cpu_model}</td>
                    </tr>
                    <tr>
                        <td class="label-col">��������</td>
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
                            <th>����</th>
                            <th>���ص�</th>
                            <th>����</th>
                            <th>����</th>
                            <th>����</th>
                            <th>ʣ��</th>
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
                <span>�ڴ�</span>
            </div>
            <div class="box-content">
                <table class="table">
                    <tr>
                        <td class="label-col">���ڴ�</td>
                        <td class="total">${mem_total} GB</td>
                    </tr>
                    <tr>
                        <td class="label-col">�����ڴ�</td>
                        <td class="available">${mem_avail} GB</td>
                    </tr>
                    <tr>
                        <td class="label-col">����</td>
                        
                        <td class="used_excl">${mem_used} GB (${mem_used_perc} %)</td>
                    </tr>
                    <tr>
                        <td class="label-col">����</td>
                        <td class="free">${mem_free} GB</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="box users">
            <div class="box-header">
                <span>�û���Ϣ</span>
            </div>
            <div class="box-content">
                <table class="table">
                    <thead>
                        <tr>
                            <th>�û�</th>
                            <th>��¼ʱ��</th>
                            <th>��¼����</th>
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
                <span>����</span>
            </div>
            <div class="box-content">
                <table class="table">
                    <thead>
                        <tr>
                            <th>�ӿ�����</th>
                            <th>IP</th>
                            <th>��������</th>
                            <th>��������</th>
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
