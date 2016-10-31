<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setCharacterEncoding("UTF-8");
%>

<div class="main">
    <form name="login-form" class="login-form" action="<%=request.getContextPath()%>/logging?option=login" method="post">
        <div class="login_header">
            <h1>АВТОРИЗАЦИЯ</h1>
        </div>
        <div class="content">
            <input name="email" type="text" class="input username" value="EMAIL" onfocus="this.value=''" />
            <input name="password" type="password" class="input password" value="Пароль" onfocus="this.value=''" />
        </div>
        <div class="footer_form">
            <a class="register" href="<%=request.getContextPath()%>/registration">Регистрация</a>
            <input type="submit" name="submit" value="ВОЙТИ" class="button"/>
        </div>
    </form>
    <div class="info">
        <p><b>Вас приветсвует сайт-соцсеть NISOC!</b></p>
        <p class="info_text">После регистрации и входа вы получите доступ к:</p>
        <p class="info_text"> - Сообщениям и переписке</p>
        <p class="info_text"> - Списку пользователей и просмотру страниц</p>
        <p class="info_text"> - Отправке сообщений с помощью обратной связи</p>
        <p class="info_text" class="info_text">Использованы технологии: <b>Servelets, JSP, SQL, JDBC, Maven, HTML, CSS, HTTP, JavaScript, AJAX, Хостинг: <a href="https://www.heroku.com/">heroku.com</a>, БД: JawsDB MySQL.</b></p>
        <p class="info_text">Исходный код: <a href="https://github.com/kusmen21/nisoc">github.com/kusmen21/nisoc</a>
        <p>Не хотите регистрироваться? Войдите под Тестовым Аккаунтом!</p>
        <a class="user_button" href="<%=request.getContextPath()%>/logging?option=login&email=test&password=test">Войти без регистрации!</a>
    </div>
</div>
