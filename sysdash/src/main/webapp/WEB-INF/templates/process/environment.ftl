<@override name="process_body">
    <table class="table">
        <thead>
            <tr>
                <th>Variable</th>
                <th>Value</th>
            </tr>
        </thead>
        <tbody>
        	<#list process_environ?keys as var>
                <tr>
                    <td>${ var }</td>
                    <td>${ process_environ[var] }</td>
                </tr>
            </#list>
        </tbody>
    </table>
</@override>
<@extends name="../process.ftl"/>