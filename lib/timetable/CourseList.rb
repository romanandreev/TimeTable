class CourseList
  include HappyMapper

  tag 'courses'

  has_many :courses, CourseInfo, :tag => 'course'
end
