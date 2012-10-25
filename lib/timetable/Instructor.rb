class Instructor
  include HappyMapper

  tag 'prof'

  namespace 'http://statmod.ru/courses'
  attribute :id, String
end
