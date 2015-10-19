<@override name="process_body">
    <table class="table">
        <thead>
            <tr>
                <th>ÎÄ¼þÂ·¾¶</th>
            </tr>
        </thead>
        <tbody>
            <#list files as f>
                <tr>
                    <td>${ f }</td>
                </tr>
            </#list>
        </tbody>
    </table>
</@override>
<@extends name="../process.ftl"/>