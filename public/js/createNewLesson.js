function createNewLesson() {
    $.get("/newlesson").done(function(result) {
        var cell = $("#newlesson");

        var oldContent = cell.replaceWith(result);

        $("#newlessonEditor").find(".DeleteButton").click(function() {
            $("#newlessonEditor").replaceWith(oldContent);
        });

        $(".newlessonfield").editable(function(value, settings) { return value; },
                   { style: 'display: inline-block', 
                     data: function(str) { return $.trim(str); },
                     placeholder: ''
                   });

        REDIPS.drag.enable_drag(true, "newlessondiv");
        REDIPS.drag.myhandler_dropped = function(target_cell) {
            if ($(REDIPS.drag.obj).attr('id') == 'newlessondiv' && $(target_cell).attr('id') != 'newlessonEditor') {
                $("#newlessonEditor").replaceWith(oldContent);
                $(REDIPS.drag.obj).find(".DeleteButton").unbind('click').click(function() {
                    $(this).parents(".drag").remove();
                });
            }
        };

    });
}
