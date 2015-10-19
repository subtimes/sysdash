<@override name="process_body">
    <table class="table">
        <thead>
            <tr>
                <th>path</th>
                <th>rss</th>
                <th>size</th>
                <th>resident</th>
                <th>shared</th>
                <th>MinorFaults</th>
                <th>MajorFaults</th>
                <th>PageFaults</th>
            </tr>
        </thead>
        <tbody>
            {% for m in memory_maps|sort(reverse=True, attribute="size") %}
			<#list memorys as m>
                <tr>
                    <td>{{ m.path }}</td>
                    <td>{{ m.rss }}</td>
                    <td>{{ m.size }}</td>
                    <td>{{ m.pss }}</td>
                    <td>{{ m.shared_clean }}</td>
                    <td>{{ m.shared_dirty }}</td>
                    <td>{{ m.private_clean }}</td>
                    <td>{{ m.private_dirty }}</td>
                    <td>{{ m.referenced }}</td>
                    <td>{{ m.anonymous }}</td>
                    <td>{{ m.swap }}</td>
                </tr>
            {% endfor %}
        </tbody>
    </table>
</@override>
<@extends name="../process.ftl"/>