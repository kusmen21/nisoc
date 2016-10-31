$(document).ready(function(){
    $('#btt').click(function(){
        // собираем данные с формы
        //var id = $('#id').val();
        var text  = $('#text').val();
        // отправляем данные
        $.ajax({
            url: "feedback", // куда отправляем
            type: "post", // метод передачи
            //dataType: "json", // тип передачи данных
            data: { // что отправляем
                "idReceiver": 5,
                "text": text
            },
            success: function(result) {
                $('#result1').html(result);
            }
        });
    });
});