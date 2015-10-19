<@override name="body">
    <div class="box">
        <div class="box-header">
            <span>Network</span>
        </div>
        <div class="box-content">
            <table class="table">
                <thead>
                    <tr>
                        <th>Interface</th>
                        <th>IP</th>
                        <th>Bytes sent</th>
                        <th>Bytes recv</th>
                        <th>Packets sent</th>
                        <th>Packets recv</th>
                        <th>Errors in</th>
                        <th>Errors out</th>
                        <th>Dropped in</th>
                        <th>Dropped out</th>
                        <th>RX/s</th>
                        <th>TX/s</th>
                    </tr>
                </thead>
                <tbody>
                    <#list interfaces as io>
                    <tr>
                        <td>${io.nicName}</td>
                        <td>${io.ipAddress}</td>
                        <td>${io.outOctets}</td>
                        <td>${io.inOctets}</td>
                        <td>${io.outUcastPkts}</td>
                        <td>${io.inUcastPkts}</td>
                        <td>${io.inErrors}</td>
                        <td>${io.outErrors}</td>
                        <td>${io.inDiscards}</td>
                        <td>${io.outDiscards}</td>
                        <td>${io.tx_per_sec}</td>
                        <td>${io.rx_per_sec}</td>
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