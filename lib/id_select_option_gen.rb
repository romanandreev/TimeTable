def optionsToHtml(arr)
  arr.map {|elem| "<option value='#{elem}'>#{elem}</option>"}
     .join
end
