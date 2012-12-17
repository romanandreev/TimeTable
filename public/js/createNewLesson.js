function createNewLesson() {

    $.get("/newlesson").done(function(result) {
        var oldContent = $("#newlesson").replaceWith(result);

        var restore = function() {
            $("#newlessonEditor").replaceWith(oldContent);
            $("#newlesson").one("click", createNewLesson);
        };

        $("#newlessonEditor").find(".DeleteButton").click(restore);

        makeEditable('newlessonfield');

        REDIPS.drag.enableDrag(true, "#newlessondiv");
        REDIPS.drag.event.dropped = function(target_cell) {
            if ($(REDIPS.drag.obj).attr('id') == 'newlessondiv' && $(target_cell).attr('id') != 'newlessonEditor') {
                restore();
                $(REDIPS.drag.obj).find(".DeleteButton").unbind('click').click(function() {
                    $(this).parents(".drag").remove();
                });
            }
        };

    });
}
