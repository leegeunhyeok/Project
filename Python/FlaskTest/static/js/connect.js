$(function() {
    $.ajax({
        url: '/process/getLoginLog',
        type: 'POST',
        dataType: 'JSON',
        success: function(data){
            day(data.day);
            month(data.month);
        }, error: function(err){
            console.log(err.statusText);
        }
    });
});

function day(data) {
    var day = c3.generate({
        'bindto': '#day-data',
        'data': {
            'columns': [
                data
            ],
            'type': 'line'
        },
        'point': {
            'show': false
        },
        'axis': {
            'x': {
                'type': 'category',
                'categories': ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23']
            }
        }
    });
}

function month(data) {
    var day = c3.generate({
        'bindto': '#month-data',
        'data': {
            'columns': [
                data
            ],
            'type': 'bar'
        },
        axis: {
            y : {
                tick: {
                    format: function (d) { return d + '명'; }
                }
            }
        }
    });
}
