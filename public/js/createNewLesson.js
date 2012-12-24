function replaceNewLessonTdWith(ajax_request) {
    ajax_request.done(function(result) {
        var oldContent = $("#newlesson").replaceWith(result);

        var restore = function() {
            $("#newlessonEditor").replaceWith(oldContent);
            $("#newlesson").one("click", createNewLesson);
            $("#courseIds").prop('disabled', false);
        };

        $("#newlessonEditor").find(".DeleteButton").click(restore);
        $("#newlessonEditor").find('.courseIdSelect').prop('disabled', 'disabled');

        makeEditable('newlessonfield');

        REDIPS.drag.enableDrag(true, "#newlessondiv");
        REDIPS.drag.event.dropped = function(target_cell) {
            if ($(REDIPS.drag.obj).attr('id') == 'newlessondiv' && $(target_cell).attr('id') != 'newlessonEditor') {

                updateCourseIdentifiers();
                $("#newlessondiv").find('.courseIdSelect').prop('disabled', false);
                $("#newlessondiv").removeAttr('id');

                restore();
                $(REDIPS.drag.obj).find(".DeleteButton").unbind('click').click(function() {
                    $(this).parents(".drag").remove();
                });
            }
        };
    });
}

function createNewLesson() {
    replaceNewLessonTdWith($.get("/newlesson"));
}

function createNewLessonFromId() {
    var id = $("#courseIds").val();
    var semester = $("#sem").attr('value');

    replaceNewLessonTdWith($.get("/newlesson/" + semester + "/" + id));

    $('#courseIds').prop('selectedIndex', -1);
    $("#courseIds").prop('disabled', 'disabled');
}

function replaceLessonTdWith(td, ajax_request) {
    ajax_request.done(function(result) {
        $(td).replaceWith(result);

        var drag_div = $(td).parents(".drag");

        $("input.DeleteButton").click(function() {
            $(this).parents(".drag").remove();
        });

        REDIPS.drag.enableDrag(true, drag_div[0]);
        makeEditable('editable');
        updateCourseIdentifiers();
    });
}

function replaceIdLesson(select) {
    
    var id = $(select).val();
    var semester = $("#sem").attr('value');
    var td = $(select).parents('td');
    var rs = td.attr('rowspan');
    var loc = td.find('.loc').text().trim();

    var url = "/replacelesson/" + semester + "/" + id + "?location=" + loc + "&rowspan=" + rs;
    replaceLessonTdWith(td, $.get(url));
}
