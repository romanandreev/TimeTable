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
  redirect '/3course'
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

post '/loadfile' do
  redirect "/edit/#{CGI::escape params['filename']}"
end
