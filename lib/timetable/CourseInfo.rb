class CourseInfo
  include HappyMapper

  tag 'course'

  attribute :alias, String
  attribute :semester, Integer
  attribute :year, Integer

  has_one :name, String
  has_one :instructor, Instructor, :tag => 'prof'
end
