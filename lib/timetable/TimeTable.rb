class TimeTable
    include HappyMapper

    attribute :year, Integer
    attribute :semester, Integer

    has_many :weekday_tables, WeekdayTable, :tag => 'weekday'

    def updateCourseInfo(courses)
      weekday_tables.each do |day|
        day.double_classes.each do |dc|
          dc.lessons.each do |lesson|
            if lesson.course.name.nil? and not lesson.course.id.nil?
              course_info = courses[lesson.course.id]
              unless course_info.nil? or course_info.instructor.nil?
                lesson.course.name = course_info.name
                lesson.course.prof = course_info.instructor.last_name
              end
            end
          end
        end
      end
    end
end