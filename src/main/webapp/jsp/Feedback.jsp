<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
%>

<div class="main">
    <form name="login-form" class="login-form">
        <div class="login_header">
            <h1>ОБРАТНАЯ СВЯЗЬ</h1>
        </div>
        <div class="content">
            <input name="text" type="text" id="text" value="Введите текст"/>
            <br>
            <span id="result1"></span>
        </div>
        <div class="footer_form">
            <input type="button" value="ОТПРАВИТЬ" id="btt"/>
        </div>
    </form>
</div>
