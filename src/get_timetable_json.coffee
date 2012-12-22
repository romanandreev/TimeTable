window.getTimeTableJson = ->
    getAttr = (lesson, attr) -> $(lesson.cell).find(attr).text().trim()

    getRowJson = (row) ->

        row = $(row)
        top = row.hasClass('top')
            
        cols = row.find 'td.lesson'
        all_lessons = []
        if cols.length == 1
            all_lessons = [{cell: cols[0], spec: 'all'}]
        else
            all_lessons = [{cell: cols[0], spec: 'sa'}, 
                           {cell: cols[1], spec: 'sm'}, 
                           {cell: cols[2], spec: 'mm'}]

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

            if not top
                json.fortnightly = 2
            else if rowspan == 1
                json.fortnightly = 1
  
            json

        (getLessonJson l for l in all_lessons when $(l.cell).text().trim().length > 0)

    getWeekday = (weekday) -> 
        rows = {}
        top_rows = $(".#{weekday}.top")
        btm_rows = $(".#{weekday}.btm")

        getLessons = (index) ->
            arr1 = getRowJson top_rows[index]
            arr2 = getRowJson btm_rows[index]
            arr1.concat arr2

        (getLessons i for i in [0 .. 4])

    weekdays = {}
    for wd in ['mon', 'tue', 'wed', 'thu', 'fri', 'sat']
        weekdays[wd] = getWeekday wd

    weekdays: weekdays
    year:     $("#year")[0].value
    semester: $("#sem")[0].value
