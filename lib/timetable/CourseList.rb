class CourseList
  include HappyMapper

  tag 'courses'
  namespace 'http://statmod.ru/courses'

  has_many :courses, CourseInfo, :tag => 'course'
end
