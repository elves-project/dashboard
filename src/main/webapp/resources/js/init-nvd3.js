
(function($) {

    'use strict';

    $(document).ready(function() {

        d3.json('http://megadin.lab.themebucket.net/assets/assets/js/init-nvd3-charts.json', function(data) {
            nv.addGraph(function() {
                var chart = nv.models.cumulativeLineChart()
                    .x(function(d) { return d[0] })
                    .y(function(d) { return d[1]/100 }) //adjusting, 100% is 1.00, not 100 as it is in the data
                    .color(d3.scale.category10().range())
                    .useInteractiveGuideline(true);

                chart.xAxis
                    .tickValues([1078030800000,1122782400000,1167541200000,1251691200000])
                    .tickFormat(function(d) {
                        return d3.time.format('%x')(new Date(d))
                    });

                chart.yAxis
                    .tickFormat(d3.format(',.1%'));

                d3.select('#nvd3-line svg')
                    .datum(data)
                    .call(chart);

                //TODO: Figure out a good way to do this automatically
                nv.utils.windowResize(chart.update);

                return chart;
            });
        });


        d3.json('http://nvd3.org/examples/stackedAreaData.json', function(data) {
            nv.addGraph(function() {
                var chart = nv.models.stackedAreaChart()
                    .margin({right: 100})
                    .x(function(d) { return d[0] })   //We can modify the data accessor functions...
                    .y(function(d) { return d[1] })   //...in case your data is formatted differently.
                    .useInteractiveGuideline(true)    //Tooltips which show all data points. Very nice!
                    .rightAlignYAxis(true)      //Let's move the y-axis to the right side.
                    .transitionDuration(500)
                    .showControls(true)       //Allow user to choose 'Stacked', 'Stream', 'Expanded' mode.
                    .clipEdge(true);

                //Format x-axis labels with custom function.
                chart.xAxis
                    .tickFormat(function(d) {
                        return d3.time.format('%x')(new Date(d))
                    });

                chart.yAxis
                    .tickFormat(d3.format('http://megadin.lab.themebucket.net/assets/js/,.2f'));

                d3.select('#nvd3-stacked svg')
                    .datum(data)
                    .call(chart);

                nv.utils.windowResize(chart.update);

                return chart;
            });
        })

    });

})(window.jQuery);
