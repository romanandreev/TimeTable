# encoding: UTF-8

require 'sinatra'
require 'haml'

set :environment, :production

before do
  content_type :html, 'charset' => 'utf-8'
end

require_relative 'lib/statmod.rb'

statmod = Statmod.new
statmod.loadSpecData(File.dirname(__FILE__) + '/data/_courses/sm_spec.xml')
statmod.loadSpecData(File.dirname(__FILE__) + '/data/_courses/mm_spec.xml')
statmod.loadSpecData(File.dirname(__FILE__) + '/data/_courses/sa_spec.xml')
statmod.loadStaffData(File.dirname(__FILE__) + '/data/_staff/prof.xml')

timetable3 = statmod.getTimeTable(File.dirname(__FILE__) + '/data/_courses/3course.xml')
timetable4 = statmod.getTimeTable(File.dirname(__FILE__) + '/data/_courses/4course.xml')
timetable5 = statmod.getTimeTable(File.dirname(__FILE__) + '/data/_courses/5course.xml')

newlesson = Lesson.new
course = Course.new
course.prof = 'Преподаватель'
course.name = 'Название предмета'
newlesson.fortnightly = nil
newlesson.course = course
newlesson.location = 'Аудитория'

get '/3course' do
  @filename = '3course.xml'
  @timetable = timetable3
  @course = 3
  haml :timetable
end

get '/4course' do
  @filename = '4course.xml'
  @timetable = timetable4
  @course = 4
  haml :timetable
end

get '/5course' do
  @filename = '5course.xml'
  @timetable = timetable5
  @course = 5
  haml :timetable
end

get '/edit/:filename' do |fn|
  @filename = fn
  @timetable = statmod.getTimeTable(File.dirname(__FILE__) + "/data/_courses/#{fn}") 
  @course = (@timetable.semester + 1) / 2;
  haml :timetable
end

get '/' do
  redirect '/3course'
end

post '/save' do
  content_type 'application/octet-stream'
  attachment params[:filename]
  params[:xmldata]
end

get '/newlesson' do
  @newlessonresponse ||= haml :newlesson, :locals => { :lesson => newlesson }
end
