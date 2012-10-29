function getTimeTableXml() {

    function getAttr(lesson, attr) {
        return lesson[0].find(attr).text().trim();
    }

    function xmlElement(contents, tag, attrs) {
        if (typeof attrs === undefined) {
            attrs = {};
        }
        var attrStrArray = _.map(attrs, function(val, key) {
                                            return [' ', key, '="', val, '"'].join('');
                                        });
        if (!contents) {
            return ['<', tag].concat(attrStrArray).concat([' />']).join('');
        } else {
            return ['<', tag].concat(attrStrArray).concat(['>', contents, '</', tag, '>']).join('');
        }
    }

    function getTwoHoursXml(lessons) {
        return _.map(lessons, function(lesson) {
            var loc = xmlElement(getAttr(lesson, '.loc'), 'location');
            var name = xmlElement(getAttr(lesson, '.name'), 'name');
            var prof = xmlElement(getAttr(lesson, '.prof'), 'prof');
            var course = xmlElement(name + '\n' + prof, 'course');
            
            var checkboxes = lesson[0].find("input[type=checkbox]");
            var fortnightly = 0;
            if ($(checkboxes[0]).attr('checked') == 'checked')
                fortnightly += 1;
            if ($(checkboxes[1]).attr('checked') == 'checked')
                fortnightly += 2;

            if (fortnightly != 3 && fortnightly != 0) {
                course += '\n' + xmlElement('', 'fortnightly', { type : fortnightly });
            }
            var xml = xmlElement(loc + '\n' + course, 'lesson', { spec : lesson[1] });
            return xml;
        }).join('\n');
    }

    var xml = xmlElement(
        _.map(['mon', 'tue', 'wed', 'thu', 'fri', 'sat'], function(weekday) {
            var rows = $("." + weekday);
            return xmlElement(_.filter(_.map(rows, function(r) {
                        var row = $(r);
                        var curIndex = row.find("th").text().trim();
                        var cols = _.map(row.find('td.lesson'), function(c) { return $(c); });
                        var lessons = undefined;
                        if (cols.length == 1) {
                            lessons = _.zip(cols, ["all"]);
                        } else {
                            lessons = _.zip(cols, ["sm", "mm", "sa"]);
                        }

                        lessons = _.filter(lessons, function(lesson) {
                                                        return $(lesson[0]).text().trim() != '';
                                                    });
                        if (lessons.length != 0) {
                            return xmlElement(getTwoHoursXml(lessons), 'two_hours', {no : curIndex});
                        } else {
                            return '';
                        }
                    }), function(str) { return str.length != 0; }).join('\n'),
                    'weekday', { id : weekday });
        }).join('\n'),
        'timetable',
        { year : $("#year")[0].value, semester : $("#sem")[0].value });

    return '<?xml version="1.0" encoding="UTF-8"?>\n'
           + '<?xml-stylesheet type="text/xsl" href="timetable.xsl"?>\n'
           + xml;
}
