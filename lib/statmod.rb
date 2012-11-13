require_relative './timetable.rb'

# The class responsible for gluing together information from different XML files
class Statmod

  # parse list of courses for a particular specialty
  def loadSpecData(filename)
    spec = CourseList.parse(File.read(filename))
    @courses ||= {}
    spec.courses.each do |course|
      @courses[course.semester] ||= {}
      @courses[course.semester][course.alias] = course
    end
  end

  # parse staff data, resolving current course instructor ids
  # to person information
  def loadStaffData(filename)
    @staff = Staff.parse(File.read(filename))
    @courses.each do |semester, courses|
      courses.each do |id, course|
        next if course.instructor.nil?
        id = course.instructor.id 
        next if @staff[id].nil?
        course.instructor = @staff[id]
      end
    end
  end

  # parse time table from a given file, resolving course aliases
  # to course information
  def getTimeTable(filename)
    timetable = TimeTable.parse(File.read(filename))
    timetable.updateCourseInfo(@courses[timetable.semester])
    timetable
  end

  # map: semester -> alias -> course information
  def getAllCourses
    @courses
  end

  # map: id -> person information
  def getAllStaff
    @staff
  end
end
