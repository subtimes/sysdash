<@override name="process_body">
    <table class="table">
        <tr class="skip-border">
            <td>����ID</td>
            <td>${ process.pid }</td>
        </tr>
        <#if process.parent_name??>
        <tr>
            <td>������</td>
            <td><a href="/sysdash/process.do?pid=${process.ppid}">${ process.parent_name }</a> (${ process.ppid })</td>
        </tr>
        </#if>
        <tr>
            <td>����</td>
            <td>
                ${ process.cmdline }
            </td>
        </tr>
        <tr>
            <td>�û�</td>
            <td>${ process.user }</td>
        </tr>
        <tr>
            <td>�û�ID</td>
            <td>
                <table class="table table-bordered">
                    <tr>
                        <td>real</td>
                        <td>${ process.uid_real }</td>
                    </tr>
                    <tr>
                        <td>effective</td>
                        <td>${ process.uid_effective }</td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>��ID</td>
            <td>
                <table class="table table-bordered">
                    <tr>
                        <td>real</td>
                        <td>${ process.gid_real }</td>
                    </tr>
                    <tr>
                        <td>effective</td>
                        <td>${ process.gid_effective }</td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>�ڴ�</td>
            <td>
                <table class="table table-bordered">
                    <tr>
                        <td>ʵ��ʹ�������ڴ�</td>
                        <td>${ process.mem_rss }</td>
                    </tr>
                    <tr>
                        <td>�����ڴ�</td>
                        <td>${ process.mem_vms }</td>
                    </tr>
                    <tr>
                        <td>�����ڴ�</td>
                        <td>${ process.mem_shared }</td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>����״̬</td>
            <td>${ process.status }</td>
        </tr>
        <tr>
            <td>CWD</td>
            <td>${ process.cwd }</td>
        </tr>
        <tr>
            <td>���ļ�����</td>
            <td>${ process.num_files }</td>
        </tr>
        <tr>
            <td>�߳�����</td>
            <td>${ process.num_threads }</td>
        </tr>
        <tr>
            <td>CPUʱ��</td>
            <td>
                <table class="table table-bordered">
                    <tr>
                        <td>�û�ģʽ</td>
                        <td>${ process.cpu_times_user }</td>
                    </tr>
                    <tr>
                        <td>ϵͳģʽ</td>
                        <td>${ process.cpu_times_system }</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</@override>
  
<@extends name="../process.ftl"/>