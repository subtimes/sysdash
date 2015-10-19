<@override name="ipmi_body">
    <table class="table">
        <thead>
            <tr>
                <th>Ãû³Æ</th>
                <th>Öµ</th>
            </tr>
        </thead>
        <tbody>
			<#list values?keys as key>
                <tr>
                    <td>${ key }</td>
                    <td>${ values[key] }</td>
                </tr>
            </#list>
        </tbody>
    </table>
</@override>
<@extends name="../ipmi.ftl"/>