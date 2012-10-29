function createNewLesson() {
    var cell = $("#newlesson");
    var divCourseName = '<div class="newlessonfield name" align="center" style="height:18px">/название предмета/</div>';
    var divInstructorName = '<div class="newlessonfield prof" align="center" style="height:18px">/преподаватель/</div>';
    var divLocation = '<div class="newlessonfield loc" align="center" style="height:18px">/аудитория/</div>';
    var tdLeft = '<td class="mark">' + divCourseName + divInstructorName + divLocation + '</td>';
    var numCheckbox = '<input type="checkbox" checked="checked" />';
    var hr = '<hr size="1" />';
    var denomCheckbox = '<input type="checkbox" checked="checked" />';
    var tdRight = '<td style="width:20px; padding-right: 2px" class="mark">' + numCheckbox + hr + denomCheckbox + '</td>';
    var table = '<table height="100%" width="100%"><tbody><tr>' + tdLeft + tdRight + '</tr></tbody></table>';
    var divNewLesson = '<div class="drag" id="newlessondiv">' + table + '</div>';
    var tdNewLesson = '<td id="newlessonEditor" class="lesson" style="height:64px">' + divNewLesson + '</td>'; 
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
