(function($) {

    'use strict';

    $(document).ready(function() {

        $("input[name='demo0']").TouchSpin();

        $("input[name='demo1']").TouchSpin({
            min: 0,
            max: 100,
            step: 0.1,
            decimals: 2,
            boostat: 5,
            maxboostedstep: 10,
            postfix: '%'
        });

        $("input[name='demo2']").TouchSpin({
            min: -1000000000,
            max: 1000000000,
            stepinterval: 50,
            maxboostedstep: 10000000,
            prefix: '$'
        });

        $("input[name='demo_vertical']").TouchSpin({
            verticalbuttons: true
        });

        $("input[name='demo_vertical2']").TouchSpin({
            verticalbuttons: true,
            verticalupclass: 'glyphicon glyphicon-plus',
            verticaldownclass: 'glyphicon glyphicon-minus'
        });

        $("input[name='demo4']").TouchSpin({
            postfix: "a button",
            postfix_extraclass: "btn btn-default"
        });

        $("input[name='demo4_2']").TouchSpin({
            postfix: "a button",
            postfix_extraclass: "btn btn-default"
        });
        
        $("input[name='demo5']").TouchSpin({
           prefix: "pre",
           postfix: "post"
       });

    });

})(window.jQuery);
