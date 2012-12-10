// Generated by CoffeeScript 1.4.0
(function() {

  window.getTimeTableJson = function() {
    var getAttr, getLessonJson, getRowJson, getWeekdayJson, wd, weekdays, _i, _len, _ref;
    getAttr = function(lesson, attr) {
      return lesson.row.find(attr).text().trim();
    };
    getLessonJson = function(lesson) {
      var checkboxes, fortnightly, json;
      json = {
        course: {
          name: getAttr(lesson, '.name'),
          prof: getAttr(lesson, '.prof')
        },
        location: getAttr(lesson, '.loc')
      };
      checkboxes = lesson.row.find("input[type=checkbox]");
      fortnightly = 0;
      if ($(checkboxes[0]).attr('checked') === 'checked') {
        fortnightly += 1;
      }
      if ($(checkboxes[1]).attr('checked') === 'checked') {
        fortnightly += 2;
      }
      if ((1 <= fortnightly && fortnightly <= 2)) {
        json.course.fortnightly = fortnightly;
      }
      return {
        spec: lesson.spec,
        lesson: json
      };
    };
    getRowJson = function(row) {
      var all_lessons, c, cols, json, l, lesson, lessons, result, _i, _len, _ref;
      cols = (function() {
        var _i, _len, _ref, _results;
        _ref = $(row).find('td.lesson');
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          c = _ref[_i];
          _results.push($(c));
        }
        return _results;
      })();
      all_lessons = [];
      if (cols.length === 1) {
        all_lessons = [
          {
            row: cols[0],
            spec: 'all'
          }
        ];
      } else {
        all_lessons = [
          {
            row: cols[0],
            spec: 'sm'
          }, {
            row: cols[1],
            spec: 'mm'
          }, {
            row: cols[2],
            spec: 'sa'
          }
        ];
      }
      lessons = (function() {
        var _i, _len, _results;
        _results = [];
        for (_i = 0, _len = all_lessons.length; _i < _len; _i++) {
          l = all_lessons[_i];
          if ($(l.row).text().trim().length > 0) {
            _results.push(l);
          }
        }
        return _results;
      })();
      if (lessons.length === 0) {
        return null;
      }
      result = {
        no: $(row).find("th").text().trim(),
        lessons: {}
      };
      _ref = (function() {
        var _j, _len, _results;
        _results = [];
        for (_j = 0, _len = lessons.length; _j < _len; _j++) {
          lesson = lessons[_j];
          _results.push(getLessonJson(lesson));
        }
        return _results;
      })();
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        json = _ref[_i];
        result.lessons[json.spec] = json.lesson;
      }
      return result;
    };
    getWeekdayJson = function(weekday) {
      var json, row, rows, _i, _len, _ref;
      rows = {};
      _ref = (function() {
        var _j, _len, _ref, _results;
        _ref = $("." + weekday);
        _results = [];
        for (_j = 0, _len = _ref.length; _j < _len; _j++) {
          row = _ref[_j];
          _results.push(getRowJson(row));
        }
        return _results;
      })();
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        json = _ref[_i];
        if (json != null) {
          rows[json.no] = json.lessons;
        }
      }
      return rows;
    };
    weekdays = {};
    _ref = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat'];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      wd = _ref[_i];
      weekdays[wd] = getWeekdayJson(wd);
    }
    return {
      weekdays: weekdays,
      year: $("#year")[0].value,
      semester: $("#sem")[0].value
    };
  };

}).call(this);
