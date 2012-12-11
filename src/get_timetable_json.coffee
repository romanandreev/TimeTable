window.getTimeTableJson = ->
    getAttr = (lesson, attr) -> $(lesson.cell).find(attr).text().trim()

    getLessonJson = (lesson) -> 

        json = 
            course:
                name: getAttr lesson, '.name'
                prof: getAttr lesson, '.prof'
            location: getAttr lesson, '.loc'

        id = $(lesson.cell).find('.lessonid')[0]
        if id?
          # still send prof and name to facilitate server job
          json.course.id = $(id).attr('value')

        checkboxes = $(lesson.cell).find "input[type=checkbox]"
        fortnightly = 0
        if $(checkboxes[0]).attr('checked') == 'checked'
            fortnightly += 1
        if $(checkboxes[1]).attr('checked') == 'checked'
            fortnightly += 2

        if 1 <= fortnightly <= 2
            json.course.fortnightly = fortnightly 

        spec:   lesson.spec
        lesson: json
 
    getRowJson = (row) ->
        cols = $(row).find 'td.lesson'
        all_lessons = []
        if cols.length == 1
            all_lessons = [{cell: cols[0], spec: 'all'}]
        else
            all_lessons = [{cell: cols[0], spec: 'sm'}, 
                           {cell: cols[1], spec: 'mm'}, 
                           {cell: cols[2], spec: 'sa'}]

        lessons = (l for l in all_lessons when $(l.cell).text().trim().length > 0)
        return null if lessons.length == 0

        result = 
            no: $(row).find("th").text().trim()
            lessons: {}
        for json in (getLessonJson lesson for lesson in lessons)
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
