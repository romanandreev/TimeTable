# encoding: UTF-8

require 'bundler/setup'

require 'sinatra'
require 'haml'
require 'cgi'
require 'set'

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

TABLE_DIR = File.dirname(__FILE__) + '/data/tables/'

timetable3 = statmod.getTimeTable(TABLE_DIR + '3course.xml')
timetable4 = statmod.getTimeTable(TABLE_DIR + '4course.xml')
timetable5 = statmod.getTimeTable(TABLE_DIR + '5course.xml')

newlesson = Lesson.new
course = Course.new
course.prof = 'Преподаватель'
course.name = 'Название предмета'
newlesson.fortnightly = nil
newlesson.course = course
newlesson.location = 'Аудитория'

courselist = statmod.getAllCourses.map{|k, v| {k => v.values.map(&:name)}}.reduce(:merge)
[timetable3, timetable4, timetable5].each do |timetable|
  courselist[timetable.semester] += timetable.courseNames
  courselist[timetable.semester] << ''
  courselist[timetable.semester].sort!
  courselist[timetable.semester].uniq!
end

stafflist = statmod.getAllStaff

require 'json'
@@courselist_json = courselist.to_json
@@stafflist_json = stafflist.persons.map(&:name).to_json

Dir.chdir TABLE_DIR do
  @@filenames = Set.new(Dir["*.xml"])
end

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
  @timetable = statmod.getTimeTable(File.dirname(__FILE__) + "/data/tables/#{CGI::unescape fn}") 
  if @timetable.semester.nil?
    @course = ''
  else
    @course = (@timetable.semester + 1) / 2
  end
  haml :timetable
end

get '/' do
  redirect '/3course'
end

get '/new' do
  @timetable = statmod.getTimeTable(File.dirname(__FILE__) + '/immutable_data/tables/empty.xml')
  @course = '?'
  haml :timetable
end

post '/save' do
  xml = params[:xmldata] 
  fn = params[:filename]
  File.write(TABLE_DIR + fn, xml)
  @@filenames << fn
  redirect "/edit/#{CGI::escape fn}"
end

post '/download' do
  content_type 'application/octet-stream'
  attachment params[:filename]
  params[:xmldata]
end

get '/newlesson' do
  @newlessonresponse ||= haml :newlesson, :locals => { :lesson => newlesson }
end

post '/loadfile' do
  redirect "/edit/#{CGI::escape params['filename']}"
end
