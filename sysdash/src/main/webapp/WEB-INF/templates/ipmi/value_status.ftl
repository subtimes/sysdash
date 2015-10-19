<@override name="ipmi_body">
    <table class="table">
        <thead>
            <tr>
                <th>Ãû³Æ</th>
                <th>Öµ</th>
                <th>×´Ì¬</th>
            </tr>
        </thead>
        <tbody>
			<#list values as s>
                <tr>
                    <td>${ s[0] }</td>
                    <td>${ s[1] }</td>
                    <td>${ s[2] }</td>
                </tr>
            </#list>
        </tbody>
    </table>
</@override>
<@extends name="../ipmi.ftl"/>