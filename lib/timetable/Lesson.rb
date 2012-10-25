class Lesson
  include HappyMapper

  attribute :type, String
  attribute :spec, String
  has_one :location, String
  has_one :course, Course
end
