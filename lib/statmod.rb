require_relative './timetable.rb'

require 'json'

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

  def jsonToTimetable(hash)
    json = hash
    timetable = TimeTable.new
    timetable.year = json['year'].to_i
    timetable.semester = json['semester'].to_i
    timetable.weekday_tables = []
    
    json['weekdays'].each do |id, classes|
      wd_table = WeekdayTable.new
      wd_table.id = id
      wd_table.double_classes = []
      classes.each do |index, lessons|
        double_class = DoubleClass.new
        double_class.index = index.to_i
        double_class.lessons = []
        lessons.each do |spec, lesson_json|
          lesson = Lesson.new
          lesson.spec = spec
          lesson.location = lesson_json['location']
          lesson.fortnightly = lesson_json['fortnightly']
          lesson.course = Course.new
          lesson.course.name = lesson_json['course']['name']
          lesson.course.prof = lesson_json['course']['prof']
          lesson.course.id = lesson_json['course']['id']
          double_class.lessons << lesson
        end
        wd_table.double_classes << double_class
      end
      timetable.weekday_tables << wd_table
    end
    timetable
  end

  def getCourseInfo(semester, id)
    semester = semester.to_i
    if @courses[semester].nil?
      nil
    else
      @courses[semester][id]
    end
  end
end
