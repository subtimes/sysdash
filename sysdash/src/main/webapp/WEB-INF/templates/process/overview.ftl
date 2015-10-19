<@override name="process_body">
    <table class="table">
        <tr class="skip-border">
            <td>进程ID</td>
            <td>${ process.pid }</td>
        </tr>
        <#if process.parent_name??>
        <tr>
            <td>父进程</td>
            <td><a href="/sysdash/process.do?pid=${process.ppid}">${ process.parent_name }</a> (${ process.ppid })</td>
        </tr>
        </#if>
        <tr>
            <td>命令</td>
            <td>
                ${ process.cmdline }
            </td>
        </tr>
        <tr>
            <td>用户</td>
            <td>${ process.user }</td>
        </tr>
        <tr>
            <td>用户ID</td>
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
            <td>组ID</td>
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
            <td>内存</td>
            <td>
                <table class="table table-bordered">
                    <tr>
                        <td>实际使用物理内存</td>
                        <td>${ process.mem_rss }</td>
                    </tr>
                    <tr>
                        <td>虚拟内存</td>
                        <td>${ process.mem_vms }</td>
                    </tr>
                    <tr>
                        <td>共享内存</td>
                        <td>${ process.mem_shared }</td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>进程状态</td>
            <td>${ process.status }</td>
        </tr>
        <tr>
            <td>CWD</td>
            <td>${ process.cwd }</td>
        </tr>
        <tr>
            <td>打开文件数量</td>
            <td>${ process.num_files }</td>
        </tr>
        <tr>
            <td>线程数量</td>
            <td>${ process.num_threads }</td>
        </tr>
        <tr>
            <td>CPU时间</td>
            <td>
                <table class="table table-bordered">
                    <tr>
                        <td>用户模式</td>
                        <td>${ process.cpu_times_user }</td>
                    </tr>
                    <tr>
                        <td>系统模式</td>
                        <td>${ process.cpu_times_system }</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</@override>
  
<@extends name="../process.ftl"/>