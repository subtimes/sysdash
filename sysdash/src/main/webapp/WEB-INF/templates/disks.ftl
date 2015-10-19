<@override name="body">
    <div id="disks">
        <div class="box">
            <div class="box-header">
                <span>磁盘信息</span>
            </div>
            <div class="box-content">
                <table id="processes" class="table table-condensed">
                    <thead>
                        <tr>
                            <th>分区</th>
                            <th>挂载点</th>
                            <th>类型</th>
                            <th>挂载方式</th>
                            <th>总容量</th>
                            <th>已用容量</th>
                            <th>剩余容量</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list disks as d>
                            <tr>
                                <td>${d.drive_letter}</td>
                                <td>${d.dir}</td>
                                <td>${d.type}</td>
                                <td>${d.opts}</td>
                                <td>${d.total}</td>
                                <td>${d.used} (${d.usedpercent} %)</td>
                                <td>${d.free}</td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="box">
            <div class="box-header">
                <span>磁盘I/O信息</span>
            </div>
            <div class="box-content">
                <table id="processes" class="table">
                    <thead>
                        <tr>
                            <th>设备名称</th>
                            <th>读次数</th>
                            <th>写次数</th>
                            <th>读字节数</th>
                            <th>写字节数</th>
                            <th>磁盘队列长度</th>
                            <th>磁盘时间 (ms)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list diskios as io>
                        <tr>
                            <td>${ io.name }</td>
                            <td>${ io.read_count }</td>
                            <td>${ io.write_count }</td>
                            <td>${ io.bytes_read }</td>
                            <td>${ io.bytes_written }</td>
                            <td>${ io.queue }</td>
                            <td>${ io.disk_time }</td>
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