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

@@specs = ['sa', 'sm', 'mm']
@@specnames = ['САПР', 'СМ', 'ММ']

def rowSpan(l)
  if l.nil? or not l.fortnightly.nil? then 1 else 2 end
end

require_relative 'lib/statmod.rb'
require_relative 'lib/id_select_option_gen.rb'

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

all_courses = statmod.getAllCourses

# map: semester -> course ids
courseidlist = all_courses.map{|k, v| {k => v.keys}}.reduce(:merge)

# map: semester -> course names
courselist = statmod.getAllCourses.map{|k, v| {k => v.values.map(&:name)}}.reduce(:merge)
[timetable3, timetable4, timetable5].each do |timetable|
  courselist[timetable.semester] += timetable.courseNames
  courselist[timetable.semester] << ''
  courselist[timetable.semester].sort!
  courselist[timetable.semester].uniq!
end

stafflist = statmod.getAllStaff

require 'json'
@@courseidlist_json = courseidlist.map{|k, v| {k => optionsToHtml(v, all_courses[k])}}.reduce(:merge).to_json
@@courselist_json = courselist.to_json
@@stafflist_json = stafflist.persons.map(&:name).to_json

Dir.chdir TABLE_DIR do
  @@filenames = Set.new(Dir["*.xml"])
end

get '/3course' do
  @filename = '3course.xml'
  @timetable = timetable3
  haml :timetable
end

get '/4course' do
  @filename = '4course.xml'
  @timetable = timetable4
  haml :timetable
end

get '/5course' do
  @filename = '5course.xml'
  @timetable = timetable5
  haml :timetable
end

get '/edit/:filename' do |fn|
  @filename = fn
  @timetable = statmod.getTimeTable(File.dirname(__FILE__) + "/data/tables/#{CGI::unescape fn}") 
  haml :timetable
end

get '/' do
  redirect "/#{3 + (rand 3)}course"
end

get '/new' do
  @timetable = statmod.getTimeTable(File.dirname(__FILE__) + '/immutable_data/tables/empty.xml')
  haml :timetable
end

post '/renderjson' do
  json = params[:jsondata]
  if json then
    begin
      @timetable = statmod.jsonToTimetable(json)
    rescue
      return 'invalid JSON'
    end
    haml :timetable
  end
end

post '/save' do
  json = JSON.parse params[:jsondata] 
  begin
    timetable = statmod.jsonToTimetable json
    fn = params[:filename]
    builder = Nokogiri::XML::Builder.new(:encoding => 'UTF-8')
    pi = Nokogiri::XML::ProcessingInstruction.new(builder.doc, 
                                                 'xml-stylesheet',
                                                 'type="text/xsl" href="timetable.xsl"')
    timetable.to_xml(builder)
    builder.doc.root.add_previous_sibling pi
    File.write(TABLE_DIR + fn, builder.to_xml(:indent => 4))
    @@filenames << fn
    redirect "/edit/#{CGI::escape fn}"
  rescue
    "Ошибка сохранения XML-файла"
  end
end

post '/download' do
  content_type 'application/octet-stream'
  attachment params[:filename]
  params[:xmldata]
end

get '/newlesson' do
  @newlessonresponse ||= haml :newlesson, :locals => { :lesson => newlesson }
end

get '/newlesson/:semester/:courseid' do |semester, id|
  lesson = Lesson.new
  lesson.course = Course.new
  lesson.course.id = id
  courseinfo = statmod.getCourseInfo semester, id
  unless course.nil?
    lesson.course.name = courseinfo.name
    lesson.course.prof = courseinfo.instructor.name
  end
  @newlessonresponse ||= haml :newlesson, :locals => { :lesson => lesson }
end

post '/loadfile' do
  redirect "/edit/#{CGI::escape params['filename']}"
end
