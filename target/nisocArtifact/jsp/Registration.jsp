<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="main">
    <form name="register_form" class="register_form" action="/nisocArtifact/logging?option=registration" method="post">

        <h1>РЕГИСТРАЦИЯ</h1>

        <div class="content">
            <div>
                <div class="entry">
                    <input name="fname" type="text" class="input" value="Имя" onfocus="this.value=''" />
                </div>
                <div class="entry">
                    Введите ваше имя
                </div>
            </div>
            <div>
                <div class="entry">
                    <input name="email" type="text" class="input" value="EMAIL" onfocus="this.value=''" />
                </div>
                <div class="entry">
                    Введите ваш уникальный EMAIL
                </div>
            </div>
            <div>
                <div class="entry">
                    <input name="password" type="text" class="input" value="Пароль" onfocus="this.value=''" />
                </div>
                <div class="entry">
                    Введите ваш пароль
                </div>
            </div>
            <p>*дополнительные данные вы сможете указать после регистрации </p>
        </div>
        <div class="footer_form">
            <input type="submit" name="submit" value="ЗАРЕГИСТРИРОВАТЬСЯ" class="button"/>
        </div>
    </form>
</div>