require 'sinatra'
require 'haml'

require_relative 'lib/statmod.rb'

statmod = Statmod.new
statmod.loadSpecData(File.dirname(__FILE__) + '/data/_courses/sm_spec.xml')
statmod.loadSpecData(File.dirname(__FILE__) + '/data/_courses/mm_spec.xml')
statmod.loadSpecData(File.dirname(__FILE__) + '/data/_courses/sa_spec.xml')
statmod.loadStaffData(File.dirname(__FILE__) + '/data/_staff/prof.xml')

get '/' do
  @timetable = statmod.getTimeTable(File.dirname(__FILE__) + '/data/_courses/3course.xml')
  haml :timetable
end
