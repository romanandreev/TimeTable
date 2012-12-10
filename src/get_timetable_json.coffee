window.getTimeTableJson = ->
    getAttr = (lesson, attr) -> lesson.row.find(attr).text().trim()

    getLessonJson = (lesson) -> 
        json = 
            course:
                name: getAttr lesson, '.name'
                prof: getAttr lesson, '.prof'
            location: getAttr lesson, '.loc'

        checkboxes = lesson.row.find "input[type=checkbox]"
        fortnightly = 0;
        if $(checkboxes[0]).attr('checked') == 'checked'
            fortnightly += 1;
        if $(checkboxes[1]).attr('checked') == 'checked'
            fortnightly += 2;

        if 1 <= fortnightly <= 2
            json.course.fortnightly = fortnightly 

        spec:   lesson.spec
        lesson: json
 
    getRowJson = (row) ->
        cols = ($(c) for c in $(row).find 'td.lesson')
        all_lessons = []
        if cols.length == 1
            all_lessons = [{row: cols[0], spec: 'all'}]
        else
            all_lessons = [{row: cols[0], spec: 'sm'}, 
                           {row: cols[1], spec: 'mm'}, 
                           {row: cols[2], spec: 'sa'}]

        lessons = (l for l in all_lessons when $(l.row).text().trim().length > 0)
        return null if lessons.length == 0

        result = 
            no: $(row).find("th").text().trim()
            lessons: {}
        for json in (getLessonJson(lesson) for lesson in lessons)
            result.lessons[json.spec] = json.lesson
       
        result

    getWeekdayJson = (weekday) -> 
        rows = {}
        for json in (getRowJson row for row in $(".#{weekday}"))
            rows[json.no] = json.lessons if json?
        rows

    weekdays = {}
    for wd in ['mon', 'tue', 'wed', 'thu', 'fri', 'sat']
        weekdays[wd] = getWeekdayJson wd

    weekdays: weekdays
    year:     $("#year")[0].value
    semester: $("#sem")[0].value
