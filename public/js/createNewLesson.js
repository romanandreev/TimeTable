function createNewLesson() {
    var cell = $("#newlesson");
    var divCourseName = '<div class="newlessonfield" align="center" style="height:18px">/название предмета/</div>';
    var divInstructorName = '<div class="newlessonfield" align="center" style="height:18px">/преподаватель/</div>';
    var divLocation = '<div class="newlessonfield" align="center" style="height:18px">/аудитория/</div>';
    var divNewLesson = '<div class="drag" id="newlessondiv">' + divCourseName + divInstructorName + divLocation + '</div>';
    var tdNewLesson = '<td id="newlessonEditor" style="height:64px">' + divNewLesson + '</td>'; 
    var oldContent = cell.replaceWith(tdNewLesson);

    $(".newlessonfield").editable(function(value, settings) { return value; },
               { style: 'display: inline-block', 
                 data: function(str) { return $.trim(str); },
                 placeholder: ''
               });

    REDIPS.drag.enable_drag(true, "newlessondiv");
    REDIPS.drag.myhandler_dropped = function(target_cell) {
        if ($(REDIPS.drag.obj).attr('id') == 'newlessondiv' && $(target_cell).attr('id') != 'newlessonEditor') {
            $("#newlessonEditor").replaceWith(oldContent);
        }
    };
}
