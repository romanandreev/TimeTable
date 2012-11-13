class Person
  include HappyMapper

  tag 'person'

  has_one :first_name, String, :tag => 'fn'
  has_one :middle_name, String, :tag => 'mn'
  has_one :last_name, String, :tag => 'ln'

  attribute :id, String

  def name
    result = last_name
    if first_name != '' and middle_name != ''
      result += ' ' + first_name[0] + '. ' + middle_name[0] + '.'
    end
    result
  end
end
