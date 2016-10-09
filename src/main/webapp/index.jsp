<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% response.setCharacterEncoding("UTF-8"); %>

<div class="main">
    <form name="login-form" class="login-form" action="/logging?option=login" method="post">
        <div class="login_header">
            <h1>АВТОРИЗАЦИЯ</h1>
        </div>
        <div class="content">
            <input name="email" type="text" class="input username" value="EMAIL" onfocus="this.value=''" />
            <input name="password" type="password" class="input password" value="Пароль" onfocus="this.value=''" />
        </div>
        <div class="footer_form">
            <a class="register" href="/registration">Регистрация</a>
            <input type="submit" name="submit" value="ВОЙТИ" class="button"/>
        </div>
    </form>
</div>
