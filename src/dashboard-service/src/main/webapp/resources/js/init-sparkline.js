var Script = function () {

    $("#bar-chart-1").sparkline([102,109,90,120,70,99,110,80,87,50,65,74], {
        type: 'bar',
        height: '32',
        barWidth: 5,
        barSpacing: 2,
        barColor: '#339ee6'
    });

    $("#bar-chart-2").sparkline([87,109,111,95,120,99,87,100,67,75,65,87], {
        type: 'bar',
        height: '32',
        barWidth: 5,
        barSpacing: 2,
        barColor: '#eac459'
    });



    $("#bar-chart-3").sparkline([102,109,120,99,110,80,87,74], {
        type: 'bar',
        height: '32',
        barWidth: 5,
        barSpacing: 2,
        barColor: '#79d4a7'
    });

    $("#line-chart-1").sparkline([5,6,7,9,9,5,3,2,2,4,6,7,5,6,7,9,9,5,3,2,2,4,6,7  ], {
        type: 'line',
        width: '100',
        height: '35',
        lineColor: '#a3d2e4',
        fillColor: '#f0fafc'
    });

    $("#line-chart-2").sparkline([1,5,3,2,2,4,6,7,5,6,7,9,9,5,3,2,2,4,6,2 ], {
        type: 'line',
        width: '150',
        height: '35',
        lineColor: '#a3d2e4',
        fillColor: '#f0fafc'});

}();
