{{#each data.rows}}
<div style="height: 250px; width: 230px; margin: 5px 5px;float: left">
	{{#equal uploadType '1'}}
    <a href="/book/bookDetail.html?uuid={{uuid}}"><img src="{{../../storage_url}}/{{image}}" style="height: 150px; width:140px"></a>
    {{/equal}}
    {{#equal uploadType '0'}}
    <a href="/book/bookDetail.html?uuid={{uuid}}"><img src="{{image}}" style="height: 150px; width:140px"></a>
    {{/equal}}
    <li style="list-style: none">书名:{{title}}</li>
    <li style="list-style: none">作者:{{author}}</li>
</div>
{{/each}}