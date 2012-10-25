class DoubleClass
  include HappyMapper

  tag 'two_hours'

  attribute :index, Integer, :tag => 'no'
  has_many :lessons, Lesson, :tag => 'lesson'

  def getLessonForSpec(spec)
    @spec_to_lesson ||= Hash[*(lessons.map{|lesson| [lesson.spec, lesson]}.flatten)]
    @spec_to_lesson[spec]
  end

end
