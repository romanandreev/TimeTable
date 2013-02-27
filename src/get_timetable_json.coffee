window.getTimeTableJson = ->
    getAttr = (lesson, attr) -> $(lesson.cell).find(attr).text().trim()

    getRowJson = (top, bottom) ->

        top_cells = $(top).find 'td'
        all_lessons = []
        if top_cells.length == 1
            all_lessons = [{cell: top_cells[0], spec: 'all'}]
        else
            all_lessons = [{cell: top_cells[0], spec: 'sa'}, 
                           {cell: top_cells[1], spec: 'sm'}, 
                           {cell: top_cells[2], spec: 'mm'}]

        for i in [0 ... all_lessons.length]
            lesson = all_lessons[i]
            if $(top_cells[i]).attr('rowspan') != '2'
                lesson.fortnightly = 1
                all_lessons[i] = lesson
        btm_cells = $(bottom).find 'td'

        if btm_cells.length > 0
            if btm_cells.length == 1 and $(btm_cells[0]).attr('colspan') == '3'
                all_lessons.push {cell: btm_cells[0], spec: 'all', fortnightly: 2}
            else
                specs = ['sa', 'sm', 'mm']
                j = 0
                for i in [0 .. 2]
                    if $(top_cells[i]).attr('rowspan') != '2'
                        all_lessons.push {cell: btm_cells[j], spec: specs[i], fortnightly: 2}
                        j += 1

        getLessonJson = (lesson) -> 

            json = 
                spec:     lesson.spec
                location: getAttr lesson, '.loc'
                course:   {}

            cell = $(lesson.cell)

            id = cell.find('.lessonid')[0]
            if id?
              json.course.id = $(id).attr('value')
            else
              json.course.name = getAttr lesson, '.name'
              json.course.prof = getAttr lesson, '.prof'

            rowspan = parseInt(cell.attr('rowspan'))

            if lesson.fortnightly
                json.fortnightly = lesson.fortnightly
  
            json

        (getLessonJson l for l in all_lessons when $(l.cell).text().trim().length > 0)

    getWeekday = (weekday) -> 
        rows = {}
        top_rows = $(".#{weekday}.top")
        btm_rows = $(".#{weekday}.btm")

        getLessons = (index) ->
            getRowJson top_rows[index], btm_rows[index]

        (getLessons i for i in [0 .. 4])

    weekdays = {}
    for wd in ['mon', 'tue', 'wed', 'thu', 'fri', 'sat']
        weekdays[wd] = getWeekday wd

    weekdays: weekdays
    year:     $("#year")[0].value
    semester: $("#sem")[0].value
