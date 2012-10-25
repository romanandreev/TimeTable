require 'sinatra'
require_relative 'Renderer.rb'

renderer = Renderer.new(File.dirname(__FILE__) + '/../templates/timetable.haml')
renderer.loadSpecData(File.dirname(__FILE__) + '/../data/_courses/sm_spec.xml')
renderer.loadSpecData(File.dirname(__FILE__) + '/../data/_courses/mm_spec.xml')
renderer.loadSpecData(File.dirname(__FILE__) + '/../data/_courses/sa_spec.xml')
renderer.loadStaffData(File.dirname(__FILE__) + '/../data/_staff/prof.xml')

get '/' do
  renderer.table (File.dirname(__FILE__) + '/../data/_courses/3course.xml')
end
