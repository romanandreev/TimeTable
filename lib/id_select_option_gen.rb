# arr -- list of course ids
# info -- map: id -> CourseInfo object
def optionsToHtml(arr, descriptions)
  arr.map {|elem| "<option value='#{elem}'>#{elem} (#{descriptions[elem].name})</option>"}
     .join
end
