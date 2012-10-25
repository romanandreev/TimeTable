class Staff
  include HappyMapper

  namespace 'http://statmod.ru/staff'
  tag 'staff'

  has_many :persons, Person, :tag => 'person'

  def [](id)
    if @staff.nil?
      @staff = {}
      persons.each do |person|
        @staff[person.id] = person
      end
    end
    @staff[id]
  end
end
