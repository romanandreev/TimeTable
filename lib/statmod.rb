require_relative './timetable.rb'

class Statmod

    def loadSpecData(filename)
      spec = CourseList.parse(File.read(filename))
      @courses ||= {}
      spec.courses.each do |course|
        @courses[course.alias] = course
      end
    end

    def loadStaffData(filename)
      @staff = Staff.parse(File.read(filename))
      @courses.each do |id, course|
        next if course.instructor.nil?
        id = course.instructor.id 
        next if @staff[id].nil?
        course.instructor = @staff[id]
      end
    end

    def getTimeTable(filename)
      timetable = TimeTable.parse(File.read(filename))
      timetable.updateCourseInfo(@courses)
      timetable
    end
end
