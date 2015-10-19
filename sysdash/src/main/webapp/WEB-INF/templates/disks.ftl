<@override name="body">
    <div id="disks">
        <div class="box">
            <div class="box-header">
                <span>������Ϣ</span>
            </div>
            <div class="box-content">
                <table id="processes" class="table table-condensed">
                    <thead>
                        <tr>
                            <th>����</th>
                            <th>���ص�</th>
                            <th>����</th>
                            <th>���ط�ʽ</th>
                            <th>������</th>
                            <th>��������</th>
                            <th>ʣ������</th>
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
                <span>����I/O��Ϣ</span>
            </div>
            <div class="box-content">
                <table id="processes" class="table">
                    <thead>
                        <tr>
                            <th>�豸����</th>
                            <th>������</th>
                            <th>д����</th>
                            <th>���ֽ���</th>
                            <th>д�ֽ���</th>
                            <th>���̶��г���</th>
                            <th>����ʱ�� (ms)</th>
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