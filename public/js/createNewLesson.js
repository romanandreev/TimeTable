function createNewLesson() {

    $.get("/newlesson").done(function(result) {
        var oldContent = $("#newlesson").replaceWith(result);

        var restore = function() {
            $("#newlessonEditor").replaceWith(oldContent);
            $("#newlesson").one("click", createNewLesson);
        };

        $("#newlessonEditor").find(".DeleteButton").click(restore);

        $(".newlessonfield").editable(function(value, settings) { return value; },
                   { style: 'display: inline-block', 
                     data: function(str) { return $.trim(str); },
                     placeholder: ''
                   });

        REDIPS.drag.enable_drag(true, "newlessondiv");
        REDIPS.drag.myhandler_dropped = function(target_cell) {
            if ($(REDIPS.drag.obj).attr('id') == 'newlessondiv' && $(target_cell).attr('id') != 'newlessonEditor') {
                restore();
                $(REDIPS.drag.obj).find(".DeleteButton").unbind('click').click(function() {
                    $(this).parents(".drag").remove();
                });
            }
        };

    });
}
